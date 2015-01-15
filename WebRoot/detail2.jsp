<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*,cn.edu.fudan.se.web.pojo.*, com.stackoverflow.bean.*"
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
<title>Post Detail Demonstration</title>
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

	<div id="title">
	Post Detail Demonstration
	</div>
	<div id="options">
	 <a href="javascript:parentAccordion.pr(1)">Expand All</a> | <a
		href="javascript:parentAccordion.pr(-1)">Collapse All</a>
	</div>
	<%
	    Post post = (Post)(request.getAttribute("post"));
	%>
	<ul class="acc" id="acc">
		<li>
			<h3>Post Title</h3>
			<div class="acc-section">
				<div class="acc-content">
					<!-- You also need to add some content to highlight, but that is covered elsewhere. -->
					<%-- <pre class="brush: java">
                           <%=post.getPost_title()%>
                   	</pre>
 --%>
 					<%=post.getPost_title()%>
					<!-- Finally, to actually run the highlighter, you need to include this JS on your page -->
					<script type="text/javascript">
						SyntaxHighlighter.all();
					</script>
				</div>
			</div>
		</li>
		<li>
			<h3>Post Body</h3>
			<div class="acc-section">
				<div class="acc-content">
					
				<p id="postBody" >${post.post_body}</p>
				</div>
			</div>
		</li>
 		<li>
			<h3>Post Comment</h3>
			<div class="acc-section">
				<div class="acc-content">
 <%-- 					<c:forEach var="comment" items="${post.getCommentList()}"
						varStatus="status"><li class="method-item">${comment.getComment_text()}</li></c:forEach> --%>
 				</div>
			</div>
		</li>
		<li>
			<h3>Post Tag</h3>
			<div class="acc-section">
				<div class="acc-content">
					
 				<p id="postBody" >${post.post_tag}</p>
 				</div>
			</div>
		</li>
 		<li>
			<h3>Post Answers</h3>
			<div class="acc-section">
				<div class="acc-content">
					<ul class="method-list">
					
<%-- 					<c:forEach var="answer" items="${post.getAnswerList()}"
						varStatus="status"><li class="method-item">${answer.getPost_body()}</li></c:forEach>
	 --%>				
				</ul>
				</div>
			</div>
		</li> 
	</ul>

	<script type="text/javascript" src="js/script.js"></script>

	<script type="text/javascript">
		var parentAccordion = new TINY.accordion.slider("parentAccordion");
		parentAccordion.init("acc", "h3", 0, 0);

		/* var nestedAccordion=new TINY.accordion.slider("nestedAccordion");
		 nestedAccordion.init("nested","h3",1,-1,"acc-selected"); */
	</script>


</body>
</html>