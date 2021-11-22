import request from "@/utils/request"

export function getGoodList(){
    return request({
        url: '/good/list',
        method: 'get',
    })
}

export function deleteGood(id){
    return request({
        url: '/good/delete',
        method: 'post',
        data:id
    })
}

export function editGood(EditGoodDTO) {
    return request({
        url: '/good/edit',
        method: 'post',
        data: EditGoodDTO
    })
}
