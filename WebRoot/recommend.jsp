<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*,cn.edu.fudan.se.web.pojo.*,cn.edu.fudan.se.web.bean.*"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Method Recommend</title>
<link rel="shortcut icon" href="favicon.ico">
<!-- Include required JS files -->
<script type="text/javascript" src="js/scripts/shCore.js"></script>

<!--
      		At least one brush, here we choose JS. You need to include a brush for every 
      		language you want to highlight
      	-->
<script type="text/javascript" src="js/scripts/shBrushJava.js"></script>

<!-- Include *at least* the core style and default theme -->
<link href="css/styles/shCore.css" rel="stylesheet" type="text/css" />
<link href="css/styles/shThemeDefault.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="css/style.css" type="text/css" />

</head>
<body>

	


	<div id="result" style="height: 500px;  overflow-y:auto;">

		<br />

		<ol>
			<c:forEach var="list" items="${map.data}" varStatus="status">
				<li class="g" id='${list.id}'>
					<div class="content">
						<h3 class="r">
							<input type="checkbox" /><a href="javascript:void(0)"
								class="name" onclick="showDetail('${list.id}');">${list.shortMethodName}</a>
						</h3>
						<div class="t">
							<cite class="package_position">${list.packageposition }</cite>
							<div class="d">

								<span class="simple_description"> <pre
										class="brush: java">
${list.reason}
      	</pre> </span>
							</div>
							<div class="s">
								<span class="tag"> <em>Degree: </em>${list.degree }</span>
							</div>
						</div>
					</div></li>
			</c:forEach>
		</ol>

	</div>

	<script type="text/javascript" src="js/script.js"></script>




</body>
</html>