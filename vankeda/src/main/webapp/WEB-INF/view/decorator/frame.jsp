<%@ page language="java" isELIgnored="false"
	contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<link href="<%=request.getContextPath()%>/image/favicon.ico"
	rel="shortcut icon" />
<title><sitemesh:write property='title' /></title>
<script type="text/javascript">
	contextPath = '<%=request.getContextPath()%>';
	deviceType = '${custom-device-type}';
</script>
<!-- jquery -->
<!-- 引入 jQuery 库 -->
<script src="<%=request.getContextPath()%>/js/vendor/jquery-3.1.1/jquery-3.1.1.min.js"></script>

<!-- bootstrap -->
<link href="<%=request.getContextPath()%>/js/vendor/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<!-- font-awesome -->
<link href="<%=request.getContextPath()%>/js/vendor/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" />

<!-- zeroClipboard -->
<script src="<%=request.getContextPath()%>/js/vendor/clipboard/clipboard.js"></script>

<!-- 自定义的库 -->
<script src="<%=request.getContextPath()%>/js/custom/jquery-form/jquery.form.js"></script>
<script src="<%=request.getContextPath()%>/js/custom/jquery-ajax/jquery.ajax.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style-custom.css" />

<script src="<%=request.getContextPath()%>/js/custom/common.js"></script>
<script type="text/javascript">
	$(function(){
		$('.menu-item').each(function(){
			var href = $(this).data('href');
			$(this).on('click', function(){
				window.open(href, '_self');
			});
		});
	});
</script>
<sitemesh:write property='head' />
</head>
<body>
	<div class="page-wrapper" class="gray-bg" style="min-height: 375px;">
		<div class="row border-bottom">
	         <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
	             <div class="navbar-header">
	                 <a class="navbar-brand" href="#">
						 <img alt="万客达" src="<%=request.getContextPath()%>/image/logo-white.png" width="120" height="36">
					 </a>
	                 <form class="navbar-form-custom">
						 <div class="form-group">
							 <div class="input-group">
								 <input name="name" type="text" class="form-control" placeholder="淘宝商品关键字" value="${param.name }">
								 <span id="search" class="input-group-addon"><i class="fa fa-search"></i></span>
							 </div>
						 </div>
					 </form>
	             </div>
	             <ul class="nav navbar-top-links navbar-right">
	                 <li class="dropdown">
	                     <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
	                         <i class="fa fa-envelope"></i>  <span class="label label-warning">16</span>
	                     </a>
	                     <ul class="dropdown-menu dropdown-messages">
	                         <li>
	                             <div class="dropdown-messages-box">
	                                 <a href="profile.html" class="pull-left">
	                                     <img alt="image" class="img-circle" src="img/a7.jpg">
	                                 </a>
	                                 <div class="media-body">
	                                     <small class="pull-right">46h ago</small>
	                                     <strong>Mike Loreipsum</strong> started following <strong>Monica Smith</strong>. <br>
	                                     <small class="text-muted">3 days ago at 7:58 pm - 10.06.2014</small>
	                                 </div>
	                             </div>
	                         </li>
	                         <li class="divider"></li>
	                         <li>
	                             <div class="text-center link-block">
	                                 <a href="mailbox.html">
	                                     <i class="fa fa-envelope"></i> <strong>Read All Messages</strong>
	                                 </a>
	                             </div>
	                         </li>
	                     </ul>
	                 </li>
	                 <li class="dropdown">
	                     <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
	                         <i class="fa fa-bell"></i>  <span class="label label-primary">8</span>
	                     </a>
	                     <ul class="dropdown-menu dropdown-alerts">
	                         <li>
	                             <a href="mailbox.html">
	                                 <div>
	                                     <i class="fa fa-envelope fa-fw"></i> You have 16 messages
	                                     <span class="pull-right text-muted small">4 minutes ago</span>
	                                 </div>
	                             </a>
	                         </li>
	                         <li class="divider"></li>
	                         <li>
	                             <div class="text-center link-block">
	                                 <a href="notifications.html">
	                                     <strong>See All Alerts</strong>
	                                     <i class="fa fa-angle-right"></i>
	                                 </a>
	                             </div>
	                         </li>
	                     </ul>
	                 </li>
	             </ul>
	         </nav>
	     </div>
	     <div class="wrapper wrapper-content animated fadeInRight p-n">
			<sitemesh:write property='body' />
			<div id="loading" class="sk-spinner sk-spinner-wave">
                <div class="sk-rect1"></div>
                <div class="sk-rect2"></div>
                <div class="sk-rect3"></div>
                <div class="sk-rect4"></div>
                <div class="sk-rect5"></div>
            </div>
		</div>
     </div>
	<div id="tokenModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h5 class="modal-title"
						style="white-space: nowrap; text-overflow: ellipsis; color: #f50;">
						<i class="fa fa-ticket fa-lg"></i> 优惠券
					</h5>
				</div>
				<div class="modal-body">
					<div class="row"
						style="border-bottom: 1px dashed #ccc; padding: 15px 0;">
						<div class="col-xs-6">
							<div id="goods-image"
								style="height: 180px; background-position: center; background-size: cover; background-repeat: no-repeat;"></div>
						</div>
						<div class="col-xs-6" style="padding: 15px;">
							<p id="token-name"></p>
							<p>
								【在售价】<span id="price"></span>
							</p>
							<p>
								【优惠券】<span id="ticket"></span>
							</p>
						</div>
					</div>
					<input id="copyContent" class="form-control" style="margin-top: 20px;" value="123"/>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" type="button" id="copyBtn" data-clipboard-target="#copyContent">复制</button>
				</div>
			</div>
		</div>
	</div>
	<div class="toolbar-fixed-r-b">
		<div id="scrollToTop" class="toolbar-tool show">
			<i class="fa fa-chevron-up"></i>
		</div>
		<div id="callQQ" class="toolbar-tool show">
			<i class="fa fa-qq"></i>
		</div>
	</div>
</body>
</html>