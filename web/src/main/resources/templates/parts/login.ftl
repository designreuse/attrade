<#include "security.ftl">
<#macro loginButtons>
    <#if principalUser??>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-sm btn-outline-primary pt-2 pb-2 px-4 shadow-none">
            ВЫХОД
        </button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
    <#else>


    <a href="#" class="btn btn-sm btn-outline-primary pt-2 px-4 shadow-none" data-toggle="modal" data-target="#exampleModalCenter">
        ВХОД
    </a>
    <div class="modal" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered pt-3" role="document" style="max-width: 380px">
            <div class="modal-content">
                <@login></@login>
            </div>
        </div>
    </div>


    <a href="/login/github" class="btn btn-sm btn-outline-primary py-2 px-2">
        <i class="fab fa-github fa-1x" aria-hidden="true"></i></i>
    </a>
    <a href="/login/facebook" class="btn btn-sm btn-outline-primary py-2 px-2">
        <i class="fab fa-facebook-f fa-1x" aria-hidden="true"></i></i>
    </a>
    <a href="/login/google" class="btn btn-sm btn-outline-primary py-2 px-2">
        <i class="fab fa-google fa-1x" aria-hidden="true"></i></i>
    </a>
    </#if>
</#macro>

<#macro login>
    <#import "picture.ftl" as p/>
<div class="parts-login">
    <div class="background">
        <div class="card shadow-lg border border-white">

            <div class="card-header pb-0 mt-2">
                <div class="row d-flex mr-2">
                <div class="ml-auto text-white">
                    <button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                </div>
                <div class="row d-flex mr-2">
                    <div class="ml-auto text-white">
                        <h3>ATTRADE&emsp;</h3>
                    </div>
                </div>
                <div class="row ml-1 mt-3 text-white">
                    <h4>Вход</h4>
                </div>
                <div class="row justify-content-center text-white mt-3">
                    через социальные сети
                </div>
                <div class="d-flex justify-content-center social_icon">
                    <a href="/login/github"><span><i class="fab fa-github-square px-2"></i></span></a>
                    <a href="/login/facebook"><span><i class="fab fa-facebook-square px-2"></i></span></a>
                    <a href="/login/google"><span><i class="fab fa-google px-2"></i></span></a>
                </div>
                <div class="row justify-content-center text-white mt-2">
                    или
                </div>
            </div>
            <div class="card-body py-0">
                <#if error??>
                    <div class="alert alert-danger py-0 my-1" role="alert">
                        <small>${error}</small>
                    </div>
                <#else>
                    <div class="alert alert-transparent py-0 my-1" role="alert">
                        <small>&nbsp;</small>
                    </div>
                </#if>
                <form action="/login" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text border-0"><i class="fas fa-user"></i></span>
                        </div>
                        <input type="text" name="username" value="<#if username??>${username}</#if>"
                               autofocus="autofocus"
                               class="form-control ${(error??)?string('is-invalid','')}"
                               placeholder="email"/>
                    </div>

                    <div class="input-group form-group mb-1">
                        <div class="input-group-prepend">
                            <span class="input-group-text border-0"><i class="fas fa-key"></i></span>
                        </div>
                        <input type="password" name="password"
                               class="form-control ${(error??)?string('is-invalid','')}"
                               placeholder="пароль"/>
                    </div>

                    <a href="#">Забыли пароль?</a>

                    <div class="form-group">
                        <input type="submit" value="Войти" class="btn float-right login_btn">
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <div class="d-flex justify-content-center text-white">
                    Еще не зарегистрированы?&nbsp;<a href="/registration">Регистрация</a>
                </div>
                <div class="row justify-content-right text-muted mt-4">
                    <small>© 2001–2019 &emsp;ATTRADE</small>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>
