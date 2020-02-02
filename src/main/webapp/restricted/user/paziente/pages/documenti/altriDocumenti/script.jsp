<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.DocumentiServlet" %>

<SCRIPT type="text/javascript">

    function getFileTitle(event) {
        let fileName = event.files[0].name, fileNameContainer = event.parentElement.parentElement;
        fileNameContainer.getElementsByTagName("span").item(0).innerText = fileName;
    }

    function checkFileForm() {
        if(document.getElementById("inputFile").files.length === 0){
            $(".dynamic-buttons label").css("background","var(--danger)");
            return false;
        }
        else return true;
    }

    function putPlaceholder(title,description,filename) {
        $("#filename2").attr("value",filename);
        $("#titolo2").attr("placeholder",title);
        $("#descrizione2").attr("placeholder",description);
    }

    const ACTION = {
        DOWNLOAD: '<%=DocumentiServlet.ACTION.DOWNLOAD.getValue()%>',
        UPLOAD: '<%=DocumentiServlet.ACTION.UPLOAD.getValue()%>',
        UPDATE: '<%=DocumentiServlet.ACTION.UPDATE.getValue()%>',
        SHOW: '<%=DocumentiServlet.ACTION.SHOW.getValue()%>',
        DELETE: '<%=DocumentiServlet.ACTION.DELETE.getValue()%>',
    };

    const _action = '<%=DocumentiServlet.ACTION.class.getSimpleName().toLowerCase()%>';

    $(function() {

        $("#TAB").DataTable({
            order: [[2,"desc"]],
            columnDefs: [{targets: 3, orderable: false}]
        });

        let initialFileContainerText = document.getElementsByClassName("file-title")[0].innerText;
        $(document).on( //RIMUOVO container per upload file
            'click', '[data-role="dynamic-fields"] > .dynamic-buttons [data-role="remove"]',
            function () {
                let input = $("input[type='file']");
                input.html(input.html()).val('');
                document.getElementsByClassName("file-title")[0].innerText = initialFileContainerText;
            }
        );

        $("#downloadForm").on('submit', function() {
            return addParamToForm(_action,ACTION.DOWNLOAD,'#downloadForm');
        });
        $("#uploadFileForm").on('submit', function() {
           return addParamToForm(_action,ACTION.UPLOAD,'#uploadFileForm');
        });
        $("#updateFileForm").on('submit', function() {
            return addParamToForm(_action,ACTION.UPDATE,'#updateFileForm');
        });
        $("#deleteForm").on('submit', function() { //mettere alert di onferma
            addParamToForm(_action,ACTION.DELETE,'#deleteForm');
            if(confirm("Sicuro di voler eliminare il file?") == true)
                return true;
            else return false;
        });
        $("#showForm").on('submit', function() {
            return addParamToForm(_action,ACTION.SHOW,'#showForm');
        });

    });

</SCRIPT>