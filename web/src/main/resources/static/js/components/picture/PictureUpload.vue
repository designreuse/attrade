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
        props: ['pathPic', 'marker', 'alt', 'pictureClass', 'pictureStyle'],
        data(){
            return {
                root: "/upload/picture",
                slash: "/",
            }
        },
        methods:{
            srcsetItem:  function (path){
                return this.root + path + this.slash + this.markerPath
            }
        },
        computed: {
            ...mapState(['pictureMedia']),
            srcset(){
                return this.root + this.slash + this.markerPath
            },
            markerPath(){
                let split = this.pathPic.split(".")
                return split[0] + this.marker + "." + split[1]
            }
        }
    }
</script>

<style>

</style>