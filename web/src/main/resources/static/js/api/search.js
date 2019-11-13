import Vue from "vue";
const products = Vue.resource('/search/product/{text}')
const categories = Vue.resource('/search/category/{text}')

export default {
    getProducts: (text, page, size) => Vue.http.get('/search/product', {params:{text, page, size}}),
    getCategories: (text, page, size) => Vue.http.get('/search/category', {params:{text, page, size}})
}