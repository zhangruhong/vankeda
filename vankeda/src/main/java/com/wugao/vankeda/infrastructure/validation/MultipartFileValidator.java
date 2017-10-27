package com.wugao.vankeda.infrastructure.validation;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import com.wugao.vankeda.infrastructure.filestore.FileException;


public class MultipartFileValidator {

	private final static long MAX_SIZE = 15*1024*1024;
	
    /** 
     * 文件大小上限 
     */  
    private long maxSize = MAX_SIZE;
    
    /** 
     * 可接受的文件content-type 
     */  
    private String[] allowedContentTypes; 
    
/*    @PostConstruct
    public void afterPropertiesSet() {  
        Assert.notEmpty(allowedContentTypes,  
                        "The content types allowed to be uploaded must contain one at least!");  
    }*/
	
    /** 
     * 验证上传文件是否合法，如果不合法那么会抛出异�? 
     *  
     * @param file 
     *            用户上传的文件封装类 
     */  
    public void validate(MultipartFile file) {  
        if(file == null){
        	throw new FileException("文件不能为空");
        }
        if (allowedContentTypes!=null && allowedContentTypes.length>0 && !ArrayUtils.contains(allowedContentTypes, file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).toLowerCase()))  
        	throw new FileException("您上传的文件类型"+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1)+"不符合要求，可上传的文件类型�?"+ArrayUtils.toString(allowedContentTypes));  
        if (file.getSize() > maxSize)  
            throw new FileException("您的文件大小�?"+(file.getSize()/1024/1024)+"M超过可上传的�?大�??"+maxSize/1024/1024+"M");  
    }
    
    /** 
     * 设置文件上传大小上限 
     *  
     * @param maxSize 
     *            文件上传大小上限 
     */  
    public void setMaxSize(long maxSize) {  
        this.maxSize = maxSize;  
    }  
  
    /** 
     * 设置可接受的文件content-type数组 
     *  
     * @param allowedContentTypes 
     *            可接受的文件content-type数组 
     */  
    public void setAllowedContentTypes(String[] allowedContentTypes) {  
        this.allowedContentTypes = allowedContentTypes;  
    } 
}
