import request from "@/utils/request"

export function getCartList(vid){
    return request({
        url: '/cart/list',
        method: 'post',
        data:vid
    })
}

export function addCart(addDto)
{
    return request({
        url: '/cart/add',
        method: 'post',
        data:addDto
    })
}

export function deleteCartOne(id)
{
    return request({
        url: '/cart/deleteOne',
        method: 'post',
        data: id
    })
}

export function editCartOne(dto)
{
    return request({
        url: '/cart/editOne',
        method: 'post',
        data: dto
    })
}

export function deleteCartMulti(dto)
{
    return request({
        url: '/cart/deleteMulti',
        method: 'post',
        data: dto
    })
}

export function checkCart(dto)
{
    return request({
        url: '/cart/check',
        method: 'post',
        data: dto
    })
}