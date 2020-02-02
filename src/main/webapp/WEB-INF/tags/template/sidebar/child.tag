<%--
    Document: child.tag
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Sidebar child <li> for SidebarMenu (use inside sidebarMenu.tag)" pageEncoding="UTF-8" %>

<%@ attribute name="title" required="true" description="title for submenu item" %>
<%@ attribute name="href" required="true" %>

<li class="nav-item"><a class="nav-link" href="${href}"><span>${title}</span></a></li>
