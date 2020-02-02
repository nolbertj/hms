<%--
    Document: error403.jsp
    Created on: December 01, 2019
    Front-end: Alessandro Tomazzoli
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 403" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-exclamation-triangle"></i>
    </jsp:attribute>
    <jsp:attribute name="title">403 - FORBIDDEN</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        Non hai i permessi necessari per accedere al contenuto di questa pagina.
        Per maggiori informazioni su cosa può causare questo errore,
        <a href="https://it.wikipedia.org/wiki/Codici_di_stato_HTTP#4xx_Client_Error">
            <strong>clicca qui</strong>.
        </a>
    </jsp:attribute>
</custom:statusPage>