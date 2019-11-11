<#import "../login.ftl" as l>
<#import "../registration.ftl" as r>

<nav class="navbar navbar-expand-lg navbar-light bg-light pb-4" style="padding-top: 80px;">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1"
            aria-controls="navbar1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar1">
        <a class="navbar-brand" href="/">
            <img src="/static/picture/logo/logo.png" alt="..">
        </a>
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0"></ul>
        <div id="search"></div>

    </div>
    <div class="btn-group btn-group-sm" role="group" aria-label="Basic example">
    <@r.account/>
        <@l.logout/>
    </div>
</nav>