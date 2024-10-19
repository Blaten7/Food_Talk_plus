<%--
  Created by IntelliJ IDEA.
  User: dnjs1
  Date: 24. 7. 10.
  Time: 오후 4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title</title>
    <style>
        .pop_wrap__inner {
            position: absolute;
            top: 50%;
            left: 50%;
            min-width: 500px;
            padding: 30px;
            background: #FFF;
            transform: translate3d(-50%, -50%, 0);
        }

        .pop_wrap {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.6);
        }

        .pop_wrap__inner .btn .btn-init {
            height: 60px;
            line-height: 60px;
            font-size: 20px;
            font-weight: 600;
        }

        .pop_wrap__inner .pop_content {
            margin-bottom: 30px;
            text-align: center;
            font-size: 18px;
            line-height: 1.3;
        }
    </style>
</head>
<header>
    <jsp:include page="../common/header.jsp"></jsp:include>
</header>
<!-- Slick Carousel 라이브러리 -->
<script src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<!-- Slick Carousel CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css">
<script>
    $(document).ready(() => {
        $(".slick-track").slick({
            slidesToShow: 1,
            autoplay: true,
            autoplaySpeed: 2000,
            infinite: true,
            arrows: true,
        });
    });
</script>
<body id="marketDetail" class="hd">
<div id="wrap">
    <main id="contents" class="hd__marketDetail">
        <ul class="breadcrumb">
            <li>HOME</li>
            <li>${c_name}</li>
        </ul>
        <div class="marketDetail hd__inner1100">
            <section class="marketDetail__sect hd__inner1100 overview"><h2 class="hidden">마켓상세 상품정보</h2>
                <div class="slider__sec" id="itemImageSlide">
                    <div class="slider slick-initialized slick-slider">
                        <div class="slick-list draggable">
                            <div class="slick-track" style="width: 100%;">
                                <%--------동기로 이미지 4장 가져와서 출력--------%>
                                ${img}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="info-area">
                    <h3 class="info-area__name">
                        <span class="strong">${c_name}&nbsp;${title}</span><span>원산지 :*상세페이지 참고</span>
                    </h3>
                    <div class="box"></div>
                    <div class="price">
                        <div class="price-discounted strong">${price}원</div>
                    </div>
                    <div class="info-area__box">
                        <div class="info-area__box-list" id="weight_line">
                            <div class="info-area__box-tit"><span>용량</span></div>
                            <div class="info-area__box-cont">
                                <div class="store">
                                    <div class="store-name"><span>${volume}</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="info-area__box-list" id="calorie_line">
                            <div class="info-area__box-tit"><span>칼로리</span></div>
                            <div class="info-area__box-cont">
                                <div class="store">
                                    <div class="store-name"><span>${cal}</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="info-area__box-list">
                            <div class="info-area__box-tit"><span>보관방법</span></div>
                            <div class="info-area__box-cont">
                                <div class="store">
                                    <div class="store-name"><span>${save}</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="info-area__box-list">
                            <div class="info-area__box-tit"><span>등록일</span></div>
                            <div class="info-area__box-cont">
                                <div class="store">
                                    <div class="store-name"><span>${date}</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="info-area__box-list">
                            <div class="info-area__box-tit"><span>판매자</span></div>
                            <div class="info-area__box-cont">
                                <div class="store">
                                    <div class="store-name"><span>관리자</span></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <section class="btn-area">
                        <div class="btn">
                            <button class="btn-init orange dvMiniCartOpen" onclick="sendCartOpen()">
                                장바구니 담기
                            </button>
                            <button class="btn-init green dvMiniCartOpen" onclick="sendCartOpen()">
                                구매하기
                            </button>
                        </div>
                    </section>
                </div>
            </section>
            <div class="vreview-summary-widget"></div>
            <section class="marketDetail__sect detail">
                <div class="tab-menu">
                    <%--버튼 --%>
                    <ul class="tab-menu__inner">
                        <li class="tab-menu__list tnb-area__list tab-menu__list--on">
                            <a href="javascript:void(0);" onclick="foodDetail(this,'foodInfo')">
                                <strong>상품정보</strong>
                            </a>
                        </li>
                        <li class="tab-menu__list tnb-area__list " id="reviewTab">
                            <a href="javascript:void(0);" onclick="foodDetail(this,'foodReview')">
                                <strong>상품리뷰</strong><span class="vreview-review-count tab-menu__count"
                                                           id="reviwCnt">29</span>
                            </a>
                        </li>
                        <li class="tab-menu__list tnb-area__list" data-tab="cont-inquiry">
                            <a href="javascript:void(0);" onclick="foodDetail(this,'foodInquiry')">
                                <strong>상품문의</strong></a><span class="tab-menu__count" id="qnaCnt">0</span>
                            </a>
                        </li>
                        <li class="tab-menu__list tnb-area__list" data-tab="cont-notice">
                            <a href="javascript:void(0);" onclick="foodDetail(this,'change')">
                                <strong>교환/반품</strong>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="tab-content cont-info tab-content--show tab-contents--current" id="cont-info">
                    <section class="detailInfo tab-content__sect">
                        <div style="display: flex; justify-content: center; align-items: center;">
                            ${info}
                        </div>
                    </section>
                </div>
            </section>
        </div>
    </main>
