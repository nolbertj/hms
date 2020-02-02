<%--
    Document: compilaReferto.jsp
    Created on: January 22, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/ssp/pages/operazioni/compilaReferto" scope="page"/>

<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Compila referto" includeSelect2="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/compilaReferto.css">
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
                                  message="Impossibile generare il referto. Contatta l'amministratore di sistema!">
                    </custom:alert>
                </c:when>
                <c:when test="${successMSG eq true}">
                    <custom:alert typeAlert="success"
                                  message="Referto generato con successo.">
                    </custom:alert>
                </c:when>
            </c:choose>
            <%-------------------------------------------------------------------------------------------%>
            <form action="${cp}/${compilaRefertoURL}" method="POST" id="compilaRefertoForm">
                <!---------------------------------------------------------------------------------------->
                <label>Paziente</label>
                <custom:select2 id="suggestionPaziente" insideContainer="${false}"></custom:select2>
                <!---------------------------------------------------------------------------------------->
                <label>Esame</label>
                <custom:select2 id="suggestionEsame" insideContainer="${false}"></custom:select2>
                <!---------------------------------------------------------------------------------------->
                <div class="form-row justify-content-between">
                    <div class="form-group col-lg-auto flex-column">
                        <label for="dataPrescrizione">Data prescrizione</label>
                        <input type="text" class="form-control" id="dataPrescrizione" readonly>
                    </div>
                    <div class="form-group col-lg-6 flex-column">
                        <label for="prescrivente">Prescrivente</label>
                        <input type="text" class="form-control" id="prescrivente" readonly>
                    </div>
                    <div class="form-group col-lg-auto flex-column">
                        <label for="codPrescrizione">Cod. prescrizione</label>
                        <input class="form-control" id="codPrescrizione" readonly>
                    </div>
                </div>
                <!---------------------------------------------------------------------------------------->
                <div class="form-group flex-column">
                    <label for="anamnesi">Anamnesi</label>
                    <textarea class="form-control" id="anamnesi" name="anamnesi" rows="3"></textarea>
                </div>
                <!---------------------------------------------------------------------------------------->
                <div class="form-group flex-column">
                    <label for="conclusioni">Conclusioni</label>
                    <textarea class="form-control" id="conclusioni" name="conclusioni" rows="3"></textarea>
                </div>
                <!---------------------------------------------------------------------------------------->
                <hr>
                <div class="custom-control custom-checkbox pl-4">
                    <input class="custom-control-input" type="checkbox" id="includiPagamento" name="includiPagamento">
                    <label for="includiPagamento" class="custom-control-label" style="cursor: pointer">
                        Includi pagamento
                    </label>
                </div>
            </form>
            <hr>
            <div class="text-center">
                <button type="submit" class="btn btn-primary btn-lg" form="compilaRefertoForm">Genera referto</button>
            </div>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
