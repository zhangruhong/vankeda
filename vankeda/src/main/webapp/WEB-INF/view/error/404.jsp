<%@ page language="java" isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" errorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - 404 页面</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link href="<%=request.getContextPath()%>/js/vendor/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" />
	<script src="<%=request.getContextPath()%>/js/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>
	
	<!-- font-awesome -->
	<link href="<%=request.getContextPath()%>/js/vendor/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" />
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/animation.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />

</head>

<body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">页面未找到！</h3>

        <div class="error-desc">
            抱歉，页面好像去火星了~
        </div>
    </div>


</body>

</html>