<%--
    Document: profilo.jsp
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.paziente.ModificaProfiloServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.paziente.ModificaMedicoServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/paziente/pages/profilo" scope="page"/>

<c:url var="modificaMedicoURL" value="<%=ModificaMedicoServlet.getURL()%>" scope="session"/>
<c:url var="modificaProfiloURL" value="<%=ModificaProfiloServlet.getURL()%>" scope="session"/>
<c:set var="passwordModificaCorrettamente" value="<%=request.getAttribute(Attr.PSWD_MODIFICATA)%>" scope="request"/>
<c:set var="incorrectCurrentPassword" value="<%=request.getAttribute(Attr.INCORRECT_PWD)%>" scope="request"/>
<c:set var="uploadFotoStatus" value="<%=request.getAttribute(Attr.UPLOAD_STATUS)%>" scope="request"/>
<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="medicoCambiato" value="<%=request.getAttribute(Attr.MED_CAMBIATO)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Profilo" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/profilo.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <SCRIPT type="text/javascript" src="${js}/utils.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="${js}/psw.js"></SCRIPT>
        <%@ include file="script.jsp"%>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA ================== -->

            <!-- ---------- ALERT MESSAGES ------- -->
            <c:choose>
                <c:when test="${medicoCambiato eq true}">
                    <custom:alert typeAlert="success" message="Medico di base cambiato con successo!"></custom:alert>
                </c:when>
                <c:when test="${medicoCambiato eq false}">
                    <custom:alert typeAlert="error" message="Medico di base NON cambiato."></custom:alert>
                </c:when>
            </c:choose>

            <c:if test="${not empty successMSG}">
                <custom:alert typeAlert="success" message="${successMSG}"></custom:alert>
            </c:if>

            <c:choose>
                <c:when test="${uploadFotoStatus}">
                    <custom:alert typeAlert="success" message="La foto è stata caricata correttamente."></custom:alert>
                </c:when>
                <c:when test="${uploadFotoStatus eq false}">
                    <custom:alert typeAlert="error" message="Errore durante il caricamento della foto: Impossibile trovare la data nella foto"></custom:alert>
                </c:when>
            </c:choose>

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

            <!-- DATI ANAGRAFICI -->
            <%@ include file="cards/anagraficaCard.jsp"%>
            <!-- --------------- -->
            <br>
            <!-- MEDICO DI BASE -->
            <%@ include file="cards/medicoCard.jsp"%>
            <!-- -------------- -->
            <br>
            <!-- TIMELINE FOTO -->
            <%@ include file="cards/timelineFotoCard.jsp"%>
            <!-- ----------------- -->
            <br>
            <!-- MODIFICA PASSWORD -->
            <custom:passwordCard isOpened="${false}"></custom:passwordCard>
            <!-- --------------------------------------- -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
