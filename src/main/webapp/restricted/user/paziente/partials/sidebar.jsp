<%--
    Document: sidebar.jsp
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.paziente.RicetteServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.EsamiPrescrivibiliServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DocumentiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.paziente.EsamiPrescrittiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.paziente.PagamentiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.AmbulatoriServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.paziente.CalendarioServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sidebar" tagdir="/WEB-INF/tags/template/sidebar" %>

<c:url var="dashboardURL" value="<%=DashboardServlet.getURL()%>" scope="session" />
<c:url var="prenotaPrelievoURL" value='<%="areaPrivata/prescrizioni/prenotaPrelievo.html"%>' scope="session" />
<c:url var="esamiPrescrittiURL" value="<%=EsamiPrescrittiServlet.getURL()%>" scope="session" />
<c:url var="ricetteFarmURL" value="<%=RicetteServlet.getURL()%>" scope="session" />
<c:url var="refertiURL" value="<%=RefertiServlet.getURL()%>" scope="session" />
<c:url var="esamiPrescURL" value="<%=EsamiPrescrivibiliServlet.getURL()%>" scope="session" />
<c:url var="documentiURL" value="<%=DocumentiServlet.getURL()%>" scope="session" />
<c:url var="pagamentiURL" value="<%=PagamentiServlet.getURL()%>" scope="session" />
<c:url var="ambulatoriURL" value="<%=AmbulatoriServlet.getURL()%>" scope="session" />
<c:url var="calendarioURL" value="<%=CalendarioServlet.getURL()%>" scope="session" />

<custom:sidebar
        userIMGpath="${img}/restricted/${userFoldername}/${USER.getUsername()}/${USER.avatarFilename}"
        userRole="Paziente"
        userData="${USER.nome} ${USER.cognome}"
>
    <jsp:attribute name="menu">
        <sidebar:menu title="Home"
                      icon="fa fa-home"
                      hasSubmenu="${false}"
                      href="${cp}/${dashboardURL}">
        </sidebar:menu>
        <sidebar:menu title="Prescrizioni"
                      icon="fas fa-calendar-check"
                      hasSubmenu="${true}"
                      id="menuPrescrizioni">
            <jsp:attribute  name="childs">
                <sidebar:child title="Prenota prelievo" href="${cp}/${prenotaPrelievoURL}"></sidebar:child>
                <sidebar:child title="Esami prescritti" href="${cp}/${esamiPrescrittiURL}"></sidebar:child>
                <sidebar:child title="Ricette farmaceutiche" href="${cp}/${ricetteFarmURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
        <sidebar:menu title="Documenti"
                      icon="fas fa-folder-open"
                      hasSubmenu="${true}"
                      id="menuDocumenti">
            <jsp:attribute  name="childs">
                <sidebar:child title="Referti" href="${cp}/${refertiURL}"></sidebar:child>
                <sidebar:child title="Esami prescrivibili" href="${cp}/${esamiPrescURL}"></sidebar:child>
                <sidebar:child title="Altri" href="${cp}/${documentiURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
        <sidebar:menu title="Pagamenti"
                      icon="fas fa-money-check-alt"
                      hasSubmenu="${true}"
                      id="menuPagamenti">
            <jsp:attribute  name="childs">
                <sidebar:child title="Lista pagamenti" href="${cp}/${pagamentiURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
        <sidebar:menu title="Ambulatori"
                      icon="fas fa-clinic-medical"
                      hasSubmenu="${true}"
                      id="menuAmbulatori">
            <jsp:attribute name="childs">
                <sidebar:child title="Lista ambulatori" href="${cp}/${ambulatoriURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
    </jsp:attribute>
    <jsp:attribute name="extraMenu">
        <sidebar:menu title="Calendario"
                      icon="far fa-calendar-alt"
                      hasSubmenu="${false}"
                      href="${cp}/${calendarioURL}">
        </sidebar:menu>
        <sidebar:menu title="Impostazioni"
                      icon="fas fa-cog"
                      hasSubmenu="${false}"
                      href="${cp}/${impostazioniURL}">
        </sidebar:menu>
    </jsp:attribute>
</custom:sidebar>