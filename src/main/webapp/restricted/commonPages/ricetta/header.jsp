<%@ page import="it.unitn.disi.wp.project.hms.persistence.entities.Paziente" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<c:set var="patient" value="<%=Paziente.class.getSimpleName()%>" scope="page"/>

<c:choose>
    <c:when test="${USER.getClass().getSimpleName() eq patient}">
        <custom:header title="Prescrizioni" currentPageTitle="Ricetta farmaceutica ${ricetta.codice}">
            <jsp:body>
                <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
                <header:li title="Prescrizioni" collapse="${true}" href="menuPrescrizioni"></header:li>
                <header:li title="Ricette farmaceutiche" href="${cp}/${ricetteFarmURL}"></header:li>
            </jsp:body>
        </custom:header>
    </c:when>
    <c:otherwise>
        <custom:header title="Scheda paziente">
            <jsp:body>
                <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
            </jsp:body>
        </custom:header>
    </c:otherwise>
</c:choose>