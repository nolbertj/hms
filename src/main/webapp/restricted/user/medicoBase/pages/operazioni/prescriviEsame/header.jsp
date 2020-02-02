<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Prescrivi esame" currentPageTitle="Prescrivi esame">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Operazioni" href="menuOperazioni" collapse="${true}"></header:li>
    </jsp:body>
</custom:header>