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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/application/admin/activity.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="ibox">
			<div class="ibox-title">
				<h5>活动列表</h5>
				<div class="ibox-tools">
					<a href="javascript: showActivityModal();" class="btn btn-primary btn-sm">新增活动</a>
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
                        	<c:forEach items="${objs }" var="act">
                        		<tr>
		                            <td class="project-status">
		                                <span class="label ${act.status ? 'label-primary' : 'label-warning' }">${act.status ? '进行中' : '已失效' }</span>
		                            </td>
		                            <td class="project-title">
		                                <a href="javascript:;">${act.title }</a>
		                                <br>
		                                <span class="label ${act.onBanner ? 'label-primary' : 'label-default' }"></span>
		                            </td>
		                            <td class="project-completion">
	                                     <small>开始时间：<fmt:formatDate value="${act.startDate }" pattern="yyyy-MM-dd HH:mm:ss"/></small>
		                            </td>
		                            <td class="project-completion">
	                                     <small>结束时间：<fmt:formatDate value="${act.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/></small>
		                            </td>
		                            <td class="project-people">
		                                <a href=""><img alt="image" class="img-circle" src="<%=request.getContextPath()%>/admin/activity/image?id=${act.id}"></a>
		                  
		                            </td>
		                            <td class="project-actions">
		                                <a href="javascript: showActivityModal('${act.id }')" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i> Edit </a>
		                            </td>
		                        </tr>
                        	</c:forEach>
                        </tbody>
                    </table>
				</div>
			</div>
		</div>
	</div>
	
	<div id="activityModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" style="white-space: nowrap; text-overflow: ellipsis; color: #f50;">
						<i class="fa fa-ticket fa-lg"></i> 活动
					</h5>
				</div>
				<div class="modal-body">
					<form id="activityForm" class="form-horizontal">
						<input name="id" type="hidden"/>
						<div class="form-group">
							<label class="col-xs-2 control-label">标题</label>
							<div class="col-xs-10">
								<input name="title" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">开始时间</label>
							<div class="col-xs-10">
								<input name="startDate" class="form-control" type="day"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">结束时间</label>
							<div class="col-xs-10">
								<input name="endDate" class="form-control" type="day"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">主图</label>
							<div class="col-xs-10">
								<div id="file-upload" class="file-upload"></div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-2 control-label">状态</label>
							<div class="col-xs-10">
								<label class="checkbox-inline i-checks">
									<input type="checkbox" name="onBanner"/> 显示在banner
								</label>
								<label class="checkbox-inline i-checks">
									<input type="checkbox" name="status"/> 可用
								</label>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" type="button" onclick="submit();">提交</button>
					<button class="btn btn-default" type="button" onclick="cancel();">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	
</body>