<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Genera report" currentPageTitle="Genera report">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Operazioni" collapse="${true}" href="menuOperazioni" id="breadcrumb-collapse"></header:li>
    </jsp:body>
</custom:header>