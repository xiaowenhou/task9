<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改學生信息</title>

</head>
<body> 
	<form id="itemForm" action="${pageContext.request.contextPath }/u/student" 
	methodParam="requestMethod" method="post">
		<input type="hidden" name="requestMethod" value="put"/>
		<input type="hidden" name="student" value="${student}" /> 输入数据：
		<table width="100%" border=1>
			<input type="hidden" name="id" value="${student.id }"/>
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
			<input type="hidden" name="createAt" value="${student.createAt }"/>
			<tr>
				<td colspan="5" align="center"><input type="submit" value="提交" />
				</td>
			</tr>
		</table>
	</form>
</body>

</html>