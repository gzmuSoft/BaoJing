package com.gzmusxxy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzmusxxy.entity.ZfApply;
import com.gzmusxxy.entity.ZfPhoto;
import com.gzmusxxy.mapper.ZfApplyMapper;
import com.gzmusxxy.mapper.ZfPhotoMapper;
import com.gzmusxxy.service.ZfApplyService;
import com.gzmusxxy.util.FileUtil;
import com.gzmusxxy.util.PageUtil;
import com.gzmusxxy.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ZfApplyServiceImpl implements ZfApplyService {

    @Autowired
    private ZfApplyMapper zfApplyMapper;

    @Autowired
    private ZfPhotoMapper zfPhotoMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return zfApplyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ZfApply record) {
        return zfApplyMapper.insert(record);
    }

    @Override
    public ZfApply selectByPrimaryKey(Integer id) {
        return zfApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ZfApply> selectAll() {
        return zfApplyMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ZfApply record) {
        return zfApplyMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<ZfApply> selectByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<ZfApply> zfApplies = zfApplyMapper.selectByNameLike(name);
        return new PageInfo<>(zfApplies);
    }

    @Override
    public PageInfo<ZfApply> selectCompleteByNameLike(String name, Integer pageNumber) {
        if (name != null && !name.equals("")){
            name = "%" + name + "%";
        }else {
            name = "%%";
        }
        //PageHelper插件的分页信息
        PageHelper.startPage(pageNumber, PageUtil.PAGE_ROW_COUNT);
        //查询数据
        List<ZfApply> zfApplies = zfApplyMapper.selectCompleteByNameLike(name);
        for (int i = 0; i < zfApplies.size(); i++) {
            ZfApply zfApply = zfApplies.get(i);
            ZfPhoto zfPhoto = zfPhotoMapper.selectByApplyId(zfApply.getId());
            List<String> paths = new ArrayList<>();
            List<String> names = new ArrayList<>();
            if (zfPhoto.getPhotoPathFront() != null) {
                String path = zfPhoto.getPhotoPathFront();
                paths.add(path);
                names.add("施工前照片"+path.substring(path.lastIndexOf(".")));
            }
            if (zfPhoto.getPhotoPathCenter() != null) {
                String path = zfPhoto.getPhotoPathCenter();
                paths.add(path);
                names.add("施工中照片"+path.substring(path.lastIndexOf(".")));
            }
            if (zfPhoto.getPhotoPathAfter() != null) {
                String path = zfPhoto.getPhotoPathAfter();
                paths.add(path);
                names.add("施工后照片"+path.substring(path.lastIndexOf(".")));
            }
            zfApply.setPhotosPath(zipPhoto(names, paths));
            zfApplies.set(i,zfApply);
        }
        return new PageInfo<>(zfApplies);
    }

    //压缩照片
    private String zipPhoto(List<String> names, List<String> paths){
        String zipPath = FileUtil.FILE_PATH + UUID.randomUUID() + ".zip";
        for (int i = 0; i < paths.size(); i++) {
            if (!FileUtil.existFile(paths.get(i))){
                paths.remove(i);
                names.remove(i);
            }
        }
        ZipUtil.toZip(paths,names,zipPath);
        return zipPath;
    }

    @Override
    public List<ZfApply> selectByStatus(Integer status) {
        return zfApplyMapper.selectByStatus(status);
    }
}
