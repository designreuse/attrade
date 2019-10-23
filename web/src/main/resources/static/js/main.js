import Vue from 'vue'
import 'api/resource'
import App from 'pages/App.vue'
import '@babel/polyfill'
import store from 'store/store'
import { connect } from './util/ws'

connect()

new Vue({
    el: '#app',
    store,
    render: a => a(App),
    created() {
        this.$store.dispatch('getMessageAction')
    },
})