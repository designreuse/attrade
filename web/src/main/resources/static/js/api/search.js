import Vue from "vue";

export default {
    getProducts: (text, page, size) => Vue.http.get('/search/product', {params:{text, page, size}}),
    getCategories: (text, page, size) => Vue.http.get('/search/category', {params:{text, page, size}})
}