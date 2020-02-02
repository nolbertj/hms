<custom:accordion isOpened="${false}" id="ambulatorio" cardTitle="Ambulatorio">
    <jsp:body>
        <div class="profile-view row">
            <div class="profile-img-wrap col-md-2 pl-0">
                <div class="profile-img-container">
                    <img src="${img}/ambulatorio.jpg" alt="AMB_PHOTO">
                </div>
            </div>
            <div class="profile-basic row flex-fill">
                <div class="profile-info-left col-md-4">
                    <h3 class="user-name">${USER.ambulatorio.denominazione}</h3>
                    <p class="user-role">Ambulatorio</p>
                </div>
                <div class="personal-info col">
                    <ul>
                        <li>
                            <span class="title">Ambulatorio:</span>
                            <span class="text">${USER.ambulatorio.denominazione}</span>
                        </li>
                        <li>
                            <span class="title">Indirizzo:</span>
                            <span class="text">${USER.ambulatorio.indirizzo}</span>
                        </li>
                        <li>
                            <span class="title">Citt&agrave;:</span>
                            <span class="text">${USER.ambulatorio.citta}</span>
                        </li>
                        <li>
                            <span class="title">Provincia:</span>
                            <span class="text">${USER.ambulatorio.provincia}</span>
                        </li>
                        <li>
                            <span class="title">Contatto principale:</span>
                            <span class="text">${USER.ambulatorio.contattoTelefonico}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:body>
</custom:accordion>

