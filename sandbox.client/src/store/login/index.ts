import {getStoreAccessors} from 'vuex-typescript';
import {RootState} from "../RootState";
import {UserCan} from "@/model/UserCan";
import {LoginStatus} from "@/components/LoginStatus";
import {PersonDisplay} from "@/model/PersonDisplay";
import {ActionContext} from "vuex";
import {loginService} from "./service";

export interface LoginState {
  username: string;
  password: string;
  error: string | null;

  status: LoginStatus;
  canList: UserCan[];
  display: PersonDisplay | null;
}

const {commit, read, dispatch} = getStoreAccessors<LoginState, RootState>('login');
type LoginContext = ActionContext<LoginState, RootState>;

const getters = {
  isLoading(state: LoginState): boolean {
    return state.status == LoginStatus.LOADING;
  },
  isLogin(state: LoginState): boolean {
    return state.status == LoginStatus.LOGIN;
  },
  getDisplay(state: LoginState): PersonDisplay | null {
    return state.display;
  },
  getUsername(state: LoginState): string {
    return state.username;
  },
  getPassword(state: LoginState): string {
    return state.password;
  },
  getError(state: LoginState): string | null {
    return state.error;
  },
  viewUsers(state: LoginState): boolean {
    if (!state.display) return false;
    return state.display.cans.indexOf(UserCan.VIEW_USERS) > -1;
  },
  viewAbout(state: LoginState): boolean {
    if (!state.display) return false;
    return state.display.cans.indexOf(UserCan.VIEW_ABOUT) > -1;
  },
};

export const readIsLoading = read(getters.isLoading);
export const readIsLogin = read(getters.isLogin);
export const readDisplay = read(getters.getDisplay);
export const readUsername = read(getters.getUsername);
export const readPassword = read(getters.getPassword);
export const readError = read(getters.getError);
export const readViewAbout = read(getters.viewAbout);
export const readViewUsers = read(getters.viewUsers);

const mutations = {
  setUsername(state: LoginState, username: string) {
    state.username = username;
  },
  setPassword(state: LoginState, password: string) {
    state.password = password;
  },
  setError(state: LoginState, error: string | null) {
    state.error = error;
  },
  setStatus(state: LoginState, status: LoginStatus) {
    state.status = status;
  },
  setDisplay(state: LoginState, display: PersonDisplay | null) {
    state.display = display;
  },
};

export const commitUsername = commit(mutations.setUsername);
export const commitPassword = commit(mutations.setPassword);

const actions = {
  async reset(context: LoginContext) {
    commit(mutations.setUsername)(context, '');
    commit(mutations.setPassword)(context, '');
    commit(mutations.setError)(context, null);
    commit(mutations.setDisplay)(context, null);
    commit(mutations.setStatus)(context, LoginStatus.LOADING);

    try {
      const display: PersonDisplay = await loginService.loadPersonDisplay();
      commit(mutations.setDisplay)(context, display);
      commit(mutations.setStatus)(context, LoginStatus.AUTH_OK);
    } catch (e) {
      commit(mutations.setStatus)(context, LoginStatus.LOGIN);
    }
  },

  async login(context: LoginContext) {
    const username: string = read(getters.getUsername)(context);
    const password: string = read(getters.getPassword)(context);
    try {
      const token = await loginService.login(username, password);
      localStorage.setItem("token", token);
      await dispatch(actions.reset)(context);
    } catch (e) {
      console.log("ewq143 e = ", e);
      commit(mutations.setError)(context, e);
    }
  },

  async exit(context: LoginContext) {
    await loginService.exit();
    localStorage.setItem("token", '');
    commit(mutations.setError)(context, null);
    commit(mutations.setDisplay)(context, null);
    commit(mutations.setStatus)(context, LoginStatus.LOGIN);
  },
};

export const dispatchReset = dispatch(actions.reset);
export const dispatchLogin = dispatch(actions.login);
export const dispatchExit = dispatch(actions.exit);

export const login = {
  namespaced: true,

  state: {
    username: '',
    password: '',
    authError: null,

    status: LoginStatus.LOADING,
    canList: [],
    display: null,
  },

  getters: getters,
  mutations: mutations,
  actions: actions,
};
