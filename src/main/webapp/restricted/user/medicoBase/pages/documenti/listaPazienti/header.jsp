<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Documenti" currentPageTitle="Lista pazienti">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Documenti" href="menuDocumenti" collapse="${true}"></header:li>
    </jsp:body>
</custom:header>