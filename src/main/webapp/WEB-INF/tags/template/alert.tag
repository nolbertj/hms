<%--
    Document: alert.tag
    Created on: November 4, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Alert Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="typeAlert"
              required="true"
              description="write 'success','error' or 'warning' for specify the type of alert"
%>
<%@ attribute name="message"
              required="true"
              description="messa to print in the alert shape"
%>

<div class="alert
    <c:choose>
        <c:when test="${typeAlert == 'success'}"> alert-success </c:when>
        <c:when test="${typeAlert == 'error'}"> alert-danger </c:when>
        <c:when test="${typeAlert == 'warning'}"> alert-warning </c:when>
        <c:otherwise> </c:otherwise>
    </c:choose>alert-dismissible mt-3 fade show" role="alert">
    ${message}
    <button type="button" class="close pt-2" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>