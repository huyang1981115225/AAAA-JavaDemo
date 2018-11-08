<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body style="font-size: 30px;">
  <form action="${pageContext.request.contextPath}/user/uploadImg3.do"
    method="post" enctype="multipart/form-data">
    <input type="file" id="file" name="files" multiple="multiple"> <br>
    <input type="submit" value="测试上传图片">
  </form>
</body>
</html>