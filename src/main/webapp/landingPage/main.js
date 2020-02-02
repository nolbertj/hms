/**
 * Document: main.js
 * Created on: January 27, 2020
 * Author: Nolbert Juarez
 * Description: scripts for the landing page
 * */
$(()=> {
    // Back to top button
    $(window).scroll(function() {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').on('click',function(){
        $('html, body').animate({scrollTop : 0},1500, 'easeInOutExpo');
        return false;
    });

    // Initiate the wowjs animation library
    new WOW().init();

    // Navbar scroll class
    $(window).scroll(function() {
        if ($(this).scrollTop() > 100) {
            $('#myNavbar').addClass('header-scrolled');
        } else {
            $('#myNavbar').removeClass('header-scrolled');
        }
    });

    if ($(window).scrollTop() > 100) {
        $('#myNavbar').addClass('header-scrolled');
    }

    if ($(window).width() > 992) {
        let zoomActivated = false;
        document.getElementById("zoomSwitch").style.display = "inline";
        $("#zoomSwitch").on('click',()=> {
            if(zoomActivated){
                $("#zoomSwitch i").css("color","white");

                $('#screenshots img').replaceWith($("#screenshots img").first().clone());
                $('.devices').replaceWith($(".devices").first().clone());
            }
            else if (!zoomActivated){
                $("#zoomSwitch i").css("color","var(--lightwhite)");

                $("#screenshots img, .devices").lightzoom();
                $("#screenshots img").lightzoom({
                    glassSize: 250,
                    zoomPower: 1.5
                });
                $(".devices").lightzoom({
                    glassSize: 200,
                    zoomPower: 2
                });
            }
        })

    }

    // carousel text for every screenshot
    updateTextCarousel("#carouselPaziente",paziente,"#pazienteTitle","#pazienteParagrafo");
    updateTextCarousel("#carouselAdmin",admin,"#adminTitle","#adminParagrafo");
    updateTextCarousel("#carouselMedicoBase",medicoBase,"#medicoBaseTitle","#medicoBaseParagrafo");
    updateTextCarousel("#carouselMedicoSpec",medicoSpecialista,"#medicoSpecTitle","#medicoSpecParagrafo");
    updateTextCarousel("#carouselFarmacia",farmacia,"#farmaciaTitle","#farmaciaParagrafo");
    updateTextCarousel("#carouselSsp",ssp,"#sspTitle","#sspParagrafo");

    $(".cc-message a").prop('target','');
    $(".cc-message a").prop('href','#cookieSection');

    $("#copyrightSection").children('div').first().remove();
    $("#copyrightSection").children('strong').first().remove();

    $("#creditsLink").prop('href','#creditsSection');
    $("#cookieLink").prop('href','#cookieSection');
    $("#copyrightLink").prop('href','#copyrightSection');


    $("#creditsLink").on('click',()=> {
        if($("#creditsSection").css('display')==='none'){
            $("#creditsSection").fadeIn().attr('style','display: flex !important;');
        } else {
            document.getElementById("creditsSection").style.display = "";
        }
    });
    $("#copyrightLink").on('click',()=> {
        if($("#copyrightSection").css('display')==='none'){
            $("#copyrightSection").fadeIn().attr('style','display: unset !important;');
        } else {
            document.getElementById("copyrightSection").style.display = "";
        }
    });
    $("#cookieLink, .cc-message a").on('click',()=> {
        if($("#cookieSection").css('display')==='none'){
            $("#cookieSection").fadeIn().attr('style','display: flex !important;');
        } else {
            document.getElementById("cookieSection").style.display = "";
        }
    });

});

function updateTextCarousel(selector,jsonData,titleID,paragraphID) {
    $(selector).on('slide.bs.carousel',(e)=> {
        let id = $(e.relatedTarget).find('img').first().get()[0].id;
        $.each(jsonData,(i,data)=> {
            if (data["id"] === id) {
                $(titleID).fadeOut(()=>{
                    $(titleID).text(data["title"]).fadeIn();
                });
                $(paragraphID).fadeOut(()=> {
                    let span = document.createElement("span");
                    span.innerHTML = data["text"];
                    $(paragraphID).text(span.innerHTML).fadeIn();
                });
            }
        })
    })
}