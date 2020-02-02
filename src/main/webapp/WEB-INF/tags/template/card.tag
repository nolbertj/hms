<%--
    Document: card.tag
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Card Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="title" required="false" %>
<%@ attribute name="id" required="false" %>

<div class="card"<c:if test="${not empty id}"> id="${id}" </c:if>>
    <c:if test="${not empty title}">
        <div class="card-header" role="tab"><h3 class="card-title">${title}</h3></div>
    </c:if>
    <div class="card-body">
        <!------------------------------------------>
        <jsp:doBody/>
        <!------------------------------------------>
    </div>
</div>
