import Vue from "vue";
const products = Vue.resource('/search/product/{text}')
const categories = Vue.resource('/search/category/{text}')

export default {
    getProducts: text => products.get({text}),
    getCategories: text => categories.get({text})
}