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
            На Ваш почтовый ящик отплавлено письмо с кодом активации.</br>
        </p>
        <#--<form class="form-inline" action="/activation/activate" method="post">-->
            <#--<div class="form-group mx-sm-3 mb-2">-->
                <#--<input type="text" name="code" class="form-control" id="code" placeholder="код активации">-->
            <#--</div>-->
            <#--<button type="submit" class="btn btn-primary mb-2">Активировать</button>-->
        <#--</form>-->
        <form action="/activate" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="text" name="code" placeholder="код активации">
        </form>
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