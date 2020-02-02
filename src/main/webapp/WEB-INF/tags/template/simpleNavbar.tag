<%--
    Document: simpleNavbar.tag
    Created on: November 1, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag import="it.unitn.disi.wp.project.hms.servlet.user.ProfiloServlet" %>
<%@ tag import="it.unitn.disi.wp.project.hms.servlet.authentication.LogoutServlet" %>

<%@ tag description="Navbar Template [invoke body attribute for add more features]" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="body"
              fragment="true"
              required="false"
              description="invoke this attribute if you want to add more features in navbar (with html code)"
%>

<c:url var="profiloURL" value="<%=ProfiloServlet.getURL()%>" scope="session"/>
<c:url var="logoutURL" value="<%=LogoutServlet.getURL()%>" scope="session"/>

<nav id="myNavbar" class="navbar navbar-expand navbar-light">
    <button type="button" data-toggle="collapse"
            data-target="#sidebar-content" aria-controls="sidebar-content" aria-expanded="false"
            aria-label="Toggle navigation">
                <span class="navbar-toggler-icon">
                    <i class="fas fa-bars"></i>
                </span>
    </button>

    <div class="collapse navbar-collapse">
        <ul class="navbar-nav">
            <jsp:invoke fragment="body"/>
            <li class="nav-item dropdown">
                <span class="nav-link pr-0" id="navbarDropdownUser" role="button" data-toggle="dropdown"
                      aria-haspopup="true" aria-expanded="false">
                    <i class="fas fa-user"></i>
                    <span>Account</span>
                    <i class="small fas fa-caret-down font-weight-bold"></i>
                </span>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownUser">
                    <a class="dropdown-item" href="${cp}/${profiloURL}"><i class="fas fa-users-cog"></i>
                        <span>Profilo</span>
                    </a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="${cp}/${logoutURL}"><i class="fas fa-sign-out-alt"></i>
                        <span>Logout</span>
                    </a>
                </div>
            </li>
        </ul>
    </div>
</nav>