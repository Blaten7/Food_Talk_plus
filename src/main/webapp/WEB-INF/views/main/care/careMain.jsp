<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24. 7. 10.
  Time: 오전 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script defer src="/common/js/common.js"></script>
    <script defer src="/common/js/jquery-3.7.1.min.js"></script>
    <style>
        .care{
            margin: 0 auto;
            margin-top: -80px;
            width: 1100px;
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            animation: fadeInUp 0.9s both 0.25s cubic-bezier(0.22, 0.61, 0.87, 1.27);
        }
        .care div{
            margin: 10px;
        }
        .care img{
            max-width: 100%;
            border-radius: 15px;
            box-shadow: 4px 4px 6px 0 rgba(0, 0, 0, 0.1);
        }
        a {
            color: #1c1c1c;
            text-decoration: none;
        }
        .careMain{
            background-image: url("/assets/img/careback.jpg");
            width: 1920px;
            height: 400px;
        }
        .careMain div{
            display: flex;
            justify-content: center;

        }
        .careMain a{
            margin-top: 80px;
            font-size: 34px;
            text-align: center;

        }
        .careMain a strong{
            color: #fff;
        }
    </style>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="careMain">
<div style="display: flex;flex-direction: column; text-align: center">
    <a href="javacript:void(0)" onclick="careServiceInfoOpenModal()"><strong>건강식단 ></strong></a><br>
    <p style="font-size: x-large; color: white">건강한 일상을 지키는 가장 어려운 관리</p>
    <br>
    <div style="justify-content: center">
        <input style="width: 120px; height: 50px; border-radius: 40px; border: 1px solid white; background-color: transparent; color: white; text-align: center" type="text" value="#일상관리" readonly>
        &nbsp;&nbsp;<input style="width: 150px; height: 50px; border-radius: 40px; border: 1px solid white; background-color: transparent; color: white; text-align: center" type="text" value="#매일건강한식사" readonly>
    </div>
</div>
</div>
<div class="care">
    <div><a href="/lowsugar" id="content1"><img src="/uploadedImg/main/저당식단.jpg"></a></div>
    <div><a href="/cal" id="content2"><img src="/uploadedImg/main/칼로리식단.jpg"></a></div>
    <div><a href="/longlivetheking" id="content3"><img src="/uploadedImg/main/알레르기식단.jpg"></a></div>
    <div><a href="/protine" id="content4"><img src="/uploadedImg/main/단백질식단.jpg"></a></div>
    <div><a href="/selfcare" id="content5"><img src="/uploadedImg/main/나만의식단.jpg"></a></div>
</div>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<footer>
    <jsp:include page="../common/footer.jsp"></jsp:include>
</footer>
</body>
</html>
