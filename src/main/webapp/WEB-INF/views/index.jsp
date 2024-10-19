<%--
  Created by IntelliJ IDEA.
  User: dnjs1
  Date: 24. 7. 4.
  Time: 오후 6:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style>
        #bodyWasteTitle:hover {
            color: #77b347;
        }

        .isHoverZoom:hover {
            transform: scale(1.3);
            transition: transform 0.7s;
        }

        .isHoverZoom {
            transform: scale(1.0);
            transition: transform 0.7s;
        }
    </style>
</head>
<body>
<header>
    <jsp:include page="./main/common/header.jsp"></jsp:include>
</header>
<script>
    console.log("랭킹 1위" + '${Rank[0]}')
    console.log("랭킹 2위" + '${Rank[1]}')
    console.log("랭킹 3위" + '${Rank[2]}')
    console.log("랭킹 4위" + '${Rank[3]}')
    $(() => {
        let rank1 = $('#rank1-in-glory')
        let rank2 = $('#rank2-in-glory')
        let rank3 = $('#rank3-in-glory')
        let rank4 = $('#rank4-in-glory')

        let str = "";
        let imgPath = '${Rank[0].IList[0].i_path}' + '${Rank[0].IList[0].i_sys_name}'
        let priceStr = '${Rank[0].f_price}'
        let price = parseInt(priceStr, 10);
        let formattedPrice = price.toLocaleString();
        str += "<a href='#' style='overflow: hidden'>"
        str += "<img class='isHoverZoom' src='" + imgPath + "' style='width: 500px'>"
        str += "</a>"
        str += "<h3 style='color: black; font-weight: bold'>" + '${Rank[0].f_title}' +"</h3>"
        str += "<a href='#' style='display: flex; flex-direction: row'>"
        str += "<h2 style='color: black; font-weight: bold; margin-right: 10px'>" + formattedPrice + "\u20A9</h2>"
        str += "<button>담기</button>"
        str += "<button>선물</button>"
        rank1.html(str)

        let str2 = "";
        let imgPath2 = '${Rank[1].IList[0].i_path}' + '${Rank[1].IList[0].i_sys_name}'
        let priceStr2 = '${Rank[1].f_price}'
        let price2 = parseInt(priceStr2, 10);
        let formattedPrice2 = price2.toLocaleString();
        str2 += "<a style='overflow: hidden'>"
        str2 += "<img class='isHoverZoom' src='" + imgPath2 + "' style='height: 200px'>"
        str2 += "</a>"
        str2 += "<div style='display: flex; flex-direction: column; width: 330px; margin-top: 30px; margin-left: 20px'>"
        str2 += "<h4 style='font-weight: bold; color: black'>" + '${Rank[1].f_title}' + "</h4>"
        str2 += "<h4 style='color: black; font-weight: bold'>" + formattedPrice2 + "\u20A9"
        str2 += "<span style='margin-left: 50px'><button>담기</button></span>"
        str2 += "<span style='margin-left: 50px'><button>선물</button></span>"
        str2 += "</h4>"
        rank2.html(str2)

        let str3 = "";
        let imgPath3 = '${Rank[2].IList[0].i_path}' + '${Rank[2].IList[0].i_sys_name}'
        let priceStr3 = '${Rank[2].f_price}'
        let price3 = parseInt(priceStr3, 10);
        let formattedPrice3 = price3.toLocaleString();
        str3 += "<a style='overflow: hidden'>"
        str3 += "<img class='isHoverZoom' src='" + imgPath3 + "' style='height: 200px'>"
        str3 += "</a>"
        str3 += "<div style='display: flex; flex-direction: column; width: 330px; margin-top: 30px; margin-left: 20px'>"
        str3 += "<h4 style='font-weight: bold; color: black'>" + '${Rank[2].f_title}' + "</h4>"
        str3 += "<h4 style='color: black; font-weight: bold'>" + formattedPrice3 + "\u20A9"
        str3 += "<span style='margin-left: 50px'><button>담기</button></span>"
        str3 += "<span style='margin-left: 50px'><button>선물</button></span>"
        str3 += "</h4>"
        rank3.html(str3)

        let str4 = "";
        let imgPath4 = '${Rank[3].IList[0].i_path}' + '${Rank[3].IList[0].i_sys_name}'
        let priceStr4 = '${Rank[3].f_price}'
        let price4 = parseInt(priceStr4, 10);
        let formattedPrice4 = price4.toLocaleString();
        str4 += "<a style='overflow: hidden'>"
        str4 += "<img class='isHoverZoom' src='" + imgPath4 + "' style='height: 200px'>"
        str4 += "</a>"
        str4 += "<div style='display: flex; flex-direction: column; width: 330px; margin-top: 30px; margin-left: 20px'>"
        str4 += "<h4 style='font-weight: bold; color: black'>" + '${Rank[3].f_title}' + "</h4>"
        str4 += "<h4 style='color: black; font-weight: bold'>" + formattedPrice4 + "\u20A9"
        str4 += "<span style='margin-left: 50px'><button>담기</button></span>"
        str4 += "<span style='margin-left: 50px'><button>선물</button></span>"
        str4 += "</h4>"
        rank4.html(str4)
    })
