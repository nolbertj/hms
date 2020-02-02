<%--
    Document: copyright.jsp
    Created on: December 31, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Copyright">
    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <%@ include file="/pages/copyright.html" %>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
