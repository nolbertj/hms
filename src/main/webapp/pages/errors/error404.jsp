<%--
    Document: error404.jsp
    Created on: October 26, 2019
    Front-end: Alessandro Tomazzoli
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 404" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-exclamation-triangle"></i>
    </jsp:attribute>
    <jsp:attribute name="title">404 - NOT FOUND</jsp:attribute>
    <jsp:attribute name="message">
        Qualcosa è andato storto...<br>
        La pagina che stavi cercando non è stata trovata.
        Per maggiori informazioni su cosa può causare questo errore,
        <a href="https://it.wikipedia.org/wiki/Errore_404">
            <strong>clicca qui</strong>.
        </a>
    </jsp:attribute>
</custom:statusPage>