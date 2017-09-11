<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>输入数据</title>
</head>
<body>
	<form id="itemForm"
		action="${pageContext.request.contextPath }/u/student" method="post">
		<input type="hidden" name="student" value="${student}" /> 输入数据：
		<table width="100%" border=1>
			<tr>
				<td>姓名</td>
				<td><input type="text" name="name" value="${student.name }" /></td>
			</tr>
			<tr>
				<td>电话</td>
				<td><input type="text" name="phoneNumber" value="${student.phoneNumber }" /></td>
			</tr>
			<tr>
				<td>QQ</td>
				<td><input type="text" name="qqNumber" value="${student.qqNumber }" /></td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td><input type="text" name="email" value="${student.email }" /></td>
			</tr>
			<tr>
				<td>职业</td>
				<td><input type="text" name="profession" value="${student.profession }" /></td>
			</tr>
			<tr>
				<td>入学时间</td>
				<td><input type="text" name="joinDate" value="${student.joinDate }" /></td>
			</tr>
			<tr>
				<td>毕业学校</td>
				<td><input type="text" name="school" value="${student.school }" /></td>
			</tr>
			<tr>
				<td>线上学号</td>
				<td><input type="text" name="onlineNumber" value="${student.onlineNumber }" /></td>
			</tr>
			<tr>
				<td>日报链接</td>
				<td><input type="text" name="dailyLink" value="${student.dailyLink }" /></td>
			</tr>
			<tr>
				<td>立愿</td>
				<td><input type="text" name="desire" value="${student.desire }" /></td>
			</tr>
			<tr>
				<td>消息来源</td>
				<td><input type="text" name="msgSource" value="${student.msgSource }" /></td>
			</tr>
			<tr>
				<td>师兄</td>
				<td><input type="text" name="brother" value="${student.brother }" /></td>
			</tr>
			<tr>
				<td>头像链接</td>
				<td><input type="text" name="image" value="${student.image }" /></td>
			</tr>
			<tr>
				<td colspan="5" align="center"><input type="submit" value="提交" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>