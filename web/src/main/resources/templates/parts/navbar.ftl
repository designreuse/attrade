<#include "security.ftl">
<#import "login.ftl" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">     <!-- размер и цветовая схема-->
    <a class="navbar-brand" href="/">Attrade</a>    <!--к главной странице-->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>   <!-- кнопка 'menu', появляющаяся на экранах размера 'lg'(смотри высший тег)-->

    <div class="collapse navbar-collapse" id="navbarSupportedContent">  <!--указываем, что меню сворачивается-->
        <ul class="navbar-nav mr-auto"> <!-- ul - список; mr-auto - автоматически генерируемое меню-->
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
            </li>
        <#if principalUser??>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/main">Messages<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/user/messages/${currentUserId}">My messages<span
                        class="sr-only">(current)</span></a>
            </li>
        </#if>
        <#if isAdmin>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/user">User list <span class="sr-only">(current)</span></a>
            </li>
        </#if>
        <#if principalUser??>
            <li class="nav-item"> <!-- элементы меню -->
                <a class="nav-link" href="/user/profile">Profile <span class="sr-only">(current)</span></a>
            </li>
        </#if>
        </ul>
        <div class="navbar-text mr-3"><#if principalUser??>${name}<#else>Please, login</#if></div>
    <@l.logout />
    </div>
</nav>
