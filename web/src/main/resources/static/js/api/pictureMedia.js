import Vue from "vue";
const pictureMedia = Vue.resource('/pictureMedia/{id}')

export default {
    get: () => pictureMedia.get()
}