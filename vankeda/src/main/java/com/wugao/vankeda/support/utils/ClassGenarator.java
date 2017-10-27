package com.wugao.vankeda.support.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ClassGenarator {
	
	private static final String separator = ";";
	private static final String rtnMark = "\n";
	private String packageName;
	private String className;
	private String tableName;
	private String dburl;
	private String dbuser;
	private String dbpwd;
	
	private static Map<String, String> typeMapping = new HashMap<>();
	static{
		typeMapping.put("bigint", "Integer");
		typeMapping.put("datetime", "Date");
		typeMapping.put("decimal", "Double");
		typeMapping.put("int", "Integer");
		typeMapping.put("nvarchar", "String");
		typeMapping.put("varchar", "String");
		typeMapping.put("char", "String");
		typeMapping.put("smallint", "Integer");
		typeMapping.put("text", "String");
		typeMapping.put("tinyint", "Boolean");
	}
	
	
	public ClassGenarator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassGenarator(String packageName, String tableName, String className, String dburl, String dbuser, String dbpwd) {
		super();
		this.packageName = packageName;
		this.tableName = tableName;
		this.className = className;
		this.dburl = dburl;
		this.dbuser = dbuser;
		this.dbpwd = dbpwd;
	}
	
	private StringBuffer generateHeader(){
		StringBuffer result = new StringBuffer();
		result.append("package ").append(this.packageName).append(separator).append(rtnMark).append(rtnMark)
				.append("import javax.validation.constraints.NotNull").append(separator).append(rtnMark)
				.append("import org.hibernate.validator.constraints.NotBlank").append(separator).append(rtnMark)
				.append("import org.hibernate.validator.constraints.Length").append(separator).append(rtnMark)
				.append("import java.io.Serializable").append(separator).append(rtnMark).append(rtnMark)
				.append("public class ").append(className).append(" implements Serializable{").append(rtnMark)
				.append("\tprivate static final long serialVersionUID = 1L").append(separator).append(rtnMark);
		return result;
	}

	public String generate(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dburl, dbuser, dbpwd);
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
			StringBuffer result = generateHeader();
			while (colRet.next()) {
				String columnName = colRet.getString("COLUMN_NAME"); 
			    String columnType = colRet.getString("TYPE_NAME"); 
			    int datasize = colRet.getInt("COLUMN_SIZE"); 
			    int nullable = colRet.getInt("NULLABLE"); 
			    String remark = colRet.getString("REMARKS");
			    if(!"".equals(remark) && remark != null){
			    	result.append("//").append(remark).append(rtnMark);
			    }
			    if(nullable == 0){
			    	result.append("\t@NotNull").append(rtnMark);
			    }
			    if(columnType.indexOf("char") != -1){
			    	if(nullable == 0){
			    		result.append("\t@NotBlank").append(rtnMark);
			    	}
			    	result.append("\t@Length(max = ").append(datasize).append(")").append(rtnMark);
			    }
			    result.append("\tprivate ").append(typeMapping.get(columnType)).append(" ").append(columnName).append(separator).append(rtnMark);
			}
			result.append("}");
			return result.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error!";
		}
	}
	
	public static void main(String[] args) {
		ClassGenarator genarator = new ClassGenarator("vank" , "t_category", "Category" ,"jdbc:mysql://127.0.0.1:3306/vank?characterEncoding=utf8&useSSL=true&serverTimezone=UTC", "root", "123456");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File("/usr/java/" + genarator.getClassName() + ".java")));
			pw.println(genarator.generate());
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			pw.close();
		}
//		System.out.println(genarator.generateHeader());
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
