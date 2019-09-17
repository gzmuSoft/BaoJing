package com.gzmusxxy.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类
 * @author xt
 *
 * 2017年10月29日
 */
public class Msg {
	private int code;
	private String message;
	private Map<String,Object> extend = new HashMap();
	
	/**
	 * 返回成功
	 * @return
	 */
	public static Msg success(){
		Msg msg = new Msg();
		msg.setCode(200);
		msg.setMessage("处理完成");
		return msg;
	}
	
	/**
	 * 返回失败
	 * @return
	 */
	public static Msg failed(){
		Msg msg = new Msg();
		msg.setCode(100);
		msg.setMessage("处理完成");
		return msg;
	}
	
	public Msg add(String key,Object value){
		this.getExtend().put(key, value);
		return this;
	}
	
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getExtend() {
		return extend;
	}
	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}

}
