<#include "security.ftl">
<#macro registration absolutePath>
<form action="${absolutePath}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Email:</label>
        <span class="input-group-text"> <i class="fas fa-at"></i> </span>
        <div class="col-sm-6">
            <input type="email" name="email" value="<#if user.email??>${user.email}<#else></#if>"
                   class="form-control ${(emailError?? || UserAlreadyExistsException?? || UserEmailValidationException??)?string('is-invalid','')}"
                   placeholder="some@some.com"/>
            <#if emailError??>
                <div class="invalid-feedback">
                ${emailError}
                </div>
            </#if>
            <#if UserAlreadyExistsException??>
                <div class="invalid-feedback">
                ${UserAlreadyExistsException}
                </div>
            </#if>
            <#if UserEmailValidationException??>
                <div class="invalid-feedback">
                ${UserEmailValidationException}
                </div>
            </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Пароль:</label>
        <span class="input-group-text"> <i class="fas fa-user-lock"></i> </span>
        <div class="col-sm-6">
            <input type="password" name="password"
                   class="form-control ${(passwordError?? || UserPasswordValidationException??)?string('is-invalid','')}"
                   placeholder="Пароль"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                ${passwordError}
                </div>
            </#if>
            <#if UserPasswordValidationException??>
                <div class="invalid-feedback">
                ${UserPasswordValidationException}
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
    <#if principalUser??>
        <@account_cabinet/>
    <#else>
        <@account_new/>
    </#if>
</#macro>

<#macro account_cabinet>
<form action="/account" method="get">
    <button type="submit" role="button" class="btn btn-sm btn-outline-success mr-2 px-2 shadow-none">
        <i class="fas fa-user-circle fa-2x" aria-hidden="true"></i>
    </button>
</form>
</#macro>


<#macro account_new>
<a href="/registration" class="btn btn-sm btn-outline-success mr-2 py-2 shadow-none">
    <i class="fa fa-user fa-1x" aria-hidden="true"></i>
</a>
</#macro>

<#macro account_new_modal>
<a href="/registration" class="btn btn-sm btn-outline-success mr-2 py-2" data-toggle="modal"
   data-target="#account-new-modal">
    <i class="fa fa-user" aria-hidden="true"></i>
</a>

<div class="modal fade" id="account-new-modal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" type="button" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                Modal body ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
</#macro>