<%--
    Document: timeout.jsp
    Created on: December 01, 2019
    Front-end: Alessandro Tomazzoli
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.authentication.LoginServlet" %>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="loginURL" value="<%=LoginServlet.getURL()%>" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Timeout">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-hourglass-end"></i>
    </jsp:attribute>
    <jsp:attribute name="title">408 - REQUEST TIMEOUT</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        Il tempo massimo della sessione attuale è scaduto.
        Effettua nuovamente il <strong><a href="${cp}/${loginURL}">login</a></strong> per continuare la navigazione.
    </jsp:attribute>
</custom:statusPage>
