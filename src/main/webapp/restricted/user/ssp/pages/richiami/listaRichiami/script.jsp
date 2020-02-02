<%@ page import="it.unitn.disi.wp.project.hms.services.SspService" %>

<SCRIPT type="text/javascript">
    //======================================================================================================================
    $(()=> {
        let dt = $("#TAB").DataTable({
            serverSide: true,
            ajax: {
                url: "${cp}/<%=SspService.getURL()%>/${USER.id}/richiami",
                data: function(dati){
                    console.log(dati);
                    return dati;
                },
                error: (e)=>console.log(e)
            },
            order: [[4,"asc"]],
            searchable: false,
            columns: [
                { data: "idRichiamo",   title: "ID Richiamo",   className: 'text-center'},
                { data: "nome",         title: "Nome esame" },
                { data: "codice",       title: "Codice esame",  className: 'text-center', searchable: false },
                { data: "area",         title: "Reparto",       className: 'text-center' },
                { data: "dataPrescrizione", title: "Data di Richiesta", className: 'text-center', render: function(data,type,row,meta){
                        return parseAndShowDates(row["dataPrescrizione"]);
                    } },
                { data: "etaInizio",    title: "Da et&agrave",      searchable: false, orderable: false, className: 'text-center'},
                { data: "etaFine",      title: "Fino a et&agrave;", searchable: false, orderable: false, className: 'text-center'}
            ]
        });

        if($(window).width()<992){
            dt.colReorder.move(0,1);
        }
    });
    //======================================================================================================================
</SCRIPT>