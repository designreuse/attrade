<#import "parts/common.ftl" as c>
<#import "parts/registration.ftl" as r>
<@c.page>
${message!}
    <@r.registration "/registration"/>
</@c.page>