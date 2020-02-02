<%--
    Document: dashboard.jsp
    Created on: October 26, 2019
    Front-end: Alessandro Tomazzolli
    Back-end: Nolbert Juarez, Alessandro Brighenti
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/medicoBase/pages/dashboard" scope="page"/>
<%-- nell'id devo mettere il nome dell'attributo salvato in sessione, nel nostro caso SessionAttributes.USER, ovvero "USER" --%>
<%-- <jsp:useBean id="<%=SessionAttributes.USER%>" dovremmo fare così ma non funziona grrrrrrrrr --%>
<jsp:useBean id="USER"
             class="it.unitn.disi.wp.project.hms.persistence.entities.MedicoBase"
             scope="session">
</jsp:useBean>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Dashboard">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" href="${absolutePath}/dashboard.css">
    </jsp:attribute>

    <jsp:body>
    <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
    <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <a class="card-body" href="${cp}/${prescriviEsameURL}">
                            <i class="fas fa-user-md"></i>
                            <span>PRESCRIVI ESAME</span>
                        </a>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <a class="card-body" href="${cp}/${prescriviRicettaURL}">
                            <i class="fas fa-file-medical"></i>
                            <span>PRESCRIVI RICETTA</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
