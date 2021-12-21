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

export function addGood(GoodDTO) {
    return request({
        url: '/good/add',
        method: 'post',
        data: GoodDTO
    })
}

export function sellGood(SaleDto){
    return request({
        url: '/good/sale',
        method: 'post',
        data:SaleDto
    })
}

