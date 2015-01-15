<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@page import="cn.edu.fudan.se.web.util.CommUtil"%>
<%@page import="cn.edu.fudan.se.data.util.ExtConstant"%>
<%@page import="cn.edu.fudan.se.web.service.GetDataService"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>StackOverflow Search- SE Lab of Fudan University</title>
<link rel="shortcut icon" href="favicon.ico" />
<link href="css/style.css" rel="stylesheet" type="text/css" />


</head>
<body >
	<div class="index">
	<div class="index_lg">
		<a title="Multi-Facet Feature Location"><img class="indexlogo" src="images/logo.png" />
		</a>
	</div>
	<div class="index_form">
		<form id="form1" name="form1" method="post" action="search">
			<div class="header_input">


				<input name="q" type="text" class="header_input_txt" id="textfield"
					value="${q }" size="30" maxlength="50" />
				<button id="search" class="search_btn" aria-label="Search"
					onmouseout="this.className='search_btn'"
					onmouseover="this.className='search_btn search_btn_hover'" />
				<span class="search_png">Search</span>
			</div>
		</form>
	</div>
	</div>
</body>
</html>