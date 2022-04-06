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