</div>
<div class="option-area" id="dvMiniCart">
    <div class="btn-option btn-option--on">
        <div class="btn dvMiniCartOpen" dvitemid="148758">
            <a href="#" id="btnSelOption" class="btn-init green" onclick="sendCartOpen()">
                옵션선택
            </a>
        </div>
    </div>
    <article class="minicart">
        <div class="minicart__inner">
            <section class="output" id="dvMiniCartOrderArea">
                <div class="output__box option dvMiniCartOrderItem">
                    <div class="output__info">
                        <span class="output__title" id="optnFullVal">${c_name} + ${title}</span>
                    </div>
                    <div class="count-area dvMiniCartOrderItemIndex" id="orderItemBoxIndex_1">
                        <span class="count__name">수량</span>
                        <div class="count hd__amount-count">
                            <input type="text" class="dvMiniCartOrderItemQty" name="QTY" value="0" max="${count}">
                            <button type="button" class="btn__count-down dvMiniCartOrderItemQtyDown" disabled="">
                                <span class="hidden">빼기</span>
                            </button>
                            <button type="button" class="btn__count-up dvMiniCartOrderItemQtyUp">
                                <span class="hidden">추가</span>
                            </button>
                        </div>
                    </div>
                    <span class="price">
                        <span class="price-discounted strong">${price}원</span>
                    </span>
                </div>
            </section>
            <div class="total">
                <span class="total__text" id="optnNeedVal"></span>
                <span class="relative">	총 상품금액</span>
            </div>
            <div class="btn-area" style="width: 900px; display: flex; flex-direction: row-reverse">
                <div class="btn" style="margin: 0">
                    <button class="btn-init orange" id="intoCart">
                        장바구니 담기
                    </button>
                    <button class="btn-init green" id="intoOrder">
                        구매하기
                    </button>
                </div>
            </div>
        </div>
    </article>
</div>
<div class="pop_wrap" id="confirm-layer" style="z-index: 10000; display: none;">
    <div class="pop_wrap__inner">
        <h2 class="pop_header hidden">confirm</h2>

        <div class="pop_content">
            <div class="text">
                장바구니에 담았습니다.<br>바로 확인 하시겠습니까?
            </div>
            <button type="button" class="btn_close btn--delete" style="visibility:hidden;"></button>
        </div>

        <div class="btn_area btn confirm">
            <button type="button" class="btn-init white" id="dvConfirmCancel">쇼핑 계속하기</button>
            <button type="button" class="btn-init green" id="dvConfirmOk">장바구니로 이동</button>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="../common/footer.jsp"></jsp:include>
