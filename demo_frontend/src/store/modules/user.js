import {
  login,
  logout
} from "@/api/auth";
import {
  getToken,
  setToken,
  removeToken,
  getRole,
  setRole,
  removeRole,
  getUserId,
  setUserId,
  removeUserId
} from "@/utils/auth";

const state = {
  token: getToken(), // token
  user: "", // 用户对象
  role: getRole(), //role,
  userId: getUserId()
};

const mutations = {
  SET_TOKEN_STATE: (state, token) => {
    state.token = token;
  },
  SET_USER_STATE: (state, user) => {
    state.user = user;
  },
  SET_ROLE_STATE: (state, role) => {
    state.role = role;
  },
  SET_USERID_STATE: (state, userId) => {
    state.userId = userId;
  }
};

const actions = {
  // 用户登录
  login({
    commit
  }, userInfo) {
    console.log(userInfo);
    const {
      name,
      pass,
      rememberMe
    } = userInfo;
    return new Promise((resolve, reject) => {
      login({
          username: name.trim(),
          password: pass,
          rememberMe: rememberMe
        })
        .then((response) => {
          const {
            data
          } = response;
          //console.log(response);
          commit("SET_TOKEN_STATE", data.token);
          setToken(data.token);
          commit("SET_ROLE_STATE", data.role);
          //console.log(this.state);
          setRole(data.role)
          //console.log(data.userId)
          commit("SET_USERID_STATE", data.userId);
          setUserId(data.userId)
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
  loginWithMsg({
    commit
  }, userInfo) {
    console.log(userInfo);
    commit("SET_TOKEN_STATE", userInfo.data.token);
    setToken(userInfo.data.token);
    commit("SET_ROLE_STATE", userInfo.data.role);
    //console.log(this.state);
    setRole(userInfo.data.role)
    //console.log(data.userId)
    commit("SET_USERID_STATE", userInfo.data.userId);
    setUserId(userInfo.data.userId);
  },

  logout({
    commit,
    state
  }) {
    return new Promise((resolve, reject) => {
      logout(state.token)
        .then((response) => {
          console.log(response);
          commit("SET_TOKEN_STATE", "");
          commit("SET_USER_STATE", "");
          commit("SET_ROLE_STATE", "");
          commit("SET_USERID_STATE", "");
          removeToken();
          removeRole();
          removeUserId();
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};