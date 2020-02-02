<%--
    Document: sidebar.jsp
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.EsamiPrescrivibiliServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.ListaFarmaciServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.ssp.GeneraReportServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.ssp.ListaRichiamiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.ssp.PrescriviRichiamoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.ssp.CompilaRefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.AmbulatoriServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sidebar" tagdir="/WEB-INF/tags/template/sidebar" %>

<c:url var="dashboardURL" value="<%=DashboardServlet.getURL()%>" scope="session" />
<c:url var="esamiPrescURL" value="<%=EsamiPrescrivibiliServlet.getURL()%>" scope="session" />
<c:url var="listaFarmaciURL" value="<%=ListaFarmaciServlet.getURL()%>" scope="session" />
<c:url var="generaReportURL" value="<%=GeneraReportServlet.getURL()%>" scope="session" />
<c:url var="listaRichiamiURL" value="<%=ListaRichiamiServlet.getURL()%>" scope="session" />
<c:url var="doRichiamoURL" value="<%=PrescriviRichiamoServlet.getURL()%>" scope="session" />
<c:url var="compilaRefertoURL" value="<%=CompilaRefertoServlet.getURL()%>" scope="session" />
<c:url var="ambulatoriURL" value="<%=AmbulatoriServlet.getURL()%>" scope="session" />
<STYLE>
    #sidebar-header img {
        border-radius: 0;
        -moz-border-radius: 0;
        -webkit-border-radius: 0;
    }
</STYLE>

<custom:sidebar
        userIMGpath="${img}/restricted/${userFoldername}/${USER.getUsername()}/${USER.avatarFilename}"
        userRole="SSP"
        userData="${USER.abbreviazione}"
>
    <jsp:attribute name="menu">
        <sidebar:menu title="Home"
                      icon="fa fa-home"
                      hasSubmenu="${false}"
                      href="${cp}/${dashboardURL}">
        </sidebar:menu>
        <sidebar:menu title="Operazioni"
                     icon="fas fa-user-cog"
                     hasSubmenu="${true}"
                     id="menuOperazioni">
            <jsp:attribute name="childs">
                <sidebar:child title="Compila referto" href="${cp}/${compilaRefertoURL}"></sidebar:child>
                <sidebar:child title="Genera report" href="${cp}/${generaReportURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
        <sidebar:menu title="Richiami"
                      icon="fas fa-calendar-check"
                      hasSubmenu="${true}"
                      id="menuRichiami">
            <jsp:attribute  name="childs">
                <sidebar:child title="Lista richiami" href="${cp}/${listaRichiamiURL}"></sidebar:child>
                <sidebar:child title="Prescrivi richiamo" href="${cp}/${doRichiamoURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
        <sidebar:menu title="Documenti"
                      icon="fas fa-folder-open"
                      hasSubmenu="${true}"
                      id="menuDocumenti">
            <jsp:attribute  name="childs">
                <sidebar:child title="Esami prescrivibili" href="${cp}/${esamiPrescURL}"></sidebar:child>
                <sidebar:child title="Lista Farmaci" href="${cp}/${listaFarmaciURL}"></sidebar:child>
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
        <sidebar:menu title="Impostazioni"
                      icon="fas fa-cog"
                      hasSubmenu="${false}"
                      href="${cp}/${impostazioniURL}">
        </sidebar:menu>
    </jsp:attribute>
</custom:sidebar>