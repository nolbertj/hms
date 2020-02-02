<%--
    Document: cercaPaziente.jsp
    Created on: December 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/medicoSpecialista/pages/operazioni/cercaPaziente" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Cerca paziente" includeDatatable="${true}" includeSelect2="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/cercaPaziente.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <%-- ============== RICERCA PAZIENTE  ================================================================= --%>
            <%@ include file="cards/cercaCard.jsp" %>
            <%-- ============== SCHEDA PAZIENTE =================================================================== --%>
            <%@ include file="cards/anagraficaCard.jsp" %>
            <%-- ============== STORIA VISITE DEL PAZIENTE ======================================================== --%>
            <%@ include file="cards/visiteCard.jsp" %>
            <%-- ================================================================================================== --%>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>