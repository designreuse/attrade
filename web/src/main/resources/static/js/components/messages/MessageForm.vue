<template>
    <div>
        <input type="text" placeholder="Write something" v-model="text"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
    import messagesApi from 'api/messages'
    export default {
        props: ['messages', 'messageAttr'],
        data() {
            return {
                text: '',
                id: ''
            }
        },
        watch: {
            messageAttr(newVal, oldVal) {
                this.text = newVal.text
                this.id = newVal.id
            }
        },
        methods: {
            save() {
                const message = {
                    id: this.id,
                    text: this.text
                }
                if (this.id) {
                    messagesApi.update(message).then(result =>
                        result.json().then(data => {
                            const index = this.messages.findIndex(item => item.id === data.id)
                            this.messages.splice(index, 1, data)
                        })
                    )
                } else {
                    messagesApi.add(message).then(result =>
                        result.json().then(data => {
                            const index = this.messages.findIndex(item => item.id === data.id)
                            if (index > -1) {
                                this.messages.splice(index, 1, data)
                            } else {
                                this.messages.push(data)
                            }
                        })
                    )
                }
                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style>
</style>