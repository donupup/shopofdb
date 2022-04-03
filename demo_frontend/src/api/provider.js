import request from "@/utils/request"

export function getProviderList(){
    return request({
        url: '/provider/list',
        method: 'get',
    })
}

export function addProvider(ProviderAddDTO) {
    return request({
        url: '/provider/add',
        method: 'post',
        data: ProviderAddDTO
    })
}

export function deleteProvider(Provider) {
    return request({
        url: '/provider/delete',
        method: 'post',
        data: Provider
    })
}

export function editProvider(ProviderEditDTO) {
    return request({
        url: '/provider/edit',
        method: 'post',
        data: ProviderEditDTO
    })
}

export function getConditionList(ProviderConditionDTO) {
    return request({
        url: '/provider/condition',
        method: 'post',
        data: ProviderConditionDTO
    })
}
