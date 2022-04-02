import request from "@/utils/request"

export function getCategoryList(){
    return request({
        url: '/category/list',
        method: 'get',
    })
}