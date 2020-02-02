<custom:modal id="updateModal" title="Aggiorna documento" setDefaultCancelBUTTON="${true}">

    <jsp:attribute name="body">
        <form action="${cp}/${documentiURL}" method="POST" id="updateFileForm">
            <label class="modal-body-title" for="filename2">File</label>
            <input class="form-control" id="filename2" type="text" name="filename" readonly>
            <hr>
            <label class="modal-body-title" for="titolo2">Titolo</label>
            <input class="form-control" id="titolo2" type="text" name="titolo">
            <hr>
            <label class="modal-body-title" for="descrizione2">Breve descrizione</label>
            <input class="form-control" id="descrizione2" type="text" name="descrizione">
        </form>
    </jsp:attribute>

    <jsp:attribute name="footer">
        <button type="submit" class="btn btn-success w-100 shadow" form="updateFileForm">
            <i class="fas fa-wrench"></i>
            AGGIORNA
        </button>
    </jsp:attribute>

</custom:modal>