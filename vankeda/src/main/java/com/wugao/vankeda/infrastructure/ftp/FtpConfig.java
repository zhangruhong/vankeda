package com.wugao.vankeda.infrastructure.ftp;

import org.apache.commons.net.ftp.FTPClientConfig;

public class FtpConfig {

	private String ip = "127.0.0.1";

	private Integer port = 21;

	private String username = "admin";

	private String password = "";

	private String controlEncoding = "utf-8";

	private String systemKey = FTPClientConfig.SYST_NT;

	private String serverLanguageCode = "zh";

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getControlEncoding() {
		return controlEncoding;
	}

	public void setControlEncoding(String controlEncoding) {
		this.controlEncoding = controlEncoding;
	}

	public String getSystemKey() {
		return systemKey;
	}

	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	public String getServerLanguageCode() {
		return serverLanguageCode;
	}

	public void setServerLanguageCode(String serverLanguageCode) {
		this.serverLanguageCode = serverLanguageCode;
	}

}
