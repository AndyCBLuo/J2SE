package com.iwebcoding.mobile.tool.model;

import java.util.List;

public class VerifyBean {
	private List<ZipVryBean> zipVryList;
	private List<JsonVryBean> jsonVryList;
	
	/**
	 * @return the zipVryList
	 */
	public List<ZipVryBean> getZipVryList() {
		return zipVryList;
	}
	
	/**
	 * @param zipVryList the zipVryList to set
	 */
	public void setZipVryList(List<ZipVryBean> zipVryList) {
		this.zipVryList = zipVryList;
	}
	
	/**
	 * @return the jsonVryList
	 */
	public List<JsonVryBean> getJsonVryList() {
		return jsonVryList;
	}
	
	/**
	 * @param jsonVryList the jsonVryList to set
	 */
	public void setJsonVryList(List<JsonVryBean> jsonVryList) {
		this.jsonVryList = jsonVryList;
	}
}
