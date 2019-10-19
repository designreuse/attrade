<template>
    <v-app>
        <v-app-bar>
            <v-toolbar-title>Sarafan</v-toolbar-title>
            <v-spacer></v-spacer>
            </v-toolbar>
            <v-content>
                <v-container>
                    <messages-list :messages="messages"/>
                </v-container>
            </v-content>
        </v-app-bar>
    </v-app>
</template>

<script>
    import {mdiExitToApp} from '@mdi/js'
    import MessagesList from 'components/messages/MessageList.vue'
    import {addHandler} from 'util/ws'
    import {getIndex} from 'util/collections'
    export default {
        components: {
            MessagesList
        },
        data() {
            logout: mdiExitToApp
            return {
                messages: messagesData
            }
        },
        created() {
            addHandler(data => {
                let index = getIndex(this.messages, data.id)
                if (index > -1)
            {
                this.messages.splice(index, 1, data)
            }
        else
            {
                this.messages.push(data)
            }
        })
        }
    }
</script>

<style>
</style>