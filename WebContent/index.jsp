<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
	String jump = base +"RuleEngine/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script language="javascript" type="text/javascript">
	// 以下方式直接跳转
	window.location.href = "panel.jsp";
</script>
	</head>

	<body>
		This is my JSP page.
		base=<%=base%>

		path=<%=path%>
		<br>
	</body>
</html>
