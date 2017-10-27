package com.wugao.vankeda.infrastructure.ftp;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.wugao.vankeda.infrastructure.filestore.FileInfo;


public class FtpTemplate implements FtpOperations {

	private FtpConfig config;
	
	public void setConfig(FtpConfig config) {
		this.config = config;
	}

	<T> T execute(ConnectionCallback connectionCallback) {
		Connection connection = null;
		try {
			connection = new ConnectionImpl(config);
			return connectionCallback.doInConnection(connection);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	@Override
	public FileInfo getFileInfo(final String fileName) {
		return execute(new ConnectionCallback() {
			@Override
			public FileInfo doInConnection(Connection connection) {
				return connection.getFileInfo(fileName);
			}
		});
	}

	@Override
	public List<FileInfo> getFileInfos(final String directoryPath) {
		return execute(new ConnectionCallback() {
			@Override
			public List<FileInfo> doInConnection(Connection connection) {
				return connection.getFileInfos(directoryPath);
			}
		});
	}

	@Override
	public void saveFile(final String fileName, final InputStream inputStream) {
		execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection connection) {
				connection.uploadFile(fileName, inputStream);
				return null;
			}
		});
	}

	@Override
	public void appendFile(final String fileName, final InputStream inputStream) {
		execute(new ConnectionCallback() {
			@Override
			public <T> T doInConnection(Connection connection) {
				connection.appendFile(fileName, inputStream);
				return null;
			}
		});
	}

	@Override
	public void getFile(final String fileName, final OutputStream outputStream) {
		execute(new ConnectionCallback() {
			@Override
			public <T> T doInConnection(Connection connection) {
				connection.downloadFile(fileName, outputStream);
				return null;
			}
		});
	}

	@Override
	public void removeFile(final String fileName) {
		execute(new ConnectionCallback() {
			@Override
			public <T> T doInConnection(Connection connection) {
				connection.deleteFile(fileName);
				return null;
			}
		});
	}

	@Override
	public void removeDir(final String path) {
		execute(new ConnectionCallback() {
			@Override
			public <T> T doInConnection(Connection connection) {
				connection.deleteDir(path);
				return null;
			}
		});
	}

	@Override
	public void makeDir(final String path) {
		execute(new ConnectionCallback() {
			@Override
			public <T> T doInConnection(Connection connection) {
				connection.makeDir(path);
				return null;
			}
		});
	}
	
	
}
