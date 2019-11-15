<#include "security.ftl">
<#macro logout>
    <#if principalUser??>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-sm btn-outline-primary pt-2 pb-2 px-4">
                ВЫХОД
        </button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>

    <#else>
    <a href="/login" class="btn btn-sm btn-outline-primary pt-2 px-4">
                ВХОД
    </a>
    <a href="/login/github" class="btn btn-sm btn-outline-primary py-2 px-2">
        <i class="fab fa-github fa-1x" aria-hidden="true"></i></i>
    </a>
    <a href="/login/facebook" class="btn btn-sm btn-outline-primary py-2 px-2">
        <i class="fab fa-facebook-f fa-1x" aria-hidden="true"></i></i>
    </a>
    <a href="/login/google" class="btn btn-sm btn-outline-primary py-2 px-2">
        <i class="fab fa-google fa-1x" aria-hidden="true"></i></i>
    </a>
    </#if>
</#macro>
