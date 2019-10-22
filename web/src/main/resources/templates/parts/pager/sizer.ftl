<#macro dropdown url page>
    <#assign sizeList=[4,16,32,64]/>
<div class="btn-group">
    <button type="button" class="btn btn-light">по:</button>
    <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
    ${page.getSize()}
        <span class="sr-only">
        </span>
    </button>
    <div class="dropdown-menu">
        <#list sizeList as size>
            <#if size == page.getSize()>
                <a class="dropdown-item" href="#" tabindex="-1">${size}</a>
            <#else>
                <#if page.isLast()>
                    <a class="dropdown-item" href="${url}?page=${(page.getTotalElements()/size)?floor}&size=${size}"
                       tabindex="-1">${size}</a>
                <#else>
                    <a class="dropdown-item" href=
                            "${url}?page=0&size=${size}" tabindex="-1">${size}</a>
                </#if>
            </#if>
        </#list>
    </div>
</div>
</#macro>

<#macro inline url page>
    <#assign sizeList=[4,16,32,64]/>
<ul class="pagination">
    <li class="page-item disabled">
        <a class="page-link" href="#" tabindex="-1">по:</a>
    </li>
    <#list sizeList as size>
        <#if size == page.getSize()>
            <li class="page-item active">
                <a class="page-link" href="#" tabindex="-1">${size}</a>
            </li>
        <#else>
            <li class="page-item">
                <#if page.isLast()>
                    <a class="page-link" href="${url}?page=${(page.getTotalElements()/size)?floor}&size=${size}"
                       tabindex="-1">${size}</a>
                <#else>
                    <a class="page-link" href=
                            "${url}?page=0&size=${size}" tabindex="-1">${size}</a>
                </#if>
            </li>
        </#if>
    </#list>
</ul>
</#macro>