<custom:accordion isOpened="${true}" id="anagrafica" cardTitle="Dati anagrafici">
    <jsp:body>
        <div class="profile-view row">
            <div class="profile-img-wrap col-md-2">
                <div class="profile-img-container">
                    <img src="${img}/restricted/${userFoldername}/${USER.getUsername()}/${USER.avatarFilename}" alt="FOTO PROFILO">
                </div>
            </div>
            <div class="profile-basic row flex-fill">
                <div class="profile-info-left col-md-4">
                    <h3 class="user-name">${USER.abbreviazione}</h3>
                    <p class="user-role">Servizio Sanitario</p>
                </div>
                <div class="personal-info col">
                    <ul>
                        <li>
                            <span class="title">Nome completo:</span>
                            <span class="text">${USER.nome}</span>
                        </li>
                        <li>
                            <span class="title">Provincia:</span>
                            <span class="text">${USER.provincia}</span>
                        </li>
                        <li>
                            <span class="title">e-mail:</span>
                            <span class="text">${USER.email}</span>
                        </li>
                        <li>
                            <span class="title">Numero di telefono:</span>
                            <span class="text">${USER.numeroTelefono}</span>
                        </li>
                        <li>
                            <span class="title">Numero di Pazienti:</span>
                            <span class="text">${USER.numeroPazienti}</span>
                        </li>
                        <li>
                            <span class="title">Numero di medici di base:</span>
                            <span class="text">${USER.numeroMediciBase}</span>
                        </li>
                        <li>
                            <span class="title">Numero di medici specialisti:</span>
                            <span class="text">${USER.numeroMediciSpecialisti}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:body>
</custom:accordion>