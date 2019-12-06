<#import "../login.ftl" as l>
<#import "../registration.ftl" as r>

<nav class="navbar navbar-expand-lg navbar-light bg-light pb-4" style="padding-top: 80px;">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar1"
            aria-controls="navbar1" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar1">
        <div class="text-danger pl-3 pr-5 mt-1">
            <a href="/" class="btn btn-transparent text-danger border-0 shadow-none">
                <h1>ATTRADE</h1></a>
        </div>
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0"></ul>
        <div id="search"></div>

    </div>
    <div class="btn-group btn-group-sm" role="group" aria-label="Basic example">
    <@r.account/>
        <@l.loginButtons/>
    </div>
</nav>