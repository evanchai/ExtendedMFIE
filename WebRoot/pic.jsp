<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<base href="<%=basePath%>">
<style type="text/css">
</style>
</head>
<div>
	<form action="search" method="post">
		<input type="hidden" name="action" value="savePics" />
		<table height="400px" width="500px">

			<tr>
				<c:forEach var="list" items="${pics}" varStatus="status">
					<td><img src="${list.url }" width="100" height="100" /><input
						type="checkbox" name="pic" value="${list.id }" />
					</td>

				</c:forEach>
			</tr>
			<tr>
				<td><input type="submit" name="close" value="确定" /> <input
					type="button" name="close" class="jqmClose" value="关闭" />
				</td>
			</tr>

		</table>
	</form>
</div>