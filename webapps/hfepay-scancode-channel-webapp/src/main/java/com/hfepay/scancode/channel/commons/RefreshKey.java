package com.hfepay.scancode.channel.commons;

public class RefreshKey {

	private String userServerUrl;

	private String userNumUrl;

	private String refreshApiKeyUrl;
	
	private String apiUrl;
	
	private String dataIsClose;
	
	public String getDataIsClose() {
		return dataIsClose;
	}

	public void setDataIsClose(String dataIsClose) {
		this.dataIsClose = dataIsClose;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getRefreshApiKeyUrl() {
		return refreshApiKeyUrl;
	}

	public void setRefreshApiKeyUrl(String refreshApiKeyUrl) {
		this.refreshApiKeyUrl = refreshApiKeyUrl;
	}
	
	public String getUserServerUrl() {
		return userServerUrl;
	}

	public void setUserServerUrl(String userServerUrl) {
		this.userServerUrl = userServerUrl;
	}

	public String getUserNumUrl() {
		return userNumUrl;
	}

	public void setUserNumUrl(String userNumUrl) {
		this.userNumUrl = userNumUrl;
	}
	
}
