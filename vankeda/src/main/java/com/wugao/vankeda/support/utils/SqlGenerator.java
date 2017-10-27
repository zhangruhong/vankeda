package com.wugao.vankeda.support.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlGenerator<T> {
	
	public static String[] excludeType = {"java.util.List","java.util.Set","java.util.Map"};
	public static List<String> excludeList = new ArrayList<String>();
	static {
		excludeList.addAll(Arrays.asList(excludeType));
	}
	
	private String idSuffix;
	private String tableName;
	private Class<T> clz;
	private Map<String, String> conditionsMap = new HashMap<>();
	
	public SqlGenerator(String tableName,String idSuffix, Class<T> clz){
		this.tableName = tableName;
		this.idSuffix = idSuffix;
		this.clz = clz;
	}
	
	public String listGenerator() throws Exception{
		String result = "<select id=\"get" + idSuffix + "List\" resultType=\"" + clz.getName() + "\">\n\tSELECT * FROM "+ tableName +"\n";
		StringBuffer sb = new StringBuffer(result);
		if(conditionsMap.keySet().size() > 0){
			sb.append("\t<where>\n");
			Set<String> fieldList = new HashSet<String>();
			Map<String, String> fieldTypeMap = new HashMap<>();
			for(Field field : this.clz.getDeclaredFields()){
				fieldList.add(field.getName());
				fieldTypeMap.put(field.getName(), field.getType().getName());
			}
			for(String key : conditionsMap.keySet()){
				if(fieldList.contains(key)){
					sb.append("\t\t<if test=\"" + conditionsMap.get(key) + " != null " + (fieldTypeMap.get(key).indexOf("String") == -1 ? "" : "and " + conditionsMap.get(key) + " != ''") + ">\n");
					sb.append("\t\t\tand " + key + " = " + "#{" + conditionsMap.get(key) + "}\n");
					sb.append("\t\t</if>\n");
				}
			}
			sb.append("\t</where>\n");
		}
		sb.append("</select>");
		return sb.toString();
	} 
	
	public String insertGenerator() throws Exception{
		T t = clz.newInstance();
		Method[] methods = t.getClass().getMethods();
		StringBuffer sbColumn = new StringBuffer();
		StringBuffer sbValues = new StringBuffer();
		sbColumn.append("(");
		sbValues.append(" VALUES(");
		for(Method  m: methods){
			if(m.getName().startsWith("get") && (m.getName()!="getClass") && !excludeList.contains(m.getReturnType().getName())){
				sbColumn.append(m.getName().substring(3, 4).toLowerCase()+m.getName().substring(4)).append(", ");
				sbValues.append("#{").append(m.getName().substring(3, 4).toLowerCase()+m.getName().substring(4)).append("}, ");
			}
		}
		sbColumn.delete(sbColumn.lastIndexOf(","), sbColumn.lastIndexOf(",")+1).append(")").insert(0, "<insert id=\"save" + idSuffix + "\">\n\tINSERT INTO "+tableName);
		sbValues.delete(sbValues.lastIndexOf(","), sbValues.lastIndexOf(",")+1).append(")");
		
		return sbColumn.append("\n\t").append(sbValues).append("\n</insert>").toString();
	}
	
	public String updateGenerator() throws Exception{
		T t = clz.newInstance();
		Method[] methods = t.getClass().getMethods();
		StringBuffer sb = new StringBuffer();
		sb.append("<update id=\"update"+ idSuffix + "\">\n\t");
		sb.append("UPDATE ").append(tableName).append(" SET ");
		int i = 1;
		for(Method m : methods){
			if(m.getName().startsWith("get") && (m.getName()!="getClass")  && (m.getName()!="getId") && !excludeList.contains(m.getReturnType().getName())){
				if(i%5==0){
					sb.append("\n").append("\t");
				}
				sb.append(m.getName().substring(3, 4).toLowerCase()+m.getName().substring(4))
					.append(" = ").append("#{").append(m.getName().substring(3, 4).toLowerCase()+m.getName().substring(4)).append("}, ");
				i++;
			}
			
		}
		sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(",")+1);
		sb.append(" WHERE id = #{id}");
		sb.append("\n</update>");
		return sb.toString();
	}
	
	public String byIdGenerator(){
		return "<select id=\"get" + idSuffix + "ById\" resultType=\"" + clz.getName() + "\">\n\tSELECT * FROM "+ tableName +" WHERE ID = #{id}\n</select>";
	}
	
	public String deleteGenerator(){
		return "<delete id=\"remove" + idSuffix + "\">\n\tDELETE FROM "+ tableName +" WHERE ID = #{id}\n</delete>";
	}
	
	public void generateXmlFile() throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\"" + this.clz.getPackage().getName() + "." + this.clz.getSimpleName() + "Repo\">\n");
		sb.append(this.listGenerator()).append("\n");
		sb.append(this.byIdGenerator()).append("\n");
		sb.append(this.insertGenerator()).append("\n");
		sb.append(this.updateGenerator()).append("\n");
		sb.append(this.deleteGenerator()).append("\n");
		sb.append("</mapper>");
		System.out.println(sb.toString());
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(new File("E:/usr/xml/" + this.clz.getSimpleName() + "Mapper.xml")));
			pw.println(sb.toString());
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			pw.close();
		}
	}
	
	
	public Map<String, String> getConditionsMap() {
		return conditionsMap;
	}

	public void setConditionsMap(Map<String, String> conditionsMap) {
		this.conditionsMap = conditionsMap;
	}

}
