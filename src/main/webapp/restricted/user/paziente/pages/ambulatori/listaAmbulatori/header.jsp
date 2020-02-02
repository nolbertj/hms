<%@ taglib prefix="header" tagdir="/WEB-INF/tags/template/header" %>

<custom:header title="Ambulatori" currentPageTitle="Lista ambulatori">
    <jsp:body>
        <header:li title="Home" icon="fas fa-home" href="${cp}/${dashboardURL}"></header:li>
        <header:li title="Ambulatori" collapse="${true}" href="menuAmbulatori"></header:li>
    </jsp:body>
</custom:header>
