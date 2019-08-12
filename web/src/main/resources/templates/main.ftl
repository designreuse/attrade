<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>
    <@l.logout />
</div>
<div>
    <form method="post">
        <input type="text" name="text" placeholder="Enter message">
        <input type="text" name="tag" placeholder="Enter tag">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit">ADD</button>
    </form>
</div>
<div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter}">
        <button type="SUBMIT">FIND</button>
    </form>
</div>
<div>List of messages</div>
<#list messages as message>
<div>
    <b>${message.id}</b>
    <span>${message.text}</span>
    <i>${message.tag}</i>
    <strong>${message.authorName}</strong>
</div>
<#else>
No message
</#list>
</@c.page>