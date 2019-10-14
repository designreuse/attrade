<#include "security.ftl">
<#macro logout>
<form action="/logout" method="post">
    <button type="submit" role="button" class="btn btn-primary">
            <span class="badge bg-transparent">
                <#if principalUser??>
                    <i class="fa fa-lock" aria-hidden="true">
                        </i>
                Выйти
                <#else>
                    <i class="fa fa-lock-open" aria-hidden="true">
                    </i>
                    Войти
                </#if>
                </span>
    </button>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
</form>
</#macro>
