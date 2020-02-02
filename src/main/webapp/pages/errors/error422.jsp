<%--
    Document: error422.jsp
    Created on: December 29, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 422" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-exclamation-triangle"></i>
    </jsp:attribute>
    <jsp:attribute name="title">422 UNPROCESSABLE ENTITY</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        Il server comprende il tipo di contenuto dell'entità richiesta
        e la sintassi della richiesta è corretta, ma non è in grado di
        processare le istruzioni contenute nella richiesta.
        Per maggiori informazioni su cosa può causare questo errore,
        <a href="https://httpstatuses.com/422">
            <strong>clicca qui</strong>.
        </a>
    </jsp:attribute>
</custom:statusPage>