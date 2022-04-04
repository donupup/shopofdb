import request from "@/utils/request"


export function getMonthSta(month){
    return request({
        url: '/sta/month',
        method: 'post',
        data:month
    })
}

export function getGoodNumSta(good){
    return request({
        url: '/sta/goodnum',
        method: 'post',
        data: good
    })
}