import Vue from "vue";
import "api/resource";
import App from "pages/App.vue";
import Search from "pages/Search.vue";
import CategoryHierarchy from "pages/CategoryHierarchy.vue";
import "@babel/polyfill";
import store from "store/store";
import {connect} from "./util/ws";
import "bootstrap";

connect()

new Vue({
    el: '#app',
    store,
    render: a => a(App),
    created() {
        this.$store.dispatch('getMessageAction')
    },
})
new Vue({
    el: '#search',
    store,
    render: a => a(Search),
    created() {
        this.$store.dispatch('getPictureMediaAction') //TODO - out of # - page scope
    },
})
new Vue({
    el: '#categoryHierarchy',
    store,
    render: a => a(CategoryHierarchy),
    created() {
        this.$store.dispatch('getCategoryChildMapAction') //TODO - out of # - page scope
        this.$store.dispatch('getCategoryRootCategoriesAction') //TODO - out of # - page scope
    },
})