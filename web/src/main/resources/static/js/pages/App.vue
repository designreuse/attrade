<template>
    <div>
        <messages-list :messages="messages"/>
    </div>
</template>


<script>
    import MessagesList from 'components/messages/MessageList.vue'
    import { addHandler } from 'util/ws'
    import { getIndex } from 'util/collections'
    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: messages
            }
        },
        created() {
            addHandler(data => {
                let index = getIndex(this.messages, data.id)
                if (index > -1) {
                this.messages.splice(index, 1, data)
            } else {
                this.messages.push(data)
            }
        })
        }
    }
</script>


<style>

</style>