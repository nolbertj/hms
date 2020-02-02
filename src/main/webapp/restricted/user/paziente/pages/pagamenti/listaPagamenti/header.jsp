<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Pagamenti" currentPageTitle="Lista pagamenti">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Pagamenti" collapse="${true}" href="menuPagamenti" id="breadcrumb-collapse"></header:li>
    </jsp:body>
</custom:header>