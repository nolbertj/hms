<%--
    Document: referti.jsp
    Created on: January 04, 2019
    Front-end: Alessandro Tomazzolli
    Back-end: Alessandro Tomazzolli
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/medicoSpecialista/pages/documenti/referti" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Lista referti" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/referti.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <custom:card title="Lista referti">
                <jsp:body>
                    <custom:table datatablePopulate="${true}"></custom:table>
                </jsp:body>
            </custom:card>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
