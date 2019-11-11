import Vue from 'vue'
import 'api/resource'
import App from 'pages/App.vue'
import Search from 'pages/Search.vue'
import '@babel/polyfill'
import store from 'store/store'
import { connect } from './util/ws'
import 'bootstrap'

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
    render: a => a(Search),
})