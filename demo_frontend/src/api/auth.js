import request from "@/utils/request"

export function login(data) {
    return request({
        url: '/user/login',
        method: 'post',
        data
    })
}
export function logout() {
    return request({
        url: '/user/logout'
    })
}

export function userRegister(userDTO) {
    return request({
        url: '/user/register',
        method: 'post',
        data: userDTO
    })
}

export function userInfo() {
    return request({
        url: '/user/info',
        method: 'get'
    })
}

export function editInfo(EditDTO) {
    return request({
        url: '/user/edit',
        method: 'post',
        data: EditDTO
    })
}

export function deleteUser(EditDTO) {
    return request({
        url: '/user/delete',
        method: 'post',
        data: EditDTO
    })
}
