<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<c:set var="pazienteFoldername" value="<%=Attr.USER_FOLDER.PATIENT.getName()%>" scope="page"/>
<custom:accordion isOpened="${true}" id="anagraficaPaziente" cardTitle="Anagrafica paziente">
    <jsp:body>
        <div class="profile-view row">
            <div class="profile-img-wrap col-md-2">
                <div class="profile-img-container">
                    <img src="${img}/restricted/${pazienteFoldername}/${paziente.getUsername()}/${paziente.avatarFilename}"
                         onError="this.onerror=null;this.src='${img}/patient.jpg'"
                         alt="FOTO PROFILO">
                </div>
            </div>
            <div class="profile-basic row flex-fill">
                <div class="profile-info-left col-md-4">
                    <h3 class="user-name">${paziente.nome}&nbsp;${paziente.cognome}</h3>
                    <h6 class="user-cf-piva">${paziente.codFiscale}</h6>
                </div>
                <div class="personal-info col">
                    <ul>
                        <li>
                            <span class="title">Data di nascita:</span>
                            <span class="text" id="dataNascita"></span>
                            <script>
                                $( ()=>{
                                    let dataNascita=parseAndShowDates('${paziente.dataNascita}');
                                    $("#dataNascita").text(dataNascita);
                                });
                            </script>
                        </li>
                        <li>
                            <span class="title">Luogo di nascita:</span>
                            <span class="text">${paziente.luogoNascita}</span>
                        </li>
                        <li>
                            <span class="title">Sesso:</span>
                            <span class="text">${paziente.sesso}</span>
                        </li>
                        <li>
                            <span class="title">Citt&agrave;:</span>
                            <span class="text">${paziente.cittaResidenza}</span>
                        </li>
                        <li>
                            <span class="title">Provincia:</span>
                            <span class="text">${paziente.provincia}</span>
                        </li>
                        <li>
                            <span class="title">Contatto principale:</span>
                            <span class="text">${paziente.contattoTelefonico}</span>
                        </li>
                        <li>
                            <span class="title">e-mail:</span>
                            <span class="text">${paziente.email}</span>
                        </li>
                        <hr>
                        <li>
                            <span class="title">Contatto d'emergenza:</span>
                            <span class="text">${paziente.contattoEmergenza}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </jsp:body>
</custom:accordion>