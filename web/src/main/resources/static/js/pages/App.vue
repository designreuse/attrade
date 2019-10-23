<template>
    <div>
        <messages-list :messages="messages"/>
    </div>
</template>

<script>
    import MessagesList from 'components/messages/MessageList.vue'
    import {addHandler} from 'util/ws'
    import messagesApi from 'api/messages'

    export default {
        components: {
            MessagesList
        },
        data() {
            return {
                messages: null
            }
        },
        created() {
            messagesApi.get().then(result => {
                if (result.ok) {
                    this.messages = result.data
                }
            })
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    const index = this.messages.findIndex(item => item.id === data.body.id)
                    switch (data.eventType) {
                        case 'CREATE':
                        case 'UPDATE':
                            if (index > -1) {
                                this.messages.splice(index, 1, data.body)
                            } else {
                                this.messages.push(data.body)
                            }
                            break;
                        case 'REMOVE':
                            this.messages.splice(index, 1)
                            break
                        default:
                            console.error('Looks like EventType is unknown "${data.eventType}".')
                    }
                } else {
                    console.error('Looks like ObjectType is unknown "${data.objectType}".')
                }

            })
        }
    }
</script>

<style>
</style>