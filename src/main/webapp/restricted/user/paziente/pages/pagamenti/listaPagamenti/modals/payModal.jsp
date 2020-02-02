<custom:modal id="payModal" title="Form pagamento" setDefaultCancelBUTTON="${true}">
    <jsp:attribute name="body">
        <form action="${cp}/${pagamentiURL}" method="POST" id="payForm">
            <span class="modal-body-title" id="payMethod">Seleziona il metodo di pagamento</span>
            <div class="row m-0 p-0">
                <div class="form-check">
                    <input class="radio-custom" name="payment" type="radio" id="paypalRadio" value="paypal">
                    <label class="radio-custom-label paypal-icon" for="paypalRadio">PayPal</label>
                </div>
                <div class="form-check">
                    <input class="radio-custom" name="payment" type="radio" id="mastercardRadio" value="mastercard">
                    <label class="radio-custom-label mastercard-icon" for="mastercardRadio">MasterCard</label>
                </div>
                <div class="form-check">
                    <input class="radio-custom" name="payment" type="radio" id="visaRadio" value="visa">
                    <label class="radio-custom-label visa-icon" for="visaRadio">Visa</label>
                </div>
            </div>
            <hr>
            <div id="creditCardInputs">
                <small class="d-flex justify-content-center text-muted mb-2">
                    Se effettui il pagamento con carta di credito inserisci i dati.
                </small>
                <div class="form-group">
                    <label class="modal-body-title" for="numeroCarta">Numero carta</label>
                    <input class="form-control form-control" id="numeroCarta" type="text" maxlength="16">
                    <small class="text-danger off" id="alertCard">Inserire i 16 numeri della carta!</small>
                </div>
                <div class="form-group">
                    <label class="modal-body-title" for="intestatario">Intestatario carta</label>
                    <input class="form-control form-control" id="intestatario" type="text" >
                </div>
                <label class="modal-body-title">Data di scadenza</label>
                <div class="form-row m-0">
                    <select class="form-control form-control-sm col-sm-1 mr-2" id="month" required></select>
                    <select class="form-control form-control-sm col-sm-2" id="year" required></select>
                </div>
            </div>
            <hr>
        </form>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <button type="submit" class="btn btn-success w-100 shadow" form="payForm">CONFERMA</button>
    </jsp:attribute>
</custom:modal>