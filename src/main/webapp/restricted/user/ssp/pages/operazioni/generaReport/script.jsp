<SCRIPT type="text/javascript">
    $(()=> {
        //==============================================================================================================
        let today = new Date(), dd = today.getDate(), mm = today.getMonth()+1, yyyy = today.getFullYear();
        if(dd < 10){
            dd = '0' + dd;
        }
        if(mm < 10) {
            mm = '0' + mm;
        }
        today = yyyy + '-' + mm + '-' + dd;
        document.getElementById("data").setAttribute("max", today);

        if($(window).width() < 992) {
            $("#data").removeClass("col-3").addClass("col");
        }
        //==============================================================================================================
    });
</SCRIPT>