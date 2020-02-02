<%--
    Document: ricetta.jsp
    Created on: Novembre 10, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.configs.QRProps" %>
<%@ page import="it.unitn.disi.wp.project.hms.persistence.entities.Farmacia" %>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/commonPages/ricetta" scope="page"/>
<c:set var="QRextension" value="<%=QRProps.getExtension()%>" scope="page" />
<c:set var="pharmacyClassName" value="<%=Farmacia.class.getSimpleName()%>" scope="page"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="warningMSG" value="<%=request.getAttribute(Attr.WARNING_MSG)%>" scope="request"/>
<c:set var="errorMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<jsp:useBean id="ricetta" class="it.unitn.disi.wp.project.hms.persistence.entities.Ricetta" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Ricetta" includeDatatable="${true}">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/ricetta.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp"%>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <%-- ==================== ALERT MESSAGES ============================================================== --%>
            <c:if test="${USER.getClass().getSimpleName() eq pharmacyClassName}">
                <c:choose>
                    <c:when test="${successMSG eq true}">
                        <custom:alert typeAlert="success" message="Ricetta erogata correttamente."></custom:alert>
                    </c:when>
                    <c:when test="${warningMSG eq true}">
                        <custom:alert typeAlert="warning" message="Ricetta già erogata!"></custom:alert>
                    </c:when>
                    <c:when test="${errorMSG eq true}">
                        <custom:alert typeAlert="error" message="Impossibile erogare la ricetta selezionata!"></custom:alert>
                    </c:when>
                </c:choose>
            </c:if>
            <%-- ========================================================================================== --%>
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <custom:card title="Ricetta farmaceutica ${ricetta.codice}">
                <jsp:body>
                    <div class="row align-items-center">
                        <div class="col">
                            <img src="data:image/${QRextension};base64,${ricetta.binaryQR}" id="qr-code">
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">Codice ricetta: </span>
                            <span>${ricetta.codice}</span>
                            <hr>
                            <span class="font-weight-bold">Stato ricetta: </span>
                            <span class="font-weight-bold text-danger">
                                <c:choose>
                                    <c:when test="${not empty ricetta.dataErogazione}">EROGATA</c:when>
                                    <c:otherwise>NON EROGATA</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">
                            <span class="font-weight-bold">Data prescrizione:</span>
                            <span id="dataPrescrizione"></span>
                            <script>
                                $( ()=>{
                                    let dataPresc=parseAndShowTimestamp('${ricetta.dataPrescrizione}');
                                    $("#dataPrescrizione").text(dataPresc);
                                });
                            </script>
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">Medico prescrittore:</span>
                            <span>${ricetta.medicoPrescrittore}</span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">
                            <span class="font-weight-bold">Paziente: </span>
                            <span>${ricetta.paziente.nome}&nbsp;${ricetta.paziente.cognome}</span>
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">Codice fiscale:</span>
                            <span>${ricetta.paziente.codFiscale}</span>
                        </div>
                    </div>
                    <c:if test="${not empty ricetta.descrizione}">
                        <hr>
                        <div class="row">
                            <div class="col">
                                <span class="font-weight-bold">Descrizione</span><br>
                                <span>${ricetta.descrizione}</span>
                            </div>
                        </div>
                    </c:if>
                    <hr>
                    <span class="row col-12 font-weight-bold mb-2">Farmaci prescritti</span>
                    <%--------------------------------------------------------------------------------------------------------------------%>
                    <%---------------------------- TABELLA CON RIGHE PERSONALIZZATE (senza datatable) ------------------------------------%>
                    <%--------------------------------------------------------------------------------------------------------------------%>
                    <custom:table datatablePopulate="${false}">
                        <jsp:attribute name="body">
                             <thead>
                                 <tr>
                                     <th class="text-center">Cod.</th>
                                     <th>Nome</th>
                                     <th>Descrizione</th>
                                     <th class="text-center">Quantità</th>
                                     <th class="text-center">Prezzo</th>
                                     <th class="text-center">Totale</th>
                                     <th>Note</th>
                                 </tr>
                             </thead>
                            <tbody>
                                <c:forEach var="farmaco" items="${ricetta.farmaciPrescritti}">
                                    <tr>
                                        <th class="text-center">${farmaco.codice}</th>
                                        <th>${farmaco.nome}</th>
                                        <th>${farmaco.descrizione}</th>
                                        <th class="text-center">${farmaco.quantita}</th>
                                        <th class="text-center">
                                                <%-- In questo modo la valuta viene presa in automatico in base alla posizione (paese) e con i decimali --%>
                                            <fmt:formatNumber type="currency" value="${farmaco.prezzo}"/>
                                        </th>
                                        <th class="text-center">
                                            <fmt:formatNumber type="currency" value="${farmaco.totale}"/>
                                        </th>
                                        <th>${farmaco.note}</th>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </jsp:attribute>
                    </custom:table>
                    <%--------------------------------------------------------------------------------------------------------------------%>
                    <c:if test="${not empty ricetta.dataErogazione && not empty ricetta.farmaciaErogante}">
                        <div class="row">
                            <div class="col text-right font-weight-bold mr-5 mt-3">TOTALE:&nbsp;
                                <fmt:formatNumber type="currency" value="${ricetta.totale}"/>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col">
                                <span class="font-weight-bold" >Data erogazione:</span>
                                <span id="dataErogazione"></span>
                                <script>
                                    $( ()=>{
                                        let dataErog=parseAndShowTimestamp('${ricetta.dataErogazione}');
                                        $("#dataErogazione").text(dataErog);
                                    });
                                </script>
                            </div>
                            <div class="col">
                                <span class="font-weight-bold">Farmacia erogante:</span>
                                <span>${ricetta.farmaciaErogante}</span>
                            </div>
                        </div>
                    </c:if>
                    <hr>
                    <c:if test="${USER.getClass().getSimpleName() eq pharmacyClassName && (empty ricetta.dataErogazione)}">
                        <div class="float-right">
                            <form action="${cp}/${erogaRicettaURL}" id="erogaRicettaForm" method="POST">
                                <input type="hidden" name="id" value="${ricetta.codice}">
                                <input type="hidden" name="idPaziente" value="${ricetta.paziente.id}">
                            </form>
                            <button class="btn btn-danger btn-lg" type="submit" form="erogaRicettaForm">
                                <i class="fas fa-cash-register"></i>&nbsp;EROGA RICETTA
                            </button>
                        </div>
                    </c:if>
                </jsp:body>
            </custom:card>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>