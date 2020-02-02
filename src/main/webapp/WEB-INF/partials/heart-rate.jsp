<style>
    .heart-rate {
        width: 150px;
        height: 73px;
        position: relative;
        zoom: 1.5;
    }
    .fade-in {
        position: absolute;
        width: 100%;
        height: 100%;
        background-color: var(--body-color);
        top: 0;
        right: 0;
        animation: heartRateIn 1.5s linear infinite;
    }
    .fade-out {
        position: absolute;
        width: 120%;
        height: 100%;
        top: 0;
        left: -120%;
        animation: heartRateOut 1.5s linear infinite;
        background: var(--body-color);
        background: -moz-linear-gradient(left, var(--body-color) 0%, var(--body-color) 50%, var(--body-color) 100%);
        background: -webkit-linear-gradient(left, var(--body-color) 0%, var(--body-color) 50%, var(--body-color) 100%);
        background: -o-linear-gradient(left, var(--body-color) 0%, var(--body-color) 50%, var(--body-color) 100%);
        background: -ms-linear-gradient(left, var(--body-color) 0%, var(--body-color) 50%, var(--body-color) 100%);
        background: linear-gradient(to right, var(--body-color) 0%, var(--body-color) 80%, var(--body-color) 100%);
    }
    @keyframes heartRateIn {
        0% {
            width: 100%;
        }
        50% {
            width: 0;
        }
        100% {
            width: 0;
        }
    }
    @keyframes heartRateOut {
        0% {
            left: -120%;
        }
        30% {
            left: -120%;
        }
        100% {
            left: 0;
        }
    }
    #heartContainer {
        display: flex;
        width: 80%;
        height: calc(100vh - var(--navbar-height));
        align-items: center;
        justify-content: center;
        position: fixed;
    }
    @media screen and (max-width: 992px) {
        #heartContainer {
            width: 100%;
        }
    }
</style>
<div id="heartContainer">
    <div class="heart-rate"><img src="${img}/hearthbeat.svg">
        <div class="fade-in"></div><div class="fade-out"></div>
    </div>
</div>
<script>
    $(()=> {
        document.getElementById("page-container").style.opacity = "50%";
        document.getElementById("page-container").style.filter = "blur(1px)";
        setTimeout(function() {
            document.getElementById("page-container").style.opacity = "";
            document.getElementById("page-container").style.filter = "";
            document.getElementsByClassName("fade-in")[0].style.animation = "none";
            document.getElementsByClassName("fade-out")[0].style.animation = "none";
            document.getElementById("heartContainer").style.zIndex = -1;
            document.getElementById("heartContainer").style.display = "none";
        },1000);
    });
</script>