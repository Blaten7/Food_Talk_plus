<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24. 8. 1.
  Time: 오후 6:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<script>
    let i = 1
    function foodTableInput(element, event) {
        if (element.value !== null && element.value !== '') {
            if (event.keyCode === 13) {
                let elem = element.value
                let inputItem = elem.split(",").map(str => str.trim());
                let fiName = inputItem[0]
                let fiCount = inputItem[1]
                let fiVolume = inputItem[2]
                let exchange = inputItem[3]
                //
                let table = document.getElementById('foodTable')
                let str = "";
                str +="<input id='selectThis"+i+"' name='"+fiName+"' onclick='thisItemDrag(this)' draggable='true' class='fiItemsInIceBox' data-count='" + fiCount + "' data-volume='" + fiVolume + "' type='text' value='" + fiName + "' data-change='" + exchange + "' " +
                    "style='border: none; width: 100px; background-color: transparent; cursor: pointer' readonly/>"
                str += "<hr class='isHr' style='width: 500px'>"
                table.innerHTML += str
                let input = $('#fiInputBox')
                input.val('')
                input.focus()
                i++

            }
        }

    }
    function thisItemDrag(v) {
        // console.log(v.value)
        // let selector = $('selectThis')
        // selector.name = v.value
    }


    function iceBoxControl(type) {
        let src = type.src
        const relativePath = src.replace(window.location.origin, '');
        const decodedPath = decodeURIComponent(relativePath);
        switch (decodedPath) {
            case "/uploadedImg/main/trade/빈냉장고정면.avif":
                console.log('냉장고 문 열기')
                $(type).attr("src", "/uploadedImg/main/trade/빈냉장고개방.avif")
                break;
            case "/uploadedImg/main/trade/빈냉장고개방.avif":
                console.log('냉장고 문 닫기')
                $(type).attr("src", "/uploadedImg/main/trade/빈냉장고정면.avif")
                break;
            default:
                console.log("냉장고 name속성 오류")
        }

    }

    function resetInput() {
        let table = $('#foodTable')
        alert('초기화 하시겠습니까?')
        table.html('')
    }
    let hiddenInput = $('#aa')
    $(document).on("dragstart", ".fiItemsInIceBox", function (e) {
        console.log("움직인다아아앗!!");
        // document.getElementById('selectThis').name = e.currentTarget.defaultValue
        console.log(hiddenInput)
        hiddenInput.name = $(this)[0].name
        hiddenInput.data = $(this)[0].id
        console.log(hiddenInput)

        let imgTag = $('.refrigeratorImg')
        let imgTagSrc = imgTag[0].currentSrc
        let imgUrl = imgTagSrc.replace(window.location.origin, '')
        let imgUrlDecoded = decodeURIComponent(imgUrl)
        console.log(imgUrlDecoded)
        const match = imgUrlDecoded.match(/\/([^\/]*)\./);
        const result = match ? match[1] : null;
        console.log(result); // "빈냉장고정면"

    });
        $(document).on("drop", "#refrigerator", function (e) {
            e.preventDefault();
            let imgTag = $('.refrigeratorImg')
            let imgTagSrc = imgTag[0].currentSrc
            let imgUrl = imgTagSrc.replace(window.location.origin, '')
            let imgUrlDecoded = decodeURIComponent(imgUrl)
            const match = imgUrlDecoded.match(/(개방|정면)/);
            const result = match ? match[0] : null;
            if (result === '개방') {
                console.log("입벌려!!!")
                console.log(hiddenInput.name)
                console.log(hiddenInput.data)
                let currentElement = document.getElementById(hiddenInput.data)
                console.log(currentElement)
                let hrElem = currentElement.closest('hr')
                console.log(hrElem)
                currentElement.remove()
            } else {
                alert('생각이란 것을 할 수 있는 뇌를 가지고 계신다면, 냉장고 문을 먼저 열어주세요!!')
            }
        });

        $(document).on("dragover", "#refrigerator", function (e) {
            e.preventDefault(); // 기본 동작 방지
        });


</script>
<body>
<header>
    <jsp:include page="../common/header.jsp"></jsp:include>
</header>
<main>
    <section style="text-align: center">
        <h1>냉장고</h1>
    </section>
    <div style="display: flex; flex-direction: row">
        <div style="width: 20%"></div>
        <div id="refrigerator">
            <img class="refrigeratorImg" style="height: 900px; display: block" onclick="iceBoxControl(this)" id="emptyRefrigeratorFront"
                 src="/uploadedImg/main/trade/빈냉장고정면.avif">
            <%--            <img style="height: 900px; display: none" name="꽉찬냉장고정면" onclick="iceBoxControl(this)" id="fullRefrigeratorFront" src="/uploadedImg/main/trade/꽉찬냉장고정면.avif">--%>
        </div>
        <div style="width: 20%; display: flex; flex-direction: column">
            <%--            <table id="foodTable">--%>
            <%--                <tr>--%>
            <%--                    <th><input type="text" onkeypress="foodTableInput(this, event)" style="width: 500px; height: 70px; border-radius: 30px" placeholder="   [식재료이름, 수량, (g, ml등) 단위, 바꾸고싶은 재료] 입력"></th>--%>
            <%--                </tr>--%>
            <%--                <tr>--%>
            <%--                    <th><span id="confirmInput">식재료이름, 수량, 단위, 교환을 원하는 식재료.<br> 전부 입력 요망</span></th>--%>
            <%--                </tr>--%>
            <%--            </table>--%>
            <button class="btn btn-danger" onclick="resetInput()">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </button>
            <input type="text" id="fiInputBox" onkeypress="foodTableInput(this, event)"
                   style="width: 500px; height: 70px; border-radius: 30px"
                   placeholder="   [식재료이름, 수량, (g, ml등) 단위, 바꾸고싶은 재료] 입력">
            <hr style="width: 500px">
            <div id="foodTable" class="foodItem">

            </div>
            <input type="hidden" style="display: none" id="aa" data="k">
        </div>
    </div>

</main>
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
