<%--
    Document: altriDocumenti.jsp
    Created on: Novembre 24, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/paziente/pages/documenti/altriDocumenti" scope="page"/>

<jsp:useBean id="documenti"
             type="java.util.List<it.unitn.disi.wp.project.hms.persistence.entities.Documento>"
             scope="request">
</jsp:useBean>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Altri" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/altriDocumenti.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <SCRIPT type="text/javascript" src="${js}/utils.js"></SCRIPT>
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <custom:card title="Altri documenti">
                <jsp:body>
                    <form action="${cp}/${documentiURL}" method="POST" id="downloadForm"></form>
                    <form action="${cp}/${documentiURL}" method="POST" id="deleteForm"></form>
                    <form action="${cp}/${documentiURL}" method="POST" id="showForm" target="_blank"></form>
                    <custom:table addBTN="${true}" idModalAddBTN="addModal" datatablePopulate="${false}">
                        <jsp:attribute name="body">
                            <thead>
                                <tr>
                                    <th>Titlo</th>
                                    <th>Descrizione</th>
                                    <th class="text-center">Data caricamento</th>
                                    <th class="text-center">Azioni</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="doc" items="${documenti}">
                                    <tr>
                                        <th>${doc.titolo}</th>
                                        <th>${doc.descrizione}</th>
                                        <th class="text-center">${doc.dataCaricamento}</th>
                                        <th class="text-center">
                                            <div class="btn-group" role="group">
                                                <button title="SCARICA DOCUMENTO" type="submit" class="btn btn-success"
                                                        onclick="return addParamToForm('filename','${doc.filename}','#downloadForm');"
                                                        form="downloadForm">
                                                    <i class="fas fa-download"></i>
                                                </button>
                                                <button title="ELIMINA DOCUMENTO" type="submit" class="btn btn-danger"
                                                        onclick="return addParamToForm('filename','${doc.filename}','#deleteForm');"
                                                        form="deleteForm">
                                                    <i class="fas fa-minus-circle"></i>
                                                </button>
                                                <button title="AGGIORNA DOCUMENTO" type="button" class="btn btn-warning"
                                                        data-toggle="modal" data-target="#updateModal"
                                                        onclick="putPlaceholder('${doc.titolo}','${doc.descrizione}','${doc.filename}');">
                                                    <i class="fas fa-wrench"></i>
                                                </button>
                                                <button title="VISUALIZZA DOCUMENTO" type="submit" class="btn btn-primary"
                                                        onclick="return addParamToForm('filename','${doc.filename}','#showForm');"
                                                        form="showForm">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                            </div>
                                        </th>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </jsp:attribute>
                    </custom:table>
                </jsp:body>
            </custom:card>
            <!-- =================================================================================================== -->
            <!-- ============== FINESTRE MODALI ==================================================================== -->
            <!-- =================================================================================================== -->
            <%@ include file="modals/addDocumentModal.jsp" %>
            <%@ include file="modals/updateDocumentModal.jsp" %>
            <!-- =================================================================================================== -->
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>