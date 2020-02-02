<%--
    Document: sidebar.tag
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Sidebar Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="userIMGpath" required="true" %>
<%@ attribute name="userRole" required="false" %>
<%@ attribute name="userData" required="true" %>
<%@ attribute name="menu" fragment="true" required="true" %>
<%@ attribute name="extraMenu" fragment="true" required="false" %>
<%@ attribute name="menuTitle" required="false" %>

<nav id="sidebar" class="navbar collapse navbar-collapse">
    <!--- SIDEBAR/NAVBAR LOGO --->
    <div class="navbar-brand">
        <i class="fas fa-stethoscope"></i>
        <span>AREA PRIVATA</span>
    </div>
    <!-- --------------- -->
    <!-- SIDEBAR-CONTENT -->
    <div id="sidebar-content" class="collapse">
        <!-- SIDEBAR-HEADER -->
        <div id="sidebar-header" class="col">
            <div class="user-photo row"><img src="${userIMGpath}" alt="USER PIC"></div>
            <div class="user-info">
                <c:if test="${not empty userRole}">
                    <span class="user-role"><small>${userRole}</small></span>
                </c:if>
                <span class="user-name"><strong>${userData}</strong></span>
            </div>
        </div>
        <ul class="navbar-nav">
            <li class="nav-item-title">
                <c:choose>
                    <c:when test="${empty menuTitle}">Opzioni</c:when>
                    <c:otherwise>${menuTitle}</c:otherwise>
                </c:choose>
            </li>
            <jsp:invoke fragment="menu"/>
            <c:if test="${not empty extraMenu}"><li class="nav-item-title">Extra</li></c:if>
            <jsp:invoke fragment="extraMenu"/>
        </ul>
    </div>
    <!-- SIDEBAR FOOTER -->
    <footer><button><i class="icon fas fa-angle-double-left"></i>
        <span class="sidebar-footer-text" id="hideSidebarMSG">Nascondi men&#249;</span>
        <span class="sidebar-footer-text off" id="showSidebarMSG">Fissa men&#249;</span>
    </button>
    </footer>
    <!-- ----------- -->
</nav>
