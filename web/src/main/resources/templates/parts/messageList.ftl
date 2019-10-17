<#include "security.ftl">
<#import "pager/pager.ftl" as p/>
<#import "pager/navigation.ftl" as n/>
<#import "pager/sizer.ftl" as s/>

<#--обернуть как группу кнопок-->
<div class="row">
<@s.dropdown url page/>
</div>
<#--обернуть как группу кнопок-->

<div class="card-columns">
<#list page.content as message>
    <div class="card my-3">
        <#if message.filename??>
            <img src="/img/${message.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <span>${message.text}</span>
            <i>${message.tag!''}</i>
        </div>
        <div class="card-footer text-muted">
            <a href="/user/messages/${message.author.id}">${message.author.username}</a>
            <#if message.author.id == principalId>
                <a class="btn btn-primary" href="/user/messages/${message.author.id}?message=${message.id}">
                    EDIT
                </a>
            </#if>
        </div>
    </div>
<#else>
    No message
</#list>
</div>
<@n.toggle url page/>
<@p.pager url page/>


