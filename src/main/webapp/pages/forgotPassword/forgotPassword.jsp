<%--
    Document: forgotPassword.jsp
    Created on: November 13, 2019
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>

<%@ page import="it.unitn.disi.wp.project.hms.commons.persistence.Attr" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.authentication.PasswordRecoveryServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.authentication.LoginServlet" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags/template" %>

<c:set var="loginURL" value="<%=LoginServlet.getURL()%>" scope="page"/>
<c:set var="tmpEmail" value="<%=request.getAttribute(Attr.TMP_EMAIL)%>" scope="request"/>
<c:set var="alertMSG" value="<%=request.getAttribute(Attr.ALERT_MSG)%>" scope="request"/>
<c:set var="successMSG" value="<%=request.getAttribute(Attr.SUCCESS_MSG)%>" scope="request"/>
<c:set var="warningMSG" value="<%=request.getAttribute(Attr.WARNING_MSG)%>" scope="request"/>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<HTML lang="it">

<HEAD>
    <META charset="UTF-8">
    <META http-equiv="X-UA-Compatible" content="IE=edge">
    <META name="viewport" content="width=device-width, initial-scale=1">

    <TITLE>Password dimenticata</TITLE>

    <%@ include file="/WEB-INF/partials/favicon.jsp" %>

    <!-- VENDORS -->
    <LINK rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    <LINK rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <SCRIPT src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></SCRIPT>

    <!-- MAIN CSS & JS -->
    <STYLE>
        /************************************************/
        /*  IMPORTS                                     */
        /************************************************/
        @import "<%=Attr.CP%>assets/css/modules/colors.css";
        @import "<%=Attr.CP%>assets/css/modules/card.css";
        /************************************************/
        :root {
            --body-background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
            --logo-color: var(--secondary-color);
            --logo-size: 5rem;
            --input-border-radius: 0.33rem;
            --input-border-color: var(--primary-color);
            --button-background: linear-gradient(to bottom, var(--primary-color), var(--secondary-color));
        }

        body {
            height: 100vh;
            background: var(--body-background);
            font-family: "SF Pro Display", "SF Pro Icons", "Helvetica Neue", "Helvetica", "Arial", sans-serif;
            align-items: center;
            -webkit-align-items: center;
            justify-content: center;
            -webkit-justify-content: center;
            display: flex;
            display: -o-flex;
            display: -ms-flex;
            display: -moz-flex;
            display: -webkit-flex;
            line-height: unset;
        }

        .card {
            width: 400px;
            height: 500px;
            padding: 2rem;
            background: var(--card-background);
            -webkit-align-items: center;
            align-items: center;
            justify-content: center;
            -webkit-justify-content: center;
            border-radius: var(--card-border-radius);
            -ms-border-radius: var(--card-border-radius);
            -moz-border-radius: var(--card-border-radius);
            -webkit-border-radius: var(--card-border-radius);
        }

        .card-title {
            font-size: 1.5rem;
            font-weight: 900;
            color: var(--secondary-color);
            text-align: -webkit-center;
            text-align: -moz-center;
            text-align: center;
        }
        #logo {
            color: var(--secondary-color);
            font-size: var(--logo-size);
            text-shadow: 2px 0 3px rgba(0, 0, 0, 0.2);
            margin: 1rem;
        }
        form {
            width: 100%;
        }
        input:active,
        input:focus,
        select:active,
        select:focus {
            outline: 0 !important;
            -webkit-appearance:none !important;
            -webkit-box-shadow: none !important;
            -moz-box-shadow: none !important;
            -o-box-shadow: none !important;
            box-shadow: none !important;
            border-color: var(--secondary-color) !important;
        }
        .form-control {
            border-left: 0;
        }
        .input-group {
            margin: 1rem 0;
            margin-bottom: 0;
        }
        .input-group > .form-control {
            height: 100%;
        }
        .input-group>.form-control:not(:last-child) {
            -webkit-border-bottom-right-radius: var(--input-border-radius);
            -webkit-border-top-right-radius: var(--input-border-radius);
            -moz-border-radius-bottomright: var(--input-border-radius);
            -moz-border-radius-topright: var(--input-border-radius);
            border-bottom-right-radius: var(--input-border-radius);
            border-top-right-radius: var(--input-border-radius);
        }
        .input-group-text {
            border: 0;
            color: white !important;
            background: var(--secondary-color);
            -webkit-border-radius: var(--input-border-radius) 0 0 var(--input-border-radius) !important;
            -moz-border-radius: var(--input-border-radius) 0 0 var(--input-border-radius) !important;
            -ms-border-radius: var(--input-border-radius) 0 0 var(--input-border-radius) !important;
            border-radius: var(--input-border-radius) 0 0 var(--input-border-radius) !important;
            margin-left:auto;
            margin-right:auto;
        }
        .form-control {
            -webkit-border-radius: var(--input-border-radius);
            -moz-border-radius: var(--input-border-radius);
            -ms-border-radius: var(--input-border-radius);
            border-radius: var(--input-border-radius);
        }

        .btn-primary {
            color: white;
            width: 100%;
            border: 0;
            background: var(--button-background);
            text-shadow: 2px 0 3px rgba(0,0,0,0.2);
            -webkit-border-radius: var(--input-border-radius);
            -moz-border-radius: var(--input-border-radius);
            -ms-border-radius: var(--input-border-radius);
            border-radius: var(--input-border-radius);
        }

        .btn-primary:focus {
            -webkit-box-shadow: none;
            -moz-box-shadow: none;
            -o-box-shadow: none;
            box-shadow: none;
            outline: 0;
        }

        footer {
            width: 100%;
            margin: 2rem 0;
            margin-bottom: 0;
        }

        .alert {
            width: 100%;
        }

        a {
            color: var(--secondary-color);
            display: block;
            text-align: center;
            text-align: -webkit-center;
            text-align: -moz-center;
            margin-top: 1rem;
        }

        @media screen and (max-width: 600px) { /* mobile screen */
            .card {
                justify-content: start;
                height: 100vh;
                width: 100vw;
                padding: 2rem;
                padding-top: 3rem;
                border-radius: 0;
                -ms-border-radius: 0;
                -moz-border-radius: 0;
                -webkit-border-radius: 0;
            }
            .form-control {
                font-size: 105%;
            }
            .input-group > .form-control {
                height: unset;
            }
            body,
            .input-group,
            .btn {
                font-size: 110%;
            }
        }
    </STYLE>
