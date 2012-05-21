package com.zyj.qiqile.task;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 
 * @author zyj
 * 
 */
public class TaskParams {

	private HashMap<String, Object> params = null;

	public TaskParams() {
		params = new HashMap<String, Object>();
	}

	public TaskParams(String key, Object value) {
		this();
		put(key, value);
	}

	public void put(String key, Object value) {
		params.put(key, value);
	}

	public Object get(String key) {
		return params.get(key);
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		if (this != null && this.params.size() > 0) {
			for (Entry entry : this.params.entrySet()) {
				result.append(entry.getKey() + "=" + entry.getValue());
			}
		}
		return result.toString();
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	
	
}
