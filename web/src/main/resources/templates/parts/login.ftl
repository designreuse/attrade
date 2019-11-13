<#include "security.ftl">
<#macro logout>
    <#if principalUser??>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-sm btn-outline-primary pt-2 pb-2 px-4">
            <#--<span class="badge bg-transparent mt-1">-->
                    <#--<i class="fa fa-lock" aria-hidden="true"></i>-->
                ВЫХОД
                <#--</span>-->
        </button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>

    <#else>
    <a href="/login" class="btn btn-sm btn-outline-primary pt-2 px-4">
            <#--<span class="badge bg-transparent mt-1">-->
                    <#--<i class="fa fa-lock-open" aria-hidden="true"></i>-->
                ВХОД
                <#--</span>-->
    </a>

    <a href="/login/google" class="btn btn-sm btn-outline-primary">
        <#--<span class="badge bg-transparent">-->
                    <i class="fab fa-google fa-2x" aria-hidden="true"></i></i>
                <#--</span>-->
    </a>
    </#if>
</#macro>
