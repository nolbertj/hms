<%--
    Document: table.tag
    Created on: November 6, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Table Template" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ attribute name="id"
              required="false"
              description="id da assegnare alla tabella"
%>
<%@ attribute name="otherButtons"
              required="false"
              fragment="true"
              description="Invocare quest'attributo se si ha intenzione di aggungere altri pulsanti nello header della tabella."
%>
<%@ attribute name="addBTN"
              required="false"
              type="java.lang.Boolean"
              description="porre ${true} o ${false} se la tabella dovrà contenere il pulsante di tipo +Aggiungi"
%>
<%@ attribute name="idModalAddBTN"
              required="false"
              description="id della finestra modale che il pulsante addBTN dovrà aprire"
%>
<%@ attribute name="exportBTN"
              required="false"
              type="java.lang.Boolean"
              description="porre true o false se la tabella dovrà contenere il pulsante ->Esporta"
%>
<%@ attribute name="idExportForm"
              required="false"
              description="id del form che farà esportare i dati"
%>
<%@ attribute name="exportHasChilds"
              required="false"
              type="java.lang.Boolean"
              description="porre ${true} o ${false} se il pulsante esporta dovrà avere un menu"
%>
<%@ attribute name="exportChilds"
              required="false"
              type="java.util.ArrayList"
              description="<br>Quest'attributo serve per porre gli elementi del menu exportBTN. <br>
              Creare un oggetto di tipo Map ponendo l'attributo nome e icona (di FontAwesome). <br>
              <a href='http://www.java2s.com/Tutorial/Java/0380__JSTL/UseForEachtoLoopThroughArrayList.htm'>Seguire quest'esempio</a>"
%>
<%@ attribute name="datatablePopulate"
              required="true"
              description="porre ${true} o ${false} se i dati verranno popolato con la Datatable"
%>
<%@ attribute name="body"
              required="false"
              fragment="true"
              description="inovca quest'attributo se si vogliono scrivere le colonne e le righe a manina (quindi senza datatable)."
%>
<%
    final String HTMLexportBTN = "<i class=\"fas fa-file-export\"></i><span>Esporta</span>";
    request.setAttribute("HTMLexportBTN",HTMLexportBTN);
%>

<div class="row">
    <div class="col-md-12">
        <div id="table-panel">
            <c:if test="${not empty otherButtons}">
                <jsp:invoke fragment="otherButtons"/>
            </c:if>
            <c:if test="${addBTN}">
                <button title="AGGIUNGI" class="btn mr-3" type="button" id="aggiungiBTN"
                        data-toggle="modal" data-target="#${idModalAddBTN}">
                    <i class="fas fa-plus"></i>
                    <span>Aggiungi</span>
                </button>
            </c:if>
            <c:choose>
                <c:when test="${exportBTN && (!exportHasChilds || empty exportHasChilds)}">
                    <button title="ESPORTA" class="btn mr-3" type="submit" id="exportBTN"
                            form="${idExportForm}">
                        ${HTMLexportBTN}
                    </button>
                </c:when>
                <c:when test="${exportBTN && exportHasChilds}">
                    <div class="dropdown" id="exportBTN">
                        <button title="ESPORTA" class="btn dropdown-toggle mr-3" type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            ${HTMLexportBTN}
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <button class="btn" type="button" data-toggle="modal" data-target="#${idModalExportBTN}">
                                <c:forEach var="child" items="${exportChilds}">
                                    <button class="btn" type="submit">
                                        <span>
                                            <i class="${child.icona}"></i>
                                            <span>${child.nome}</span>
                                        </span>
                                    </button>
                                </c:forEach>
                                <button class="btn" type="button" data-toggle="modal" data-target="#${idModalExportBTN}">
                                    <span>
                                        <i class="fas fa-file-alt"></i>
                                        <span>Personalizza</span>
                                    </span>
                                </button>
                            </button>
                        </div>
                    </div>
                </c:when>
            </c:choose>
            <button class="btn mr-3" type="button" id="compactTableBTN" title="Reduce table view">
                <i class="fas fa-compress-arrows-alt"></i>
                <span>Ridimensiona</span>
            </button>
        </div>
        <table <c:choose>
                    <c:when test="${empty id}"> id="TAB" </c:when>
                    <c:otherwise> id="${id}" </c:otherwise>
                </c:choose>
                class="table table-striped table-borderless table-hover dt-responsive w-100">
            <c:if test="${datatablePopulate eq false}">
                <jsp:invoke fragment="body"/>
            </c:if>
        </table>
        <div id="table-status"></div>
    </div>
</div>

<script>
    $(function() { //questo script va qui perchè è l'ultimo che dovrà essere eseguito
        //  Poichè nel parametro 'dom' di datatable posso specificare solo la posizione degli elementi della tabella
        // (esempio, dove stanno i numeri dell epagine, dove sta la casella in cui scegliere il nr di righe da visualizzare, etc..)
        // ho optato per lasciare il parametro 'dom' di default e stilizzare, muovere gli elementi della tabella con js

        let arrowUP = document.createElement('i');
        arrowUP.className = "fas fa-caret-up";

        //ATTENZIONE, i seguenti id (tipo TAB_length label e successivi) vengono creati in runtime appena verrà creata la datatable

        $("#TAB_length label").append(arrowUP);

        $("#TAB_filter").prependTo("#table-panel");
        $("#TAB_length").prependTo("#table-status");
        $("#TAB_info").appendTo("#table-status");
        $("#TAB_paginate").appendTo("#table-status");

        $("#TAB_filter input").on('keyup', function() {
            if($("#TAB_filter label input").val() < 1)
                $("#TAB_filter label input").css("border-bottom","");
            else
                $("#TAB_filter label input").css("border-bottom","1px solid white");
        });

        if($(window).width()<992){
            if($(".accordion")) {
                $(".card-title").on('click',()=> {
                    $(".page-item.last").trigger('click');
                    setTimeout(()=>{
                        $(".page-item.first").trigger('click');
                    },10);
                });
            }
        }

        let compact = $("#compactTableBTN"); //pulsante per ridimensionare tabella

        compact.on('click', function () {
            $("#TAB").toggleClass("table-sm");

            let compactIcon = compact.children()[0].classList[1];
            if (compactIcon === "fa-compress-arrows-alt")
                compact.children()[0].className = "fas fa-expand-arrows-alt";
            else if (compactIcon === "fa-expand-arrows-alt")
                compact.children()[0].className = "fas fa-compress-arrows-alt";
        });

        if(document.getElementsByClassName("dt-buttons btn-group")[0]) {
            document.getElementsByClassName("dt-buttons btn-group")[0].id = "showColumnBTN";
            $("#showColumnBTN button").removeClass("btn-secondary");
            let columnsIcon = document.createElement('i');
            columnsIcon.className = "fas fa-columns";
            let _showColumnBTN = document.getElementsByClassName("btn buttons-collection dropdown-toggle buttons-colvis")[0];
            _showColumnBTN.prepend(columnsIcon);
            let showColumnText = _showColumnBTN.getElementsByTagName("span")[0];
            showColumnText.innerText = "Visualizza";
            $("#showColumnBTN").appendTo("#table-panel");
        }
    });
</script>