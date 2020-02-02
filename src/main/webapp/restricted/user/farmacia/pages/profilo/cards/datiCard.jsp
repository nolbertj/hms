<custom:accordion isOpened="${true}" id="anagrafica" cardTitle="Dati">
    <jsp:body>
        <div class="profile-view row">
            <div class="profile-img-wrap col-md-2">
                <div class="profile-img-container">
                    <img src="${img}/restricted/${userFoldername}/${USER.avatarFilename}" alt="FOTO PROFILO">
                </div>
            </div>
            <div class="profile-basic row flex-fill">
                <div class="profile-info-left col-md-4">
                    <h3 class="user-name">${USER.nome}</h3>
                    <p class="user-role">Farmacia</p>
                    <h6 class="user-cf-piva">P.IVA.: ${USER.partitaIva}</h6>
                </div>
                <div class="personal-info col">
                    <ul>
                        <li>
                            <span class="title">Citt&agrave;:</span>
                            <span class="text">${USER.citta}</span>
                        </li>
                        <li>
                            <span class="title">Provincia:</span>
                            <span class="text">${USER.provincia}</span>
                        </li>
                        <li>
                            <span class="title">Email:</span>
                            <span class="text">${USER.email}</span>
                        </li>
                        <li>
                            <span class="title">Contatto:</span>
                            <span class="text">${USER.contattoTelefonico}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:body>
</custom:accordion>