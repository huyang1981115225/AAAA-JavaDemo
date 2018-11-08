n<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
</head>
<body>
  <div>
    <p>用户列表</p>
  </div>
  <table border="1" cellpadding="5" cellspacing="0">
    <tr>
      <td width="50px">id</td>
      <td>用户名</td>
      <td>性&nbsp;别</td>
      <td>手机号</td>
      <td>操&nbsp;作</td>
    </tr>
    <c:forEach items="${users}" var="user">
      <tr>
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.sex}</td>
        <td>${user.phoneNum}</td>
        <td>
          <ct:display >
               <a href="userEdit.do?id=${user.id}">修改</a>
          </ct:display>
        </td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>