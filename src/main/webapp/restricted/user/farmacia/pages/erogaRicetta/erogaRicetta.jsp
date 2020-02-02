<%--
    Document: erogaRicetta.jsp
    Created on: Novembre 23, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/farmacia/pages/erogaRicetta" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Eroga ricetta" includeSelect2="${true}" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/erogaRicetta.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <%-- ============================= HEADER PER RICERCA PAZIENTE  =============================== --%>
            <custom:select2 id="inputPaziente"></custom:select2>
            <!-- =========================================================================================== -->
            <!-- ========= TABELLA DELLE RICETTE NON ANCORA EROGATE DEL PAZIENTE SELEZIONATO =============== -->
            <custom:accordion cardTitle="Ricette non erogate" isOpened="${false}" id="ricette">
                <jsp:body>
                    <custom:table datatablePopulate="${true}">
                        <jsp:attribute name="otherButtons">
                            <button class="btn mr-3" type="button" id="cleanBTN"><i class="fas fa-eraser"></i>
                                <span>Pulisci</span>
                            </button>
                        </jsp:attribute>
                    </custom:table>
                </jsp:body>
            </custom:accordion>
            <!-- =========================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>