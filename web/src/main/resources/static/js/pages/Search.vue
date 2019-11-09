<template>
    <div>
        <p>
            <input v-model="question">
        </p>
        <category-row v-for="(category,i) in categories"
                      :key="i"
                      :category="category"/>
        <product-row v-for="(product,i) in products"
                     :key="`A-${i}`"
                     :product="product"/>
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
                categories: [],
                products: [],
            }
        },
        watch: {
            // эта функция запускается при любом изменении вопроса
            question: function (newQuestion, oldQuestion) {
                if (newQuestion === '') {
                    this.clearAll()
                } else {
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
                if (this.question != '') {
                    var vm = this
                    searchApi.getCategories(this.question)
                        .then(function (response) {
                            vm.categories = response.data
                        })
                        .catch(function (error) {
                            console.info('Ошибка! Не могу связаться с API. ' + error)
                        })
                    searchApi.getProducts(this.question)
                        .then(function (response) {
                            vm.products = response.data
                        })
                        .catch(function (error) {
                            console.info('Ошибка! Не могу связаться с API. ' + error)
                        })
                }
            },
            clearAll: function () {
                this.categories = []
                this.products = []
            }
        }
    }
</script>
<style>

</style>