<%@ page language="java" isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<style type="text/css">
	.bg-gray{background: #f5f5f5;}
	.loginColumns{max-width: 800px; margin: 0 auto; padding: 100px 20px 20px 20px;}
	.ibox-content{background-color: #ffffff;color: inherit;padding: 15px 20px 20px 20px;border-color: #e7eaec;border-image: none;border-style: solid solid none;border-width: 1px 0;box-shadow: 0.5px 0.5px 1px 1px rgba(0,0,0,0.25)}
</style>
<!-- jquery -->
<script src="<%=request.getContextPath()%>/js/vendor/jquery-3.1.1/jquery-3.1.1.min.js"></script>

<!-- bootstrap -->
<link href="<%=request.getContextPath()%>/js/vendor/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<!-- font-awesome -->
<link href="<%=request.getContextPath()%>/js/vendor/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" />

<script src="<%=request.getContextPath()%>/js/custom/jquery-form/jquery.form.js"></script>
<script src="<%=request.getContextPath()%>/js/custom/jquery-ajax/jquery.ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/custom/common.js"></script>
</head>
<body style="background: url(../image/login-bg.jpg) top left no-repeat;">
	<div class="loginColumns animated fadeInDown">
        <div class="row ${context.user != null ? 'hide' : '' }">
            <div class="col-md-6 ${firstLogin ? 'hide' :'' }">
                <div class="ibox-content">
                    <form id="loginForm" style="margin-top: 20px;" class="form-horizontal">
                    	<h3 class="text-center" style="color: #333;border-bottom: 1px solid #ccc;padding-bottom: 10px;">账号登录</h3>
                        <div class="form-group">
                        	<div class="col-xs-12">
                        		<input class="form-control" placeholder="用户名" name="username">
                        	</div>
                        </div>
                        <div class="form-group">
                        	<div class="col-xs-12">
                        		<input type="password" class="form-control" placeholder="密码" name="password">
                        	</div>
                        </div>
                        <button type="button" onclick="doLogin();" class="btn btn-primary btn-block" style="margin-bottom: 10px;">登录</button>

                        <a href="#" class="btn btn-sm btn-white btn-block">
                            <small>忘记密码?</small>
                        </a>

                        <p class="text-muted text-center">
                            <small>没有账号?</small>
                            <a class="btn btn-sm btn-white" href="<%=request.getContextPath()%>/register">注册账户</a>
                        </p>
                        
                    </form>
                </div>
            </div>
            <div class="col-md-12 ${firstLogin ? '' : 'hide' }">
                <div class="ibox-content">
                	<h3 class="font-bold text-center">欢迎登录万客达</h3>
                	<p class="text-center">
	                    	本次是您第一次登录，请完善您的个人信息
	                </p>
                    <form id="firstLoginForm" style="margin-top: 20px;" class="form-horizontal">
                        <div class="form-group">
                        	<label class="control-label col-xs-4">用户名</label>
                        	<div class="col-xs-7">
                        		<p class="form-control-static">${user.username }</p>
                        		<input type="hidden" name="username" value="${user.username }"/>
                        	</div>
                        </div>
                        <div class="form-group">
                        	<label class="control-label col-xs-4">用户昵称</label>
                        	<div class="col-xs-7">
                        		<input class="form-control" placeholder="用户昵称" name="nickname" value="${user.nickname }">
                        	</div>
                        </div>
                        <div class="form-group">
                        	<label class="control-label col-xs-4">邮箱</label>
                        	<div class="col-xs-7">
                        		<input class="form-control" placeholder="电子邮箱" name="email" value="${user.email }">
                        	</div>
                        </div>
                        <div class="form-group">
                        	<label class="control-label col-xs-4">密码</label>
                        	<div class="col-xs-7">
                        		<input type="password" class="form-control" placeholder="密码" name="password">
                        	</div>
                        </div>
                        <div class="form-group">
                        	<label class="control-label col-xs-4">密码确认</label>
                        	<div class="col-xs-7">
                        		<input type="password" class="form-control" placeholder="确认密码" name="confirmPassword">
                        	</div>
                        </div>
                        <button type="button" onclick="firstLogin();" class="btn btn-primary btn-block" style="margin-bottom: 10px;">提交</button>

                        <a href="#" class="btn btn-sm btn-white btn-block">
                            <small>忘记密码?</small>
                        </a>
                        <p class="text-muted text-center">
                            <small>没有账号?</small>
                            <a class="btn btn-sm btn-white" href="<%=request.getContextPath()%>/register">注册账户</a>
                        </p>
                        
                    </form>
                </div>
            </div>
        </div>
        <div class="row ${context.user != null ? '' : 'hide' }">
        	<div class="col-md-6">
                <div class="ibox-content">
                    <form id="loginForm" style="margin-top: 20px;" class="form-horizontal">
                    	<h3 class="text-center" style="color: #333;border-bottom: 1px solid #ccc;padding-bottom: 10px;">您好！${context.user.nickname }</h3>
                        <a href="<%=request.getContextPath() %>/logout" class="btn btn-primary btn-block" style="margin-bottom: 10px;">登出</a>

                        <a href="javascript: history.back();" class="btn btn-sm btn-white btn-block">
                            <small>返回上一页?</small>
                        </a>

                        <a class="btn btn-sm btn-white btn-block" href="<%=request.getContextPath()%>/">
                        	<small>回到首页?</small>
                        </a>
                        
                    </form>
                </div>
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col-md-6">
                Personal Copyright
            </div>
            <div class="col-md-6 text-right">
               <small>© 2016-2017</small>
            </div>
        </div>
    </div>
</body>
</html>