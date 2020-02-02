<%--
    Document: profilo.jsp
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/farmacia/pages/profilo" scope="page"/>

<c:set var="passwordModificaCorrettamente" value="<%=request.getAttribute(Attr.PSWD_MODIFICATA)%>" scope="request"/>
<c:set var="incorrectCurrentPassword" value="<%=request.getAttribute(Attr.INCORRECT_PWD)%>" scope="request"/>
<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Profilo" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/profilo.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <SCRIPT type="text/javascript" src="${js}/utils.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="${js}/psw.js"></SCRIPT>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ---------- ALERT MESSAGES ------- -->
            <c:if test="${not empty successMSG}">
                <div class="alert alert-success alert-dismissible mt-4 fade show" role="alert">
                    ${successMSG}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>

            <c:if test="${not empty passwordModificaCorrettamente}">
                <div class="alert
                <c:if test="${passwordModificaCorrettamente}">alert-success </c:if>
                <c:if test="${!passwordModificaCorrettamente}">alert-danger </c:if>
                alert-dismissible mt-4 fade show" role="alert">
                    <c:if test="${passwordModificaCorrettamente}">
                        La tua password è stata modificata <strong>correttamente</strong>.</c:if>
                    <c:if test="${!passwordModificaCorrettamente}">
                        <strong>Errore durante la modifica della password!</strong><br>
                        <c:choose>
                            <c:when test="${incorrectCurrentPassword}">
                                La password corrente immessa non è corretta!
                            </c:when>
                            <c:when test="${!incorrectCurrentPassword}">
                                I requisiti della password non sono stati rispettati!
                            </c:when>
                        </c:choose>
                    </c:if>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <!-- --------------------------------- -->
            <!-- DATI -->
            <%@ include file="cards/datiCard.jsp" %>
            <!-- --------------- -->
            <br>
            <!-- MODIFICA PASSWORD -->
            <custom:passwordCard isOpened="${false}"></custom:passwordCard>
            <!-- --------------------------------------- -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
