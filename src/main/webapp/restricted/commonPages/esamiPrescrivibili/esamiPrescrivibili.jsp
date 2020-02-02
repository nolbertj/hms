<%--
    Document: esamiPrescrivibili.jsp
    Created on: Novembre 5, 2019
    Front-end: Alessandro Brighenti
    Back-end: Alessandro Brighenti, Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/commonPages/esamiPrescrivibili" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Esami prescrivibili" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/esamiPrescrivibili.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <custom:card title="Esami prescrivibili">
                <jsp:body>
                    <custom:table datatablePopulate="${true}"></custom:table>
                </jsp:body>
            </custom:card>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
