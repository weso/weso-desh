<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/controlpanel.css" /> 
<title><s:text name="controlpanel.title"/></title>
</head>
<body>

<div id="header">

<h1><s:text name="controlpanel.title"/></h1>
<h2><s:text name="controlpanel.main.title"/></h2>

<ul>

    <li><s:a action="ControlPanelAction"><s:text name="controlpanel.home"/></s:a></li><!-- 
    -->
    <li id="selected"><s:a action="ConfigurationBaseAction"><s:text name="controlpanel.configurationBase"/></s:a></li><!-- 
    
    --><li><s:a action="UserAction"><s:text name="controlpanel.users"/></s:a></li><!--

    --><li><s:a action="NamespacesAction"><s:text name="controlpanel.namespaces"/></s:a></li><!--
    
    --><li><s:a action="MimeTypesAction"><s:text name="controlpanel.mimetypes"/></s:a></li><!--

    --><li><s:a action="RulesAction"><s:text name="controlpanel.rules"/></s:a></li><!--

    --><li><s:a action="ExitAction"><s:text name="controlpanel.exit"/></s:a></li>


</ul>



</div>



<div id="content">


</div>

</body>
</html>