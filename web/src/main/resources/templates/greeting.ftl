<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page>
<h5><#if user??>Hello, ${name}<#else>Hello, guest</#if></h5>
<div>This is a simple clone of Twitter</div>
</@c.page>