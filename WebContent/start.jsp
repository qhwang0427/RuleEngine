<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Insert title here</title>
		<script type="text/javascript" language="javascript">
  function browseFolder(path) {
 
  
   try {
        var Message = "\请\选\择\文\件\夹";  //选择框提示信息
        var Shell = new ActiveXObject("Shell.Application");
        var Folder = Shell.BrowseForFolder(0, Message, 64, 17);//起始目录为：我的电脑
  //var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
        if (Folder != null) {
             Folder = Folder.items();  // 返回 FolderItems 对象
             Folder = Folder.item();  // 返回 Folderitem 对象
             Folder = Folder.Path;   // 返回路径
          if (Folder.charAt(Folder.length - 1) != "\\") {
                 Folder = Folder + "\\";
             }
             document.getElementById("rulesUrl").value = Folder;
          
            return Folder;
         }
     }
    catch (e) {
         alert(e.message);
     }
     }
      </script>
	</head>
	<body>
		<center>
			<h2>
				Start Rule Engines
			</h2>
			<form action="start.action" method="post">
				<input type="text" id="rulesUrl" name="rulesUrl" />
				<input type="button" value="locate xml folder" onclick="browseFolder('path')"/>
				<input type="submit" value="start engines" />
			</form>
		</center>

	</body>
</html>