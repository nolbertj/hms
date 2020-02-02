<%--
    Document: prescriviRichiamo.jsp
    Created on: January 18, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/ssp/pages/richiami/prescriviRichiamo" scope="page"/>

<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="warningMSG" value="<%=request.getAttribute(Attr.WARNING_MSG)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Prescrivi richiamo" includeSelect2="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/prescriviRichiamo.css">
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
            <%--------------------------------------- ALERT MESSAGES ------------------------------------%>
            <c:choose>
                <c:when test="${alertMSG eq true}">
                    <custom:alert typeAlert="error"
                                  message="Impossibile prescrivere il richiamo impostato!">
                    </custom:alert>
                </c:when>
                <c:when test="${successMSG eq true}">
                    <custom:alert typeAlert="success"
                                  message="Richiamo generato con successo">
                    </custom:alert>
                </c:when>
                <c:when test="${warningMSG eq true}">
                    <custom:alert typeAlert="warning"
                                  message="Eta selezionate non corrette.">
                    </custom:alert>
                </c:when>
            </c:choose>
            <%-------------------------------------------------------------------------------------------%>
            <form action="${cp}/${doRichiamoURL}" method="POST" id="prescriviRichiamoForm">
                <div class="form-row">
                    <div class="col-2">
                        <label for="etaInizio"> Età iniziale </label>
                        <input type="number" class="form-control col" id="etaInizio" name="etaInizio" min="0" max="105">
                    </div>
                    <div class="col-2">
                        <label for="etaFine">Età finale </label>
                        <input type="number" class="form-control col" id="etaFine" name="etaFine" min="0" max="105">
                    </div>
                </div>
                <br>
                <label>Esame da prescrivere</label>
                <custom:select2 id="esame" insideContainer="${false}"></custom:select2>
            </form>
            <hr>
            <div class="text-center">
                <button type="submit" class="btn btn-primary btn-lg" form="prescriviRichiamoForm">
                    Genera richiamo
                </button>
            </div>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>