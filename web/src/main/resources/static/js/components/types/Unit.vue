<template>
    <b>
        {{text}}
    </b>
</template>

<script>
    export default {
        props: ['unit'],
        computed:{
            text(){
                switch (this.unit){
                    case "ITEM": return "шт.";
                    case "METER": return "м.";
                    case "LITER": return "л.";
                    case "KG": return "кг."
                    default: return "шт."
                }
            }
        }
    }
</script>

<style>

</style>