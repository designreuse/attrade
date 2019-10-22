<div class="parts-carousel-categoryMain">
    <div id="carouselProductMainIndicators"
         class="carousel slide carousel-fade "
         data-ride="carousel"
         data-interval="false"
         data-wrap="false">
        <ol class="carousel-indicators">
            <li data-target="#carouselProductMainIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselProductMainIndicators" data-slide-to="1"></li>
            <li data-target="#carouselProductMainIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
            <#include "../group/categoryMainLeft.ftl"/>
            </div>
            <div class="carousel-item">
            <#include "../group/categoryMainCentre.ftl"/>
            </div>
            <div class="carousel-item">
            <#include "../group/categoryMainRight.ftl"/>
            </div>
        </div>

        <a class="carousel-control-prev" href="#carouselProductMainIndicators" role="button" data-slide="prev">
            <span class="fas fa-chevron-circle-left fa-2x" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselProductMainIndicators" role="button" data-slide="next">
            <span class="fas fa-chevron-circle-right fa-2x" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
</div>