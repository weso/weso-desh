<%@ page language="java" contentType="text/html; charset=UTF-8"
 import="es.uniovi.weso.core.impl.ApplicationManagerImpl"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="css/rdfa.css" /> 
<title>Weso DESH Administration</title>
</head>
<body>


<div class="entity">
<h1>Welcome to WESO DESH</h1> 

<s:form action="Login">
	<s:textfield key="username"  />
	<s:password key="password" />
	<s:submit key="submitLogin"/>
</s:form>


</div>
</body>
</html>