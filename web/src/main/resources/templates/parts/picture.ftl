<#assign
staticRoot = "/static/picture"
uploadRoot = "/upload/picture"
<#--Classification-->
<#--path320 = "/320"&lt;#&ndash;@media (min-width:320px)  { /* smartphones, iPhone, portrait 480x320 phones */ }&ndash;&gt;-->
<#--path481 = "/481"&lt;#&ndash;@media (min-width:481px)  { /* portrait e-readers (Nook/Kindle), smaller tablets @ 600 or @ 640 wide. */ }&ndash;&gt;-->
<#--path641 = "/641"&lt;#&ndash;@media (min-width:641px)  { /* portrait tablets, portrait iPad, landscape e-readers, landscape 800x480 or 854x480 phones */ }&ndash;&gt;-->
<#--path961 = "/961"&lt;#&ndash;@media (min-width:961px)  { /* tablet, landscape iPad, lo-res laptops ands desktops */ }&ndash;&gt;-->
<#--path1025 = "/1025"&lt;#&ndash;@media (min-width:1025px) { /* big landscape tablets, laptops, and desktops */ }&ndash;&gt;-->
<#--path1281 = "/1281"&lt;#&ndash;@media (min-width:1281px) { /* hi-res laptops and desktops */ }&ndash;&gt;-->

<#--Bootstrap grid sm md lg xl-->
path540 = "/540"
<#--path720 = "/720"-->
<#--path992 = "/992"-->
<#--path1140 = "/1140"-->

path541 = "/541"
/>
<#macro static path alt class style>
<picture>
    <source srcset="${staticRoot}${path540}${path}" media="(max-width: 540px)">
    <source srcset="${staticRoot}${path541}${path}" media="(min-width: 541px)">

    <source srcset="${staticRoot}${path}">
    <img srcset="${staticRoot}${path}" alt="${alt}" class="${class}" style="${style}">
</picture>
</#macro>

<#macro upload path alt class style>
<picture>
    <source srcset="${uploadRoot}${path540}${path}" media="(max-width: 540px)">
    <source srcset="${uploadRoot}${path541}${path}" media="(min-width: 541px)">

    <source srcset="${uploadRoot}${path}">
    <img srcset="${uploadRoot}${path}" alt="${alt}" class="${class}" style="${style}">
</picture>
</#macro>