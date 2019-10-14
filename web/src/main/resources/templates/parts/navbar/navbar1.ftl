<#import "../login.ftl" as l>
<#import "../registration.ftl" as r>

<nav class="navbar navbar-expand-lg navbar-light bg-light navbar-static-top">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1"
            aria-controls="navbar1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar1">
        <a class="navbar-brand" href="/">
            <img src="/static/image/logo.png" alt="..">
        </a>
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
        </ul>
        <div class="input-group md-form form-sm form-1 pl-0 ml-1">
            <div class="input-group-prepend">
                        <span class="input-group-text purple lighten-3" id="basic-text1"><i
                                class="fas fa-search text-white" aria-hidden="true"></i></span>
            </div>
            <input class="form-control my-0 py-1 mr-5" type="text"
                   placeholder="Поиск в каталоге. Например, &#34;лампа led&#34;" aria-label="Search">
        </div>
    </div>
    <div class="btn-group btn-group-sm" role="group" aria-label="Basic example">
    <@r.account/>
        <@l.logout/>
    </div>
</nav>