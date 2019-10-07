<#include "../security.ftl">
<nav class="navbar navbar-expand-lg navbar-light bg-light">     <!-- размер и цветовая схема-->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"
            aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbar">  <!--указываем, что меню сворачивается-->
        <ul class="navbar-nav mr-auto"> <!-- ul - список; mr-auto - автоматически генерируемое меню-->
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/">О нас<span class="sr-only">(current)</span></a>
            </li>
        <#if principalUser??>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/main">Сообщения<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/user/messages/${principalId}">My messages<span
                        class="sr-only">(current)</span></a>
            </li>
        </#if>
        <#if isAdmin>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/user">Ваши сообщения<span class="sr-only">(current)</span></a>
            </li>
        </#if>
        <#if principalUser??>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/user/profile">Профайл <span class="sr-only">(current)</span></a>
            </li>
        </#if>
        </ul>
        </div>
    </div>
</nav>
