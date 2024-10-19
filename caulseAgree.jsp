<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 24. 7. 5.
  Time: 오후 3:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>이용약관</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        h2 {
            margin-top: 20px;
        }
        p {
            text-align: justify;
            margin-bottom: 15px;
        }
        .checkbox-container {
            margin-bottom: 15px;
        }
        .checkbox-label {
            display: block;
            margin-bottom: 5px;
        }
        .checkbox-label input[type="checkbox"] {
            margin-right: 5px;
        }
        #agree-all {
            margin-bottom: 10px;
        }
        #agree-all label {
            font-weight: bold;
        }
        .btn-container {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>이용약관 동의</h1>

    <div class="checkbox-container">
        <h2>필수 동의 항목</h2>
        <label class="checkbox-label">
            <input type="checkbox" class="agree-essential">
            [필수] 이용약관
            <div class="terms-box">
                <p>그리팅몰 서비스 및 제품(이하'서비스')을 이용해 주셔서 감사합니다.</p>
            </div>
        </label>
        <label class="checkbox-label">
            <input type="checkbox" class="agree-essential">
            [필수] 개인정보 수집 및 이용
            <div class="terms-box">
                <p>개인정보보호법에 따라 그리팅몰에 회원가입 신청하시는 분께 수집하는 개인정보의 항목,
                개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간, 동의 거부권 및 동의 거부 시 불이익에
                관한 사항을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.</p>
            </div>
        </label>
        <label class="checkbox-label">
            <input type="checkbox" class="agree-essential">
            [필수] 실명 인증된 아이디로 가입
            <div class="terms-box">
                <p>실명 인증된 아이디로 가입하시면 본인증이 필요한 서비스를 가입 후 바로 이용하실 수 있어요.</p>
            </div>
        </label>
        <label class="checkbox-label">
            <input type="checkbox" class="agree-essential">
            [선택] 위치기반서비스 이용약관
        </label>
        <label class="checkbox-label">
            <input type="checkbox" class="agree-essential">
            [필수] 개인정보 수집 및 이용
        </label>

        <h2>선택 동의 항목</h2>
        <label class="checkbox-label">
            <input type="checkbox" class="agree-optional">
            선택 동의 항목
        </label>
    </div>

    <div id="agree-all">
        <label>
            <input type="checkbox" id="agree-all-checkbox">
            모든 항목에 동의합니다. (필수 및 선택 동의)
        </label>
    </div>

    <div class="btn-container">
        <button onclick="checkAgreement()">다음 페이지로 이동</button>
        <button onclick="home()">홈으로</button>
    </div>
</div>
<footer>
<%--    <jsp:include page="..common/footer.jsp"></jsp:include>--%>
</footer>
<script>
    function checkAgreement() {
        var agreeEssentialCheckboxes = document.querySelectorAll('.agree-essential');
        var agreeOptionalCheckbox = document.querySelector('.agree-optional');
        var agreeAllCheckbox = document.getElementById('agree-all-checkbox');

        var allEssentialChecked = true;

        // 필수 동의 항목 중 하나라도 체크되지 않은 경우
        for (var i = 0; i < agreeEssentialCheckboxes.length; i++) {
            if (!agreeEssentialCheckboxes[i].checked) {
                allEssentialChecked = false;
                break;
            }
        }

        // 필수 동의 항목 중 하나라도 체크되지 않았을 때
        if (!allEssentialChecked) {
            alert('필수 동의 항목에 모두 동의해야 다음 페이지로 이동할 수 있습니다.');
            return;
        }

        // 모든 동의 항목에 동의한 경우
        if (agreeAllCheckbox.checked || allEssentialChecked) {
            alert('모든 항목에 동의하셨습니다.');
            // 여기에 실제 다음 페이지로 이동하는 코드를 추가할 수 있습니다.
            location.href="/member/join"
        } else {
            alert('모든 항목에 동의해야 다음 페이지로 이동할 수 있습니다.');
        }
    }
    // 전체 동의 체크박스 클릭 시 필수 및 선택 동의 체크박스 모두 선택/해제
    document.getElementById('agree-all-checkbox').addEventListener('click', function() {
        var checked = this.checked;
        var agreeEssentialCheckboxes = document.querySelectorAll('.agree-essential');
        var agreeOptionalCheckbox = document.querySelector('.agree-optional');

        // 필수 동의 항목 체크박스 모두 선택/해제
        for (var i = 0; i < agreeEssentialCheckboxes.length; i++) {
            agreeEssentialCheckboxes[i].checked = checked;
        }

        // 선택 동의 항목 체크박스 선택/해제
        if (agreeOptionalCheckbox) {
            agreeOptionalCheckbox.checked = checked;
        }
    });

    // 필수 동의 및 선택 동의 체크박스를 클릭할 때 전체 동의 체크박스 상태 변경
    var agreeEssentialCheckboxes = document.querySelectorAll('.agree-essential');
    var agreeOptionalCheckbox = document.querySelector('.agree-optional');
    var agreeAllCheckbox = document.getElementById('agree-all-checkbox');

    for (var i = 0; i < agreeEssentialCheckboxes.length; i++) {
        agreeEssentialCheckboxes[i].addEventListener('click', updateAgreeAllCheckbox);
    }

    if (agreeOptionalCheckbox) {
        agreeOptionalCheckbox.addEventListener('click', updateAgreeAllCheckbox);
    }

    function updateAgreeAllCheckbox() {
        var allChecked = true;

        // 필수 동의 체크 여부 확인
        for (var i = 0; i < agreeEssentialCheckboxes.length; i++) {
            if (!agreeEssentialCheckboxes[i].checked) {
                allChecked = false;
                break;
            }
        }

        // 선택 동의 체크 여부 확인
        if (!agreeOptionalCheckbox.checked) {
            allChecked = false;
        }

        // 전체 동의 체크박스 상태 변경
        agreeAllCheckbox.checked = allChecked;
    }
    function home(){
        location.href="/"
    }
</script>
</body>
</html>