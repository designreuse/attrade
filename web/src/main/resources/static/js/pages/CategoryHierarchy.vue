<template>
    <div v-on-clickaway="away" class="container-fluid">
        <div class="row">
            <div class="col px-0">
                <nav>
                    <div class="nav nav-pills d-flex flex-row flex-wrap justify-content-center align-items-center py-1 px-0 border-top border-bottom border-muted bg-light"
                         id="nav-tab" role="tablist">
                        <button v-for="(item, index) in categoryRootCategories"
                                class="nav-link btn border border-white border-2 rounded-0 px-0 py-0 shadow-none"
                                :href="hrefTab(index)" data-toggle="tab" role="tab" aria-controls="tabId(index)"
                                :id="buttonId(index)"
                                aria-selected="false"
                                @click="f_levelId(index)">
                            <div class="container-fluid px-1 border border-secondary">
                                <div class="d-flex justify-content-center" style="width: 130px; min-height: 40px;">
                                    <small>{{item.name}}</small>
                                </div>
                            </div>
                        </button>
                    </div>
                </nav>
                <div class="tab-content" id="nav-tabContent">
                    <div v-for="(item, index) in categoryRootCategories" class="tab-pane fade"
                         :id="tabId(index)" role="tabpanel" aria-labelledby="...">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-4 col-sm-3 border-right border-muted px-0">
                                    <div class="nav nav-pills flex-column " id="v-pills-tab"
                                         role="tablist" aria-orientation="vertical">
                                        <div v-for="(item1, index1) in childMap(item)"
                                                class="nav-link btn btn-block border-bottom border-muted rounded-0 text-left shadow-none my-0 pl-2 pr-1"
                                                :href="hrefTab1(index1)" data-toggle="tab" role="tab"
                                                aria-controls="tabId1(index1)"
                                                aria-selected="false">
                                            <small>{{item1.name}}</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-8 col-sm-9 px-0">
                                    <div class="tab-content">
                                        <div v-for="(item1, index1) in childMap(item)" class="tab-pane fade"
                                             :id="tabId1(index1)" role="tabpanel" aria-labelledby="...">
                                            <div class="container px-0">
                                                <nav>
                                                    <div class="nav nav-pills d-flex flex-row flex-wrap justify-content-left align-self-stretch">
                                                        <div class="row no-gutters">
                                                            <a v-for="(item2, index2) in childMap(item1)"
                                                                 class="btn col-6 col-md-3 border-right border-bottom border-1 border-muted btn rounded-0 shadow-none text-left py-1 px-1 level2"
                                                                 role="button" :href="item2.path">
                                                                <div class="row no-gutters">
                                                                    <picture-upload :pathPic="picture(item2)"
                                                                                    :marker="marker" :alt="alt(item2)"
                                                                                    :pictureClass="pictureClass"
                                                                                    :pictureStyle="pictureStyle"/>
                                                                </div>
                                                                <div class="row no-gutters my-2 pb-4 px-2">
                                                                    <small>{{item2.name}}</small>
                                                                </div>
                                                                <div class="row no-gutters text-muted pb-2 px-2" style="position: absolute; bottom: 0; ">
                                                                    <small>товаров:&nbsp;</small><span class="badge badge-white">{{item2.productCount}}</span>
                                                                </div>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </nav>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import {mixin as clickaway} from 'vue-clickaway';
    import PictureUpload from 'components/picture/PictureUpload.vue'
    export default{
        components: {
            PictureUpload,
        },
        data() {
            return {
                levelId: undefined,
                marker: "-small",
                pictureClass: "img-fluid",
                pictureStyle: "max-width: 100%; height: auto;"
            }
        },
        mixins: [clickaway],
        computed: {
            ...mapState(['categoryChildMap', 'categoryRootCategories']),
        },
        methods: {
            hrefTab: function (index) {
                return "#tab-" + index
            },
            tabId: function (index) {
                return "tab-" + index
            },
            buttonTab: function (index) {
                return "#button-" + index
            },
            buttonId: function (index) {
                return "button-" + index
            },
            hrefTab1: function (index) {
                return "#tab1-" + index
            },
            tabId1: function (index) {
                return "tab1-" + index
            },
            childMap: function (category) {
                let key = category.id
                return this.categoryChildMap[key]
            },
            f_levelId: function (index) {
                this.levelId = index
            },
            away: function () {
                $(this.hrefTab(this.levelId)).removeClass('active')
                $(this.buttonTab(this.levelId)).removeClass('active')
            },
            picture: function (category) {
                return category.picture
            },
            alt: function (category) {
                return category.name
            },
        },
    }

</script>

<style scoped>
    .btn {
        background-color: white;
    }

    .btn:hover, .btn:active:focus {
        color: black;
        background-color: rgb(232, 240, 254);
    }

    @media screen and (min-width: 577px) {
        .level2 {
            min-height: 100px;
        }
    }

    @media screen and (max-width: 576px) {
        .level2 {
            min-height: 100px;
        }
    }
</style>