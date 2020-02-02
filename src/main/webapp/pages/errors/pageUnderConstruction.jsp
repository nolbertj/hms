<%--
    Document: pageUnderConstruction.jsp
    Created on: October 26, 2019
    Front-end: Alessandro Tomazzoli
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true" %>

<custom:statusPage pageTitle="Error 503" errorPage="true">
    <jsp:attribute name="imageOrIcon">
        <i class="fas fa-tools"></i>
    </jsp:attribute>
    <jsp:attribute name="title">503 - SERVICE TEMPORARILY UNAVAILABLE</jsp:attribute>
    <jsp:attribute name="message">
        Stiamo ancora lavorando su questa pagina, sarà disponibile a breve.
    </jsp:attribute>
</custom:statusPage>