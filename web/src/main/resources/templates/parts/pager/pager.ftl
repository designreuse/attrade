<#macro pager url page>
    <#if page.getTotalPages() gt 7>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])

        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
        >
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>

    <#assign
    minusDrop1 = 5
    minusDrop2 = 10
    minusDrop3 = 25
    plusDrop1 = 5
    plusDrop2 = 10
    plusDrop3 = 25
    minusDrop1Page = ((page.getNumber()-1-minusDrop1)<0)?then(0,page.getNumber()-minusDrop1)
    minusDrop2Page = ((page.getNumber()-1-minusDrop2)<0)?then(0,page.getNumber()-minusDrop2)
    minusDrop3Page = ((page.getNumber()-1-minusDrop3)<0)?then(0,page.getNumber()-minusDrop3)
    plusDrop1Page = ((page.getNumber()-1+plusDrop1)>page.getTotalPages())?then(page.getTotalPages()-1,page.getNumber()+plusDrop1)
    plusDrop2Page = ((page.getNumber()-1+plusDrop2)>page.getTotalPages())?then(page.getTotalPages()-1,page.getNumber()+plusDrop2)
    plusDrop3Page = ((page.getNumber()-1+plusDrop3)>page.getTotalPages())?then(page.getTotalPages()-1,page.getNumber()+plusDrop3)
    />
<ul class="pagination justify-content-center">
    <li class="page-item <#if !page.hasPrevious()>disabled</#if>">
        <a class="page-link" href="${url}?page=${page.getNumber()-1}&size=${page.getSize()}" aria-label="Previous">
            <span aria-hidden="true">&laquo; Пред.</span>
        </a>
    </li>
    <li class="page-item disabled">
        <button id="btnMinusDrop" type="button"
                class="btn btn-primary dropdown-toggle <#if !page.hasPrevious()>disabled</#if>" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-minus"></i>
        </button>
        <div class="dropdown-menu" aria-labelledby="btnMinusDrop">
            <a class="dropdown-item" href="${url}?page=${minusDrop1Page}&size=${page.getSize()}">${minusDrop1}</a>
            <a class="dropdown-item" href="${url}?page=${minusDrop2Page}&size=${page.getSize()}">${minusDrop2}</a>
            <a class="dropdown-item" href="${url}?page=${minusDrop3Page}&size=${page.getSize()}">${minusDrop3}</a>
        </div>
    </li>
    <li class="page-item disabled">
        <button id="btnPlusDrop" type="button"
                class="btn btn-primary dropdown-toggle <#if !page.hasNext()>disabled</#if>" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-plus"></i>
        </button>
        <div class="dropdown-menu" aria-labelledby="btnPlusDrop">
            <a class="dropdown-item" href="${url}?page=${plusDrop1Page}&size=${page.getSize()}">${plusDrop1}</a>
            <a class="dropdown-item" href="${url}?page=${plusDrop2Page}&size=${page.getSize()}">${plusDrop2}</a>
            <a class="dropdown-item" href="${url}?page=${plusDrop3Page}&size=${page.getSize()}">${plusDrop3}</a>
        </div>
    </li>
    <li class="page-item <#if !page.hasNext()>disabled</#if>">
        <a class="page-link" href="${url}?page=${page.getNumber()+1}&size=${page.getSize()}" aria-label="Next">
            <span aria-hidden="true">След. &raquo;</span>
        </a>
    </li>
</ul>

<ul class="pagination justify-content-center">
    <#list body as p>
        <#if (p - 1) == page.getNumber()>
            <li class="page-item active">
                <a class="page-link" href="#" tabindex="-1">${p}</a>
            </li>
        <#elseif p == -1>
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">...</a>
            </li>
        <#else>
            <li class="page-item">
                <a class="page-link" href="${url}?page=${p - 1}&size=${page.getSize()}" tabindex="-1">${p}</a>
            </li>
        </#if>
    </#list>
</ul>

<div class="btn-group">
    <button type="button" class="btn btn-light">по:</button>
    <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
    ${page.getSize()}
        <span class="sr-only">
        </span>
    </button>
    <div class="dropdown-menu">
        <#list [5, 10, 25, 50] as c>
            <#if c == page.getSize()>
                <a class="dropdown-item" href="#" tabindex="-1">${c}</a>
            <#else>
                <#if page.isLast()>
                    <a class="dropdown-item" href="${url}?page=${(page.getTotalElements()/c)?floor}&size=${c}"
                       tabindex="-1">${c}</a>
                <#else>
                    <a class="dropdown-item" href=
                            "${url}?page=0&size=${c}" tabindex="-1">${c}</a>
                </#if>
            </#if>
        </#list>
    </div>
</div>


<ul class="pagination">
    <li class="page-item disabled">
        <a class="page-link" href="#" tabindex="-1">по:</a>
    </li>
    <#list [5, 10, 25, 50] as c>
        <#if c == page.getSize()>
            <li class="page-item active">
                <a class="page-link" href="#" tabindex="-1">${c}</a>
            </li>
        <#else>
            <li class="page-item">
                <#if page.isLast()>
                    <a class="page-link" href="${url}?page=${(page.getTotalElements()/c)?floor}&size=${c}"
                       tabindex="-1">${c}</a>
                <#else>
                    <a class="page-link" href=
                            "${url}?page=0&size=${c}" tabindex="-1">${c}</a>
                </#if>
            </li>
        </#if>
    </#list>
</ul>

</#macro>