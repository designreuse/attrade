<template>
    <div>
        <i>({{ message.id }})</i> {{ message.text }}
        <span style="position: absolute; right: 0">
            <input type="button" value="Edit" @click="edit"/>
            <input type="button" value="X" @click="del"/>
        </span>
    </div>
</template>

<script>
    import { mapActions } from 'vuex'
    export default {
        props: ['message', 'editMessage'],
        methods: {
            ...mapActions(['removeMessageAction']),
            edit() {
                this.editMessage(this.message)
            },
            del() {
                this.removeMessageAction(this.message)
            }
        }
    }
</script>

<style>
</style>