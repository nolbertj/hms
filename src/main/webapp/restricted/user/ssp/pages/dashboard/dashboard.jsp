<%--
    Document: dashboard.jsp
    Created on: January 08, 2020
    Front-end: Alessandro Brighenti
    Back-end: Nolbert Juarez, Alessandro Brighenti
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/ssp/pages/dashboard" scope="page"/>

<jsp:useBean id="USER" class="it.unitn.disi.wp.project.hms.persistence.entities.Ssp" scope="session"></jsp:useBean>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Dashboard">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" href="${absolutePath}/dashboard.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <SCRIPT type="text/javascript">
            $(()=> {
                $('.counter').each(function() {
                    let $this = $(this);
                    $({ Counter: 0 }).animate({Counter: $this.text()}, {
                        duration: 2000,
                        easing: 'swing',
                        step: function() {
                            $this.text(Math.ceil(this.Counter));
                        }
                    });
                    if($(window).width()<992){
                        $("#content .row").removeClass("mb-5");
                        $("#content").prepend($("#content .row:last"));
                    }
                });
            });
        </SCRIPT>
    </jsp:attribute>

    <jsp:body>
    <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <div id="content">
            <div class="row mb-5">
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <span class="counter">${USER.numeroPazienti}</span>
                            <span>PAZIENTI</span>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <span class="counter">${USER.numeroMediciBase}</span>
                            <span>MEDICI DI BASE</span>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <span class="counter">${USER.numeroMediciSpecialisti}</span>
                            <span>MEDICI SPECIALISTI</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <div class="card">
                        <a class="card-body" href="${cp}/${listaRichiamiURL}">
                            <i class="fas fa-notes-medical fa-fw"></i>
                            <span>RICHIAMI</span>
                        </a>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <a class="card-body" href="${cp}/${generaReportURL}">
                            <i class="fas fa-laptop-medical fa-fw"></i>
                            <span>GENERA REPORT</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
