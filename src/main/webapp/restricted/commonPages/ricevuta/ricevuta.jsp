<%--
    Document: ricevuta.jsp
    Created on: Novembre 10, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page import="it.unitn.disi.wp.project.hms.commons.configs.QRProps" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/restricted/commonPages/ricevuta" scope="page"/>
<c:set var="QRextension" value="<%=QRProps.getExtension()%>" scope="page"/>
<jsp:useBean id="ricevuta" class="it.unitn.disi.wp.project.hms.persistence.entities.Ricevuta" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<custom:userInterface pageTitle="Ricevuta">

    <jsp:attribute name="customStylesheets">
        <LINK rel="stylesheet" type="text/css" href="${absolutePath}/ricevuta.css">
    </jsp:attribute>
    <jsp:attribute name="customScripts">
        <%@ include file="script.jsp" %>
    </jsp:attribute>

    <jsp:body>
        <%------------------------------------------------------------------------------------------------------------%>
        <%@ include file="header.jsp" %>
        <%------------------------------------------------------------------------------------------------------------%>
        <div id="content">
            <!-- ============== CONTENUTO PAGINA =================================================================== -->
            <custom:card title="Ricevuta ${ricevuta.idRicevuta}">
                <jsp:body>
                    <div class="row align-items-center">
                        <div class="col">
                            <img src="data:image/${QRextension};base64,${ricevuta.binaryQR}" id="qr-code">
                        </div>
                        <div class="col">
                            <span class="font-weight-bold text-danger">
                                <c:choose>
                                    <c:when test="${not empty ricevuta.dataPagamento}">PAGATO</c:when>
                                    <c:otherwise>NON PAGATO</c:otherwise>
                                </c:choose>
                            </span>
                            <br>
                            <span class="font-weight-bold">Numero di riferimento del pagamento: </span>
                            <span>${ricevuta.idRicevuta}</span>
                            <br>
                            <span class="font-weight-bold">Data pagamento:</span>
                            <span id="dataPagamento"></span>
                            <script>
                                $( ()=>{
                                    let dataPag=parseAndShowDates('${ricevuta.dataPagamento}');
                                    $("#dataPagamento").text(dataPag);
                                });
                            </script>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">
                            <span class="font-weight-bold">Paziente:</span>
                            <span>${ricevuta.paziente.nome} ${ricevuta.paziente.cognome}</span>
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">C.F.:</span>
                            <span>${ricevuta.paziente.codFiscale}</span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">
                            <span class="font-weight-bold">Causale</span><br>
                            <span>
                                <c:choose>
                                    <c:when test="${ricevuta.isEsame eq true and ricevuta.isRicetta eq false}">
                                        Esame specialistico
                                    </c:when>
                                    <c:when test="${ricevuta.isEsame eq false and ricevuta.isRicetta eq true}">
                                        Ricetta farmaceutica
                                    </c:when>
                                    <c:otherwise>Altro</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">Cod. riferimento:</span>
                            <span>${ricevuta.idCausale}</span>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col">
                            <span class="font-weight-bold">Metodo di pagamento: </span>
                            <span>${ricevuta.metodo}</span>
                        </div>
                        <div class="col">
                            <span class="font-weight-bold">Ticket:</span>
                            <span><fmt:formatNumber type="currency" value="${ricevuta.importo}"/></span>
                        </div>
                    </div>
                </jsp:body>
            </custom:card>
            <!-- =================================================================================================== -->
        </div>
        <%------------------------------------------------------------------------------------------------------------%>
    </jsp:body>
</custom:userInterface>
