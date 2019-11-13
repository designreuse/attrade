<#import "parts/common/main.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
<div class="card text-center">
    <div class="card-header bg-warning">
        <b>АКТИВИРУЙТЕ АККАУНТ</b>
    </div>
    <div class="card-body">
        <h5 class="card-title">
            На Ваш почтовый ящик <#if email??><b>${email}</b></#if> отплавлено письмо со ссылкой на активацию аккаунта.
        </h5>
        <p class="card-text">
            Активируйте аккаунт - это поволит Вам совершать покупки без ограничений.
        </p>
        <#if activationError??>
            <div class="alert alert-danger" role="alert">
            ${activationError}
            </div>
        </#if>
    </div>
    <div class="card-footer text-muted bg-warning">
        АКТИВИРУЙТЕ АККАУНТ
    </div>
</div>
</@c.page>