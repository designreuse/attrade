<#import "parts/common/main.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
<div id="carouselExampleIndicators" class="carousel slide carousel-fade" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
        <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
    </ol>
    <div class="carousel-inner">
        <div class="carousel-item active" data-interval="6000">
            <img src="/static/image/greetingBaner/baner-maz.jpg" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item" data-interval="4000">
            <img src="/static/image/greetingBaner/banner-dostavka.jpg" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item" data-interval="4000">
            <img src="/static/image/greetingBaner/banner-lp_eco.jpg" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item" data-interval="4000">
            <img src="/static/image/greetingBaner/rexenergo5.jpg" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item" data-interval="40">
            <img src="/static/image/greetingBaner/sklad-era-tex.jpg" class="d-block w-100" alt="...">
        </div>
    </div>

    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
</@c.page>