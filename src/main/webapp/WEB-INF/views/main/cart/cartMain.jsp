<%--
  Created by IntelliJ IDEA.
  User: dnjs1
  Date: 24. 7. 19.
  Time: 오전 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<script>
    window.onload = () => {
        const noneList = document.querySelector('.none-list');
        const onList = document.querySelector(".orderCart__list");
        const allList = document.getElementById("orderCartTop");
        if ("${count}" === "0") {
            noneList.style.display = "block";
            onList.style.display = "none";
        } else {
            allList.style.display = "block";
        }
    }
</script>
<body id="orderCart" class="hd sub">
<jsp:include page="../common/header.jsp"></jsp:include>
<div id="wrap">
    <main id="contents" class="hd__orderCart">
        <ul class="breadcrumb">
            <li>HOME</li>
            <li>장바구니</li>
        </ul>
        <div class="orderCart hd__inner1100">
            <h2 id="cartTitleGT" class="hd__page-title">장바구니</h2>
            <a id="cartBtnGt" class="hd__page-title__btn-link" style="display: none;">그리팅 장바구니</a>
            <section class="orderCart__sec">
                <ul class="tab-menu clearfix col3" id="carttab" style="padding: 0">
                    <li class="tab-menu__list tab-menu__list--on disabled" data-tab="cartOriginal" id="cartOriginal"><a>
                        <span id="originalTab">일반(${count})</span></a></li>
                    <li class="tab-menu__list disabled" data-tab="cartGift" id="cartGift">
                        <a style="cursor: pointer"><span id="giftTab">선물(0)</span></a></li>
                </ul>
                <div id="orderCartTop" class="orderCart__top" style="display: none;">
                    <div class="orderCart__top__ctrl" id="allDiv">
                        <span class="checks">
                            <input type="checkbox" name="all" id="checkall" checked="">
                            <label class="form-label" for="checkall">전체선택</label></span>
                        <div class="btn__delete">
                            <a class="btn-round" id="dvShippingTypeSelectCart" style="display: none;">선물주문 이동</a>
                            <a class="btn-round" id="dvDelSelectCart" style="cursor: pointer">선택삭제</a>
                        </div>
                    </div>
                    <div id="giftType10" class="orderCart__top__gift accordion" style="display: none;"><a
                            href="https://www.greating.co.kr/order/orderCart" class="gift-link"></a>
                        <div class="accordion__head" id="giftType10Smr"></div>
                        <div class="accordion__cont">
                            <div class="gift-box">
                                <div class="gift-info">
                                    <div class="gift-info__price">
                                        <p><span id="giftType10CtgryNm"></span> 선택 상품 금액 </p>
                                        <span class="price" id="giftType10Payment"></span>
                                    </div>
                                    <div class="gift-info__text">
                                        <strong id="giftType10ArlY"></strong>
                                        <p id="giftType10ArlN"></p>
                                    </div>
                                </div>
                                <div class="gift-list" id="giftType10Item"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="width: 1100px; margin: 0 auto">
                    <div class="orderCart__list">
                        <ul class="orderCart__title">
                            <li>제품정보</li>
                            <li>판매금액</li>
                            <li>수량</li>
                            <li>구매금액</li>
                        </ul>
                        <ul class="box" id="dvCartListArea" style="padding: 0">
                            ${caList}
                            <%-- <li class="orderCartBox__list single">
                                  <div class="orderCartBox__list__inner">
                                      <input type="hidden" name="f_num" value="f_num">&lt;%&ndash;f_num&ndash;%&gt;
                                      <input type="hidden" name="o_name" value="보양추어탕 550g">&lt;%&ndash;f_name&ndash;%&gt;
                                      <input type="hidden" name="dvItemPrice" value="f_price">&lt;%&ndash;f_price&ndash;%&gt;
                                      <input type="hidden" name="dvItemCount" value="">
                                      <input type="hidden" name="dvCartId" value="ca_num">&lt;%&ndash;ca_num 주문 완료시  삭제&ndash;%&gt;
                                      <input type="hidden" name="dvItemImg"
                                             value="append(cart.getIList().get(0).getI_path()).append(cart.getIList().get(0).getI_sys_name())">
                                      <!-- 상품선택 체크박스 -->
                                      <div class="checkBoxAll">
                                          <span class="checks">
                                          <!-- 판매중단 / 일시품절 / 판매중단인 경우 비노출 -->
                                              <input type="checkbox" id="prdSelect16453040" name="all" title="상품선택"
                                                     checked="checked">
                                                  <label for="prdSelect16453040"></label>
                                          </span>
                                      </div>
                                      <!--// 상품선택 체크박스 -->
                                      <!-- 아이템 박스 -->
                                      <div class="orderCartBox__list__cont">
                                          <figure class="cont__img">
                                              <a href="/fooditem/detail?f_num=+(f_num)">
                                                  <img src="https://image.greating.co.kr/IL/item/202206/21/B_037EE1DC9AAC44E5B8C93FEEBFD663BC.jpg">
                                              </a>
                                          </figure>
                                          <div class="cont__text">
                                              <a href="/fooditem/detail?f_num=+(f_num)">
                                                  <strong class="cont__title">c_name + f_title</strong>
                                              </a>
                                          </div>
                                      </div>
                                      <span class="selling-price price01">f_price원</span>
                                      <div class="prd_value">
                                          <span class="prd_length">
                                              <input type="text" min="1" max="10" class="dvCartDetlQty"
                                                     value="2">
                                              <button type="button" class="btn_st1_plus">수량 감소</button>
                                              <button type="button" class="btn_st1_minus">수량 증가</button>
                                          </span>
                                        <span class="discount-tooltip bottom qtyDcToolTip_127565 hide"></span>
                                    </div>
                                    <span class="selling-price price02">f_count * f_price</span>
                                    <button type="button" class="btn__list-delete dvDelCartDetl">
                                          <span class="hidden">삭제</span>
                                      </button>
                                  </div>
                              </li>--%>
                        </ul>
                        <div class="orderCart__total" id="dvCartSummaryArea">
                            <div class="orderCart__total__cont">
                                <p class="orderCart__total__txt">총 결제 예정금액</p>
                                <em class="orderCart__total__price"><span id="totalPaymentPrice">11,250</span>원</em>
                            </div>
                        </div>
                        <div class="btn">
                            <button type="button" class="btn-init green" id="dvSelectOrder" onclick="dvOrder()">구매하기
                            </button>
                        </div>
                    </div>
                </div>
                <div class="none-list" style="display: none">
                    <div class="none-list__inner">
                        <p class="none-list__txt">장바구니에 담긴 상품이 없습니다.</p>
                    </div>
                    <a class="btn__goHome" id="goHome" href="/">홈으로</a></div>
            </section>
        </div>
        <form id="orderForm" method="POST" action="/cart/order" style="display:none;"></form>
        <script>
            document.addEventListener('DOMContentLoaded', () => {
                price()
                const dvList = document.getElementById("dvCartListArea");
                const check = document.getElementsByName("all");
                Array.from(check).forEach(c => {
                    c.addEventListener("click", function () {
                        if (this.id === "checkall") {
                            if (this.checked) {
                                Array.from(check).forEach(e => {
                                    e.checked = true
                                })
                            } else {
                                Array.from(check).forEach(e => {
                                    e.checked = false
                                })
                            }
                        } else if (this.id !== "checkall") {
                            if (this.checked === false) {
                                check[0].checked = false
                            } else {
                                const allChecked = Array.from(check).slice(1).every(box => box.checked);
                                if (allChecked) {
                                    check[0].checked = true;
                                }
                            }
                        }
                    })
                })

                function registerDeleteButtons() {
                    const dvDelete = document.getElementsByClassName("dvDelCartDetl");
                    Array.from(dvDelete).forEach(d => {
                        d.removeEventListener('click', deleteHandler);
                        d.addEventListener('click', deleteHandler);
                    });
                    const countBt = document.querySelectorAll(".prd_length>*")
                    countBt.forEach(bt => {
                        bt.removeEventListener("click", countBtHandler)
                        bt.addEventListener("click", countBtHandler)
                    });
                    price()
                }

                function deleteHandler() {
                    console.log(this)
                    const deleteDiv = this.closest(".orderCartBox__list__inner")
                    const deleteButton = deleteDiv.querySelector("input[name='dvCartId']")
                    $.ajax({
                        url: "/cart/deleteList?ca_num=" + deleteButton.value,
                        type: "get"
                    }).done(resp => {
                        dvList.innerHTML = resp
                        registerDeleteButtons();
                    }).fail(error => console.log(error))
                }

                const dvDeleteAll = document.getElementById("dvDelSelectCart");
                dvDeleteAll.addEventListener("click", () => {
                    let deleteAll = [];
                    Array.from(check).slice(1).forEach(e => {
                        if (e.checked === true) {
                            let deleteDiv = e.closest(".orderCartBox__list__inner")
                            let deleteNum = deleteDiv.querySelector("input[name='dvCartId']")
                            if (deleteNum) {
                                deleteAll.push(deleteNum.value);
                            }
                        }
                    })
                    $.ajax({
                        url: "/cart/deleteList?",
                        type: "get",
                        data: {ca_num: deleteAll}
                    }).done(resp => {
                        dvList.innerHTML = resp
                        registerDeleteButtons();
                    }).fail(error => console.log(error))
                    console.log("아...", deleteAll)
                })

                function countBtHandler() {
                    let dvLi = this.closest(".orderCartBox__list__inner")
                    const dvItemCount = dvLi.querySelector("input[name='dvItemCount']")
                    const dvItemMaxcount = dvLi.querySelector("input[name='dvItemMaxcount']")
                    let price01 = dvLi.querySelector(".price01")
                    let price02 = dvLi.querySelector(".price02")
                    let dvInput = dvLi.querySelector(".dvCartDetlQty")
                    let currentValue = parseInt(dvInput.value); // 현재 값을 숫자로 변환
                    let minValue = parseInt(dvInput.min); // 최소값
                    let maxValue = parseInt(dvItemMaxcount.value); // 최대값
                    switch (this.className) {
                        case "btn_st1_minus":
                            if (currentValue > minValue) {
                                dvInput.value = --currentValue;
                            }
                            break;
                        case "btn_st1_plus":
                            if (currentValue < maxValue) {
                                dvInput.value = ++currentValue;
                            }
                            break;
                    }
                    dvItemCount.value = dvInput.value
                    let numberPrice = price01.innerHTML.replace(/[^0-9]/g, '');
                    let newPrice2 = (dvInput.value * numberPrice).toLocaleString() + "원"
                    price02.innerHTML = newPrice2
                    price()
                }

                function price() {
                    const allPrice02 = document.getElementsByClassName("price02");
                    const totalPriceHtml = document.getElementById("totalPaymentPrice");
                    let allprice = 0;
                    Array.from(allPrice02).forEach(i => {
                        allprice += Number(i.innerHTML.replace(/[^0-9]/g, ''));
                    })
                    totalPriceHtml.innerHTML = allprice.toLocaleString()
                }

                registerDeleteButtons();
            })

            function dvOrder() {
                const dvLi = document.getElementsByClassName("orderCartBox__list__inner")
                let dvItemList = [];
                Array.from(dvLi).forEach(e => {
                    let dvItem = [];
                    let select = e.querySelectorAll("input[type='hidden']")
                    Array.from(select).forEach(i => {
                        dvItem.push(i.name + ":" + i.value);
                    });
                    dvItemList.push(dvItem)
                })
                // FormData 객체 생성
                const formData = new FormData();
                dvItemList.forEach(list => {
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
                console.log(form)
                // 폼 제출

                form.submit();
            }
        </script>
    </main>
</div>
</body>
</html>
