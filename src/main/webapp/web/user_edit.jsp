<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仔仔网</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
		$('#update').click(function() {
			$.ajax({
				"url" : "${pageContext.request.contextPath}/user/updateUser.do?id=${user.id}",
				"type" : "POST",
				"data" : $("form").serialize(),
				"datatype" : "json",
				"success" : function(obj) {
					$("#msg").text(obj.message);
				}
			});
		})
	}
</script>
</head>
<body>
  <form action="" method="post">
    <div>
      <p>修改用户</p>
    </div><br>
    <div>
      <label for="name">用户名：</label> 
      <input type="text" name="name" placeholder="请输入用户名" id="name" value="${user.name}">
    </div><br>
    <div>
      <label for="password">密&nbsp;码：</label> 
      <input type="password" name="password" placeholder="请输入密码" id="password" value="${user.password}">
    </div><br>
    <div>
      <label for="sex">性&nbsp;别：</label>
      <select name="sex" id="sex" >
        <option  <c:if test="${user.sex == '男'}">selected="selected"</c:if>>男</option>
        <option  <c:if test="${user.sex == '女'}">selected="selected" </c:if>>女</option>
      </select>
    </div><br>
    <div>
      <label for="phoneNum">手机号：</label>
      <input type="text" name="phoneNum" id="phoneNum" placeholder="请输入手机号" value="${user.phoneNum}">
    </div><br>
    <div>
      <input type="button" value="修改" id="update">
    </div>
    <div id="msg"></div>
  </form>
</body>
</html>