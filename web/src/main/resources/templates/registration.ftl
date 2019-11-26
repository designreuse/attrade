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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js"></script>
    <style>
        .turbolinks-progress-bar {
            height: 3px;
            background-color: deepskyblue;
            z-index: 10000;
        }
    </style>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
    <!-- Material Design Bootstrap -->
<#--<link href="/static/mdb/css/mdb.min.css" rel="stylesheet">-->

    <!-- версия для разработки, отображает полезные предупреждения в консоли -->
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <!--test for: hover-->
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>

    <!-- MDB core JavaScript -->
<#--<script type="text/javascript" src="/static/mdb/js/mdb.min.js"></script>-->
</head>
<body>

<div style="font-family: 'Numans', sans-serif;">
    <div class="modal-dialog modal-dialog-centered pt-3" role="document" style="max-width: 380px">
        <div class="modal-content">
            <div class="card shadow border border-white">
                <div class="card-header pb-0 mb-4">
                    <div class="row d-flex ml-1">
                        <a href="javascript:history.go(-1)"
                           class="btn btn-transparent border-0 shadow-none px-2 py-2 mt-2">
                            <i class="fas fa-long-arrow-alt-left text-secondary fa-2x"></i>
                        </a>
                        <div class="ml-auto">
                            <div class="text-danger ml-2 mt-2">
                                <a href="/" class="btn btn-transparent text-danger border-0 shadow-none"><h3>
                                    ATTRADE</h3></a>
                            </div>
                        </div>
                    </div>
                    <div class="row ml-1 mt-4 mb-2 text-dark">
                        <h4>РЕГИСТРАЦИЯ</h4>
                    </div>

                </div>


                <div class="card-body py-0">
                    <form action="/registration" method="post">
                    <#if  emailError??>
                        <div class="alert alert-danger py-0 my-0" role="alert">
                            <small>${emailError}</small>
                        </div>
                    </#if>
                    <#if  UserAlreadyExistsException??>
                        <div class="alert alert-danger py-0 my-0" role="alert">
                            <small>${ UserAlreadyExistsException}</small>
                        </div>
                    </#if>
                    <#if  UserEmailValidationException??>
                        <div class="alert alert-danger py-0 my-0" role="alert">
                            <small>${ UserEmailValidationException}</small>
                        </div>
                    </#if>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-at"></i></span>
                            </div>
                            <input type="email" name="email" value="<#if user.email??>${user.email}<#else></#if>"
                                   <#if !user.email??>autofocus="autofocus"</#if>
                                   class="form-control ${(emailError?? || UserAlreadyExistsException?? || UserEmailValidationException??)?string('is-invalid','')}"
                                   placeholder="email"/>
                        </div>

                    <#if  UserPasswordValidationException??>
                        <div class="alert alert-danger py-0 my-0" role="alert">
                            <small>${ UserPasswordValidationException}</small>
                        </div>
                    <#else>
                        <div class="alert alert-warning py-0 my-0" role="alert">
                            <small>${ userPasswordInfo}</small>
                        </div>
                    </#if>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-key"></i></span>
                            </div>
                            <input type="password" name="password"
                                   class="form-control ${(passwordError?? || UserPasswordValidationException??)?string('is-invalid','')}"
                                   placeholder="пароль"/>
                        </div>

                    <#if  passwordError??>
                        <div class="alert alert-danger py-0 my-0" role="alert">
                            <small>${ passwordError}</small>
                        </div>
                    </#if>
                        <div class="input-group form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"><i class="fas fa-redo"></i></span>
                            </div>
                            <input type="password" name="passwordConfirm"
                                   class="form-control ${(passwordError??)?string('is-invalid','')}"
                                   placeholder="повторите пароль"/>
                        </div>

                    <#if  captchaError??>
                        <div class="alert alert-danger py-0 my-0" role="alert">
                            <small>${ captchaError}</small>
                        </div>
                    </#if>
                        <div class="col">
                            <div class="g-recaptcha"
                                 data-sitekey="6LcO9rUUAAAAAMG8LhAgV46PZq2g_hTPoP4L6WDt"></div>
                        </div>

                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <div class="form-group">
                            <button type="submit"
                                    class="btn btn-success btn-block float-right mt-2 mb-4">
                                Зарегистрировать
                            </button>
                        </div>
                    </form>
                </div>

                <div class="card-footer">
                    <div class="d-flex justify-content-center text-secondary">
                        Уже зарегистрированы?&nbsp;
                        <form action="/login" method="get">
                            <button type="submit" class="btn btn-light border-0 shadow-none text-primary pt-0">
                                Войти
                            </button>
                        </form>
                    </div>
                    <div class="row justify-content-right text-muted mt-4">
                        <small>© 2001–2019 &emsp;ATTRADE</small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>

</body>
</html>