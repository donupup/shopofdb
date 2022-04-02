import request from "@/utils/request"

export function getVipList(){
    return request({
        url: '/vip/list',
        method: 'get',
    })
}

export function addVip(VipAddDTO) {
    return request({
        url: '/vip/add',
        method: 'post',
        data: VipAddDTO
    })
}

export function deleteVip(Vip) {
    return request({
        url: '/vip/delete',
        method: 'post',
        data: Vip
    })
}

export function editVip(VipEditDTO) {
    return request({
        url: '/vip/edit',
        method: 'post',
        data: VipEditDTO
    })
}