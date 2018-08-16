import {RootState} from "@/store/RootState";
import {UserCan} from "@/model/UserCan";
import {LoginStatus} from "@/components/LoginStatus";
import {PersonDisplay} from "@/model/PersonDisplay";
import {loginService} from "@/store/login/loginService";
import {BareActionContext, getStoreBuilder} from "vuex-typex";

export interface LoginState {
  username: string;
  password: string;
  loginError: string | null;

  status: LoginStatus;
  canList: UserCan[];
  display: PersonDisplay | null;
}

const initialLoginState: LoginState = {
  username: '',
  password: '',
  loginError: null,

  status: LoginStatus.LOADING,
  canList: [],
  display: null,
};

const b = getStoreBuilder<RootState>().module("login", initialLoginState);

//
//   GETTERS
//

function isLoading(state: LoginState): boolean {
  return state.status == LoginStatus.LOADING;
}

function isLogin(state: LoginState): boolean {
  return state.status == LoginStatus.LOGIN;
}

function getDisplay(state: LoginState): PersonDisplay | null {
  return state.display;
}

function getUsername(state: LoginState): string {
  return state.username;
}

function getPassword(state: LoginState): string {
  return state.password;
}

function getLoginError(state: LoginState): string | null {
  return state.loginError;
}

function isViewUsers(state: LoginState): boolean {
  return hasCan(state, UserCan.VIEW_USERS);
}

function isViewAbout(state: LoginState): boolean {
  return hasCan(state, UserCan.VIEW_ABOUT);
}

const readIsLoading = b.read(isLoading);
const readIsLogin = b.read(isLogin);
const readDisplay = b.read(getDisplay);
const readUsername = b.read(getUsername);
const readPassword = b.read(getPassword);
const readLoginError = b.read(getLoginError);

const readCanViewUsers = b.read(isViewUsers);
const readCanViewAbout = b.read(isViewAbout);

function hasCan(state: LoginState, can: UserCan) {
  if (!state.display) return false;
  return state.display.cans.indexOf(can) > -1;
}

//
//  MUTATIONS
//

function setUsername(state: LoginState, payload: { username: string }) {
  state.username = payload.username;
}

function setPassword(state: LoginState, payload: { password: string }) {
  state.password = payload.password;
}

function setLoginError(state: LoginState, payload: { loginError: string | null }) {
  state.loginError = payload.loginError;
}

function setStatus(state: LoginState, payload: { status: LoginStatus }) {
  state.status = payload.status;
}

function setDisplay(state: LoginState, payload: { display: PersonDisplay | null }) {
  state.display = payload.display;
}

const commitSetUsername = b.commit(setUsername);
const commitSetPassword = b.commit(setPassword);
const commitSetLoginError = b.commit(setLoginError);
const commitSetStatus = b.commit(setStatus);
const commitSetDisplay = b.commit(setDisplay);

//
// ACTIONS
//

type LoginContext = BareActionContext<LoginState, RootState>;

async function doReset(ignore: LoginContext) {
  commitSetUsername({username: ''});
  commitSetPassword({password: ''});
  commitSetLoginError({loginError: null});
  commitSetDisplay({display: null});
  commitSetStatus({status: LoginStatus.LOADING});

  try {
    const display: PersonDisplay = await loginService.loadPersonDisplay();
    commitSetDisplay({display: display});
    commitSetStatus({status: LoginStatus.AUTH_OK});
  } catch (e) {
    commitSetStatus({status: LoginStatus.LOGIN});
  }

}

async function doLogin(ignore: LoginContext) {
  let username: string = readUsername();
  let password: string = readPassword();
  try {
    const token = await loginService.login(username, password);
    localStorage.setItem("token", token);
    await dispatchReset();
  } catch (e) {
    commitSetLoginError({loginError: e});
    commitSetPassword({password: ''});
  }
}

async function doExit(ignore: LoginContext) {
  await loginService.exit();
  localStorage.setItem("token", '');
  commitSetLoginError({loginError: null});
  commitSetDisplay({display: null});
  commitSetStatus({status: LoginStatus.LOGIN});
}

const dispatchReset = b.dispatch(doReset);
const dispatchLogin = b.dispatch(doLogin);
const dispatchExit = b.dispatch(doExit);

//
// STATE
//

const stateGetter: (() => LoginState) = b.state();

//
// MAIN EXPORT
//

const login = {

  get state(): LoginState {
    return stateGetter();
  },

  isLoading: readIsLoading,
  isLogin: readIsLogin,
  getDisplay: readDisplay,
  getUsername: readUsername,
  getPassword: readPassword,
  getLoginError: readLoginError,
  isViewAbout: readCanViewAbout,
  isViewUsers: readCanViewUsers,

  commitSetUsername: commitSetUsername,
  commitSetPassword: commitSetPassword,

  dispatchReset: dispatchReset,
  dispatchLogin: dispatchLogin,
  dispatchExit: dispatchExit,
};

// noinspection JSUnusedGlobalSymbols
export default login;
