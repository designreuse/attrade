<template>
    <div>
        <a class="dropdown-item" :href="link">
            <div class="row my-1 align-items-center">
                &emsp;
                <i class="fas fa-angle-right text-muted"></i>
                &emsp;
                {{ category.name }}&emsp;&emsp;<small class="text-muted">товаров:&nbsp;<span class="badge badge-white">{{category.productCount}}</span></small>
            </div>
        </a>
    </div>
</template>

<script>
    export default {
        computed: {
            link(){
                return this.category.path;
            }
        },
        props: ['category'],
        methods: {}
    }
</script>

<style>
</style>