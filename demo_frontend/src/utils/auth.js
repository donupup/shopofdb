//此模块是对写入浏览器的token
import Cookies from 'js-cookie'

const uToken = 'u_token'
const darkMode = 'dark_mode';
const uRole = 'u_role'

// 获取Token
export function getToken() {
    return Cookies.get(uToken);
}

// 设置Token，1天,与后端同步
export function setToken(token) {
    return Cookies.set(uToken, token, {expires: 1})
}

// 删除Token
export function removeToken() {
    return Cookies.remove(uToken)
}

export function removeAll() {
    return Cookies.Cookies.removeAll()
}

export function setDarkMode(mode) {
    return Cookies.set(darkMode, mode, {expires: 365})
}

export function getDarkMode() {
    return !(undefined === Cookies.get(darkMode) || 'false' === Cookies.get(darkMode));
}

// 获取role
export function getRole() {
    return Cookies.get(uRole);
}

// 设置Token，1天,与后端同步
export function setRole(role) {
    return Cookies.set(uRole, role, {expires: 1})
}

// 删除Token
export function removeRole() {
    return Cookies.remove(uRole)
}
