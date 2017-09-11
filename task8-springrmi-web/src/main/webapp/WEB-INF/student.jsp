<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询结果列表</title>
</head>
<body>
	查询结果列表:
	<table width="100%" border=1>
	<div>
						<c:set var="info" value="${info }" />
						<c:out value="${info }" />
					</div>
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
				
				<td align="center">
				<form action="${pageContext.request.contextPath }/u/editdata"
					methodParam="requestMethod" method="post">
					 <input id="id" type="hidden" name="id" value="${student.id }"/>
					 <input type="submit" value="更新" />
				</form>
			    </td>
				
				<td align="center">
				<form action="${pageContext.request.contextPath }/u/student"
					methodParam="requestMethod" method="post">
					<input type="hidden" name="requestMethod" value="delete" />
					<input type="hidden" name="id" value="${student.id }">
					<input type="submit" value="删除" />
				</form>
			    </td>
			    <td>
			    <form id="uploadForm" enctype="multipart/form-data">
					<input id="file" type="file" name="file"/>
					<button id="upload" type="button" onclick="doUpload()">上传</button>
				</form>
				</td>
				<td>
					<a href="${student.image}">查看头像图片</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>

<script type="text/javascript">
	function doUpload(){
		var formData = new FormData(document.getElementById("uploadForm"));

		var id = document.getElementById("id");
		var value = id.value.trim();
		formData.append('id', value);//可以在已有表单数据的基础上，继续添加新的键值对
		console.log(formData);
		$.ajax({
		    url: '${pageContext.request.contextPath}/u/upload',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false,
		    success: function (returndata) {  
	              alert(returndata);  
	          },  
	          error: function (returndata) {  
	              alert(returndata);  
	          } 
		});
	}
</script>