</footer>
<form id="orderForm" method="POST" action="/cart/order" style="display:none;"></form>
<script>
    function sendCartOpen() {
        console.log('들어왔니?');
        const miniCart = document.getElementById("dvMiniCart");
        const aTage = document.getElementById("btnSelOption");
        if (!miniCart.classList.contains('open')) {
            miniCart.classList.add('open');
            aTage.style.background = "#1c1c1c url(https://www.greating.co.kr/front_pc/images/btn_comm_arrow-down-e6bda780593314221fc082521283755d.png) no-repeat 154px 25px";
        } else {
            miniCart.classList.remove('open');
            aTage.style.background = "#1c1c1c url(https://www.greating.co.kr/front_pc/images/btn_comm_arrow-up-dcb850e9a9bb6140063ddc673388506e.png) no-repeat 154px 25px";
        }
    }

    const countList = document.querySelectorAll('.count>*');
    const itemCount = document.querySelector('.count>input')
    const relative = document.getElementsByClassName('relative');
    const count1 = "${count}";
    let i = 0;
    countList.forEach(count => {
        count.addEventListener('click', function () {
            switch (count.className) {
                case "btn__count-up dvMiniCartOrderItemQtyUp":
                    console.log(count.className);
                    ++i;
                    if(i > count1){i=count1}
                    break;
                case "btn__count-down dvMiniCartOrderItemQtyDown":
                    --i;
                    if(i > count1){i=count1}
                    break;
                case "dvMiniCartOrderItemQty":
                    count.addEventListener('change', () => {
                        i = count.value;
                        if(i > count1){i=count1}
                    })
                    break;
            }
            let price = "${price}";
            let rePrice = price.replace(/,/g, "");
            console.log(rePrice);
            (i > count1) ? itemCount.value = count1 : itemCount.value = i;
            totalPrice = rePrice * itemCount.value;
            relative[0].innerHTML = "총 상품금액  " + totalPrice.toLocaleString()
            if (itemCount.value <= 0) {
                countList[1].disabled = true;
            } else {
                countList[1].disabled = false;
            }
        })
    })

    function foodDetail(obj, result) {
        const searchClass = document.getElementsByClassName("tab-menu__list--on");
        const addClass = obj.closest(".tab-menu__list");
        console.log("a..", addClass);
        const urlParams = new URLSearchParams(window.location.search);
        const fNum = urlParams.get("f_num");
        console.log("fNum", fNum);

        if (!addClass.classList.contains("tab-menu__list--on")) {
            // 모든 기존 tab-menu__list--on 클래스를 제거
            Array.from(searchClass).forEach(el => {
                el.classList.remove("tab-menu__list--on");
            });

            // 클릭한 요소의 부모 <li>에 클래스 추가
            addClass.classList.add("tab-menu__list--on");
        }
        $.ajax({
            type: 'get',
            url: "/fooditem/restDetail",
            data: {
                f_num: fNum, type: result
            }
        }).done(resp => {
            console.log("resp", resp);
            $("#cont-info div").html(resp)
        }).fail(err => {
            console.log(err)
        });
    }

    let cartBtn = document.getElementsByClassName("btn-init");
    const qureyString = window.location.search;
    const urlParams = new URLSearchParams(qureyString)
    console.log(cartBtn);
    Array.from(cartBtn).forEach(bt => {
        bt.addEventListener("click", function () {
            if (itemCount.value === 0){
                alert("수량을 입력해주세요")
                return false
            }
            console.log("id", bt.id);
            switch (bt.id) {
                case "intoOrder":
                    intoOrder();
                    break;
                case "intoCart":
                    /* console.log("현재 f_num",urlParams.get("f_num"))
                     let cartList ={num: urlParams.get("f_num"), count:itemCount.value}*/
                    const popWrap = document.getElementById("confirm-layer")
                    const popButton = document.getElementById("dvConfirmOk")
                    /*       location.href = "/cart/"+ bt.id +"?num=" + urlParams.get("f_num")+"&count="+itemCount.value;*/
                    $.ajax({
                        type: "get",
                        url: "/cart/" + bt.id + "?num=" + urlParams.get("f_num") + "&count=" + itemCount.value
                    }).done(resp => {
                        if (resp) {
                            popWrap.style.display = "block";
                            popButton.focus()
                        }
                    }).fail(err => console.log(err))
                    break;
                default:
                    console.log("이상한거 들어옴 꾸엑")
            }

        })
    })
    const dvPopbtn = document.getElementsByClassName("btn_area")
    const popbtn = dvPopbtn[0].querySelectorAll(".btn-init")
    popbtn.forEach(b => {
        b.addEventListener("click", function () {
            const value = this.id;
            switch (value) {
                case"dvConfirmCancel":
                    location.href = "/fooditem/main";
                    break;
                case"dvConfirmOk":
                    location.href = "/cart/main";
                    break;
            }
        })
    })

    function intoOrder() {
        const itemCount = document.querySelector('.count>input')
        const qureyString = window.location.search;
        const urlParams = new URLSearchParams(qureyString)
        const price = "${price}"
        const dvImg = document.getElementsByClassName("slider__list")
        let img = dvImg[0].querySelector("img")
            img = img.src.replace("http://localhost", "")
        const jsonImg = img
        let rePrice = price.replace(/,/g, "");
        let dvItem = [];
        dvItem.push("dvItemId:" + urlParams.get("f_num"));
        dvItem.push("dvItemName:" + "${title}")
        dvItem.push("dvItemPrice:" + rePrice);
        dvItem.push("dvItemCount:" + itemCount.value);
        dvItem.push("dvItemImg:" + jsonImg)
        dvItem.push("dvCartId:"+0);
        console.log(dvItem)
        // FormData 객체 생성
        const formData = new FormData();
        dvItem.forEach(list => {
            formData.append(`orderList[]`, list);
        });
        // 폼 요소 가져오기
        const form = document.getElementById('orderForm');
        // FormData를 폼 요소에 추가
        for (let [key, value] of formData.entries()) {
            let input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = value;
            form.appendChild(input);
        }
        // 폼 제출
        form.submit();

    }



</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
