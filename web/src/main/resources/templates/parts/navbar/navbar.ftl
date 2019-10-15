<#include "../security.ftl">
<div class="parts-navbar-navbar">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark navbar-static-top pb-2 pt-2">
        <!-- размер и цветовая схема-->
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"
                aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbar">  <!--указываем, что меню сворачивается-->
            <ul class="navbar-nav mr-auto"> <!-- ul - список; mr-auto - автоматически генерируемое меню-->
                <li class="nav-item"> <!-- элементы меню -->
                    <div class="btn-group btn-group-md btn-group-justified ml-2" role="group"
                         aria-label="Basic example">
                        <button type="submit" role="button" href="/product" class="btn btn-primary">Каталог
                        </button>
                    </div>
                </li>
                <li class="nav-item">
                    <div class="btn-group btn-group-sm btn-group-justified  ml-2">
                        <a class="btn btn-secondary btn-sm rounded-0 mr-1" href="tel:+375445867823">
                            <img src="/static/image/icon/velcom.png">
                            <span class="glyphicon glyphicon-earphone"></span>
                            <small>+375(44)</small>
                            <b>586-78-23</b>
                        </a>
                        <a class="btn btn-secondary btn-sm rounded-0  mr-1" href="tel:+375292612505">
                            <span class="glyphicon glyphicon-earphone"></span>
                            <img src="/static/image/icon/mts.png">
                            <small>+375(29)</small>
                            <b>261-25-05</b>
                        </a>
                        <a class="btn btn-secondary btn-sm rounded-0 " href="tel:+375173884060">
                            <span class="glyphicon glyphicon-earphone"></span>
                            <img src="/static/image/icon/phone_wipe.png">
                            <small>+375(17)</small>
                            <b>388-40-60</b>
                        </a>
                    </div>
                </li>
            <#if isAdmin>
                <li class="nav-item"> <!-- элементы меню -->
                    <a class="nav-link" href="/admin">Администратор<span class="sr-only">(current)</span></a>
                </li>
            </#if>
            </ul>
        </div>


        <div class="btn-group btn-group-md btn-group-justified btn-group-md">

            <a href="/recentlyWatch" class="btn btn-secondary mr-sm-1">
                <i class="fas fa-eye" aria-hidden="true"></i>
            </a>

            <a href="/history" class="btn btn-secondary mr-sm-1">
                <i class="fas fa-history" aria-hidden="true"></i>
            <#if principalUser??>
                <span class="badge badge-<#if isOrdersAccept??>success<#else>danger</#if>">#</span>
            <#else>
                <span class="badge badge-danger"><i class="fas fa-user-lock"></i></span>
            </#if>
            </a>

            <a href="/basket" class="btn btn-secondary">
                <i class="fa fa-shopping-cart" aria-hidden="true"></i>
            <#if basket??>
                <span class="badge badge-success">4</span>
            <#else>
                <span class="badge badge-primary">0</span>
            </#if>
            </a>

        </div>
    </nav>
</div>
