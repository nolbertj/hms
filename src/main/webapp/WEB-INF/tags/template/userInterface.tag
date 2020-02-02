<%--
    Document: ui-template.tag
    Created on: October 27, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ tag import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ tag description="User Interface Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="customStylesheets" fragment="true" required="false" %>
<%@ attribute name="thirdStylesheets" fragment="true" required="false" %>
<%@ attribute name="customScripts" fragment="true" required="false" %>
<%@ attribute name="thirdScripts" fragment="true" required="false" %>
<%@ attribute name="includeDatatable" required="false" %>
<%@ attribute name="includeSelect2" required="false" %>

<c:set var="cp" value="${pageContext.servletContext.contextPath}" scope="application"/>
<c:set var="userFoldername" value="<%=request.getSession().getAttribute(Attr.USER_FOLDERNAME)%>" scope="session"/>
<c:url var="img" value="/assets/img" scope="application"/>
<c:url var="css" value="/assets/css" scope="application"/>
<c:url var="js" value="/assets/js" scope="application"/>
<c:set var="loader" value='<%=request.getAttribute("loader")%>' scope="request"/>
<c:url var="impostazioniURL" value='<%="areaPrivata/impostazioni.html"%>' scope="session"/>

<!DOCTYPE html>
<HTML lang="it">
<HEAD>
    <META http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <META http-equiv="X-UA-Compatible" content="IE=edge">
    <META name="viewport" content="width=device-width, initial-scale=1">

    <TITLE>${pageTitle}</TITLE>

    <%@ include file="/WEB-INF/partials/favicon.jsp" %>

    <!-- VENDORS -->
    <LINK rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <LINK rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <c:if test="${includeDatatable eq true}">
        <LINK rel="stylesheet" type="text/css" href="https://cdn.datatables.net/w/bs4/jqc-1.12.4/jszip-2.5.0/dt-1.10.18/b-1.5.6/b-colvis-1.5.6/b-flash-1.5.6/b-html5-1.5.6/cr-1.5.0/fc-3.2.5/fh-3.1.4/kt-2.5.0/r-2.2.2/sc-2.0.0/sl-1.3.0/datatables.min.css">
    </c:if>
    <c:if test="${includeSelect2 eq true}">
        <LINK rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.10/css/select2.min.css" >
    </c:if>
    <LINK rel="stylesheet" href="https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.css" />

    <jsp:invoke fragment="thirdStylesheets"/>
    <SCRIPT src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment-with-locales.min.js"></SCRIPT>
    <c:if test="${includeDatatable eq true}">
        <SCRIPT type="text/javascript" src="https://cdn.datatables.net/w/bs4/jqc-1.12.4/jszip-2.5.0/dt-1.10.18/b-1.5.6/b-colvis-1.5.6/b-flash-1.5.6/b-html5-1.5.6/cr-1.5.0/fc-3.2.5/fh-3.1.4/kt-2.5.0/r-2.2.2/sc-2.0.0/sl-1.3.0/datatables.min.js"></SCRIPT>
    </c:if>
    <c:if test="${includeSelect2 eq true}">
        <SCRIPT type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.10/js/select2.min.js"></SCRIPT>
    </c:if>
    <SCRIPT src="https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.js" data-cfasync="false"></SCRIPT>
    <jsp:invoke fragment="thirdScripts"/>

    <!-- MAIN CSS & JS -->
    <LINK rel="stylesheet" href="${css}/style.css">
    <LINK rel="stylesheet" href="${css}/style-mobile.css">
    <jsp:invoke fragment="customStylesheets"/>
    <SCRIPT type="text/javascript" src="${js}/modules/sidebar.js"></SCRIPT>
    <c:if test="${includeDatatable eq true}">
        <SCRIPT type="text/javascript" src="${js}/modules/table.js"></SCRIPT>
    </c:if>
    <c:if test="${includeSelect2 eq true}">
        <SCRIPT type="text/javascript" src="${js}/modules/suggestionBox.js"></SCRIPT>
    </c:if>
    <script>
        const myFormatDate = "DD-MM-YYYY";
        function parseAndShowDates(date){
            if(date===undefined || date==='' || date===null){
                return "";
            }else{
                return moment(date).format(myFormatDate)
            }
        }
        function parseAndShowTimestamp(timestamp){
            if(timestamp===undefined || timestamp==='' || timestamp===null){
                return "";
            }else{
                return moment(timestamp).format(myFormatDate + " kk:mm:ss");
            }
        }
    </script>
    <SCRIPT src="${js}/cookies/cookieHandler.js"></SCRIPT>
    <SCRIPT src="${js}/cookies/cookieConsent.js"></SCRIPT>
    <jsp:invoke fragment="customScripts"/>
</HEAD>

<BODY>
    <DIV id="page">
<!-- ----------- SIDEBAR ------------------------------------------------------------- -->
        <jsp:include page="/restricted/user/${userFoldername}/partials/sidebar.jsp" />
<!-- --------------------------------------------------------------------------------- -->
        <DIV id="page-wrapper">
<!-- ----------- NAVBAR ----------------------------------------------------------------->
            <jsp:include page="/restricted/user/${userFoldername}/partials/navbar.jsp" />
<!-- --------------------------------------------------------------------------------- -->
            <c:if test="${loader eq true}">
                <%@ include file="../../partials/heart-rate.jsp" %>
            </c:if>
            <MAIN id="page-container">
<!-- ----------- CONTENT ------------------------------------------------------------- -->
                <jsp:doBody/>
                <br>
                <jsp:include page="/restricted/user/footer.jsp" />

<!-- --------------------------------------------------------------------------------- -->
            </MAIN>
        </DIV>
    </DIV>
    <script type="text/javascript">
        showCookieConsent();
    </script>
</BODY>
</HTML>