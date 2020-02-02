<%--
    Document: footer.jsp
    Created on: December 26, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.InfoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.CookieServlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="creditsURL" value="<%=InfoServlet.getCreditsURL()%>" scope="session" />
<c:url var="copyrightPrivacyURL" value="<%=InfoServlet.getCopyrightPrivacyURL()%>" scope="session" />
<c:url var="cookieURL" value="<%=CookieServlet.getURL()%>" scope="session" />

<footer>
    <span>&nbsp;</span>
    <span>&#169; 2019 Healthcare Management System - Partita Iva: 00101010101</span>
    <span>
        <a href="${cp}/${creditsURL}" id="creditsLink"> Credits |</a>
        <a href="${cp}/${copyrightPrivacyURL}" id="copyrightLink"> Copyright e Privacy |</a>
        <a href="${cp}/${cookieURL}" id="cookieLink"> Cookie</a>
    </span>
    <span>&nbsp;</span>
</footer>