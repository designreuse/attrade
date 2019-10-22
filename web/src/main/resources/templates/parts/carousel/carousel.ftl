<#import "../picture.ftl" as p/>
<div class="parts-carousel-banner">
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
            <@p.picture path="/banner/banner-maz.jpg" alt="..." class="d-block w-100 active" style=""/>
            </div>
            <div class="carousel-item" data-interval="4000">
            <@p.picture path="/banner/banner-dostavka.jpg" alt="..." class="d-block w-100" style=""/>
            </div>
            <div class="carousel-item" data-interval="4000">
            <@p.picture path="/banner/banner-lp-eco.jpg" alt="..." class="d-block w-100" style=""/>
            </div>
            <div class="carousel-item" data-interval="4000">
            <@p.picture path="/banner/banner-rexenergo5.jpg" alt="..." class="d-block w-100" style=""/>
            </div>
            <div class="carousel-item" data-interval="4000">
            <@p.picture path="/banner/banner-sklad-era-tex.jpg" alt="..." class="d-block w-100" style=""/>
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
</div>