import Vue from "vue";
const products = Vue.resource('/search/product/{text}')
const categories = Vue.resource('/search/category/{text}')

export default {
    getProducts: () => products.get({text}),
    getCategories: () => categories.get({text})
}