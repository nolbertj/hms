<%--
    Document: sidebar.jsp
    Created on: December 27, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertiServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.medicoSpecialista.CercaPazienteServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.medicoSpecialista.CompilaRefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.AppuntamentiServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sidebar" tagdir="/WEB-INF/tags/template/sidebar" %>

<c:url var="cercaPazienteURL" value="<%=CercaPazienteServlet.getURL()%>" scope="session"/>
<c:url var="dashboardURL" value="<%=DashboardServlet.getURL()%>" scope="session"/>
<c:url var="refertiURL" value="<%=RefertiServlet.getURL()%>" scope="session"/>
<c:url var="compilaRefertoURL" value="<%=CompilaRefertoServlet.getURL()%>" scope="session"/>
<c:url var="appuntamentiURL" value='<%=AppuntamentiServlet.getURL()%>' scope="session"/>

<custom:sidebar
        userIMGpath="${img}/restricted/${userFoldername}/${USER.getUsername()}/${USER.avatarFilename}"
        userRole="Medico specialista in<br>${USER.specialita}"
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
                <sidebar:child title="Lista referti" href="${cp}/${refertiURL}"></sidebar:child>
            </jsp:attribute>
        </sidebar:menu>
        <sidebar:menu title="Operazioni"
                      icon="fas fa-user-cog"
                      hasSubmenu="${true}"
                      id="menuOperazioni">
            <jsp:attribute  name="childs">
                <sidebar:child title="Cerca paziente" href="${cp}/${cercaPazienteURL}"></sidebar:child>
                <sidebar:child title="Compila referto" href="${cp}/${compilaRefertoURL}"></sidebar:child>
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