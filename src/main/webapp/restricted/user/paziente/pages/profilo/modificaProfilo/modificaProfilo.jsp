<%--
    Document: modificaProfilo.jsp
    Created on: October 28, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/user/paziente/pages/profilo/modificaProfilo" scope="page"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Modifica profilo">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/modificaProfilo.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <SCRIPT type="text/javascript" src="${js}/utils.js"></SCRIPT>
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <form action="${cp}/${modificaProfiloURL}" method="POST" enctype="multipart/form-data" id="modificaProfiloForm">
                <div class="form-group">
                    <label for="contattoPrincipale" class="custom-label">Contatto principale</label>
                    <input type="text" class="form-control w-25" id="contattoPrincipale" name="contattoPrincipale" placeholder="${USER.contattoTelefonico}">
                </div>
                <div class="form-group">
                    <label for="contattoEmergenza" class="custom-label">Contatto d'emergenza</label>
                    <input type="text" class="form-control w-25" id="contattoEmergenza" name="contattoEmergenza" placeholder="${USER.contattoEmergenza}">
                </div>
                <hr>
                <div data-role="dynamic-fields">
                    <span class="custom-label">Foto profilo</span>
                    <div class="dynamic-buttons text-center">
                        <span class="form-control my-2 file">Nessun documento selezionato</span>
                        <label class="btn btn-primary" for="inputFile" style="cursor: pointer">
                            Scegli file
                            <input type="file" id="inputFile" class="files-submit custom-input" accept="image/x-png,image/jpeg" name="foto" onchange="getFileTitle(this)">
                        </label>
                        <button class="btn btn-danger" type="button" data-role="remove">CANCELLA</button>
                    </div>
                </div>
            </form>
            <hr>
            <div class="text-center">
                <button type="submit" class="btn btn-success btn-lg" form="modificaProfiloForm" id="confirmBTN">
                    Conferma modifiche
                </button>
            </div>
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>