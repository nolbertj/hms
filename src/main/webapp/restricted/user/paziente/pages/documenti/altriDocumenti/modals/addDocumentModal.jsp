<custom:modal id="addModal" title="Aggiungi documento" setDefaultCancelBUTTON="${true}">

    <jsp:attribute name="body">

        <form action="${cp}/${documentiURL}" method="POST" enctype="multipart/form-data" id="uploadFileForm">
            <label class="modal-body-title" for="titolo">Titolo</label>
            <input class="form-control" id="titolo" type="text">
            <hr>
            <label class="modal-body-title" for="descrizione">Breve descrizione</label>
            <input class="form-control" id="descrizione" type="text">
            <hr>
            <div data-role="dynamic-fields">
                <div class="row no-gutters dynamic-buttons">
                    <label class="btn btn-primary w-100" for="inputFile">
                        Scegli file
                        <input type="file" name="fileUpload" id="inputFile" class="files-submit custom-input"
                               accept="application/pdf" onchange="getFileTitle(this)">
                    </label>
                    <span class="form-control file-title">Nessun documento selezionato</span>
                    <div class="row mt-3 justify-content-between w-100 no-gutters">
                        <div class="col d-flex align-items-center mb-0" style="cursor: pointer">
                            <ul class="row mb-0">
                                <li class="col custom-control custom-checkbox">
                                    <input class="custom-control-input" type="checkbox" id="compress" name="compress">
                                    <label for="compress" class="custom-control-label"
                                           style="cursor: pointer">
                                        Compress PDF
                                    </label>
                                </li>
                            </ul>
                        </div>
                        <button class="col btn btn-link" type="button" data-role="remove">CANCELLA</button>
                    </div>
                </div>
            </div>
        </form>

    </jsp:attribute>

    <jsp:attribute name="footer">
        <button type="submit" class="btn btn-success w-100 shadow" onclick="return checkFileForm();" form="uploadFileForm">
            <i class="fas fa-upload"></i>
            CARICA
        </button>
    </jsp:attribute>

</custom:modal>