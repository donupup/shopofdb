import request from "@/utils/request"

export function sendMsg(dto){
    return request({
        url: '/sms/sendCode',
        method: 'post',
        data:dto
    })
}