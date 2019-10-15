<#import "parts/common/main.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
<div class="card text-center">
    <div class="card-header">
        Активация
    </div>
    <div class="card-body">
        <h5 class="card-title">
            Активируйте аккаунт - это поволит Вам совершать покупки без ограничений.
        </h5>
        <p class="card-text">
            На Ваш почтовый ящик отплавлено письмо со ссылкой на активацию аккаунта.</br>
        </p>
        <#if activationError??>
            <div class="alert alert-danger" role="alert">
            ${activationError}
            </div>
        </#if>
    </div>
    <div class="card-footer text-muted">
        Активация
    </div>
</div>
</@c.page>