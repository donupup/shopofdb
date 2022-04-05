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

export function getGoodSta(){
    return request({
        url: '/sta/good',
        method: 'post',
    })
}

export function getNumOfVip(vip){
    return request({
        url: '/sta/numofvip',
        method: 'post',
        data: vip
    })
}

export function getVipSta(){
    return request({
        url: '/sta/vip',
        method: 'post',
    })
}

export function getDaySta(day){
    return request({
        url: '/sta/day',
        method: 'post',
        data: day
    })
}

export function getNumOfUser(user){
    return request({
        url: '/sta/numofuser',
        method: 'post',
        data: user
    })
}

export function getUserSta(){
    return request({
        url: '/sta/user',
        method: 'post',
    })
}