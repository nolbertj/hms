<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Pagamenti" currentPageTitle="Ricevuta ${ricevuta.idRicevuta}">
    <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
    <header:li title="Pagamenti" collapse="${true}" href="menuPagamenti"></header:li>
    <header:li title="Lista pagamenti" href="${cp}/${pagamentiURL}"></header:li>
</custom:header>