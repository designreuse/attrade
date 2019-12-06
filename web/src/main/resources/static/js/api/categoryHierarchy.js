import Vue from "vue";

export default {
    getChildMap: () => Vue.http.get('/category/hierarchy/child-map'),
    getRootCategories: () => Vue.http.get('/category/hierarchy/root-categories'),
}