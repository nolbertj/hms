<%--
    Document: dashboard.jsp
    Created on: November 29, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.admin.QueriesServlet" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:set var="userFoldername" value="<%=Attr.USER_FOLDER.ADMIN.getName()%>" scope="session"/>
<c:url var="absolutePath" value="/restricted/user/admin/pages/dashboard" scope="page"/>
<c:url var="dashboardURL" value="<%=DashboardServlet.getURL()%>" scope="session"/>
<c:url var="queriesServletURL" value="<%=QueriesServlet.getURL()%>" scope="session"/>
<c:set var="query" value='<%=request.getAttribute("query")%>' scope="request"/>
<c:set var="columnNames" value='<%=request.getAttribute("columnNames")%>' scope="request"/>
<c:set var="cols" value='<%=request.getAttribute("cols")%>' scope="request"/>
<c:set var="msg" value='<%=request.getAttribute("msg")%>' scope="request"/>

<jsp:useBean id="USER" class="it.unitn.disi.wp.project.hms.persistence.entities.User" scope="session"></jsp:useBean>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Dashboard" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" href="${absolutePath}/dashboard.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <SCRIPT>
            $(()=>{
               // let sidebarWidth = getComputedStyle(document.documentElement).getPropertyValue('--sidebar-open-width').replace(' ','');
               // document.documentElement.style.setProperty('--sidebar-open-width','calc('+sidebarWidth+' + 2rem)');
                $("#TAB").DataTable({
                    responsive: true,
                    pageLength: 50
                });
                document.getElementById("textArea").value='${query}';
                let audio = document.getElementsByTagName("audio")[0];
                $("#runBTN").on('mouseenter',()=>audio.play());
                $("#runBTN").on('mouseleave',()=>audio.pause());

                <c:if test="${not empty columnNames}">
                    setTimeout(()=>document.getElementById("compactTableBTN").click(),1000);
                </c:if>
            });
        </SCRIPT>
    </jsp:attribute>

    <jsp:body>
    <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
    <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
    <%------------------------------------------------------------------------------------------------------------%>
            <form action="${cp}/${queriesServletURL}" method="POST" id="queriesForm">
                <div class="form-group">
                    <label for="textArea" class="font-weight-bold mb-1">QUERY TOOL</label>
                    <textarea class="form-control" id="textArea" name="textArea" rows="5"></textarea>
                    <audio><source src="${cp}/media/run.mp3"></audio>
                    <button class="btn btn-success mt-3 w-100" type="submit" id="runBTN">
                        <i class="fas fa-play"></i>&nbsp;RUN
                    </button>
                </div>
            </form>
            <label class="font-weight-bold mb-1">OUTPUT</label>
            <div id="outputContainer">
                <c:choose>
                    <c:when test="${not empty columnNames && empty msg}">
                        <custom:table datatablePopulate="${false}">
                            <jsp:attribute name="body">
                                 <thead>
                                    <tr>
                                        <c:forEach var="col" items="${columnNames}">
                                            <th class="font-weight-bold">${col.name}</th>
                                        </c:forEach>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="nrColumns" value='${fn:length(columnNames)}' scope="request"/>
                                    <c:forEach var="c" items="${cols}" varStatus="status">
                                        <c:if test="${status.index mod nrColumns eq 0}"><tr></c:if>
                                            <th class="font-weight-normal">${c.name}</th>
                                        <c:if test="${(status.index+1) mod nrColumns eq 0}"></tr></c:if>
                                    </c:forEach>
                                </tbody>
                            </jsp:attribute>
                        </custom:table>
                    </c:when>
                    <c:when test="${not empty msg && empty columnNames}">
                        <span>${msg}</span>
                    </c:when>
                </c:choose>
            </div>
    <%------------------------------------------------------------------------------------------------------------%>
        </div>
    <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
