<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Prescrizioni" currentPageTitle="Esami prescritti">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Prescrizioni" collapse="${true}" href="menuPrescrizioni"></header:li>
    </jsp:body>
</custom:header>