<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>
<#if known>
    <#assign
    principalUser=Session.SPRING_SECURITY_CONTEXT.authentication.principal
    principalName=principalUser.getUsername()
    isAdmin=principalUser.isAdmin()
    principalId=principalUser.getId()
    >
<#else>
    <#assign
    principalName = "unknown"
    isAdmin=false
    principalId=-1
    >
</#if>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <#assign
    securityMessage=Session.SPRING_SECURITY_LAST_EXCEPTION.message
    >
</#if>
