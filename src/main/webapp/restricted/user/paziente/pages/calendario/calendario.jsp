<%--
    Document: calendario.jsp
    Created on: January 16, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Calendario">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" href="${css}/modules/calendario.css">
    </jsp:attribute>
    <jsp:attribute name="thirdScripts">
        <SCRIPT type="text/javascript" src="${cp}/vendors/daypilot/js/daypilot-all.min.js"></SCRIPT>
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <custom:card>
                <div class="btn-group d-flex mb-3 mx-auto" role="group">
                    <button id="previous" class="btn btn-primary" type="button">Indietro</button>
                    <button id="choose" class="btn btn-warning" type="button">Scegli</button>
                    <button id="today" class="btn btn-success" type="button">Oggi</button>
                    <button id="next" class="btn btn-primary" type="button">Avanti</button>
                </div>
                <div id="calendar"></div>
                <div class="table-status d-flex"></div>
            </custom:card>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>

</custom:userInterface>
