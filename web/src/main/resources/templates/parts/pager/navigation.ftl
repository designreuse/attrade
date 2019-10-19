<#macro toggle url page>
    <#assign
    steps = [5,10,25,50]
    />
<ul class="pagination justify-content-center">
    <li class="page-item <#if !page.hasPrevious()>disabled</#if>">
        <a class="page-link" href="${url}?page=${page.getNumber()-1}&size=${page.getSize()}" aria-label="Previous">
            <span aria-hidden="true">&laquo; Назад</span>
        </a>
    </li>
    <li class="page-item disabled">
        <button id="btnMinusDrop" type="button"
                class="btn btn-primary rounded-0 dropdown-toggle <#if !page.hasPrevious()>disabled</#if>" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-minus"></i>
        </button>
        <div class="dropdown-menu" aria-labelledby="btnMinusDrop">
            <#list steps as step>
                <a class="dropdown-item"
                   href="${url}?page=${((page.getNumber()-1-step)<0)?then(0,page.getNumber()-step)
                   }&size=${page.getSize()}">${step}</a>
            </#list>
        </div>
    </li>
    <li class="page-item disabled">
        <button id="btnPlusDrop" type="button"
                class="btn btn-primary rounded-0 dropdown-toggle <#if !page.hasNext()>disabled</#if>" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-plus"></i>
        </button>
        <div class="dropdown-menu" aria-labelledby="btnPlusDrop">
            <#list steps as step>
                <a class="dropdown-item"
                   href="${url}?page=${((page.getNumber()+step)>=page.getTotalPages())?then(page.getTotalPages()-1,page.getNumber()+step)}&size=${page.getSize()}">${step}</a>
            </#list>
        </div>
    </li>
    <li class="page-item <#if !page.hasNext()>disabled</#if>">
        <a class="page-link" href="${url}?page=${page.getNumber()+1}&size=${page.getSize()}" aria-label="Next">
            <span aria-hidden="true">Вперед &raquo;</span>
        </a>
    </li>
</ul>
</#macro>