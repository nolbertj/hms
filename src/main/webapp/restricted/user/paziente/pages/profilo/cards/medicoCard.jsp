<custom:accordion isOpened="${false}" id="medico" cardTitle="Medico di base">
    <jsp:body>
        <div class="profile-view row">
            <div class="profile-img-wrap col-md-2 pl-0">
                <div class="profile-img-container">
                    <img src="${img}/restricted/<c:out value="<%=Attr.USER_FOLDER.BASE_DOCTOR.getName()%>"/>/${USER.medicoBase.getUsername()}/${USER.medicoBase.avatarFilename}" alt="DOCTOR_PHOTO">
                </div>
                <div><a href="${cp}/${modificaMedicoURL}" class="btn btn-primary" role="button" aria-pressed="true">
                    CAMBIA MEDICO</a>
                </div>
            </div>
            <div class="profile-basic row flex-fill">
                <div class="profile-info-left col-md-4">
                    <h3 class="user-name">${USER.medicoBase.nome}&nbsp;${USER.medicoBase.cognome}</h3>
                    <p class="user-role">Medico di base</p>
                </div>
                <div class="personal-info col">
                    <ul>
                        <li>
                            <span class="title">Ambulatorio:</span>
                            <span class="text">${USER.medicoBase.ambulatorio.denominazione}</span>
                        </li>
                        <li>
                            <span class="title">Indirizzo:</span>
                            <span class="text">${USER.medicoBase.ambulatorio.indirizzo}</span>
                        </li>
                        <li>
                            <span class="title">Citt&agrave;:</span>
                            <span class="text">${USER.medicoBase.ambulatorio.citta}</span>
                        </li>
                        <li>
                            <span class="title">Provincia:</span>
                            <span class="text">${USER.medicoBase.ambulatorio.provincia}</span>
                        </li>
                        <li>
                            <span class="title">Contatto principale:</span>
                            <span class="text">${USER.medicoBase.ambulatorio.contattoTelefonico}</span>
                        </li>
                        <li>
                            <span class="title">e-mail:</span>
                            <span class="text">${USER.medicoBase.email}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:body>
</custom:accordion>

