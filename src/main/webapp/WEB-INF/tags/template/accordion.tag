<%--
    Document: accordion.tag
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Accordion Template (with arrow icon)" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="isOpened"
              required="true"
              description="set ${true} or ${false} if accordion must be opened on document ready or not"
%>
<%@ attribute name="id"
              required="true"
              description="prefix id for this element. Pattern: ${id}Collapse, ${id}Accordion, ${id}Card"
%>
<%@ attribute name="cardTitle"
              required="false"
%>

<div class="row">
    <div class="col">
        <div class="accordion" id="${id}Accordion" role="tablist" aria-multiselectable="true">
            <div class="card">
                <div class="card-header" role="tab" id="${id}Card">
                    <a class="accordion-toggle <c:if test="${!isOpened}">collapsed</c:if>"
                       data-toggle="collapse" data-parent="#${id}Accordion" href="#${id}Collapse"
                       aria-expanded="
                        <c:choose>
                            <c:when test="${isOpened}">true</c:when>
                            <c:when test="${!isOpened}">false</c:when>
                            <c:otherwise>false</c:otherwise>
                        </c:choose>" aria-controls="${id}Collapse">
                        <h3 class="card-title">${cardTitle}
                            <i class="icon fas fa-angle-up fa-pull-right"></i>
                        </h3>
                    </a>
                </div>
                <div id="${id}Collapse" class="collapse<c:if test="${isOpened}"> show</c:if>" role="tabpanel" aria-labelledby="${id}Card" data-parent="#${id}Accordion">
                    <div class="card-body">
                        <jsp:doBody/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>