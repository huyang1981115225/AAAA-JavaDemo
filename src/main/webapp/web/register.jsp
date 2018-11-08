<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仔仔网</title>
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
		$('#register').click(function() {
			$.ajax({
				"url" : "${pageContext.request.contextPath}/user/register.do",
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
      <p>新用户注册</p>
    </div><br>
    <div>
      <label for="name">用户名：</label> 
      <input type="text" name="name" placeholder="请输入用户名" id="name">
    </div><br>
    <div>
      <label for="password">密&nbsp;码：</label> 
      <input type="password" name="password" placeholder="请输入密码" id="password">
    </div><br>
    <div>
      <label for="sex">性&nbsp;别：</label>
      <select name="sex" id="sex">
        <option>请选择</option>
        <option>男</option>
        <option>女</option>
      </select>
    </div><br>
    <div>
      <label for="phoneNum">手机号：</label>
      <input type="text" name="phoneNum" id="phoneNum" placeholder="请输入手机号">
    </div><br>
    <div>
      <input type="button" value="注册" id="register">
    </div>
    <div id="msg"></div>
  </form>
</body>
</html>