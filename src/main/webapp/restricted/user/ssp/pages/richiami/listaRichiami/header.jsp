<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Richiami" currentPageTitle="Lista richiami">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Richiami" href="menuRichiami" collapse="${true}"></header:li>
    </jsp:body>
</custom:header>