<%@ page import="it.unitn.disi.wp.project.hms.servlet.authentication.LoginServlet" %>

<header id="myNavbar" class="fixed-top">
    <div class="container">
        <div class="logo float-left">
            <a href="#intro" class="scrollto"><img src="${cp}/assets/img/logo.svg" alt="logo" class="img-fluid"></a>
            <span class="pl-3">SISTEMA PER I SERVIZI SANITARI</span>
        </div>
        <nav class="main-nav float-right d-none d-lg-block">
            <ul>
                <li class="active"><a href="#intro">Home</a></li>
                <li><a href="#funzionalita">Funzionalit&agrave;</a></li>
                <li><a href="#screenshots">Screenshots</a></li>
                <li><a href="#team">Team</a></li>
                <li>
                    <a href="${pageContext.servletContext.contextPath}/<%=LoginServlet.getURL()%>"
                       class="btn btn-primary scrollto">LOGIN
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</header>
