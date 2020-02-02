console.log("loading utils.js ...");
//======================================================================================================================
function setInputFilter(textbox, inputFilter) {
    ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
        textbox.addEventListener(event, function() {
            if (inputFilter(this.value)) {
                this.oldValue = this.value;
                this.oldSelectionStart = this.selectionStart;
                this.oldSelectionEnd = this.selectionEnd;
            } else if (this.hasOwnProperty("oldValue")) {
                this.value = this.oldValue;
                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
            }
        });
    });
}
//======================================================================================================================
/**
 * Ritorna true se la value contiene solo lettere e spazi, false altrimenti.
 * */
function onlyCharsAndWhiteSpace(value){
    return  /^[A-Za-z ]+$/.test(value);
}
/**
 * Ritorna true se la value contiene solo numeri, false altrimenti
 * */
function onlyDigits(value){
    return /^\d*$/.test(value);
}
//======================================================================================================================
function addParamToForm(name,value,form) {
    $('<input>').attr('type','hidden')
                .attr('name',name)
                .attr('value',value)
                .appendTo(form);
    return true;
}
//======================================================================================================================
/*$(function() { //script per far funzionare le tooltip, da copiare all'interno di un tag <script> quando si vuole mettere la tooltip
    $("[data-toggle=tooltip]").tooltip();
    $('.text').tooltip({
        selector: "[data-toggle=tooltip]",
        container: "body"
    });
});
 */
//======================================================================================================================
console.log("utils.js loaded successfully!");