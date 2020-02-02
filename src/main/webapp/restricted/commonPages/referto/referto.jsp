<%--
    Document: referto.jsp
    Created on: Novembre 14, 2019
    Front-end: Alessandro Brighenti
    Back-end: Alessandro Brighenti
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/commonPages/referto" scope="page"/>
<jsp:useBean id="referto" class="it.unitn.disi.wp.project.hms.persistence.entities.Referto" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Referto">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/referto.css">
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <custom:card title="Referto">
                <jsp:body>
                    <div class="row">
                        <div class="col">
                            <span class="font-weight-bold">Paziente: </span>
                            <span>${referto.paziente}</span>
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">Esame: </span>
                            <span>${referto.nomeEsame}</span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">
                            <span class="font-weight-bold">Medico prescrittore: </span>
                            <span>${referto.medicoPrescrivente}</span>
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">Medico erogatore: </span>
                            <span>${referto.medicoEsecutore}</span>
                        </div>
                    </div>
                    <hr>
                    <span class="row col-12 font-weight-bold mb-2">Anamnesi</span>
                    <div class="row">
                        <div class="col">
                            <span>${referto.anamnesi}</span>
                        </div>
                    </div>
                    <hr>
                    <span class="row col-12 font-weight-bold mb-2">Conclusioni</span>
                    <div class="row">
                        <div class="col">
                            <span>${referto.conclusioni}</span>
                        </div>
                    </div>
                    <hr>
                    <span class="row col-12 font-weight-bold mb-2">Data erogazione</span>
                    <div class="row">
                        <div class="col">
                            <span id="dataReferto"></span>
                                <script>
                                    $( ()=>{
                                        let dataErog=parseAndShowTimestamp('${referto.dataErogazione}');
                                        $("#dataReferto").text(dataErog);
                                    });
                                </script>
                        </div>
                    </div>
                    <hr>
                </jsp:body>
            </custom:card>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
