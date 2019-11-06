<template>
    <div>
        <p>
            <input v-model="question">
        </p>
        <category-row v-for="category in categories"
                      :key="category.id"
                      :category="category"/>
        <product-row v-for="product in products"
                     :key="product.id"
                     :product="product"/>
    </div>
</template>

<script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
<script>
    import CategoryRow from 'components/search/CategoryRow.vue'
    import ProductRow from 'components/search/ProductRow.vue'
    import { mapActions } from 'vuex'
    import {mapState} from 'vuex'
    export default {
        data: {
            question: '',
        },
        computed: mapState(['products'] ['categories']),
        watch: {
            // эта функция запускается при любом изменении вопроса
            question: function (newQuestion, oldQuestion) {
                if (newQuestion === ''){
                    this.clearAll()
                }else {
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
            ...mapActions(['getProductsAction','getCategoriesAction', 'removeProductsAction', 'removeCategoriesAction']),
            getAnswer() {
                //                this.answer = 'Думаю...'
                this.categories = this.getCategoriesAction(this.question)
                this.products = this.getProductsAction(this.question)
            },
            clearAll(){
                this.categories = []
                this.products = []
                this.removeCategoriesAction()
                this.removeProductsAction()
            }
        }
    }
</script>
<style>

</style>