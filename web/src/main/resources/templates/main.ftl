<#import "parts/common/main.ftl" as c>
<@c.page>
<div class="form-row">
    <div class="form group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" value="${filter?if_exists}" placeholder="Search by tag">
            <button type="SUBMIT" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>
<#include "parts/messageEdit.ftl"/>
<#include "parts/messageList.ftl"/>
</@c.page>