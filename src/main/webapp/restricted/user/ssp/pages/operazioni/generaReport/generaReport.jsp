<%--
    Document: generaReport.jsp
    Created on: January 17, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.ssp.GeneraReportServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/ssp/pages/operazioni/generaReport" scope="page"/>

<c:set var="warningMSG" value="<%=request.getAttribute(Attr.WARNING_MSG)%>" scope="request"/>
<c:url var="GeneraReportURL" value="<%=GeneraReportServlet.getURL()%>" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Genera report" includeSelect2="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/generaReport.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <%--------------------------------------- ALERT MESSAGES ------------------------------------%>
            <c:choose>
                <c:when test="${warningMSG eq true}">
                    <custom:alert typeAlert="warning"
                                  message="Nessun dato disponibile per la data selezionata">
                    </custom:alert>
                </c:when>
            </c:choose>
            <%-------------------------------------------------------------------------------------------%>
            <form action="${cp}/${GeneraReportURL}" method="POST" id="generaReportForm">
                <div class="form-group flex-column">
                    <label for="data">Seleziona data</label>
                    <input type="date" class="form-control col-3" id="data" name="data">
                </div>
            </form>
            <hr>
            <div class="text-center">
                <button type="submit" class="btn btn-success btn-lg" form="generaReportForm">
                    <i class="fas fa-download">&nbsp;</i>
                    Genera e scarica report
                </button>
            </div>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>