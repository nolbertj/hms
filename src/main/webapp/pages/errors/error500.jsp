<%--
    Document: error500.jsp
    Created on: October 26, 2019
    Front-end: Alessandro Tomazzoli
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 500" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-exclamation-triangle"></i>
    </jsp:attribute>
    <jsp:attribute name="title">500 - INTERNAL SERVER ERROR</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        Errore interno al server.
        Per maggiori informazioni su cosa può causare questo errore,
        <a href="https://it.wikipedia.org/wiki/Codici_di_stato_HTTP#5xx_Server_Error">
            <strong>clicca qui</strong>.
        </a>
    </jsp:attribute>
</custom:statusPage>