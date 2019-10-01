<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>
<#if known>
    <#assign
    principalUser=Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name=principalUser.getUsername()
    isAdmin=principalUser.isAdmin()
    currentUserId=principalUser.getId()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin=false
    currentUserId=-1
    >
</#if>