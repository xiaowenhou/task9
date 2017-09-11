<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	欢迎您注册...
	<div float="left">
		<div>
			<form id="validateForm"
				action="${pageContext.request.contextPath}/registration"
				onsubmit="return checkUser();" method="post">
				<input type="hidden" name="user" value="${user}" />
				<div>
					<c:set var="info" value="${info }" />
					<c:out value="${info }" />
				</div>
				<div>
					用户名： <input id="username" type="text" name="username"
						value="${user.username }"  width="100"/>
				</div>
				<div>
					密码: <input id="password" type="password" name="password"
						value="${user.password }"  width="100"/>
				</div>
				<div>
					确认密码： <input id="repassword" type="password" name="repassword"
						value=""  width="100"/>
				</div>
				<div>
					邮箱： <input id="email" type="text" name="email" value="${user.email }"  width="100"/>
						<button id="validationEmail" type="button" onclick="sendMail(this);"
						title="邮箱验证">邮箱验证</button>
				</div>
				<div>
					手机号： <input id="phone" type="text" name="phoneNumber"
						value="${user.phoneNumber }" maxlength="11"  width="100"/>
				</div>
				<div>
					验证码: <input id="code" type="text" name="codeNumber" value="${codeNumber }"
						maxlength="6"  width="100"/>
					<button id="validationCode" type="button" onclick="sendCode(this);"
						title="获取验证码">获取验证码</button>
				</div>
				<div>
					<input type="submit" value="注册" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
	function sendCode(obj) {
		var phone = document.getElementById("phone");
		var value = phone.value.trim();
		if (value && value.length == 11) {
			$.ajax({
				cache : false,
				url : "${pageContext.request.contextPath}/SMS",
				data : {phoneNumber : value}
			});
			// 1分钟内禁止点击  
			for (var i = 1; i <= 60; i++) {
				// 1秒后显示  
				window.setTimeout("updateTime1(" + (60 - i) + ")", i * 1000);
			}
		} else {
			alert("请输入正确的手机号码");
			phone.focus();

		}
	}
	
	function sendMail(obj) {
		var email = document.getElementById("email");
		var value = email.value.trim();
		if (value) {
			$.ajax({
				cache : false,
				url : "${pageContext.request.contextPath}/email",
				data : {email : value}
			});
			// 1分钟内禁止点击  
			for (var i = 1; i <= 60; i++) {
				// 1秒后显示  
				window.setTimeout("updateTime2(" + (60 - i) + ")", i * 1000);
			}
		} else {
			alert("邮箱不能为空");
			phone.focus();

		}
	}

	function updateTime1(i) {
		// setTimeout传多个参数到function有点麻烦，只能重新获取对象  
		var obj = document.getElementById("validationCode");
		if (i > 0) {
			obj.innerHTML = "距离下次获取还有" + i + "秒";
			obj.disabled = true;
		} else {
			obj.innerHTML = "获取验证码";
			obj.disabled = false;
		}
	}
	
	function updateTime2(i) {
		// setTimeout传多个参数到function有点麻烦，只能重新获取对象  
		var obj = document.getElementById("validationEmail");
		if (i > 0) {
			obj.innerHTML = "距离下次获取还有" + i + "秒";
			obj.disabled = true;
		} else {
			obj.innerHTML = "获取验证码";
			obj.disabled = false;
		}
	}

	function checkUser() {
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var repassword = document.getElementById("repassword").value;
		var email = document.getElementById("email").value;
		var code = document.getElementById("code").value;

		if (username == "") {
			alert("用户名不能为空");
			return false;
		}
		if (password == "") {
			alert("密码不能为空");
			return false;
		}
		if (email == "") {
			alert("邮箱不能为空");
			return false;
		}
		if (code == "") {
			alert("验证码不能为空");
			return false;
		}
		if (password != repassword) {
			alert("两次密码不一致");
		} else {
			return true;
		}
	}
</script>
