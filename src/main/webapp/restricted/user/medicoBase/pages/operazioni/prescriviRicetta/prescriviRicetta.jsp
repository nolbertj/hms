<%--
    Document: prescriviRicetta.jsp
    Created on: January 15, 2019
    Front-end: Alessandro Brighenti, Nolbert Juarez
    Back-end: Alessandro Brighenti, Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/medicoBase/pages/operazioni/prescriviRicetta" scope="page"/>

<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="warningMSG" value="<%=request.getAttribute(Attr.WARNING_MSG)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Prescrivi ricetta" includeDatatable="${true}" includeSelect2="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/prescriviRicetta.css">
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
                                  message="Prescrizione NON effettuata!<br>Contatta l'amministratore del database">
                    </custom:alert>
                </c:when>
                <c:when test="${successMSG eq true}">
                    <custom:alert typeAlert="success"
                                  message="Prescrizione effettuata con successo.">
                    </custom:alert>
                </c:when>
                <c:when test="${warningMSG eq true}">
                    <custom:alert typeAlert="warning"
                                  message="Prescrizione NON effettuata.<br>
                                  Probabilmente i parametri richiesti sono vuoti">
                    </custom:alert>
                </c:when>
            </c:choose>

            <form action="${cp}/${prescriviRicettaURL}" method="POST" id="prescriviRicettaForm">

                <label>Paziente</label>
                <custom:select2 id="suggestionPaziente" insideContainer="${false}"></custom:select2>

                <label>Farmaci</label>
                <div id="containerFarmaci">
                    <div class="divFarmaco">
                        <custom:select2 id="suggestionFarmaci" clazzSelectTag="boxFarmaci" clazz="mb-4" insideContainer="${false}"></custom:select2>
                        <div class="text-right">
                            <button class="btn btn-link text-danger removeBTN"
                                    style="text-shadow: none !important; display: none"
                                    type="button" onclick="removeFarmaco(this)">
                                Rimuovi
                            </button>
                        </div>
                    </div>
                </div>

                <button type="button" class="btn btn-primary" id="addFarmacoBTN" disabled>
                    <i class="fas fa-plus"></i>&nbsp;Aggiungi
                </button>

                <div class="form-group mt-5">
                    <label for="descrizione">Descrizione</label>
                    <textarea class="form-control" id="descrizione" name="descrizione" rows="3"></textarea>
                </div>
            </form>
            <hr>
            <div class="text-center">
                <button type="submit" class="btn btn-primary btn-lg" form="prescriviRicettaForm">
                    Prescrivi ricetta
                </button>
            </div>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>