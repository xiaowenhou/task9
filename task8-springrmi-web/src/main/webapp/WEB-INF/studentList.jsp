<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生列表</title>
</head>
<body>
	<table width="100%" border=1>
		<tr>
			<td align="center">学生列表：</td>
			<td align="center">
				<form id="itemForm"
					action="${pageContext.request.contextPath }/u/inputdata"
					method="get">
					<input type="submit" value="添加" />
				</form>
			</td>
		</tr>
	</table>
	<table width="100%" border=1>
		<tr>
			<td align="center">姓名</td>
			<td align="center">电话</td>
			<td align="center">QQ</td>
			<td align="center">邮箱</td>
			<td align="center">职业</td>
			<td align="center">入学时间</td>
			<td align="center">毕业学校</td>
			<td align="center">线上学号</td>
			<td align="center">日报链接</td>
			<td align="center">立愿</td>
			<td align="center">消息来源</td>
			<td align="center">师兄</td>
			<td align="center">头像链接</td>
		</tr>
		<c:forEach items="${studentList}" var="student">
			<tr>
				<td align="center">${student.name }</td>
				<td align="center">${student.phoneNumber }</td>
				<td align="center">${student.qqNumber }</td>
				<td align="center">${student.email }</td>
				<td align="center">${student.profession }</td>
				<td align="center">${student.joinDate }</td>
				<td align="center">${student.school }</td>
				<td align="center">${student.onlineNumber }</td>
				<td align="center">${student.dailyLink }</td>
				<td align="center">${student.desire }</td>
				<td align="center">${student.msgSource }</td>
				<td align="center">${student.brother }</td>
				<td align="center">${student.image }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>