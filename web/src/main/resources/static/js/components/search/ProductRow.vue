<template>
    <div>
        <div class="row mx-0 border-top border-muted">
            <div class="col-9 px-0">
                <div class="card border-0 bg-transparent">
                    <div class="row no-gutters">
                        <div class="col-3 align-self-center py-2">
                            <a href="link">
                                <img :src="picture" class="card-img">
                            </a>
                        </div>

                        <div class="col-9">

                            <a :href="link" class="btn btn-light border-0 btn-block px-0 py-0" style="height: 100%">
                                <div class="container-fluid">
                                    <div class="card border-success mb-3 bg-transparent border-0 text-left"
                                         style="width: 100%">
                                        <div class="card-body border-0 px-2 pt-2">
                                            <h6 class="card-title border-0 mb-1">{{product.name}}</h6>
                                            <small class="text-muted">
                                                Some quick example text to build on the card title and make up the bulk
                                                of the card's content.
                                            </small>
                                        </div>
                                        <div class="card-footer bg-transparent border-0 pt-0 pb-2">
                                            {{store}}
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-3 ">
                <a :href="link" class="btn border-0 float-right mt-2">
                    {{price}}
                </a>
                <a href="#" class="btn btn-sm btn-success shadow-sm float-right my-3">
                    В корзину
                </a>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        computed: {
            link(){
                return this.product.category.path + this.product.path;
            },
            picture(){
                return "upload/picture/" + this.product.picture
            },
            alt(){
                return this.product.name
            },
            price(){
                if (this.product.price != null) {
                    return this.product.price + " р."
                } else {
                    return "не указана"
                }
            },
            store(){
                if (this.product.quantityInStock) {
                    return this.product.quantityInStock
                } else {
                    return "под заказ"
                }
            }
        },
        props: ['product'],
        methods: {}
    }
</script>

<style>
</style>