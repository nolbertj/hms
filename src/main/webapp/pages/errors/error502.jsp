<%--
    Document: error502.jsp
    Created on: December 01, 2019
    Front-end: Alessandro Tomazzoli
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 502" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-exclamation-triangle"></i>
    </jsp:attribute>
    <jsp:attribute name="title">502 - BAD GATEWAY</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        Il server non è riuscito ad inoltrare la richiesta alla destinazione.
        Per maggiori informazioni su cosa può causare questo errore,
        <a href="https://it.wikipedia.org/wiki/Codici_di_stato_HTTP#5xx_Server_Error">
            <strong>clicca qui</strong>.
        </a>
    </jsp:attribute>
</custom:statusPage>
