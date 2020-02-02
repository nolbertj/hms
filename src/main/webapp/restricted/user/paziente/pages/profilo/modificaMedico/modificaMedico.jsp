<%--
    Document: modificaMedico.jsp
    Created on: Dicembre 24, 2019
    Front-end: Alessandro Brighenti
    Back-end: Alessandro Brighenti
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/paziente/pages/profilo/modificaMedico" scope="page"/>

<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="medicoModificatoCorrettamente" value="<%=request.getAttribute(Attr.MED_CAMBIATO)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Modifica medico" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/modificaMedico.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA =================================================================== -->

            <!-- ---------- ALERT MESSAGES ------- -->
            <c:if test="${not empty successMSG}">
                <div class="alert alert-success alert-dismissible mt-4 fade show" role="alert">
                        ${successMSG}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>

            <c:if test="${not empty medicoModificatoCorrettamente}">
                <div class="alert
                <c:if test="${medicoModificatoCorrettamente}">alert-success </c:if>
                <c:if test="${!medicoModificatoCorrettamente}">alert-danger </c:if>
                alert-dismissible mt-4 fade show" role="alert">
                    <c:if test="${medicoModificatoCorrettamente}">
                        Il medico di base <strong>è stato cambiato</strong>.</c:if>
                    <c:if test="${!medicoModificatoCorrettamente}">
                        <strong>Errore durante la richiesta di cambiato medico di base!</strong><br>
                    </c:if>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <!-- --------------------------------- -->
            <custom:card title="Medici disponibili">
                <jsp:body>
                    <form action="${cp}/${modificaMedicoURL}" method="POST" id="editMedic">
                        <custom:table datatablePopulate="${true}"></custom:table>
                    </form>
                </jsp:body>
            </custom:card>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
