<%--
    Document: sidebar.jsp
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.farmacia.ErogaRicettaServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sidebar" tagdir="/WEB-INF/tags/template/sidebar" %>

<c:url var="dashboardURL" value="<%=DashboardServlet.getURL()%>" scope="session"/>
<c:url var="erogaRicettaURL" value="<%=ErogaRicettaServlet.getURL()%>" scope="session"/>

<custom:sidebar
        userIMGpath="${img}/restricted/${userFoldername}/${USER.avatarFilename}"
        userRole="Farmacia"
        userData="${USER.nome}"
>
    <jsp:attribute name="menu">
        <sidebar:menu title="Home"
                      icon="fa fa-home"
                      hasSubmenu="${false}"
                      href="${cp}/${dashboardURL}">
        </sidebar:menu>
        <sidebar:menu title="Eroga ricetta"
                      icon="fas fa-file-medical"
                      hasSubmenu="${false}"
                      href="${cp}/${erogaRicettaURL}">
        </sidebar:menu>
    </jsp:attribute>
    <jsp:attribute name="extraMenu">
        <sidebar:menu title="Impostazioni"
                      icon="fas fa-cog"
                      hasSubmenu="${false}"
                      href="${cp}/${impostazioniURL}">
        </sidebar:menu>
    </jsp:attribute>
</custom:sidebar>