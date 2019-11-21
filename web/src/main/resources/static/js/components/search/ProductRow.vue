<template>
    <div>
        <div class="row mx-0 border-top border-muted border-2">
            <div class="col-9 px-0">
                <div class="card border-0">
                    <div class="row no-gutters">
                        <div class="col-4 py-3 pr-1">
                            <a :href="link" class="btn bg-transparent border-0 rounded-0 btn-block px-0 py-0"
                               style="height: 100%">
                                <img :src="picture" class="card-img">
                                <div class="text-left pl-2">
                                    <small>код:&nbsp;{{product.code}}</small>
                                </div>
                            </a>
                        </div>

                        <div class="col-8">
                            <a :href="link" class="btn btn-light border-0 rounded-0 btn-block px-0 py-0"
                               style="height: 100%">
                                <div class="container-fluid">
                                    <div class="card mb-3 bg-transparent border-0 text-left"
                                         style="width: 100%">
                                        <div class="card-body border-0 px-2 pt-3">
                                            <h6 class="card-title border-0 mb-1">{{product.name}}</h6>
                                            <small class="text-muted">
                                                Some quick example text to build on the card title and make up the bulk
                                                of the card's content.
                                            </small>
                                        </div>
                                        <div class="card-footer bg-transparent border-0 pt-0 pb-2">
                                            <div v-if="product.quantityInStock" class="row text-muted">
                                                <small>Наличие:</small>
                                                <i class="fas fa-check-circle text-success"></i>
                                            </div>
                                            <div v-if="product.quantitySupplier" class="row text-muted">
                                                <small>
                                                    На ближайшем складе: {{product.quantitySupplier}}
                                                    <unit :unit="unit"/>
                                                </small>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-3">
                <div class="row">
                    <div v-if="product.price" class="col text-right">
                        <a :href="link" class="btn border-0 float-right py-3">
                            {{product.price}}&nbsp;р.
                        </a>
                    </div>
                    <div v-else="product.price" class="col text-right">
                        <a :href="link" class="btn border-0 float-right py-3">
                            не указана
                        </a>
                    </div>
                </div>

                <div class="row">
                    <div class="col text-center">
                        <a href="#" class="btn btn-sm btn-primary shadow-sm">
                            <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                            &nbsp;В&nbsp;корзину
                        </a>
                    </div>
                </div>

                <div class="row">
                    <div class="col text-center pb-3">
                        <div class="btn-group btn-group-sm" role="group">
                            <button type="button" class="btn btn-sm btn-light border shadow-none" @click.stop="decrement()">
                                <i class="fa fa-minus fa-xs" aria-hidden="true"></i>
                            </button>
                            <input v-model="count"
                                   min="1"
                                   class="form-control text-center rounded-0 border border-left-0 border-right-0 px-0"
                                   style="width: 53px; height: 32px;"
                                   onkeypress="return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57"
                                   @blur="defaultCount"
                            >
                            <button type="button" class="btn btn-sm btn-light border shadow-none" @click.stop="increment()">
                                <i class="fa fa-plus fa-xs" aria-hidden="true"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col text-center">
                        <a href="#" class="btn btn-sm btn-warning shadow-sm mb-3">
                            <i class="far fa-star " aria-hidden="true" style="color: yellow;"></i>
                            <small>
                            &nbsp;В&nbsp;избранное
                            </small>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</template>

<script>
    import Unit from 'components/types/Unit.vue'
    export default {
        data() {
            return {
                unit: this.product.unit,
                count: 1
            }
        },
        components: {
            Unit,
        },
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
            store(){
                return !!this.product.quantityInStock;
            }
        },
        props: ['product'],
        methods: {
            increment: function(){
                this.count = this.count + 1
            },
            decrement: function(){
                if (this.count > 1) {
                    this.count = this.count - 1
                }
            },
            defaultCount: function () {
                if (!this.count || this.count == 0){
                    this.count = 1
                }
            },
        }
    }
</script>

<style>
    .border-2 {
        border-width: 2px !important;
    }
</style>