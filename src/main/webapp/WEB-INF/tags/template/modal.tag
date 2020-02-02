<%--
    Document: modal.tag
    Created on: October 29, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Modal Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="id"
              required="true"
              description="id for modal"
%>
<%@ attribute name="title"
              required="false"
              description="title for modal header"
%>
<%@ attribute name="body"
              fragment="true"
              required="true"
              description="content of modal"
%>
<%@ attribute name="footer"
              fragment="true"
              required="true"
              description="footer of modal. It may contains button for upload, download etc..."
%>
<%@ attribute name="setDefaultCancelBUTTON"
              required="true"
              description="set ${true} or ${false} for set the default cancel button for dismiss the modal window"
%>

<div id="${id}" class="modal" tabindex="-1" role="form" aria-labelledby="${id}" aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog modal-dialog-scrollable modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <c:if test="${not empty title}">
                    <span class="modal-title">${title}</span>
                </c:if>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <jsp:invoke fragment="body"/>
            </div>
            <div class="modal-footer">
                <jsp:invoke fragment="footer"/>
                <c:if test="${setDefaultCancelBUTTON}">
                    <button type="button" class="btn btn-danger w-100 shadow" data-dismiss="modal">ANNULLA</button>
                </c:if>
            </div>
        </div>
    </div>
</div>