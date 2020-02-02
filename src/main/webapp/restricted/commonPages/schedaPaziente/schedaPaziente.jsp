<%--
    Document: schedaPaziente.jsp
    Created on: January 18, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/commonPages/schedaPaziente" scope="page"/>
<jsp:useBean id="paziente" class="it.unitn.disi.wp.project.hms.persistence.entities.Paziente" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Cerca paziente" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/schedaPaziente.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <jsp:include page="/restricted/user/${userFoldername}/pages/documenti/schedaPaziente/script.jsp"/>
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <%-- ============== SCHEDA PAZIENTE =================================================================== --%>
            <%@ include file="cards/anagraficaCard.jsp" %>
            <%-- ============== STORIA VISITE DEL PAZIENTE ======================================================== --%>
            <%@ include file="cards/visiteCard.jsp" %>
            <%-- ================================================================================================== --%>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>