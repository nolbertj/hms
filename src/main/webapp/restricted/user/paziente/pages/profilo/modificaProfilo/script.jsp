<SCRIPT type="text/javascript">
    $(function() {
        //==============================================================================================================
        setInputFilter(document.getElementById("contattoPrincipale"), (value) => { return onlyDigits(value) });
        setInputFilter(document.getElementById("contattoEmergenza"), (value) => {return onlyDigits(value) });
        //==============================================================================================================
        let initialFileContainerText = document.getElementsByClassName("file")[0].innerText;
        $(document).on( //RIMUOVO container per upload file
            'click',
            '[data-role="dynamic-fields"] > .dynamic-buttons [data-role="remove"]',
            function(){
                let input = $("input[type='file']");
                input.html(input.html()).val('');
                document.getElementsByClassName("file")[0].innerText = initialFileContainerText;
            }
        );

        if($(document).width() < 992) {
            let inputs = document.getElementsByTagName("input");
            for(let i=0; i<inputs.length; i++) {
                console.log(inputs[i]);
                inputs[i].classList.remove("w-25");
            }
            $("#confirmBTN").addClass("w-100");
        }
    });
//======================================================================================================================
    function getFileTitle(event) {
        let fileName = event.files[0].name,
            fileNameContainer= event.parentElement.parentElement;
        fileNameContainer.getElementsByTagName("span").item(0).innerText = fileName;
    }
//======================================================================================================================
</SCRIPT>