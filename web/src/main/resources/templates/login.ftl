<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--Define parameters of viewing on different devices-->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Recaptcha-->
    <script src="https://www.google.com/recaptcha/api.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <!-- Material Design Bootstrap -->
<#--<link href="/static/mdb/css/mdb.min.css" rel="stylesheet">-->

    <!-- версия для разработки, отображает полезные предупреждения в консоли -->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

    <link href="/static/css/login.css" rel="stylesheet">

</head>
<body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

<#include "parts/security.ftl">
<#import "parts/picture.ftl" as p/>
<div class="container">
    <div class="d-flex justify-content-center h-100">
        <div class="background">
        <div class="card shadow-lg border border-warning">

            <div class="card-header pb-0 mt-2">
                <div class="row d-flex mr-2">
                    <i class="fas fa-long-arrow-alt-left fa-2x pl-3 text-white"></i>
                    <div class="ml-auto text-white">
               <h3>ATTRADE</h3>
                    </div>
            </div>
                <div class="row ml-1 mt-3 text-white">
               <h4>Вход</h4>
                </div>
            <#--<@p.static path="/logo/logo.png" alt="..." class="" style=""/>-->
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
                        <input type="text" name="username" value="<#if username??>${username}</#if>" autofocus="autofocus"
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
</div>

</body>
</html>
