<%--
    Document: statusPage.tag
    Created on: October 27, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Status Page Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="pageTitle" required="true" %>
<%@ attribute name="imageOrIcon" fragment="true" required="true" %>
<%@ attribute name="title" fragment="true" required="true" %>
<%@ attribute name="message" fragment="true" required="true" %>
<%@ attribute name="errorPage" required="false" %>

<!DOCTYPE html>

<HTML lang="it">

<HEAD>
    <META http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <META http-equiv="X-UA-Compatible" content="IE=edge">
    <META name="viewport" content="width=device-width, initial-scale=1">

    <TITLE>${pageTitle}</TITLE>

    <%@ include file="/WEB-INF/partials/favicon.jsp" %>

    <LINK rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">

    <STYLE>
        @import "${pageContext.request.contextPath}/assets/css/modules/colors.css";
        :root {
            --custom-background: to left, var(--primary-color), var(--secondary-color);
        }
        body {
            background: -webkit-linear-gradient(var(--custom-background));
            background: -moz-linear-gradient(var(--custom-background));
            background: -ms-linear-gradient(var(--custom-background));
            background: -o-linear-gradient(var(--custom-background));
            background: linear-gradient(var(--custom-background));
            font-family: "SF Pro Display", "SF Pro Icons", "Helvetica Neue", "Helvetica", "Arial", sans-serif;
            text-shadow: 2px 0 3px rgba(0, 0, 0, 0.2);
            text-align: center;
            color: rgb(255,255,255);
            margin: 0;
            padding: 1rem;
            display: flex;
            display: -o-flex;
            display: -ms-flex;
            display: -moz-flex;
            display: -webkit-flex;
            flex-direction: column;
            -ms-flex-direction: column;
            -webkit-flex-direction: column;
            -ms-flex-preferred-size: 0;
            flex-basis: 0;
            flex-grow: 1;
            -ms-flex-positive: 1;
            max-width: 100%;
            align-items: center;
            -webkit-align-items: center;
        }
        i {
            font-size: 6em;
        }
        i, img {
            margin: 2rem 0;
            height: 6rem;
            width: 6rem;
        }
        .title {
            margin: 1rem 0;
            font-size: 36px;
            font-weight: 700;
        }
        hr {
            width: 100%;
            margin: 1rem 0;
            border-top: 1px solid rgba(255,255,255,0.5);
        }
        .text {
            font-size: 18px;
            font-weight: 300;
            line-height: 1.5;
        }
        a {
            margin-top: 1rem;
            color: inherit;
            text-decoration: none;
        }
        a:hover {
            color: unset;
            text-decoration: underline;
        }
    </STYLE>
</HEAD>

<BODY>
    <jsp:invoke fragment="imageOrIcon"/>
    <span class="title"><jsp:invoke fragment="title"/></span>
    <hr>
    <span class="text"><jsp:invoke fragment="message"/></span>
    <hr>
    <c:choose>
        <c:when test="${errorPage eq true and not empty USER}">
            <a href="${cp}/${dashboardURL}">Ritorna alla Dashboard</a>
        </c:when>
        <c:otherwise>
            <a href="${cp}">Ritorna alla Homepage</a>
        </c:otherwise>
    </c:choose>
</BODY>
</HTML>