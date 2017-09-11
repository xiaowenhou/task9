<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>操作</title>
</head>
<body>
<table width="100%" border=1>
    <form id="itemForm"
          action="${pageContext.request.contextPath }/u/student" method="get">
        <table width="100%" border=1>
            <tr>
                <td>按姓名查询</td>
                <td><input type="text" name="name"/></td>
            </tr>
            <tr>
                <td colspan="5" align="center"><input type="submit" value="查询"/>
                </td>
            </tr>
        </table>
    </form>

    <c:set var="info" value="${info}"/>
    <c:out value="${info}"/>
    <a href="${pageContext.request.contextPath }/u/transfer">迁移数据</a>
</table>
</body>

</html>