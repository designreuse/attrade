<#include "security.ftl">
<#macro login path>
<div class="parts-login-login">
    <div class="d-flex justify-content-center h-100">
        <div class="card">
            <div class="card-header">
                <h6> через соцсети &#8594;</h6>
                <h3>Вход в аккаунт</h3>
                <div class="d-flex justify-content-end social_icon">
                    <span><i class="fab fa-facebook-square"></i></span>
                    <span><i class="fab fa-google-plus-square"></i></span>
                    <span><i class="fab fa-twitter-square"></i></span>
                </div>
            </div>
            <div class="card-body">
                <form action="${path}" method="post" xmlns="http://www.w3.org/1999/html">
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input type="text" name="username"
                               class="form-control ${(securityMessage??)?string('is-invalid','')}"
                               placeholder="логин или email"/>

                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input type="password" name="password"
                               class="form-control ${(securityMessage??)?string('is-invalid','')}"
                               placeholder="пароль"/>
                    </div>

                    <div class="form-group">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" value="Войти" class="btn float-right login_btn">
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center links">
                    Еще не зарегистрированы?<a href="/registration">Регистрация</a>
                </div>
                <div class="d-flex justify-content-center">
                    <a href="#">Забыли пароль?</a>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro logout>
    <#include "security.ftl">
<input type="hidden" name="_csrf" value="${_csrf.token}">
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
    </form>
</#macro>