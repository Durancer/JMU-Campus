import hyRequest from "..";

export function register(data: any) {
    return hyRequest.post({
        url: '/user/register',
        data: data
    })
}