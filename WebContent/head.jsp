<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<!-- 添加Base标签，页内跳转是相对于base来的 -->
		<!--<base href="http://localhost:8080/RuleEngine/"> -->
		<title>Rule Engine Manager</title>


	</head>


	<body background="images/header.jpg">
		<!-- 网页加载时调用一次 以后就自动调用了-->
		<center>
			<h1>
				Rule Engine Manager
			</h1>
			<a href="start.jsp" target="mainFrame">Start Rule Engines</a>
			<a href="manage.action" target="mainFrame">Manage Rule Engines</a>
			<a href="model.jsp" target="mainFrame">Add XML File</a>
			<a href="connect.action"" target="mainFrame">Init DIA Tester</a>

		</center>

	</body>
</html>