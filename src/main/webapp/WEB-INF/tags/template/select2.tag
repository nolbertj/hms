<%--
    Document: select2.tag
    Created on: December 29, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ tag description="Select2 Custom Template" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="id"
              required="true"
              description="id for the input html tag"
%>
<%@ attribute name="clazz"
              required="false"
              description="classes for the container of select2 html tag"
%>
<%@ attribute name="insideContainer"
              required="false"
              description="${true} for render the select <input> inside a card. ${false} otherwise"
%>
<%@ attribute name="clazzSelectTag"
              required="false"
              description="classes for the <select> tag"
%>

<c:choose>
    <c:when test="${insideContainer eq false}">
        <style>
            label {
                margin-bottom: 0.5rem;
                color: var(--content-header-title-color) !important;
            }

            .select2-container--default .select2-selection--single .select2-selection__placeholder {
                color: var(--content-header-title-color) !important;
            }
            .select2-container--default .select2-selection--single .select2-selection__rendered {
                border-bottom: 1px solid var(--secondary-color) !important;
                font-weight: inherit !important;
                color: black !important;
            }
            .select2-container--open .select2-dropdown--below {
                top: unset !important;
            }
            span.select2-selection__clear {
                color: var(--secondary-color) !important;
            }
        </style>
        <div class="row">
            <div class="col-md-12">
                <div class="selectContainer <c:if test="${not empty clazz}"> ${clazz} </c:if>" style="background: none">
                    <select id="${id}" class="form-control form-control-sm
                        <c:if test="${not empty clazzSelectTag}"> ${clazzSelectTag} </c:if>">
                    </select>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <custom:card>
            <jsp:body>
                <div class="row">
                    <div class="col-md-12">
                        <div class="selectContainer <c:if test="${not empty clazz}"> ${clazz} </c:if>">
                            <div class="form-row">
                                <select id="${id}" class="form-control form-control-sm
                                    <c:if test="${not empty clazzSelectTag}"> ${clazzSelectTag} </c:if>">
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </jsp:body>
        </custom:card>
    </c:otherwise>
</c:choose>