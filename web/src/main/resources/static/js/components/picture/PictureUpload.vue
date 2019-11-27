<template>
    <picture>
        <source :srcset="srcset540" :media="media540">

        <source :srcset="srcset">
        <img :srcset="srcset" :alt="alt" :class="pictureClass" :style="pictureStyle"/>
    </picture>
</template>

<script>
    export default{
        props: ['pathPic', 'alt', 'pictureClass', 'pictureStyle'],
        data(){
            return {
                root: "/upload/picture",
                path540: "/540",
                media540: "(max-width: 540px)",
                slash: "/"
            }
        },
        computed: {
            srcset(){
                return this.root + this.slash + this.pathPic
            },
            srcset540(){
                return
                this.root
//                + this.path540
                + this.slash
                + this.pathPic
            },
        }
    }
</script>

<style>

</style>