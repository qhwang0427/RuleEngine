<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Insert title here</title>
		<script type="text/javascript" language="javascript">
				$ = function(id) {
			return document.getElementById(id);
		}
		function loadDialog() {
			var _i = $("uploadfile");
			_i.click();
		}
		function chooseFile() {
			$("SXmlPath").value = (document.f1.uf.value);
		}
		window.onload = function() {
		}
</script>
	</head>
	<body>
		<center>
			<h2>
				Add XML File
			</h2>
			<form id="form1" action="sxml" name="f1" method="post">
				
				<input type="text" name="SXmlPath" id="SXmlPath" />
				<input type="button" value="Browse..." onclick=
	loadDialog();
/>
				<input type="file" name="uf" id="uploadfile" style="display: none"
					onchange="chooseFile()" />
					
				<input type="submit" value="Submit">
			</form>
			
			</center>
</html>