<template>
    <div>
        <messages-list :messages="messages"/>
    </div>
</template>


<script>
    import MessagesList from 'components/messages/MessageList.vue'
    export default{
        components: {
            MessagesList
        },
        data(){
            return {
                messages: messages
            }
        }
    }
</script>


<style>

</style>