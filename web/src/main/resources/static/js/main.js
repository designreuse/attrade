import Vue from 'vue'
import VueResource from 'vue-resource'
import Vuetify from 'vuetify'
import App from 'pages/App.vue'
import { connect } from './util/ws'
import 'vuetify/dist/vuetify.min.css'

connect()

Vue.use(VueResource)
Vue.use(Vuetify,{ iconfont: 'mdiSvg' })

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    render: a => a(App)
})