<custom:accordion isOpened="${false}" id="anagraficaPaziente" cardTitle="Anagrafica paziente">
    <jsp:body>
        <div class="profile-view row">
            <div class="profile-img-wrap col-md-2">
                <div class="profile-img-container">
                    <img src="${img}/patient.jpg" id="fotoPaziente" onerror="this.onerror=null;this.src='${img}/patient.jpg'">
                </div>
            </div>
            <div class="profile-basic row flex-fill">
                <div class="profile-info-left col-md-4">
                    <h3 class="user-name" id="nomePaziente">undefined</h3>
                    <h6 class="user-cf-piva" id="CFPaziente">undefined</h6>
                </div>
                <div class="personal-info col">
                    <ul>
                        <li>
                            <span class="title">Data di nascita:</span>
                            <span class="text" id="dataNascitaPaziente">undefined</span>
                        </li>
                        <li>
                            <span class="title">Luogo di nascita:</span>
                            <span class="text" id="luogoNascitaPaziente">undefined</span>
                        </li>
                        <li>
                            <span class="title">Sesso:</span>
                            <span class="text" id="sessoPaziente">undefined</span>
                        </li>
                        <li>
                            <span class="title">Citt&agrave;:</span>
                            <span class="text" id="cittaPaziente">undefined</span>
                        </li>
                        <li>
                            <span class="title">Provincia:</span>
                            <span class="text" id="provinciaPaziente">undefined</span>
                        </li>
                        <li>
                            <span class="title">Contatto principale:</span>
                            <span class="text" id="contattoTelefonicoPaziente">undefined</span>
                        </li>
                        <li>
                            <span class="title">e-mail:</span>
                            <span class="text" id="emailPaziente">undefined</span>
                        </li>
                        <hr>
                        <li>
                            <span class="title">Contatto d'emergenza:</span>
                            <span class="text" id="contattoEmergenzaPaziente">undefined</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:body>
</custom:accordion>