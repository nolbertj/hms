<%--
    Document: prescriviEsame.jsp
    Created on: January 10, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/medicoBase/pages/operazioni/prescriviEsame" scope="page"/>

<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="warningMSG" value="<%=request.getAttribute(Attr.WARNING_MSG)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Prescrivi esame" includeDatatable="${true}" includeSelect2="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/prescriviEsame.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <SCRIPT type="text/javascript" src="${js}/utils.js"></SCRIPT>
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">

            <c:choose>
                <c:when test="${alertMSG eq true}">
                    <custom:alert typeAlert="error"
                                  message="Prescrizione NON effettuata!<br>Errore interno al server">
                    </custom:alert>
                </c:when>
                <c:when test="${successMSG eq true}">
                    <custom:alert typeAlert="success"
                                  message="Prescrizione effettuata con successo.">
                    </custom:alert>
                </c:when>
                <c:when test="${warningMSG eq true}">
                    <custom:alert typeAlert="warning"
                                  message="Prescrizione non effettuata. Errore del database.">
                    </custom:alert>
                </c:when>
            </c:choose>

            <form action="${cp}/${prescriviEsameURL}" method="POST" id="prescriviEsameForm">
                <label>Paziente</label>
                <custom:select2 id="suggestionPaziente" insideContainer="${false}"></custom:select2>

                <label>Esame</label>
                <custom:select2 id="suggestionEsame" insideContainer="${false}"></custom:select2>

                <div class="form-group">
                    <label for="annotazioni">Annotazioni</label>
                    <textarea class="form-control" id="annotazioni" name="annotazioni" rows="3"></textarea>
                </div>
            </form>

            <hr>
            <div class="text-center">
                <button type="submit" class="btn btn-primary btn-lg" form="prescriviEsameForm">Prescrivi esame</button>
            </div>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>