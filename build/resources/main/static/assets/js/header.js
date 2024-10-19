let socket = new WebSocket("ws://"+location.host+"/ws/alert");
$(document).ready(function () {
    //소켓 연결
    connectWs();
    $.ajax({
        url: "/alert/List",
        method: "post",
    }).done(resp => {
        let $socketAlert = $('#socketAlert');
        $socketAlert.empty()
        $socketAlert.append("<h6 class='dropdown-header'>알림</h6>")
        $socketAlert.append(resp)
        let notice = document.querySelector("#span-notice-count");
        let noticelist = document.querySelectorAll("#noticelist a");
            notice.innerHTML = noticelist.length - 1 + "+";
    }).fail(err => {
        console.log(err)
    })
});

function connectWs() {
    // let ws = new SockJS("/ws/alert");
    let ws = socket;
    ws.onopen = function () {
        console.log('open');
    };
    ws.onmessage = function (event) {
        let $socketAlert = $('#socketAlert');
        //EchoHandler에서 설정한 메세지 넣어줌
        $socketAlert.append(event.data)
        let notice = document.querySelector("#span-notice-count");
        let noticelist = document.querySelectorAll("#noticelist a");
        notice.innerHTML = noticelist.length - 1 + "+";
        // $socketAlert.css('display', 'block');
        // ws.onclose = function () {
        //     console.log('close');
        // };
    }
    ws.onerror = function (error) {
        console.error('WebSocket Error:', error);
    };
    socket.onclose = function () {
        console.log('WebSocket connection closed');
    };
}

function accept(t_num, item, itemcount,tradesend,m_id) {
    let tNum = t_num
    let t_item = item;
    let t_itemcount = itemcount;
    const param = {"t_num": tNum, "t_item": t_item, "t_itemcount": t_itemcount}
    $.ajax({
        url: "/trade/accept",
        method: "post",
        data: param,
    }).done(resp => {
        console.log(resp)
        if (resp != null) {
            let selector=`#notification_${tNum}_${t_item}_${t_itemcount}`
            console.log($(selector))
            $(selector).remove()
            // $('#socketAlert').append("<h6 class='dropdown-header'>알림</h6>")
            // $('#socketAlert').append(resp)
            let notice = document.querySelector("#span-notice-count");
            let noticelist = document.querySelectorAll("#noticelist a");
            notice.innerHTML = noticelist.length - 1 + "+";
            let acceptMessage={"type":"accept","t_num":t_num,"tradesend":m_id,"m_id":tradesend}
            socket.send(JSON.stringify(acceptMessage))
        } else {
            console.log("실패")
        }
    }).fail(err => {
        console.log(err)
    })
}

function refuse(t_num, item, itemcount,tradesend,m_id) {
    console.log("거절 글번호: " + t_num);
    console.log("거절 할사람: " + m_id)
    console.log("거절 당할사람: " + tradesend)
    let tNum = t_num
    let t_item = item;
    let t_itemcount = itemcount

    $.ajax({
        url: "/trade/refuse",
        method: "post",
        data: {"t_num": t_num,"t_item":t_item, "tradesend": tradesend, "m_id": m_id},
    }).done(resp => {
        if (resp != null) {
            let selector=`#notification_${tNum}_${t_item}_${t_itemcount}`
            console.log($(selector))
            $(selector).remove()
            // $('#socketAlert').append(resp)
            let notice = document.querySelector("#span-notice-count");
            let noticelist = document.querySelectorAll("#noticelist a");
            notice.innerHTML = noticelist.length - 1 + "+";
            let refuseMessage={"type":"refuse","t_num":t_num,"tradesend":m_id,"m_id":tradesend}
            socket.send(JSON.stringify(refuseMessage))
        }
    }).fail(err => {
        console.log(err)
    })
}


function commonSearch(value, event) {
    if (event.keyCode === 13) {
        if (window.find(value)) { // 찾고자 하는 결과가 현재 페이지에 있으면 강조표시하고 함수 종료
            return
        } else {
            // window.location.href='/main/search/result'
            // $.ajax({
            //     method:'get',
            //     url:"/common/main/search?value="+value
            // }).done(resp=>{
            //     console.log(resp)
            // }).fail(err=>{
            //     console.log(err)
            // })
        }


    }
}