<%@ page language="java" isELIgnored="false"
	contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>后台管理</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/application/admin/home.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="ibox">
			<div class="ibox-title">
				<h5>活动列表</h5>
				<div class="ibox-tools">
					<a href="<%=request.getContextPath()%>/v/admin/activity/new" class="btn btn-primary btn-sm">新增活动</a>
				</div>
			</div>
			<div class="ibox-content">
				<div class="row m-b-sm m-t-sm">
					<div class="col-xs-1">
						<button type="button" class="btn btn-sm btn-white"><i class="fa fa-refresh"></i> 刷新</button>
					</div>
					<div class="col-xs-11">
						<div class="input-group">
							<input class="input-sm form-control" name="name"/>
							<span class="input-group-btn">
								<button type="button" class="btn btn-primary btn-sm"><i class="fa fa-search"></i></button>
							</span>
						</div>
					</div>
				</div>
				<div class="project-list">
					<table class="table table-hover">
                        <tbody>
	                        <tr>
	                            <td class="project-status">
	                                <span class="label label-primary">进行中</span>
	                            </td>
	                            <td class="project-title">
	                                <a href="project_detail.html">双11大促销</a>
	                                <br>
	                                <small>开始时间：2017-05-16 00:00:00</small>
	                            </td>
	                            <td class="project-completion">
                                    <small>点击量: 321次</small>
                                    <div class="progress progress-mini">
                                        <div style="width: 48%;" class="progress-bar"></div>
                                    </div>
	                            </td>
	                            <td class="project-people">
	                                <a href=""><img alt="image" class="img-circle" src="img/a3.jpg"></a>
	                            </td>
	                            <td class="project-actions">
	                                <a href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i> Edit </a>
	                            </td>
	                        </tr>
                        </tbody>
                    </table>
				</div>
			</div>
		</div>
	</div>
</body>