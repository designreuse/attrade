<#import "parts/common.ftl" as c>

<@c.page>
<h5>${username}</h5>
${message?if_exists}
<form method="post" xmlns="http://www.w3.org/1999/html">
    <div class="form-group">
        <label class="col-sm-2 col-form-label"> Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
        </div>
    </div>
        <div class="form-group">
            <label class="col-sm-2 col-form-label"> Email:</label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}"/>
            </div>
        </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-primary">Save</button> <!-- button ввиде контента(т.е. <div> не нужен)-->
</form>
</@c.page>