<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>Insert title here</title>
		<script type="text/javascript">
	function $(id) {
		return document.getElementById(id);
	}
	window.onload = function() {
		var selectAll = $("selectAll"), selectAdverse = $("selectAdverse"), inputs = document
				.getElementsByName('select'), len = inputs.length;
		// 设置selectAll复选框的事件
		selectAll.onclick = function() {
			var bool = this.checked;
			for ( var i = 0; i < len; i++) {
				inputs[i].checked = bool;
			}
		}
		selectAdverse.onclick = function() {
			for ( var i = 0; i < len; i++) {
				var o = inputs[i];
				o.checked ? o.checked = false : o.checked = true;
			}
			if(allChecked()==true){
				selectAll.checked=true;
			}else{
				selectAll.checked=false;
			}
		}
	}
	
	function resume() {
		//获取页面的第一个表单
		targetForm = document.forms[0];
		if(neverChecked() == true){
			alert("None checkbox has bean checked.");
		}else{
			//动态修改表单的action属性
			targetForm.action = "resumeEngines";
		}
	}
	function pause() {
		//获取页面的第一个表单
		targetForm = document.forms[0];
		if(neverChecked() == true){
			alert("None checkbox has bean checked.");
		}else{
			//动态修改表单的action属性
			targetForm.action = "pauseEngines";
		}
	}
	// 判断Checkbox是否没有选中
	function neverChecked() {
		var obj = document.getElementsByName("select");//根据自己的多选框名称修改下
		var b = true;
		for ( var i = 0; i < obj.length; i++) {
			if (obj[i].checked) {
				b = false;
			}
		}
		return b;
	}
	// 判断Checkbox是否全部选中
	function allChecked() {
		var obj = document.getElementsByName("select");//根据自己的多选框名称修改下
		var b = true;
		for ( var i = 0; i < obj.length; i++) {
			if (!obj[i].checked) {
				b = false;
			}
		}
		return b;
	}
</script>
	</head>
	<body>
		<center>
			<br>
			<h2>
				Manage Rule Engines
			</h2>
		</center>

		<form method="post">

			<table align="center"
				style="height: 100px; width: 650 overflow-y :         auto;">

				<tr>
					<td>
						<table align="center" border="1" cellspacing="0"
							bordercolor="DarkBlue">
							<tr bgcolor="#7CFC00">
								<td align="center" width="50">
									<center>
										☺
									</center>
								</td>
								<td width="200">
									<center>
										Name
									</center>
								</td>
								<td width="200">
									<center>
										ID
									</center>
								</td>
								<td width="200">
									<center>
										State
									</center>
								</td>
							</tr>
							<!-- 迭代输出引擎信息 -->
							<s:iterator value="list" status="index">
								<tr
									<s:if test="#index.odd">style="background-color:#bbbbbb"</s:if>>
									<td>
										<center>
											<input type="checkbox" name="select"
												value="<s:property value="id" />" />
										</center>
									</td>
									<td>
										<s:property value="name" />
									</td>
									<td>
										<s:property value="id" />
									</td>
									<td>
										<s:property value="state" />
									</td>
								</tr>
							</s:iterator>

						</table>
					</td>
				</tr>
				<!-- 使用一行来放置控制按钮等 -->
				<tr>
					<td>
						<table align="center" border="0" cellspacing="0">
							<tr>
								<td align="left" width="250">
									<input type="checkbox" id="selectAll" />
									select all&nbsp;
									<input type="checkbox" id="selectAdverse" />
									deselect
								</td>
								<td align="center" width="200">
									<input type="submit" value="delete checked engines" onclick="pause();" />
								</td>
								<td align="center" width="200">
									<input type="submit" style="display: none" value="resume checked engines" onclick="resume();" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</form>


	</body>
</html>