</HEAD>

<BODY>
    <div class="card">
        <i id="logo" class="fas fa-user-lock fa-fw"></i>
        <span class="card-title">PASSWORD DIMENTICATA</span>
        <%-------------------------------------------------------------------------------------------------%>
        <%------------------------------ RESPONSE MESSAGES WITH ALERTS ------------------------------------%>
        <c:choose>
            <c:when test="${not empty successMSG}">
                <custom:alert typeAlert="success" message="Email inviata con successo!"></custom:alert>
            </c:when>
            <c:when test="${not empty alertMSG}">
                <custom:alert typeAlert="error" message="Errore durante l'invio dell'email!"></custom:alert>
            </c:when>
        </c:choose>
        <%-------------------------------------------------------------------------------------------------%>
        <form id="forgotPasswordForm" action="<%=PasswordRecoveryServlet.getURL()%>" method="POST">
            <div class="input-group">
                <label class="input-group-text" for="email"><i class="fas fa-envelope fa-fw"></i></label>
                <input type="email" class="form-control" id="email" name="email" required
                <c:choose>
                    <c:when test="${not empty tmpEmail}"> value="${tmpEmail}" </c:when>
                    <c:otherwise> placeholder="Inserisci email" </c:otherwise>
                </c:choose>
                >
            </div>
            <c:if test="${not empty warningMSG}">
                <small class="text-danger font-weight-bold">E-mail inesistente!</small>
            </c:if>
        </form>
        <footer>
            <button type="submit" form="forgotPasswordForm" class="btn btn-primary">CONTINUA</button>
            <a href="<c:url value="/${loginURL}"/>">Torna al login</a>
        </footer>
    </div>
</BODY>

</HTML>