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
<script type="text/javascript" src="<%=request.getContextPath()%>/js/application/search.js"></script>
</head>
<body>
	<div class="container-fluid p-n">
		<form id="custom-form" class="form-horizontal">
			<ul class="select-bar">
				<li>
					<a href="javascript: orderSelect('', '');">全部</a>
				</li>
				<li>
					<a href="javascript: orderSelect('soldCountPerMonth', 'desc');">销量</a>
				</li>
				<li>
					<a href="javascript: orderSelect('priceAfterTicket/originalPrice', 'desc');">折扣</a>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">筛选</a>
					<ul class="dropdown-menu">
						<li>
							<div class="category-item">
								<div class="category-item-button" onclick="categorySelect(true);">
									<span>全部</span>
								</div>
							</div>
						</li>
						<li class="divider"></li>
						<c:forEach items="${topCategories }" var="c">
							<li>
								<div class="category-item">
									<div class="category-item-check">
										<input type="checkbox" name="categoryPid" value="${c.id}"/>
									</div>
									<div class="category-item-name">
										<span>${c.name }</span>
									</div>
								</div>
							</li>
						</c:forEach>
						<li class="divider"></li>
						<li>
							<div class="category-item">
								<div class="category-item-button" onclick="categorySelect(true);">
									<span>确定</span>
								</div>
							</div>
						</li>
					</ul>
				</li>
			</ul>
		</form>
		<div class=""></div>
	</div>
	
	<div class="container-fluid p-n">
		<div id="goods-list" class="goods-list"></div>
	</div>
	
</body>