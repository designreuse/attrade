<template>
    <div>
        <a class="dropdown-item" :href="link">
            <div class="row my-1">
                <div class="col-1">
                    <i class="far fa-circle"></i>
                </div>
                <div class="col-11">
                    {{ category.name }}
                </div>
            </div>
        </a>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                url: infoUrl
            }
        },
        computed: {
            link(){
                return this.url + this.category.path;
            }
        },
        props: ['category'],
        methods: {}
    }
</script>

<style>
</style>