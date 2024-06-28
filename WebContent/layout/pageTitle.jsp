<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String requestURI = request.getRequestURI();

String fileName = requestURI.substring(requestURI.lastIndexOf("/") + 1);

if (fileName.contains(".")) {
	fileName = fileName.substring(0, fileName.lastIndexOf("."));
}
if (fileName.equals("index") || fileName.isEmpty()) {
	fileName = "Homepage";
}

if (fileName.equals("uomo")) {
	fileName = "Uomo";
}

if (fileName.equals("donna")) {
	fileName = "Donna";
}

if (fileName.equals("errorPage")) {
	fileName = "Errore";
}
%>
<div class="page-title"><%=fileName%></div>

