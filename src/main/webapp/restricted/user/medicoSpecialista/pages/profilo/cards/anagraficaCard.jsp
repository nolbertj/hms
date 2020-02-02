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
                    <h3 class="user-name">${USER.nome}&nbsp;${USER.cognome}</h3>
                    <p class="user-role">Medico specialista</p>
                    <h6 class="user-cf-piva">C.F.: ${USER.codFiscale}</h6>
                </div>
                <div class="personal-info col">
                    <ul>
                        <li>
                            <span class="title">Data di nascita:</span>
                            <span class="text" id="dataNascita"></span>
                            <script>
                                $( ()=>{
                                    let dataNascita=parseAndShowDates('${USER.dataNascita}');
                                    $("#dataNascita").text(dataNascita);
                                });
                            </script>
                        </li>
                        <li>
                            <span class="title">Luogo di nascita:</span>
                            <span class="text">${USER.luogoNascita}</span>
                        </li>
                        <li>
                            <span class="title">Provincia di nascita:</span>
                            <span class="text">${USER.provincia}</span>
                        </li>
                        <li>
                            <span class="title">Sesso:</span>
                            <span class="text">${USER.sesso}</span>
                        </li>
                        <li>
                            <span class="title">e-mail:</span>
                            <span class="text">${USER.email}</span>
                        </li>
                        <li>
                            <span class="title">Specialita:</span>
                            <span class="text">${USER.specialita}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:body>
</custom:accordion>