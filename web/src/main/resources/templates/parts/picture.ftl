<#macro picture path alt class style>
<#assign
    root = "/static/image"
    path320 = "/320"<#--@media (min-width:320px)  { /* smartphones, iPhone, portrait 480x320 phones */ }-->
    path481 = "/481"<#--@media (min-width:481px)  { /* portrait e-readers (Nook/Kindle), smaller tablets @ 600 or @ 640 wide. */ }-->
    path641 = "/641"<#--@media (min-width:641px)  { /* portrait tablets, portrait iPad, landscape e-readers, landscape 800x480 or 854x480 phones */ }-->
    path961 = "/961"<#--@media (min-width:961px)  { /* tablet, landscape iPad, lo-res laptops ands desktops */ }-->
    path1025 = "/1025"<#--@media (min-width:1025px) { /* big landscape tablets, laptops, and desktops */ }-->
    path1281 = "/1281"<#--@media (min-width:1281px) { /* hi-res laptops and desktops */ }-->

<#--Bootstrap grid sm md lg xl-->
    path540 = "/540"
    <#--path720 = "/720"-->
    <#--path992 = "/992"-->
    <#--path1140 = "/1140"-->

    path541 = "/541"
/>
<picture>
    <source srcset="${root}${path540}${path}" media="(max-width: 540px)">
    <source srcset="${root}${path541}${path}" media="(min-width: 541px)">

    <source srcset="${root}${path}">
    <img srcset="${root}${path}" alt="${alt}" class="${class}" style="${style}">
</picture>
</#macro>