</script>
<main>
    <div id="carouselExample" class="carousel slide">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="/uploadedImg/main/MainPage/구매스탬프.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/바이오랜드.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/식단마스터.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/쿠폰.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/한솔냉면.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/해일찍여름.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/급상승%20키워드.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/제주%20고당도%20감귤.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/MainPage/천중도%20복숭아.jpg" class="d-block w-100" alt="...">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
    <div style="margin-left: 400px; width: 1100px; margin-top: 100px">
        <h4 style="color: gray">건강은 밥상으로부터</h4>
        <a href="#" style="color: black"><h2 id="bodyWasteTitle" style="font-weight: bold;">푸드톡이 자신있게 추천드려요 <i
                class="main__ico v2">SALE</i></h2></a>
        <div style="display: flex; flex-direction: row">
            <div id="rank1-in-glory" class="isHoverDiv" style="width: 500px; display: flex; flex-direction: column">

            </div>
            <div style="width: 550px; margin-left: 50px; display: flex; flex-direction: column">
                <div id="rank2-in-glory" style="display: flex; flex-direction: row; margin-bottom: 20px"
                     class="isHoverDiv">

                </div>
                <div id="rank3-in-glory" style="display: flex; flex-direction: row; margin-bottom: 20px">
                    <a style="overflow: hidden">
                        <img class="isHoverZoom" src="/uploadedImg/main/고등어조림.jpg" style="height: 200px">
                    </a>
                    <div style="display: flex; flex-direction: column; width: 330px; margin-top: 30px; margin-left: 20px">
                        <h5>씹기 편한 고단백 식사</h5>
                        <h4 style="font-weight: bold; color: black">[소프트프로틴] 뼈까지 먹는 고등어조림 180g</h4>
                        <h6 style="text-decoration: line-through; color: gray">6,500원</h6>
                        <h4 style="color: black; font-weight: bold">
                            <span style="color: red; font-weight: bold">10% </span> 5,850원
                            <span style="margin-left: 50px">
                                <button>담기</button>
                                <span style="margin-left: 20px">
                                <button>선물</button>
                                </span></span>
                        </h4>
                    </div>
                </div>
                <div id="rank4-in-glory" style="display: flex; flex-direction: row; margin-bottom: 20px">
                    <a style="overflow:hidden;">
                        <img class="isHoverZoom" src="/uploadedImg/main/하얀고추짬뽕.jpg" style="height: 200px">
                    </a>
                    <div style="display: flex; flex-direction: column; width: 330px; margin-top: 30px; margin-left: 20px">
                        <h5>익산 중식 노포 맛집 신동양</h5>
                        <h4 style="font-weight: bold; color: black">[모두의맛집] 하얀고추짬뽕 (2인분) 740g</h4>
                        <h6 style="text-decoration: line-through; color: gray">13,900원</h6>
                        <h4 style="color: black; font-weight: bold"><span
                                style="color: red; font-weight: bold">30% </span> 9,730원<span
                                style="margin-left: 50px"><button>담기</button><span style="margin-left: 20px"><button>선물</button></span></span>
                        </h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="carouselExampleIndicators" class="carousel carousel-dark slide" data-bs-ride="carousel"
         style="margin-top: 100px; margin-left: 400px; width: 1100px;">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
                    aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
                    aria-label="Slide 3"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="3"
                    aria-label="Slide 3"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="4"
                    aria-label="Slide 3"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="5"
                    aria-label="Slide 3"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="6"
                    aria-label="Slide 3"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="7"
                    aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="/uploadedImg/main/맞춤형건강식단.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/암환자체험판.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/6일챌린지.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/암환자.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/당뇨.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/건강.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/신장.jpg" class="d-block w-100" alt="...">
            </div>
            <div class="carousel-item">
                <img src="/uploadedImg/main/고혈압.jpg" class="d-block w-100" alt="...">
            </div>
        </div>
    </div>
</main>
<br>
<br>
<br>
<br>

<footer>
    <jsp:include page="./main/common/footer.jsp"></jsp:include>
</footer>
</body>
</html>
