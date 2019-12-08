<template>
    <div>
        <button class="btn btn-white dropdown-item" :href="link">
            <div class="row my-1 align-items-center">
                &emsp;
                <i class="fas fa-angle-right text-muted"></i>
                &emsp;
                {{ category.name }}&emsp;&emsp;<small class="text-muted">товаров:&nbsp;<span class="badge badge-white">{{category.productCount}}</span></small>
            </div>
        </button>
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

<style scoped>
    .btn{
        background-color: white;
    }
    .btn:hover{
        background-color: #eaeaea;
    }
</style>