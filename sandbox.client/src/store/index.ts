import Vue from 'vue';
import Vuex, {Store} from 'vuex';
import {RootState} from "@/store/RootState";
import {getStoreBuilder} from "vuex-typex";

Vue.use(Vuex);

const store: Store<RootState> = getStoreBuilder<RootState>().vuexStore();

// noinspection JSUnusedGlobalSymbols
export default store;
