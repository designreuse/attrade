<template>
    <div>
        <messages-list/>
    </div>
</template>

<script>
    import { mapState, mapMutations } from 'vuex'
    import MessagesList from 'components/messages/MessageList.vue'
    import {addHandler} from 'util/ws'

    export default {
        components: {
            MessagesList
        },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
        created() {
            addHandler(data => {
                if (data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE':
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE':
                            this.updateMessageMutation(data.body)
                            break;
                        case 'REMOVE':
                            this.removeMessageMutation(data.body)
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