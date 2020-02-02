<%--
    Document: listaPagamenti.jsp
    Created on: December 27, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.PDFServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/paziente/pages/pagamenti/listaPagamenti" scope="page"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="errorMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>

<c:url var="PDFServletURL" value="<%=PDFServlet.getURL()%>" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Lista pagamenti" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/listaPagamenti.css">
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
            <%----------------- ALERT MESSAGES -----------------------------------------------------------------------%>
            <c:choose>
                <c:when test="${successMSG eq true}">
                    <custom:alert typeAlert="success" message="Pagamento effettuato correttamente"></custom:alert>
                </c:when>
                <c:when test="${errorMSG eq true}">
                    <custom:alert typeAlert="error" message="Impossibile effettuare il pagamento"></custom:alert>
                </c:when>
            </c:choose>
            <%--------------------------------------------------------------------------------------------------------%>
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <custom:card title="Lista pagamenti">
                <jsp:body>
                    <form action="${cp}/${PDFServletURL}" method="POST" id="PDFGen"></form>
                    <custom:table datatablePopulate="${true}" exportBTN="${true}" idExportForm="PDFGen"></custom:table>
                </jsp:body>
            </custom:card>
            <!-- ============== FINESTRE MODALI ==================================================================== -->
            <%@ include file="modals/payModal.jsp" %>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>