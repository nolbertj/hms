<%--
    Document: logout.jsp
    Created on: October 2, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:statusPage pageTitle="Logout">
    <jsp:attribute name="imageOrIcon">
        <img src="${pageContext.servletContext.contextPath}/assets/img/logo.svg" alt="LOGO">
    </jsp:attribute>
    <jsp:attribute name="title">LOGOUT</jsp:attribute>
    <jsp:attribute name="message">
        La navigazione nell'area privata è terminata.<br>
        È necessario chiudere subito tutte le finestre del browser,
        per garantire la protezione dei tuoi dati personali, specie se lavori da un computer pubblico o condiviso.
        <script>window.sessionStorage.removeItem("sidebarClosed")</script>
    </jsp:attribute>
</custom:statusPage>