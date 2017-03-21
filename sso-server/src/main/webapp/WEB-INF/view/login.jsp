<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>认证中心-登录</title>
</head>
<body>
<form action="login" method="post">
<input type="hidden" name=origUrl value="<%=request.getParameter("origUrl")%>">
用户名：<input type="text" name="account" />
密 码：<input type="password" name="passwd"/>
<input type="submit" value="登  录">
</form>

</body>
</html>