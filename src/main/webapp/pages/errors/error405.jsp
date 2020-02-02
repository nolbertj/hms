<%--
    Document: error405.jsp
    Created on: January 20, 2019
    Front-end: Alessandro Tomazzoli
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 405" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-exclamation-triangle"></i>
    </jsp:attribute>
    <jsp:attribute name="title">405 - METHOD NOT ALLOWED</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        La richiesta &egrave; stata eseguita usando un metodo non permesso.
        Ad esempio questo accade quando si usa il metodo GET per inviare dati da presentare con un metodo POST.
        Per maggiori informazioni su cosa può causare questo errore,
        <a href="https://httpstatuses.com/405">
            <strong>clicca qui</strong>.
        </a>
    </jsp:attribute>
</custom:statusPage>