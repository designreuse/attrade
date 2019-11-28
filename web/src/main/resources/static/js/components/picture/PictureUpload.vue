<template>
    <picture>
        <source v-for="(item, index) in pictureMedia" :srcset="srcsetItem(item.path)" :media="item.media">

        <source :srcset="srcset">
        <img :srcset="srcset" :alt="alt" :class="pictureClass" :style="pictureStyle"/>
    </picture>
</template>

<script>
    import { mapState } from 'vuex'
    export default{
        props: ['pathPic', 'alt', 'pictureClass', 'pictureStyle'],
        data(){
            return {
                root: "/upload/picture",
                slash: "/",
            }
        },
        methods:{
            srcsetItem:  function (path){
                return this.root + path + this.slash + this.pathPic
            }
        },
        computed: {
            ...mapState(['pictureMedia']),
            srcset(){
                return this.root + this.slash + this.pathPic
            },
        }
    }
</script>

<style>

</style>