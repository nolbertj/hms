<%@ page import="it.unitn.disi.wp.project.hms.persistence.entities.Paziente" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<c:set var="patient" value="<%=Paziente.class.getSimpleName()%>" scope="page"/>

<c:choose>
    <c:when test="${USER.getClass().getSimpleName() eq patient}">
        <c:set var="isPatient" value="${true}" scope="page"/>
        <c:set var="headerTitle" value="Documenti" scope="page" />
    </c:when>
    <c:otherwise>
        <c:set var="isPatient" value="${false}" scope="page"/>
        <c:set var="headerTitle" value="Scheda paziente" scope="page"/>
    </c:otherwise>
</c:choose>

<custom:header title="${headerTitle}" currentPageTitle="Referto ${referto.idEsame}">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <c:if test="${isPatient eq true}">
            <header:li title="Documenti" collapse="${true}" href="menuDocumenti"></header:li>
            <header:li title="Referti" href="${cp}/${refertiURL}"></header:li>
        </c:if>
    </jsp:body>
</custom:header>