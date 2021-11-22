import {  login, logout } from "@/api/auth";
import { getToken, setToken, removeToken, getRole, setRole, removeRole } from "@/utils/auth";

const state = {
  token: getToken(), // token
  user: "", // 用户对象
  role: getRole() //role
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
  }
};

const actions = {
  // 用户登录
  login({ commit }, userInfo) {
    console.log(userInfo);
    const { name, pass, rememberMe } = userInfo;
    return new Promise((resolve, reject) => {
      login({ username: name.trim(), password: pass, rememberMe: rememberMe })
        .then((response) => {
          const { data } = response;
          //console.log(response);
          commit("SET_TOKEN_STATE", data.token);
          setToken(data.token);
          commit("SET_ROLE_STATE",data.role);
          //console.log(this.state);
          setRole(data.role)
          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },

  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token)
          .then((response) => {
            console.log(response);
            commit("SET_TOKEN_STATE", "");
            commit("SET_USER_STATE", "");
            commit("SET_ROLE_STATE", "");
            removeToken();
            removeRole();
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
