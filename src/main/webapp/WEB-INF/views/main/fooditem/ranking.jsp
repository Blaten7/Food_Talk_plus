<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24. 7. 29.
  Time: 오후 3:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header>
    <jsp:include page="../common/header.jsp"></jsp:include>
</header>
<script>
</script>
<main>
    <h1 style="font-size: large; margin-top: 10px; margin-left: 400px">HOME > 베스트</h1>
    <div style="margin-top: 50px;">
        <div style="border: 3px solid #77b347; width: 55%; height: 150px; border-radius: 30px;margin-left: 400px">
            <table class="table table-borderless">
                <tr>
                    <th>
                        <button style="width: 85px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            전체
                        </button>
                    </th>
                    <th>
                        <button style="width: 100px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            메인요리/반찬
                        </button>
                    </th>
                    <th>
                        <button style="width: 85px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            그리팅 추천
                        </button>
                    </th>
                    <th>
                        <button style="width: 85px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            국/탕/찌개
                        </button>
                    </th>
                    <th>
                        <button style="width: 85px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            밥/면/죽
                        </button>
                    </th>
                    <th>
                        <button style="width: 140px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            간편식/샐러드/간식
                        </button>
                    </th>
                    <th></th>
                </tr>
                <tr>
                    <th></th>
                    <th>
                        <button style="width: 85px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            식재료
                        </button>
                    </th>
                    <th>
                        <button style="width: 110px; height: 35px; border: 1px solid lightgray; border-radius: 40px; margin-left: 50px; margin-top: 20px">
                            반찬 정기구독
                        </button>
                    </th>
                </tr>
            </table>
        </div>
        <div style="width: 55%;margin-left: 400px;margin-top: 20px">
            <a href="#"><img src="/uploadedImg/main/MainPage/건강한%20식재료%20장보기.jpg"></a>
            <div style="display: flex; flex-direction: row; width: 100%; text-align: center">
                <div style="flex-grow: 1; margin-right: 0.5%">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <!-- 1, 4, 7, 10번째 항목만 처리 -->
                        <c:if test="${(status.index % 3) == 0}">
                            <p style="font-size: x-large; font-weight: bold; text-align: center; margin-top: 30px">
                                Best${status.index + 1}
                            </p>
                            <a href="/fooditem/detail?f_num=${item.f_num}">
                                <img src="${item.IList[0].i_path}${item.IList[0].i_sys_name}"
                                     style="display: block; margin: 0 auto; height: 225px; width: 225px;"
                                     onerror="this.onerror=null; this.src='/uploadedImg/main/noImg.png';">
                            </a>
                            <input type="text" value="${item.f_title}"
                                   style="text-decoration: none; border: none; text-align: center; color: gray;"
                                   draggable="false">
                        </c:if>
                    </c:forEach>
                </div>
                <div style="flex-grow: 1; margin-right: 0.5%; text-align: center">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <!-- 2, 5, 8, 11번째 항목만 처리 -->
                        <c:if test="${(status.index + 1) % 3 == 2}">
                            <p style="font-size: x-large; font-weight: bold; text-align: center; margin-top: 30px">
                                Best${status.index + 1}
                            </p>
                            <a href="/fooditem/detail?f_num=${item.f_num}">
                                <img src="${item.IList[0].i_path}${item.IList[0].i_sys_name}"
                                     style="display: block; margin: 0 auto; height: 225px; width: 225px;"
                                     onerror="this.onerror=null; this.src='/uploadedImg/main/noImg.png';">
                            </a>
                            <input type="text" value="${item.f_title}"
                                   style="text-decoration: none; border: none; text-align: center; color: gray;"
                                   draggable="false">
                        </c:if>
                    </c:forEach>
                </div>
                <div style="flex-grow: 1; margin-right: 0.5%; text-align: center">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <!-- 3, 6, 9, 12번째 항목만 처리 -->
                        <c:if test="${(status.index + 1) % 3 == 0}">
                            <p style="font-size: x-large; font-weight: bold; text-align: center; margin-top: 30px">
                                Best${status.index + 1}
                            </p>
                            <a href="/fooditem/detail?f_num=${item.f_num}">
                                <img src="${item.IList[0].i_path}${item.IList[0].i_sys_name}"
                                     style="display: block; margin: 0 auto; height: 225px; width: 225px;"
                                     onerror="this.onerror=null; this.src='/uploadedImg/main/noImg.png';">
                            </a>

                            <input type="text" value="${item.f_title}"
                                   style="text-decoration: none; border: none; text-align: center; color: gray;"
                                   draggable="false">
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</main>
<footer>
    <jsp:include page="../common/footer.jsp"></jsp:include>
</footer>
</body>
</html>
