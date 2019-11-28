import Vue from "vue";
const messages = Vue.resource('/pictureMedia/{id}')

export default {
    get: () => messages.get()
}