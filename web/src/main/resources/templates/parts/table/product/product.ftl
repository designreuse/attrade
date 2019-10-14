<#--<div class="parts-table-table-product">-->
<div class="container">
    <div class="row">
    <#list 1..20 as i>
        <div class="col-xs-2 col-lg-3 my-2">
            <div class="thumbnail">
                <a href="#"><img src="http://placehold.it/200x180" alt=""></a>
                <div class="caption">
                    <h5><a href="#">Name of product</a></h5>
                    <p>Description of product</p>
                    <a href="#" class="btn btn-success" style="background: green">Купить</a>
                </div>
            </div>
        </div>
    </#list>
    </div>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>
            <li class="page-item"><a class="page-link" href="#">1</a></li>
            <li class="page-item"><a class="page-link" href="#">2</a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#">Next</a>
            </li>
        </ul>
    </nav>
</div>

<#--</div>-->