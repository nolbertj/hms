<%--
    Document: menu.tag
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Sidebar Menu Template w/ <li> (use inside sidebar.tag; FontAwesome is required)"
        pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="id"
              required="false"
              description="set id for menu if it will submenu items"
%>
<%@ attribute name="href"
              required="false"
              description="set href if menu will NOT contain submenu items"
%>
<%@ attribute name="title"
              required="true"
              description="title for menu item"
%>
<%@ attribute name="icon"
              required="true"
              description="insert only name of FontAwesome icon, e.g.: 'far fa-calendar-check'"
%>
<%@ attribute name="hasSubmenu"
              required="true"
              description="set ${true} or ${false} if it wil contain submenu items"
%>
<%@ attribute name="childs"
              fragment="true"
              required="false"
              description="invoke this if u'll use sidebarMenuChild.tag"
%>

<li class="nav-item">
    <a class="nav-link collapsed" href="
        <c:choose>
            <c:when test="${hasSubmenu}">#${id}</c:when>
            <c:otherwise>${href}</c:otherwise>
        </c:choose>
    "
            <c:if test="${hasSubmenu}">
                data-toggle="collapse" role="button" aria-expanded="false"
                aria-controls="${id}" data-parent="#sidebar-content"
            </c:if>
    >
        <i class="${icon} fa-fw"></i>
        <span class="nav-item-text">${title}</span>
        <c:if test="${hasSubmenu}"><i class="icon fas fa-angle-up float-right"></i></c:if>
    </a>
    <c:if test="${hasSubmenu}">
        <ul class="sidebar-submenu collapse hide" id="${id}">
            <jsp:invoke fragment="childs"/>
        </ul>
    </c:if>
</li>
