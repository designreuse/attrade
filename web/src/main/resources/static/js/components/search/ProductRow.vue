<template>
    <div>
        <div class="row my-2 mx-0 border-bottom border-top border-white shadow-sm">
            <div class="col-9">
                <div class="card border-0 bg-transparent">
                    <div class="row no-gutters">
                        <div class="col-3 align-self-center">
                            <a href="link">
                                <img :src="picture" class="card-img">
                            </a>
                        </div>

                        <div class="col-9 pl-2">
                            <button type="button" :href="link" class="btn-light border-0">
                                <p class="font-weight-bold text-left">{{product.name}}</p>
                                <p class="font-weight-normal text-left"></p>
                                <div class="card-footer bg-transparent border-0">
                                    <p class="text-left">
                                        <small class="text-muted">{{store}}</small>
                                    </p>
                                </div>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-3 align-self-center">
                <a :href="link" class="btn border-0 float-right">
                    {{price}}
                </a>
                <a href="#" class="btn btn-success shadow-sm float-right">В корзину</a>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                url: infoUrl
            }
        },
        computed: {
            link(){
                return this.url + "/" + this.product.category.path + this.product.path;
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