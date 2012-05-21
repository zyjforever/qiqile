package com.zyj.qiqile.task;

import java.util.HashMap;

public class TaskResult {
	public enum ResultCode {
		SUCCESS, FAILED, CANCELLED, NETWORK_ERROR, AUTH_ERROR,UNKNOW_ERROR
	};
	
	public final static String RESULTCODE="resultCode";
	
	private ResultCode result;
	private int errorCode;

	private HashMap<String, Object> params = null;

	public TaskResult() {
		params = new HashMap<String, Object>();
	}

	public TaskResult(String key, Object value) {
		this();
		put(key, value);
	}

	public void put(String key, Object value) {
		params.put(key, value);
	}

	public Object get(String key) {
		return params.get(key);
	}

	public ResultCode getResult() {
		return result;
	}

	public void setResult(ResultCode result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}