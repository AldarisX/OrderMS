function formatUnixTime(unixDate) {
    const unixTimestamp = new Date(unixDate * 1000);
    const Y = unixTimestamp.getFullYear();
    const M = ((unixTimestamp.getMonth() + 1) > 10 ? (unixTimestamp.getMonth() + 1) : '0' + (unixTimestamp.getMonth() + 1));
    const D = (unixTimestamp.getDate() > 10 ? unixTimestamp.getDate() : '0' + unixTimestamp.getDate());
    const toDay = Y + '/' + M + '/' + D;
    let hour = unixTimestamp.getHours();
    hour = hour < 10 ? ('0' + hour) : hour;
    let minute = unixTimestamp.getMinutes();
    minute = minute < 10 ? ('0' + minute) : minute;
    let second = unixTimestamp.getSeconds();
    second = second < 10 ? ('0' + second) : second;
    let time = hour + ":" + minute + ":" + second;
    return toDay + " " + time;
}

function toUnixTime(time, offsetDay) {
    let date = new Date(time);
    if (offsetDay !== undefined) {
        date.setDate(date.getDate() + offsetDay);
    }
    return date.getTime() / 1000;
}

Date.prototype.format = function (format) {
    const o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};