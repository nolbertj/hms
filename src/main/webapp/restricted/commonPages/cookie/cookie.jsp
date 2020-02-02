<%--
    Document: cookie.jsp
    Created on: January 20, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:set var="successMSG" value='<%=request.getAttribute(Attr.SUCCESS_MSG)%>' scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Cookie">
    <jsp:attribute name="customStylesheets">
        <style type="text/css">
            @import "${cp}/assets/css/modules/checkbox.css";
            #content-header .content-header-title {
                margin-top: 0.5rem;
                font-size: calc(var(--content-header-title-size)*0.75) !important;
            }
            p {
                text-align: justify;
            }
            .container {
                background-color: rgba(0,0,0,0.1);
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <%-- ============== ALERT MESSAGES ==================================================================== --%>
            <c:if test="${successMSG eq true}">
                <custom:alert typeAlert="success" message="Impostazioni salvate con successo"></custom:alert>
            </c:if>
            <%-- ================================================================================================== --%>
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <%@ include file="/pages/cookie.jsp" %>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
