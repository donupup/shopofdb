import request from "@/utils/request"

export function getGoodList(){
    return request({
        url: '/good/list',
        method: 'get',
    })
}
