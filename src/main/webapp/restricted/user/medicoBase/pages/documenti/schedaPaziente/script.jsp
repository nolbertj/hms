<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RefertoServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.servlet.user.RicettaServlet" %>
<%@ page import="it.unitn.disi.wp.project.hms.services.MedicoBaseService" %>

<SCRIPT type="text/javascript">
    let URLs = {
        visiteURL:  '${cp}/<%=MedicoBaseService.getURL()%>/${USER.id}/pazienti/visite?idPaziente=${paziente.id}',
        refertoURL: '${cp}/<%=RefertoServlet.getURL()%>',
        ricettaURL: '${cp}/<%=RicettaServlet.getURL()%>'
    };
</SCRIPT>