<%--
    Document: landingPage.jsp
    Created on: January 27, 2020
    Front-end: Nolbert Juarez
    Back-end: Nolbert Juarez
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="cp" value="${pageContext.servletContext.contextPath}" scope="application" />

<!DOCTYPE html>
<HTML lang="it">
<HEAD>
    <META http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <META http-equiv="X-UA-Compatible" content="IE=edge">
    <META name="viewport" content="width=device-width, initial-scale=1">
    <!------------------------------------------------------------------------------------------------------------------->
    <TITLE>Healthcare Management System</TITLE>
    <!------------------------------------------------------------------------------------------------------------------->
    <%@ include file="/WEB-INF/partials/favicon.jsp" %>
    <!------------------------------------------------------------------------------------------------------------------->
    <!--- CSS VENDORS --------------------------------------------------------------------------------------------------->
    <LINK rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
    <LINK rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css"
          integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous" />
    <LINK rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.min.css"
          integrity="sha256-PHcOkPmOshsMBC+vtJdVr5Mwb7r0LkSVJPlPrp/IMpU=" crossorigin="anonymous" />
    <LINK rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/owl-carousel/1.3.3/owl.carousel.min.css"
          integrity="sha256-fDncdclXlALqR3HOO34OGHxek91q8ApmD3gGldM+Rng=" crossorigin="anonymous" />
    <LINK rel="stylesheet" href="https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.css" />
    <!------------------------------------------------------------------------------------------------------------------->
    <!----- JS VENDORS -------------------------------------------------------------------------------------------------->
    <SCRIPT src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"
            integrity="sha384-6khuMg9gaYr5AxOqhkVIODVIvm9ynTT5J4V1cfthmT+emCG6yVmEZsRHdxlotUnm" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"
            integrity="sha256-H3cjtrm/ztDeuhCN9I4yh4iN2Ybx/y1RM7rMmAesA0k=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://cdnjs.cloudflare.com/ajax/libs/wow/1.1.2/wow.min.js"
            integrity="sha256-z6FznuNG1jo9PP3/jBjL6P3tvLMtSwiVAowZPOgo56U=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://cdnjs.cloudflare.com/ajax/libs/waypoints/4.0.1/jquery.waypoints.min.js"
            integrity="sha256-jDnOKIOq2KNsQZTcBTEnsp76FnfMEttF6AV2DF2fFNE=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"
            integrity="sha256-pTxD+DSzIwmwhOqTFN+DB+nHjO4iAsbgfyFq5K5bcE0=" crossorigin="anonymous"></SCRIPT>
    <SCRIPT src="https://cdn.jsdelivr.net/npm/lightzoom@1.0.0/lightzoom.min.js"></SCRIPT>
    <SCRIPT src="https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.js"
            data-cfasync="false"></SCRIPT>
    <!------------------------------------------------------------------------------------------------------------------->
    <!------ MAIN CSS & JS ------------------>
    <LINK rel="stylesheet" href="${cp}/landingPage/style.css" />
    <LINK rel="stylesheet" href="${cp}/landingPage/style-mobile.css" />

    <SCRIPT src="${cp}/landingPage/descriptions.js"></SCRIPT>
    <SCRIPT src="${cp}/landingPage/main.js"></SCRIPT>
    <SCRIPT src="${cp}/landingPage/mobile-nav.js"></SCRIPT>
    <SCRIPT src="${cp}/assets/js/cookies/cookieHandler.js"></SCRIPT>
    <SCRIPT src="${cp}/assets/js/cookies/cookieConsent.js"></SCRIPT>
    <!--------------------------------------->
</HEAD>

<BODY>
<!-- HEADER/NAVBAR ------------------------------------------------------------------------------------------------>
<%@ include file="/landingPage/navbar.jsp" %>
<!------------------------------------------------------------------------------------------------------------------->
<%@ include file="/landingPage/sections/intro.jsp" %>
<!------------------------------------------------------------------------------------------------------------------->
<MAIN id="main">
    <%@ include file="/landingPage/sections/funzionalita.jsp" %>
    <%@ include file="/landingPage/sections/screenshots.jsp" %>
    <%@ include file="/landingPage/sections/team.jsp" %>
    <%@ include file="/landingPage/sections/contattaci.jsp" %>
    <%@ include file="/pages/cookie.jsp" %>
    <%@ include file="/pages/copyright.html"%>
    <%@ include file="/pages/credits.html" %>
</MAIN>
<!------------------------------------------------------------------------------------------------------------------->
<!-- FOOTER --------------------------------------------------------------------------------------------------------->
<%@ include file="/restricted/user/footer.jsp" %>
<!------------------------------------------------------------------------------------------------------------------->
<button type="button" id="zoomSwitch" class="back-to-top"><i class="fa fa-search"></i></button>
<a href="#" class="back-to-top" id="backToTop"><i class="fa fa-chevron-up"></i></a>
<!------------------------------------------------------------------------------------------------------------------->
<script type="text/javascript">
    showCookieConsent();
</script>
</BODY>
</HTML>