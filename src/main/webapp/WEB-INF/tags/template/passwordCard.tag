<%--
    Document: passwordCard.tag
    Created on: November 1, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ tag description="Password Card Template [richiede psw.js]" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<%@ tag import="it.unitn.disi.wp.project.hms.commons.configs.Password" %>
<%@ tag import="it.unitn.disi.wp.project.hms.servlet.authentication.PasswordUpdateServlet" %>

<c:url var="passwordUpdateURL" value="<%=PasswordUpdateServlet.getURL()%>" scope="page"/>
<c:set var="minL" value="<%=Password.getMinLength()%>" scope="page"/>
<c:set var="maxL" value="<%=Password.getMaxLength()%>" scope="page"/>
<c:set var="oneNum" value="<%=Password.isAtLeastOneNumber()%>" scope="page"/>
<c:set var="oneUpper" value="<%=Password.isAtLeastOneMaiusc()%>" scope="page"/>
<c:set var="oneLower" value="<%=Password.isAtLeastOneMinusc()%>" scope="page"/>
<c:set var="oneSpecial" value="<%=Password.isAtLeastOneSpecialCharacter()%>" scope="page"/>
<c:set var="acceptSpecial" value="<%=Password.acceptSpecialCharacters()%>" scope="page"/>

<%@ attribute name="isOpened"
              required="true"
              description="Porre ${false} o ${true} se la card deve essere aperta all'avvio."
%>

<custom:accordion isOpened="${isOpened}" id="password" cardTitle="Modifica password">
    <jsp:body>
        <form action="${cp}/${passwordUpdateURL}" method="POST" id="newPwdForm">
            <div class="form-group">
                <label class="form-label" for="currentPwd">Password corrente</label>
                <input type="password" class="form-control" id="currentPwd" name="currentPwd" placeholder="Password corrente" required
                <c:if test="${incorrectCurrentPassword}"> style="border-color: red"</c:if>
                >
                <c:if test="${incorrectCurrentPassword}">
                    <small style="color: red"><i class="fas fa-exclamation-circle" id="incorrectCurrentMSG"></i>
                        La password corrente immessa non &#232; corretta!
                    </small>
                </c:if>
            </div>
            <div id="password-form-container" class="row">
                <div class="col">
                    <div class="form-group row">
                        <label class="form-label" for="newPwd">Nuova password</label>
                        <input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="Nuova password" required>
                    </div>
                </div>
                <div class="col">
                    <div class="form-group row">
                        <label class="form-label" for="confirmPwd">Conferma password</label>
                        <input type="password" class="form-control" id="confirmPwd" name="confirmPwd" placeholder="Conferma password" required>
                        <small id="notEqualPwdMSG" class="off" style="color: red"><i class="fas fa-exclamation-circle"></i>
                            Le due password non coincidono!
                        </small>
                    </div>
                </div>
            </div>

            <script type="text/javascript">initPasswordAttributes(${minL},${maxL},${oneNum},${oneUpper},${oneLower},${oneSpecial},${acceptSpecial});</script>

            <small class="form-text mt-0" id="requirmentsForPassword">
                La password deve essere lunga almeno ${minL} caratteri
                (fino a ${maxL}) e rispettare le seguenti condizioni:<br>
                <ul>
                    <c:if test="${oneNum eq 'true'}"><li>almeno un numero</li></c:if>
                    <c:if test="${oneLower eq 'true'}"><li>almeno una lettera maiuscola</li></c:if>
                    <c:if test="${oneUpper eq 'true'}"><li>almeno una lettera minuscola</li></c:if>
                    <c:if test="${oneSpecial eq 'true'}"><li>almeno un carattere speciale</li></c:if>
                </ul>
            </small>
            <button id="submitPasswordBTN" type="submit" class="btn btn-danger" form="newPwdForm" disabled>MODIFICA PASSWORD</button>
        </form>
    </jsp:body>
</custom:accordion>
