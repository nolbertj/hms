<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Documenti" currentPageTitle="Referti">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Documenti" collapse="${true}" href="menuDocumenti"></header:li>
    </jsp:body>
</custom:header>