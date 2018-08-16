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

const isLoadingGetter = b.read(state => state.status == LoginStatus.LOADING);
const isLoginGetter = b.read(state => state.status == LoginStatus.LOGIN);
const displayGetter = b.read(state => state.display);
const usernameGetter = b.read(state => state.username);
const passwordGetter = b.read(state => state.password);
const loginErrorGetter = b.read(state => state.loginError);

const isViewUsersGetter = b.read(state => hasCan(state, UserCan.VIEW_USERS));
const isViewAboutGetter = b.read(state => hasCan(state, UserCan.VIEW_ABOUT));

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

//
// ACTIONS
//

type LoginContext = BareActionContext<LoginState, RootState>;

async function doReset(ignore: LoginContext) {
  b.commit(setUsername)({username: ''});
  b.commit(setPassword)({password: ''});
  b.commit(setLoginError)({loginError: null});
  b.commit(setDisplay)({display: null});
  b.commit(setStatus)({status: LoginStatus.LOADING});

  try {
    const display: PersonDisplay = await loginService.loadPersonDisplay();
    b.commit(setDisplay)({display: display});
    b.commit(setStatus)({status: LoginStatus.AUTH_OK});
  } catch (e) {
    b.commit(setStatus)({status: LoginStatus.LOGIN});
  }

}

async function doLogin(ignore: LoginContext) {
  let username: string = b.read(usernameGetter)();
  let password: string = b.read(passwordGetter)();
  try {
    const token = await loginService.login(username, password);
    localStorage.setItem("token", token);
    await b.dispatch(doReset)();
  } catch (e) {
    b.commit(setLoginError)({loginError: e});
    b.commit(setPassword)({password: ''});
  }
}

async function doExit(ignore: LoginContext) {
  await loginService.exit();
  localStorage.setItem("token", '');
  b.commit(setLoginError)({loginError: null});
  b.commit(setDisplay)({display: null});
  b.commit(setStatus)({status: LoginStatus.LOGIN});
}

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

  isLoading: b.read(isLoadingGetter),
  isLogin: b.read(isLoginGetter),
  getDisplay: b.read(displayGetter),
  getUsername: b.read(usernameGetter),
  getPassword: b.read(passwordGetter),
  getLoginError: b.read(loginErrorGetter),
  isViewAbout: b.read(isViewAboutGetter),
  isViewUsers: b.read(isViewUsersGetter),

  commitUsername: b.commit(setUsername),
  commitPassword: b.commit(setPassword),

  dispatchReset: b.dispatch(doReset),
  dispatchLogin: b.dispatch(doLogin),
  dispatchExit: b.dispatch(doExit),
};

// noinspection JSUnusedGlobalSymbols
export default login;
