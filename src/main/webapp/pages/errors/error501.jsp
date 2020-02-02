<%--
    Document: error501.jsp
    Created on: January 22, 2020
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 501" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-exclamation-triangle"></i>
    </jsp:attribute>
    <jsp:attribute name="title">501 - NOT IMPLEMENTED</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        Il server non è in grado di soddisfare il metodo della richiesta.
        <a href="https://httpstatuses.com/501">
            <strong>clicca qui</strong>.
        </a>
    </jsp:attribute>
</custom:statusPage>
