package com.wugao.vankeda.application.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wugao.vankeda.infrastructure.filestore.FileInfo;
import com.wugao.vankeda.infrastructure.utils.ServletUtil;

@RestController
@RequestMapping(value = "common")
public class CommonController {

	@RequestMapping(value = "fileupload", method = RequestMethod.POST)
	public void fileupload(MultipartHttpServletRequest request, HttpServletResponse resp) throws Exception{
		MultipartFile file = request.getFile("upload-file");
		File f = new File(File.separator + "usr" + File.separator + "local" + File.separator + "file" + File.separator + System.currentTimeMillis() + "_"+ file.getOriginalFilename());
		if(!f.exists()) {
			f.mkdirs();
		}
		file.transferTo(f);
		Map<String, String> fileInfo = new HashMap<>();
		fileInfo.put("fileName", f.getName());
		fileInfo.put("filePath", f.getPath());
		ServletUtil.respondObjectAsJson(resp, fileInfo);
	}
	
	@RequestMapping(value = "removefile", method = RequestMethod.POST)
	public void removeFile(String filePath) {
		File file = new File(filePath);
		if(file.exists() && file.isFile()) {
			file.renameTo(new File(file.getPath() + ".bak"));
			file.delete();
		}
	}
	
	@RequestMapping(value = "filedownload", method = RequestMethod.GET)
	public void filedownload(String filePath, HttpServletResponse resp) throws Exception {
		File file = new File(filePath);
		if(file.exists()) {
			byte[] b = new byte[Long.valueOf(file.length()).intValue()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(b);
			FileInfo fileInfo = new FileInfo(file.getName(), b.length , new Date());
			ServletUtil.respondFileInfo(resp, fileInfo);
			ServletUtil.respondBytes(resp, b); 
		}
	}
}
