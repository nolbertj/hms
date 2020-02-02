<%--
    Document: farmaci.jsp
    Created on: December 20, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/commonPages/farmaci" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Lista farmaci" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/farmaci.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <custom:card title="Lista farmaci">
                <jsp:body>
                    <custom:table datatablePopulate="${true}"></custom:table>
                </jsp:body>
            </custom:card>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>

</custom:userInterface>