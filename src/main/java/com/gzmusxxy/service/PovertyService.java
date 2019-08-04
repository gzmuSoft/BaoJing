package com.gzmusxxy.service;

import com.gzmusxxy.entity.XjhbInformation;
import com.gzmusxxy.entity.XjhbPerson;
import com.gzmusxxy.entity.XjhbProject;

import java.util.List;

public interface PovertyService {
    int insert(XjhbPerson record);

    List<XjhbProject> findXjhbProject();

    int saveInformation(XjhbInformation xjhbInformation);
}
