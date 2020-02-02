<%--
    Document: li.tag
    Created on: December 26, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Header <li> Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="title"
              required="true"
%>
<%@ attribute name="icon"
              required="false"
              description="Font Awesome icon, e.g. 'fas fa-home'"
%>
<%@ attribute name="href"
              required="false"
              description="simple url or href for another item in the html document"
%>
<%@ attribute name="id"
              required="false"
              description="id for this <li> element"
%>
<%@ attribute name="collapse"
              required="false"
              description="${true} if this <li> must open/close an item defined in the href attribute, ${false} or blank for nothing"
%>

<li class="breadcrumb-item"
    <c:choose>
        <c:when test="${not empty id}"> id="${id}" </c:when>
        <c:when test="${collapse eq true and empty id}"> id="breadcrumb-collapse" </c:when>
    </c:choose>
>
    <c:if test="${not empty href}">
        <c:choose>
            <c:when test="${collapse eq true}">
                <a role="button" href="#${href}" data-toggle="collapse" aria-expanded="true" aria-controls="${href}">
            </c:when>
            <c:otherwise>
                <a href="${href}">
            </c:otherwise>
        </c:choose>
    </c:if>
        <c:if test="${not empty icon}">
            <i class="${icon} fa-fw"></i>
        </c:if>
        &nbsp;${title}
    <c:if test="${not empty href}"></a></c:if>
</li>