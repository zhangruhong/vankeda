<%@ page language="java" isELIgnored="false"
	contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>万客达超级券 - 百万张超级券等你来抢</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/application/tqg.js"></script>
</head>
<body>
	<nav class="navbar navbar-default navbar-orange">
		<!-- 标题 -->
		<div class="navbar-header">
			<a class="navbar-brand" href="javascript: history.back();" style="color: white;line-height: 30px;">
				<i class="fa fa-chevron-left"></i>&nbsp;返回
			</a>
			<form class="navbar-form navbar-right">
				<div class="form-group">
					<div class="input-group">
						<input name="name" type="text" class="form-control" placeholder="淘宝商品关键字">
						<span id="search" class="input-group-addon"><i class="fa fa-search"></i></span>
					</div>
				</div>
			</form>
		</div>
	</nav>
	
	<div class="container-fluid">
		<div class="menu-container">
			<div class="row">
				<div class="col-xs-3 menu-item" data-href="<%=request.getContextPath() %>/">
					<div class="menu-image"></div>
					<p class="menu-name">首页</p>
				</div>
				<div class="col-xs-3 menu-item" data-href="<%=request.getContextPath() %>/v/ticket">
					<div class="menu-image"></div>
					<p class="menu-title">超级券</p>
				</div>
				<div class="col-xs-3 menu-item" data-href="<%=request.getContextPath() %>/v/tenyuan">
					<div class="menu-image"></div>
					<p class="menu-title">十元购</p>
				</div>
				<div class="col-xs-3 menu-item" data-href="<%=request.getContextPath() %>/v/tqg">
					<div class="menu-image"></div>
					<p class="menu-title">淘抢购</p>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container-fluid">
		<div class="hr-line-dashed" style="border-color: #ccc;"></div>
	</div>
	<div class="container-fluid">
		<div id="goods-list" class="goods-list"></div>
	</div>
	
</body>