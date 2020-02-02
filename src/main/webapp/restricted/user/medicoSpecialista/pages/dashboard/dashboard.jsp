<%--
    Document: dashboard.jsp
    Created on: December 29, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/medicoSpecialista/pages/dashboard" scope="page"/>
<jsp:useBean id="USER"
             class="it.unitn.disi.wp.project.hms.persistence.entities.MedicoSpecialista"
             scope="session">
</jsp:useBean>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Dashboard">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" href="${absolutePath}/dashboard.css">
    </jsp:attribute>

    <jsp:body>
    <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
    <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <a class="card-body" href="${cp}/${cercaPazienteURL}">
                            <i class="fas fa-search fa-fw"></i>
                            <span>CERCA PAZIENTE</span>
                        </a>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <a class="card-body" href="${cp}/${compilaRefertoURL}">
                            <i class="fas fa-file-signature fa-fw"></i>
                            <span>COMPILA REFERTO</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
