import request from "@/utils/request"

export function getGoodSaleList(){
    return request({
        url: '/goodsale/list',
        method: 'get',
    })
}

export function addSale(SaleAddDTO) {
    return request({
        url: '/goodsale/add',
        method: 'post',
        data: SaleAddDTO
    })
}

export function deleteSale(Sale) {
    return request({
        url: '/goodsale/delete',
        method: 'post',
        data: Sale
    })
}

export function editSale(SaleEditDTO) {
    return request({
        url: '/goodsale/edit',
        method: 'post',
        data: SaleEditDTO
    })
}

export function getConditionList(SaleConditionDTO) {
    return request({
        url: '/goodsale/condition',
        method: 'post',
        data: SaleConditionDTO
    })
}