<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body style="font-size:30px;">
	<form action="${pageContext.request.contextPath}/user/uploadExcel.do" method="post" 
		enctype="multipart/form-data">
		<input type="file" id="excelFile" name="excelFile">
		<br><input type="submit" value="测试Excel导入">
	</form>
</body>
</html>