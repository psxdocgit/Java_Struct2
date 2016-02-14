<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body> 
	<s:form action="/link/Link">
	    <s:textfield key="username"/>
	    <s:hidden name="link" value="UserLogin" />
	    <s:submit/>
	</s:form>
</body>
</html>