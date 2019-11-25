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


    <a href="/login" class="btn btn-sm btn-outline-primary pt-2 px-4 shadow-none">
    <#--data-toggle="modal"-->
    <#--data-target="#exampleModalCenter">-->
        ВХОД
    </a>
    <#--<div class="modal" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"-->
    <#--aria-hidden="true">-->
    <#--</div>-->


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
<div style="font-family: 'Numans', sans-serif;">
    <div class="modal-dialog modal-dialog-centered pt-3" role="document" style="max-width: 380px">
        <div class="modal-content">
            <div class="card shadow-lg border border-white">
                <div class="card-header pb-0">
                    <div class="row d-flex ml-1">
                        <a href="javascript:history.go(-1)"
                           class="btn btn-transparent border-0 shadow-none text-secondary px-2 py-2 mt-2"
                        <#--data-dismiss="modal"-->
                        <#--aria-label="Close"-->
                        >
                            <i class="fas fa-long-arrow-alt-left fa-2x"></i>
                        </a>
                        <div class="ml-auto">
                            <div class="text-danger ml-2 mt-2">
                                <a href="/" class="btn btn-transparent text-danger border-0 shadow-none"><h3>
                                    ATTRADE</h3></a>
                            </div>
                        </div>
                    </div>
                    <div class="row ml-1 mt-4 mb-2 text-dark">
                        <h4>ВХОД</h4>
                    </div>
                </div>
                <div class="card-body py-0">
                    <div class="row justify-content-center text-secondary mt-4">
                        через социальные сети
                    </div>
                    <div class="d-flex justify-content-center mt-2 mb-4">
                        <a href="/login/github" class="btn btn-outline-dark mx-2"><i
                                class="fab fa-github  fa-3x"></i></a>
                        <a href="/login/facebook" class="btn btn-outline-primary mx-2"><i
                                class="fab fa-facebook fa-3x"></i></a>
                        <a href="/login/google" class="btn btn-outline-danger mx-2"><i
                                class="fab fa-google fa-3x"></i></a>
                    </div>
                    <div class="row justify-content-center text-secondary">
                        или
                    </div>
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
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                            </div>
                            <input type="text" name="username" value="<#if username??>${username}</#if>"
                                   autofocus="autofocus"
                                   class="form-control ${(error??)?string('is-invalid','')}"
                                   placeholder="email"/>
                        </div>

                        <div class="input-group form-group mb-1">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" name="password"
                                   class="form-control ${(error??)?string('is-invalid','')}"
                                   placeholder="пароль"/>
                        </div>

                        <a href="#" class="float-right">Забыли пароль?</a>

                        <div class="form-group">
                            <button type="submit"
                                    class="btn btn-success btn-block float-right mt-2 mb-4">
                                Войти
                            </button>
                        </div>
                    </form>
                </div>
                <div class="card-footer">
                    <div class="d-flex justify-content-center text-secondary">
                        Еще не зарегистрированы?&nbsp;<a href="/registration">Регистрация</a>
                    </div>
                    <div class="row justify-content-right text-muted mt-4">
                        <small>© 2001–2019 &emsp;ATTRADE</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>
