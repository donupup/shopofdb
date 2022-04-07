import request from "@/utils/request"

export function sendMsg(dto){
    return request({
        url: '/sms/sendCode',
        method: 'post',
        data:dto
    })
}

export function changeSecretCheck(DTO) {
    return request({
        url: '/sms/changeSecret',
        method: 'post',
        data: DTO
    })
}