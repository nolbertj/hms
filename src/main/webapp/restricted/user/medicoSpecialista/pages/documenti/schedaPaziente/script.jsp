<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RicettaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoSpecialistaService" %>

<SCRIPT type="text/javascript">
    let URLs = {
        visiteURL:  '${cp}/<%=MedicoSpecialistaService.getVisitePazienteURL()%>${paziente.id}',
        refertoURL: '${cp}/<%=RefertoServlet.getURL()%>',
        ricettaURL: '${cp}/<%=RicettaServlet.getURL()%>'
    };
</SCRIPT>