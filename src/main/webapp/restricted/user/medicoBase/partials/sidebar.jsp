<%--
    Document: sidebar.jsp
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DocumentiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.paziente.RicetteServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.EsamiPrescrivibiliServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.medicoBase.ListaPazientiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.ListaFarmaciServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.medicoBase.PrescriviEsameServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.medicoBase.PrescriviRicettaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.AppuntamentiServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sidebar" tagdir="/WEB-INF/tags/template/sidebar" %>

<c:url var="dashboardURL" value="<%=DashboardServlet.getURL()%>" scope="session"/>
<c:url var="esamiPrescURL" value="<%=EsamiPrescrivibiliServlet.getURL()%>" scope="session" />
<c:url var="ricetteFarmURL" value="<%=RicetteServlet.getURL()%>" scope="session" />
<c:url var="documentiURL" value="<%=DocumentiServlet.getURL()%>" scope="session"/>
<c:url var="listaPazientiURL" value="<%=ListaPazientiServlet.getURL()%>" scope="session"/>
<c:url var="listaFarmaciURL" value="<%=ListaFarmaciServlet.getURL()%>" scope="session"/>
<c:url var="prescriviEsameURL" value="<%=PrescriviEsameServlet.getURL()%>" scope="session"/>
<c:url var="prescriviRicettaURL" value='<%=PrescriviRicettaServlet.getURL()%>' scope="session"/>
<c:url var="appuntamentiURL" value='<%=AppuntamentiServlet.getURL()%>' scope="session"/>

<custom:sidebar
        userIMGpath="${img}/restricted/${userFoldername}/${USER.getUsername()}/${USER.avatarFilename}"
        userRole="Medico di base"
        userData="${USER.nome} ${USER.cognome}"
>
    <jsp:attribute name="menu">
        <sidebar:menu title="Home"
                      icon="fa fa-home"
                      hasSubmenu="${false}"
                      href="${cp}/${dashboardURL}">
        </sidebar:menu>
        <sidebar:menu title="Documenti"
                      icon="fas fa-folder-open"
                      hasSubmenu="${true}"
                      id="menuDocumenti">
            <jsp:attribute  name="childs">
                <sidebar:child title="Lista farmaci" href="${cp}/${listaFarmaciURL}"></sidebar:child>
                <sidebar:child title="Lista pazienti" href="${cp}/${listaPazientiURL}"></sidebar:child>
                <sidebar:child title="Esami prescrivibili" href="${cp}/${esamiPrescURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
        <sidebar:menu title="Operazioni"
                      icon="fas fa-user-cog"
                      hasSubmenu="${true}"
                      id="menuOperazioni">
            <jsp:attribute  name="childs">
                <sidebar:child title="Prescrivi esame" href="${cp}/${prescriviEsameURL}"></sidebar:child>
                <sidebar:child title="Prescrivi ricetta" href="${cp}/${prescriviRicettaURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
    </jsp:attribute>
    <jsp:attribute name="extraMenu">
        <sidebar:menu title="Appuntamenti"
                      icon="far fa-calendar-alt"
                      hasSubmenu="${false}"
                      href="${cp}/${appuntamentiURL}">
        </sidebar:menu>
        <sidebar:menu title="Impostazioni"
                      icon="fas fa-cog"
                      hasSubmenu="${false}"
                      href="${cp}/${impostazioniURL}">
        </sidebar:menu>
    </jsp:attribute>
</custom:sidebar>