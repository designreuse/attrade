import Vue from "vue";
import Vuex from "vuex";
import messagesApi from 'api/messages'
import pictureMediaApi from 'api/pictureMedia'
import categoryHierarchyApi from 'api/categoryHierarchy'

Vue.use(Vuex)

export default new Vuex.Store(
    {
        state: {
            messages: [],
            pictureMedia: [],
            categoryChildMap: null,
            categoryRootCategories: null,
            categoryProductCountMap: null,
        },
        getters: {
            sortedMessages: state => state.messages.sort((a, b) => -(a.id - b.id))
        },
        mutations: {
            addMessageMutation(state, message){
                state.messages = [
                    ...state.messages,
                    message
                ]
            },
            updateMessageMutation(state, message){
                const updateIndex = state.messages.findIndex(item => item.id == message.id)
                state.messages = [
                    ...state.messages.slice(0, updateIndex),
                    message,
                    ...state.messages.slice(updateIndex + 1)
                ]
            },
            removeMessageMutation(state, message){
                const deleteIndex = state.messages.findIndex(item => item.id == message.id)
                if (deleteIndex > -1) {
                    state.messages = [
                        ...state.messages.slice(0, deleteIndex),
                        ...state.messages.slice(deleteIndex + 1)
                    ]
                }
            },
            getMessageMutation(state, data){
                state.messages = data
            },
            getPictureMediaMutation(state, data){
                state.pictureMedia = data
            },
            getCategoryChildMapMutation(state, data){
                state.categoryChildMap = data
            },
            getCategoryRootCategoriesMutation(state, data){
                state.categoryRootCategories = data
            },

        },
        actions: {
            async addMessageAction({commit, state}, message){
                const result = await messagesApi.add(message)
                const data = await result.json()
                const index = state.messages.findIndex(item => item.id === data.id)
                if (index > -1) {
                    commit('updateMessageMutation', data)
                } else {
                    commit('addMessageMutation', data)
                }
            },
            async updateMessageAction({commit}, message){
                const result = await messagesApi.update(message)
                const data = await result.json()
                commit('updateMessageMutation', data)
            },
            async removeMessageAction({commit}, message){
                const result = await messagesApi.remove(message.id)
                    if (result.ok) {
                        commit('removeMessageMutation', message)
                    }
            },
            async getMessageAction({commit}){
                const result = await messagesApi.get()
                const data = await result.data
                if (result.ok) {
                    commit('getMessageMutation', data)
                }
            },
            async getPictureMediaAction({commit}){
                const result = await pictureMediaApi.get()
                const data = await result.data
                if (result.ok) {
                    commit('getPictureMediaMutation', data)
                }
            },
            async getCategoryChildMapAction({commit}){
                const result = await categoryHierarchyApi.getChildMap()
                const data = await result.data
                if (result.ok) {
                    commit('getCategoryChildMapMutation', data)
                }
            },
            async getCategoryRootCategoriesAction({commit}){
                const result = await categoryHierarchyApi.getRootCategories()
                const data = await result.data
                if (result.ok) {
                    commit('getCategoryRootCategoriesMutation', data)
                }
            },

        }
    }
)