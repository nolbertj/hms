function pinSidebar(){
    $("#showSidebarMSG").toggleClass("off");
    $("#hideSidebarMSG").toggleClass("off");

    if($("#page").hasClass("pinned")) {
        //console.log("apertura fissa");

        $("#sidebar").off("hover");
        $("#page").removeClass("pinned");
        $("#sidebar footer").removeClass("pinned");
        $("#sidebar footer").addClass("unpinned");

        $(".cc-window.cc-banner").css("margin-left","var(--sidebar-open-width)");
    }
    else {
        //console.log("chiusura automatica");

        $("#page").addClass("pinned");

        $("#sidebar").on('mouseenter',function() {
            console.log("mouse enter in sidebar");
            $("#page").addClass("sidebar-hovered");
            $("#sidebar footer").removeClass("pinned");
            $("#sidebar footer").addClass("unpinned");
        });

        $("#sidebar").on('mouseleave', function() {
            console.log("mouse exit from sidebar");
            $("#page").removeClass("sidebar-hovered");
            $("#sidebar footer").addClass("pinned");
            $("#sidebar footer").removeClass("unpinned");
        });

        $(".cc-window.cc-banner").css("margin-left","var(--sidebar-closed-width)");
    }
}

$(()=> {
    console.log("loading sidebar.js ...");
//======================================================================================================================
    $("#breadcrumb-collapse").on('click', function() {
        let navLink = $(this).children(0).attr('href');
        $(navLink).trigger('click');
    });

    if($(window).width() > 992) { // script only for DESKTOP screen
        $("#sidebar-content").toggleClass("show");

        $("#sidebar footer button").on('click', function() {

            pinSidebar();

            let sidebarClosed = window.sessionStorage.getItem("sidebarClosed");

            if(sidebarClosed==='true') {
                window.sessionStorage.setItem("sidebarClosed", 'false');
            }
            else if(sidebarClosed==='false') {
                window.sessionStorage.setItem("sidebarClosed", 'true');
            }
        });

        if( window.sessionStorage.getItem("sidebarClosed")==null ||
            window.sessionStorage.getItem("sidebarClosed")==undefined) {
            window.sessionStorage.setItem("sidebarClosed",'false');
        }

        let TRANSITIONED = {
            page: $("#page-wrapper"),
            navbar: $("#sidebar .navbar-brand"),
            sidebar: $("#sidebar"),
            sidebarFooter: $("#sidebar footer"),
            sidebarButton: $("#sidebar footer button"),
            sidebarContent: $("#sidebar-content"),
            sidebarIcons: $("#sidebar-content i"),
            sidebarSubmenu: $("#sidebar-content .sidebar-submenu li a span")
        };

        if(window.sessionStorage.getItem("sidebarClosed")==='true') {
            Object.keys(TRANSITIONED).forEach((key)=>TRANSITIONED[key].addClass("notransition"));
            pinSidebar();
            setTimeout(()=>Object.keys(TRANSITIONED).forEach(function(key) {
                    TRANSITIONED[key].removeClass("notransition")
                }),1250
            );
        }
    }
//======================================================================================================================
    console.log("sidebar.js loaded successfully!");
});