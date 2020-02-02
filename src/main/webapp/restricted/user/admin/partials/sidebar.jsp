<%--
    Document: sidebar.jsp
    Created on: November 30, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ page import="java.util.List" %>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<%@ page import="it.unitn.disi.wp.project.hms.persistence.dao.AdminDAO" %>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.entities.SimpleTable" %>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.entities.Column" %>
<%@ page import="it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="sidebar" tagdir="/WEB-INF/tags/template/sidebar" %>

<%
    if(request.getSession().getAttribute("tables") == null) {
        DAOFactory daoFactory = (DAOFactory)request.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory==null) throw new ServletException("Impossible to get dao factory");
        AdminDAO adminDAO = daoFactory.getDAO(AdminDAO.class);
        List<SimpleTable> tables = adminDAO.getTables();
        for(SimpleTable t:tables){
            List<Column> columns = adminDAO.getColumns(t.getName());
            t.setColumns(columns);
        }
        request.getSession().setAttribute("tables",tables);
    }
%>

<jsp:useBean id="tables"
             type="java.util.List<it.unitn.disi.wp.project.hms.commons.persistence.entities.SimpleTable>"
             scope="session">
</jsp:useBean>

<custom:sidebar userIMGpath="${img}/admin.png" userRole="Admin" userData="${USER.email}" menuTitle="Tabelle">
    <jsp:attribute name="menu">
        <c:forEach var="table" items="${tables}">
            <sidebar:menu title="${table.name}" icon="fas fa-table" hasSubmenu="${true}" id="${table.name}">
                <jsp:attribute name="childs">
                    <c:forEach var="column" items="${table.columns}">
                        <sidebar:child title="${column.name}" href="#"></sidebar:child>
                    </c:forEach>
                </jsp:attribute>
            </sidebar:menu>
        </c:forEach>
    </jsp:attribute>
</custom:sidebar>