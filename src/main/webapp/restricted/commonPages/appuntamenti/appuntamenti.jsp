<%--
    Document: appuntamenti.jsp
    Created on: January 18, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Gestione appuntamenti" includeSelect2="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" href="${css}/modules/calendario.css">
        <LINK rel="stylesheet" href="${css}/modules/suggestionBox.css">
         <style>
            label {
                margin-bottom: 0.5rem;
                color: var(--content-header-title-color) !important;
            }

            .select2-container--default .select2-selection--single .select2-selection__placeholder {
                color: var(--content-header-title-color) !important;
            }
            .select2-container--default .select2-selection--single .select2-selection__rendered {
                border-bottom: 1px solid var(--secondary-color) !important;
                font-weight: inherit !important;
                color: black !important;
            }
            .select2-container--open .select2-dropdown--below {
                top: unset !important;
            }
            span.select2-selection__clear {
                color: var(--secondary-color) !important;
            }
            .select2-dropdown {
                z-index: 99999 !important;
            }
         </style>
    </jsp:attribute>
    <jsp:attribute name="thirdScripts">
        <SCRIPT type="text/javascript" src="${cp}/vendors/daypilot/js/daypilot-all.min.js"></SCRIPT>
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <jsp:include page="/restricted/user/${userFoldername}/pages/appuntamenti/script.jsp"/>
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
