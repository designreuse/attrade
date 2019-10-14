<#include "security.ftl">
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
    <#if principalUser??>
        <@account_cabinet/>
    <#else>
        <@account_new_modal/>
    </#if>
</#macro>

<#macro account_cabinet>
<form action="/account" method="get">
    <button type="submit" role="button" class="btn btn-success btn mr-2">
            <span class="badge bg-transparent">
                    <i class="fa fa-user" aria-hidden="true">
                        </i>
            ${principalName}
                </span>
    </button>
</form>
</#macro>
<#macro account_new>
<form action="/registration" method="get">
    <button type="submit" role="button" class="btn btn-danger btn mr-2">
            <span class="badge bg-transparent">
                    <i class="fa fa-user" aria-hidden="true">
                        </i>
            Регистрация
                </span>
    </button>
</form>
</#macro>

<#macro account_new_modal>
<form>
    <button type="button" class="btn btn-danger btn mr-2" data-toggle="modal" data-target="#account-new-modal">
            <span class="badge bg-transparent">
                    <i class="fa fa-user" aria-hidden="true">
                        </i>
            Регистрация
                </span>
    </button>
</form>

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
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</#macro>