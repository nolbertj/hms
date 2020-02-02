<%--
    Document: Login.jsp
    Created on: October 2, 2019
    Front-end: Alessandro Tomazzoli, Alessandro Brighenti, Nolbert Juarez
    Back-end: Alessandro Brighenti, Nolbert Juarez
--%>

<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Ruoli" %>
<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.authentication.PasswordRecoveryServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:url var="absolutePath" value="/login" scope="page"/>
<c:url var="img" value="/assets/img" scope="page"/>
<c:set var="pazienteStringRole" value="<%=Ruoli.PAZIENTE%>" scope="page"/>
<c:set var="medicoBStringRole" value="<%=Ruoli.MEDICO_B%>" scope="page"/>
<c:set var="medicoSStringRole" value="<%=Ruoli.MEDICO_S%>" scope="page"/>
<c:set var="farmaciaStringRole" value="<%=Ruoli.FARM%>" scope="page"/>
<c:set var="sspStringRole" value="<%=Ruoli.SSP%>" scope="page"/>
<c:set var="adminStringRole" value="<%=Ruoli.ADMIN%>" scope="page"/>
<c:set var="forgotPasswordURL" value="<%=PasswordRecoveryServlet.getURL()%>" scope="page" />
<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="tmpEmail" value="<%=request.getAttribute(Attr.TMP_EMAIL)%>" scope="request"/>
<c:set var="tmpRole" value="<%=request.getAttribute(Attr.TMP_ROLE)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<HTML lang="it">

<HEAD>
    <META charset="UTF-8">
    <META http-equiv="X-UA-Compatible" content="IE=edge">
    <META name="viewport" content="width=device-width, initial-scale=1">

    <TITLE>Login</TITLE>

    <%@ include file="/WEB-INF/partials/favicon.jsp" %>

    <!-- VENDORS -->
    <LINK rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <LINK rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <LINK rel="stylesheet" href="https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.css" />
    <SCRIPT src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.js" data-cfasync="false"></SCRIPT>

    <!-- MAIN CSS & JS -->
    <LINK rel="stylesheet" href="${absolutePath}/login.css">
    <SCRIPT type="text/javascript" src="${absolutePath}/login.js"></SCRIPT>
    <SCRIPT src="${pageContext.servletContext.contextPath}/assets/js/cookies/cookieHandler.js"></SCRIPT>
    <SCRIPT src="${pageContext.servletContext.contextPath}/assets/js/cookies/cookieConsent.js"></SCRIPT>
</HEAD>

<BODY>
    <div class="card">
        <i id="logo" class="fas fa-stethoscope fa-fw"></i>
        <span class="card-title mb-0">AREA RISERVATA</span>       
        <form action="login" id="loginForm" method="post">
            <select class="custom-select" id="userRole" onchange="resetBorder(event)" name="ruolo">
                <option value="none" disabled
                        <c:if test="${empty tmpRole}">selected</c:if>
                    >Seleziona il tipo di utente
                </option>
                <option value="${pazienteStringRole}"
                        <c:if test="${pazienteStringRole eq tmpRole}">selected</c:if>
                    >PAZIENTE
                </option>
                <option value="${medicoBStringRole}"
                        <c:if test="${medicoSStringRole eq tmpRole}">selected</c:if>
                    >MEDICO DI BASE
                </option>
                <option value="${medicoSStringRole}"
                        <c:if test="${medicoSStringRole eq tmpRole}">selected</c:if>
                    >MEDICO SPECIALISTA
                </option>
                <option value="${farmaciaStringRole}"
                        <c:if test="${farmaciaStringRole eq tmpRole}">selected</c:if>
                    >FARMACIA
                </option>
                <option value="${sspStringRole}"
                        <c:if test="${sspStringRole eq tmpRole}">selected</c:if>
                    >SERVIZIO SANITARIO PROV.
                </option>
                <option value="${adminStringRole}"
                        <c:if test="${adminStringRole eq tmpRole}">selected</c:if>
                    >AMMINISTRATORE
                </option>
            </select>

            <c:if test="${not empty alertMSG}">
                <custom:alert typeAlert="error" message="${alertMSG}"></custom:alert>
            </c:if>

            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-user fa-fw"></i></span>
                <input type="email" class="form-control" name="username" required
                    <c:choose>
                        <c:when test="${not empty tmpEmail}"> value="${tmpEmail}"</c:when>
                        <c:otherwise> placeholder="Utente"</c:otherwise>
                    </c:choose>
                >
            </div>
            <div class="input-group">
                <span class="input-group-text"><i class="fas fa-key fa-fw"></i></span>
                <input type="password" class="form-control" placeholder="Password" id="pswdInput" name="password" required>
            </div>

            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" id="showPwd" onclick="showPassword()">
                <label class="custom-control-label" for="showPwd">Mostra password</label>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" id="rememberMe" name="ricordami">
                <label class="custom-control-label" for="rememberMe">Ricordami</label>
            </div>
        </form>
        <footer>
            <button class="btn login_btn" onclick="return checkForm();" type="submit" form="loginForm" value="Accedi">ACCEDI</button>
            <a href="<c:url value="/${forgotPasswordURL}"/>">Password dimenticata?</a>
        </footer>
    </div>
    <script type="text/javascript">
        showCookieConsent();
    </script>
</BODY>
</HTML>
