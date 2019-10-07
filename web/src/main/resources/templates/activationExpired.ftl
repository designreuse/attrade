<#import "parts/common/main.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
<div class="card text-center">
    <div class="card-header">
            <div class="alert alert-danger" role="alert">
               Активация
            </div>
    </div>
    <div class="card-body">
        <h5 class="card-title">
            Срок активации по данному коду истек
        </h5>
        <p class="card-text">
            Отправить новый код?
            <button class="btn btn-danger" onclick="/">Нет</button>
            <button class="btn btn-success" onclick="/activationAgain">Да</button>
        </p>
    </div>
    <div class="card-footer text-muted">
        Активация
    </div>
</div>
</@c.page>