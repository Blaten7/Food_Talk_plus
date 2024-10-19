<%--
  Created by IntelliJ IDEA.
  User: dnjs1
  Date: 24. 7. 4.
  Time: 오후 5:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags"
           prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Title</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/assets/css/header.css">
    <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css?h=cb606d99bb2418df19b6bc818b41e412">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.12.0/css/all.css">
    <link rel="stylesheet" href="/assets/css/styles.min.css?h=94c76ca45cf1136042bce4cad72a7b5e">
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
    <script src="/assets/js/header.js"></script>

</head>

<style>
    #header {
        border: 1px solid #e6e7e8;
    }

    * {
        font-family: 'Noto Sans KR';
        font-weight: 500;
    }

    a {
        text-decoration: none;
        color: gray;
    }

    li {
        list-style: none;
    }
</style>
<script>
    $(() => {
        document.getElementById('titleName1').classList.remove("on")
        document.getElementById('titleName2').classList.remove("on")
        switch (window.location.pathname) {
            case "/":
                document.getElementById('titleName1').classList.add("on")
                break;
            case "/careMain":
                document.getElementById('titleName2').classList.add("on")
                break;
            default:
                document.getElementById('titleName1').classList.add("on")
        }
        if("${cartCount}"!== null && "${cartCount}" !== ""){
            const cartCount = document.getElementById("cartCnt")
            cartCount.innerHTML = "${cartCount}";
        }else{
            cartCount.innerHTML = 0;
        }
    })
    function saveTitleName(titleName) {
        console.log("현재 타이틀 이름은 ?")
        let tName = titleName.innerHTML
        console.log(window.location.pathname)
        switch (tName) {
            case "건강마켓":
                window.location.href="/"
                break;
            case "식단관리":
                window.location.href="/careMain"
                break;
            default:
                console.log("건강/식단 선택 에러")
        }
    }
    function commonSearch(keyword, event) {
        console.log("충격과 공포의 검색기능 시작이다 그지깽깽이들아")
        if (event.keyCode === 13) {
            if (window.find(keyword)) {
                $('#searchInput2').focus()
            } else {
                console.log("일단 여기는 없다 니모를 찾아서 ㄱㄱ")
                window.location.href="/common/main/search?value="+keyword
            }
        }

    }
</script>
<body id="main" class="hd">
<div id="wrap">
    <header id="header" class="hd__header">
        <div class="header">
            <section class="user-area">
                <ul class="user-area__menu" style="margin: 0">
                    <%--로그인 클릭--%>
                    <sec:authorize access="isAnonymous()">
                        <li><a href="/loginfrm">로그인</a></li>
                        <li><a href="/joinfrm">회원가입</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <span>${sessionScope.m_name}</span>님 환영합니다
                        <li><a href="/member/logout">로그아웃</a></li>
                        <sec:authorize access="hasRole('ADMIN')">
                            <li><a href="/main">관리자 페이지</a></li>
                        </sec:authorize>
                    </sec:authorize>
                    <%--배송정보 클릭--%>
                    <li><a href="/delivery/info">배송정보</a></li>
                    <%--고객센터 클릭--%>
                    <li><a href="/customer/center">고객센터</a></li>&nbsp;&nbsp;&nbsp;&nbsp;
                    <sec:authorize access="isAuthenticated()">
                        <li id="noticelist" class="nav-item dropdown no-arrow mx-1">
                            <div class="nav-item dropdown no-arrow">
                                <a class="dropdown-toggle nav-link" aria-expanded="false" data-bs-toggle="dropdown"
                                   href="#">
                                    <span id="span-notice-count" class="badge bg-danger badge-counter"></span>
                                    <i class="fas fa-bell fa-fw"></i></a>
                                <div id="socketAlert"
                                     class="dropdown-menu dropdown-menu-end dropdown-list animated--grow-in">
                                    <h6 class="dropdown-header">알림</h6>
                                        <%--                                    <a class="dropdown-item text-center small text-gray-500" href="#">--%>
                                        <%--                                        모든 알림 보기</a>--%>
                                </div>
                            </div>
                        </li>
                    </sec:authorize>
                </ul>
            </section>
            <div class="header__inner">
                <div class="header__sec">
                    <h1 class="logo"style="height: 80px;display: inline-block" >
                        <a href="/">
                            <img src="/uploadedImg/main/logo.png" alt=""style="width: 180px;"><%--추후 제목 이미지 넣어야함--%>
                        </a>
                    </h1>
                    <div class="menu">
                        <a id="titleName1" href="javascript:void(0)" onclick="saveTitleName(this)">건강마켓</a>
                        <a id="titleName2" href="javascript:void(0)" onclick="saveTitleName(this)">식단관리</a>
                    </div>
                    <input id="savedTitleName" style="display: none">
                    <nav class="direct">
                        <div class="direct__search btn__modal-open" data-login="y" id="searchPopup"
                             data-popup-name="popup_search" style="margin-bottom: 10px;">
                            <input type="text"
                                   style="height: 50px"
                                   id="searchInput2"
                                   class="search-input"
                                   placeholder="재료를 입력해 주세요"
                                   autocomplete="off"
                                   onkeypress="commonSearch(this.value, event)"> <%--추후 input 창 출력 클릭 이벤트 --%>
                            <button type="button" class="direct__search-remove" style="display: none;">지우기</button>
                            <a class="btn__modal-open" data-login="n" data-popup-name="popup_search">검색</a></div>

                        <%--내정보 클릭--%>
                        <a href="/member/mypage">
                            <img src="/uploadedImg/main/스크린샷 2024-07-04 163834.png" width="69" height="66">
                        </a>
                        <%--레시피 글쓰기 클릭--%>
                        <a href="/cart/test">
                            <img src="/uploadedImg/main/스크린샷%202024-06-27%20171241.png?h=9f0eff141daebd0b04983e61b2ea4b97"
                                 width="72" height="75" style="margin-bottom: -2px;margin-top: -11px;">
                        </a>
                        <a class="direct__heart"> <img id="heartIMG"></a>
                        <a href="/cart/main" class="direct__cart"> <span id="cartCnt">0</span>
                            <img src="/uploadedImg/main/icon_header_cart.png?h=b0cf2eaea34afb39f82041851f5691b1"
                                 alt="장바구니"></a>
                    </nav>
                </div>
                <div class="gnb">
                    <div class="gnb__inner">
                        <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <%--식자제 링크--%>
                                <li class="gnb__list"><a id="headCardLink" class="gnb__list-name" href="/fooditem/main">식자재</a>
                                </li>
                                <%--랭킹 링크--%>
                                <li class="gnb__list"><a class="gnb__list-name main_tab" href="/fooditem/ranking">베스트</a></li>
                                <%--분류 링크--%>
                                <li class="gnb__list"><a class="gnb__list-name main_tab" href="/recipe/main">분류</a></li>
                                <%--물물교환 링크--%>
                                <li class="gnb__list"><a class="gnb__list-name main_tab" href="/trade/main">물물교환</a>
                                </li>
                                <li></li>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
</div>
</body>
</html>
