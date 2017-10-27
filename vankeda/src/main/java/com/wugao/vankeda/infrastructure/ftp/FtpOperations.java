package com.wugao.vankeda.infrastructure.ftp;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.wugao.vankeda.infrastructure.filestore.FileInfo;


public interface FtpOperations {

	FileInfo getFileInfo(String fileName);

	List<FileInfo> getFileInfos(String directoryPath);

	void saveFile(String fileName, InputStream inputStream);

	void appendFile(String fileName, InputStream inputStream);

	void getFile(String fileName, OutputStream outputStream);

	void removeFile(String fileName);

	void removeDir(String path);

	void makeDir(String path);

}
