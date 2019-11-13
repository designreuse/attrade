<template>
    <div class="input-group pr-3">
        <div class="input-group-prepend col-12 px-0 mx-0">
            <span class="input-group-text bg-white border-right-0" id="basic-text1"><i
                    class="fas fa-search text-muted" aria-hidden="true"></i>
                </span>
            <input v-model="question" id="question" ref="search" class="form-control rounded-0 border-left-0" type="text"
                   placeholder="Поиск в каталоге. Например, 'лампа led'" aria-label="Search" data-toggle="dropdown"
                   aria-haspopup="false" aria-expanded="true"
                    @focus="getAnswer">
            <div class="dropdown-menu col-12 pb-0" aria-labelledby="question" id="dropdown-menu" ref="menu">
                <category-row  v-for="(category,i) in categories"
                              :key="i"
                              :category="category"/>
                <product-row v-for="(product,i) in products"
                             :key="`A-${i}`"
                             :product="product"/>
                <div class="row justify-content-center mx-0 mb-1">
                    <button type="button" class="btn btn-light btn-block border border-white shadow-lg" @click.stop="getMoreProducts()">
                        <i class="fas fa-angle-double-down text-muted"></i>
                    </button>
                </div>
                <div class="row justify-content-center mx-0" id="up">
                    <button type="button" class="btn btn-light btn-block border border-white shadow-lg"  @click="setFocus()">
                        <i class="fas fa-chevron-circle-up text-muted"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import CategoryRow from 'components/search/CategoryRow.vue'
    import ProductRow from 'components/search/ProductRow.vue'
    import * as _ from 'lodash'
    import searchApi from 'api/search'

    export default {
        components: {
            CategoryRow,
            ProductRow
        },
        data() {
            return {
                question: null,
                page: 0,
                categories: [],
                products: [],
            }
        },
        watch: {
            // эта функция запускается при любом изменении вопроса
            question: function (newQuestion, oldQuestion) {
                if (newQuestion === '') {
                    $('#question').dropdown('hide')
                    this.clearAll()
                } else {
                    $('#dropdown-menu').scrollTop(0)
                    this.debouncedGetAnswer()
                }
            }
        },
        created: function () {
            // _.debounce — это функция lodash, позволяющая ограничить то,
            // насколько часто может выполняться определённая операция.
            // В данном случае мы ограничиваем частоту обращений к yesno.wtf/api,
            // дожидаясь завершения печати вопроса перед отправкой ajax-запроса.
            // Узнать больше о функции _.debounce (и её родственнице _.throttle),
            // можно в документации: https://lodash.com/docs#debounce
            this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
        },
        methods: {
            getAnswer: function () {
//                this.answer = 'Думаю...'
                if (this.question) {
                    let vm = this
                    searchApi.getCategories(this.question, this.page, null)
                        .then(function (response) {
                            vm.categories = response.data
                            if (vm.categories.length != 0) {
                                $('#question').dropdown('show')
                            } else {
                                $('#question').dropdown('hide')
                            }
                        })
                        .catch(function (error) {
                            console.info('Ошибка! Не могу связаться с API. ' + error)
                        })
                    searchApi.getProducts(this.question, this.page, null)
                        .then(function (response) {
                            vm.products = response.data
                        })
                        .catch(function (error) {
                            console.info('Ошибка! Не могу связаться с API. ' + error)
                        })
                }
            },
            clearAll: function () {
                this.page = 0
                this.categories = []
                this.products = []
            },
            setFocus: function() {
                this.page = 0
                this.$refs.search.focus();
                $('#dropdown-menu').scrollTop(0)
            },
            getMoreProducts: function () {
                let vm = this
                vm.page = vm.page + 1
                searchApi.getProducts(this.question, this.page, null)
                    .then(function (response) {
                        vm.products = vm.products.concat(response.data)
                    })
                    .catch(function (error) {
                        console.info('Ошибка! Не могу связаться с API. ' + error)
                    })
            }
        },
    }
</script>
<style>
    #dropdown-menu {
        height: 600px !important;
        overflow: scroll;
        overflow-x: hidden;
    }
    #up{
        position: -webkit-sticky;
        position: sticky;
        bottom: 0;
        z-index: 10;
    }
</style>