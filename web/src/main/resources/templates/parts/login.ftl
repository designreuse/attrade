<#macro login path>
<form action="${path}" method="post" xmlns="http://www.w3.org/1999/html">
    <div class="form-group row">
        <!--поле 'User Name' размером в 2 клонки, поле как label-->
        <label class="col-sm-2 col-form-label"> User Name: </label>
        <span class="input-group-text"> <i class="fas fa-user"></i> </span>
        <div class="col-sm-6">
            <input type="text" name="username"
                   class="form-control ${(usernameError??)?string('is-invalid','')}" placeholder="User name"/>
            <!--поле ввода как 'form-control'-->
            <#if usernameError??>
                <div class="invalid-feedback">
                ${usernameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Password:</label>
        <span class="input-group-text"> <i class="fas fa-user-lock"></i> </span>
        <div class="col-sm-6">
            <input type="password" name="password"
                   class="form-control ${(passwordError??)?string('is-invalid','')}" placeholder="Password"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                ${passwordError}
                </div>
            </#if>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <a href="/registration">Add new user</a>
    <!-- button ввиде контента(т.е. <div> не нужен)-->
    <button type="submit" class="btn btn-primary">Sign In</button>
</form>
</#macro>

<#macro logout>
    <#include "security.ftl">
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-primary"><#if principalUser??>Sign Out<#else>Log in</#if></button>
</form>
</#macro>