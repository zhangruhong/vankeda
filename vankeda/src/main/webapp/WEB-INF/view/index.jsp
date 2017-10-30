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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/application/index.js"></script>
</head>
<body>
	<div class="container-fluid p-n">
		<div class="banner-container">
			<div class="banner active" style="background-image: url(<%=request.getContextPath()%>/image/banner1.png);"><a href="javascript: void(0);"></a></div>
			<div class="banner" style="background-image: url(<%=request.getContextPath()%>/image/banner2.png);"><a href="javascript: void(0);"></a></div>
			<div class="banner" style="background-image: url(<%=request.getContextPath()%>/image/banner3.png);"><a href="javascript: void(0);"></a></div>
			<div class="banner" style="background-image: url(<%=request.getContextPath()%>/image/banner4.png);"><a href="javascript: void(0);"></a></div>
			<div class="banner-controller-container">
				<ul class="banner-controller">
					<li class="active"></li>
					<li></li>
					<li></li>
					<li></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container-fluid p-n">
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
	
	<div class="container-fluid p-n">
		<div id="goods-list" class="goods-list"></div>
	</div>
	
</body>