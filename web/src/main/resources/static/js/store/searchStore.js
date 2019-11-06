import Vue from "vue";
import Vuex from "vuex";
import searchApi from 'api/search'

Vue.use(Vuex)

export default new Vuex.Store(
    {
        state: {
            categories: [],
            products: []
        },
        mutations: {
            getCategoriesMutation(state, data){
                state.categories = data
            },
            getProductsMutation(state, data){
                state.products = data
            },
            removeCategoriesMutation(state){
                state.categories = []
            },
            removeProductsMutation(state){
                state.products = []
            }
        },
        actions: {
            async getCategoriesAction({commit}, text){
                const result = await searchApi.getCategories(text)
                const data = await result.data
                if (result.ok) {
                    commit('getCategoriesMutation', data)
                }
            },
            async getProductsAction({commit}, text){
                const result = await searchApi.getProducts(text)
                const data = await result.data
                if (result.ok) {
                    commit('getProductsMutation', data)
                }
            },
            async removeCategoriesAction({commit}){
                commit('removeCategoriesMutation')

            },
            async removeProductsAction({commit}){
                commit('removeProductsMutation')
            }
        }
    }
)