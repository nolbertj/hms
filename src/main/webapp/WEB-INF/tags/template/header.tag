<%--
    Document: header.tag
    Created on: December 26, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Header Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="title"
              required="true"
              description=""
%>
<%@ attribute name="currentPageTitle"
              required="false"
              description=""
%>

<header id="content-header" class="justify-content-between align-items-center">
    <!-- ==================== HEADER ====================== -->
    <h3 class="content-header-title">${title}</h3>
    <nav class="d-flex flex-wrap m-0" aria-label="breadcrumb">
        <ol class="breadcrumb m-0">
            <jsp:doBody/>
            <c:if test="${not empty currentPageTitle}">
                <li class="breadcrumb-item active" aria-current="page">${currentPageTitle}</li>
            </c:if>
        </ol>
    </nav>
    <!-- ================================================== -->
</header>