import hyRequest from "..";

export function register(data: any) {
    return hyRequest.post({
        url: `/user/register?nickname=${data.nickname}&username=${data.username}&password=${data.password}&email=${data.email}&idencode=${data.idencode}`,
        // data: data
    })
}

export function requestCode(data: any) {
    return hyRequest.post({
        url: `/user/send/code?email=${data.email}`,
        // data: { data: data }
    })
}