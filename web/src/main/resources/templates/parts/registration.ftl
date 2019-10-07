<#macro registration path>
<form action="${path}" method="post">
    <div class="form-group row">
        <!--поле 'User Name' размером в 2 клонки, поле как label-->
        <label class="col-sm-2 col-form-label"> Логин: </label>
        <span class="input-group-text"> <i class="fas fa-user"></i> </span>
        <div class="col-sm-6">
            <input type="text" name="username" value="<#if user.username??>${user.username}<#else></#if>"
                   class="form-control ${(usernameError??)?string('is-invalid','')}" placeholder="Логин"/>
            <!--поле ввода как 'form-control'-->
            <#if usernameError??>
                <div class="invalid-feedback">
                ${usernameError}
                </div>
            </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Email:</label>
        <span class="input-group-text"> <i class="fas fa-at"></i> </span>
        <div class="col-sm-6">
            <input type="email" name="email" value="<#if user.email??>${user.email}<#else></#if>"
                   class="form-control ${(emailError??)?string('is-invalid','')}" placeholder="some@some.com"/>
            <#if emailError??>
                <div class="invalid-feedback">
                ${emailError}
                </div>
            </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Пароль:</label>
        <span class="input-group-text"> <i class="fas fa-user-lock"></i> </span>
        <div class="col-sm-6">
            <input type="password" name="password"
                   class="form-control ${(passwordError??)?string('is-invalid','')}" placeholder="Пароль"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                ${passwordError}
                </div>
            </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Пароль (повтор):</label>
        <span class="input-group-text"> <i class="fas fa-user-lock"></i> </span>
        <div class="col-sm-6">
            <input type="password" name="passwordConfirm"
                   class="form-control ${(passwordError??)?string('is-invalid','')}"
                   placeholder="Повторите пароль"/>
        </div>
    </div>

    <div>
        <div class="col-sm-6">
            <div class="g-recaptcha" data-sitekey="6LcO9rUUAAAAAMG8LhAgV46PZq2g_hTPoP4L6WDt"></div>
            <#if captchaError??>
                <div class="alert alert-danger" role="alert">
                ${captchaError}
                </div>
            </#if>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-primary">ЗАРЕГИСТРИРОВАТЬ</button>
</form>
</#macro>

<#macro account>
    <#include "security.ftl">
    <#if principalUser??>
    <form action="/cabinet" method="get">
        <button type="submit" class="btn btn-success btn-md mr-sm-2">${principalName}</button>
    </form>
    <#else>
    <form action="/registration" method="get">
        <button type="submit" class="btn btn-danger btn-md mr-sm-2">Регистрация</button>
    </form>
    </#if>
</#macro>