<template>
    <div>
        <input type="text" placeholder="Write something" v-model="text"/>
        <input type="button" value="Save" @click="save"/>
    </div>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        props: ['messageAttr'],
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
            ...mapActions(['addMessageAction','updateMessageAction']),
            save() {
                const message = {
                    id: this.id,
                    text: this.text
                }
                if (this.id) {
                    this.updateMessageAction(message)
                } else {
                    this.addMessageAction(message)
                }
                this.text = ''
                this.id = ''
            }
        }
    }
</script>

<style>
</style>