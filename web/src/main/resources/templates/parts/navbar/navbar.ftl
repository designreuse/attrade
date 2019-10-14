<#include "../security.ftl">
<div class="parts-navbar-navbar">
    <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-static-top">     <!-- размер и цветовая схема-->
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"
                aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbar">  <!--указываем, что меню сворачивается-->
            <ul class="navbar-nav mr-auto"> <!-- ul - список; mr-auto - автоматически генерируемое меню-->
                <li class="nav-item"> <!-- элементы меню -->
                    <div class="btn-group btn-group-md" role="group" aria-label="Basic example">
                        <button type="submit" role="button" href="/product" class="btn btn-secondary">Каталог
                        </button>
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
