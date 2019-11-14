<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>
<#if known>
    <#assign
    principalUser="known"
    principalName="name"
    isAdmin=false
    principalId=1
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
