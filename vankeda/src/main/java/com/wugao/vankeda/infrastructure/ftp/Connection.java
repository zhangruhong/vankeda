package com.wugao.vankeda.infrastructure.ftp;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.wugao.vankeda.infrastructure.filestore.FileInfo;

public interface Connection {

	FileInfo getFileInfo(String fileName);

	List<FileInfo> getFileInfos(String directoryPath);

	void uploadFile(String fileName, InputStream inputStream);

	void appendFile(String fileName, InputStream inputStream);

	void downloadFile(String fileName, OutputStream outputStream);

	void deleteFile(String fileName);

	void deleteDir(String path);

	void makeDir(String path);

	void close();

}
