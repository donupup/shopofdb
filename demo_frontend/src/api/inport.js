import request from "@/utils/request"

export function getInportList(){
    return request({
        url: '/inport/list',
        method: 'get',
    })
}

export function addInport(InportAddDTO) {
    return request({
        url: '/inport/add',
        method: 'post',
        data: InportAddDTO
    })
}

export function deleteInport(Inport) {
    return request({
        url: '/inport/delete',
        method: 'post',
        data: Inport
    })
}

export function editInport(InportEditDTO) {
    return request({
        url: '/inport/edit',
        method: 'post',
        data: InportEditDTO
    })
}

export function getConditionList(InportConditionDTO) {
    return request({
        url: '/inport/condition',
        method: 'post',
        data: InportConditionDTO
    })
}