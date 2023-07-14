<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Title</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"
        integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw=="
        crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>

    <h1> kakaoPay api 이용하기 </h1>

    <form method="post" action="/kakaoPay">
        <input type="text" class="form-control" name="campaignId" value="${campaignId}" readonly>
        <input type="text" class="form-control" name="campaignName" value="${campaignName}" />
        <input type="text" class="form-control" name="donor" value="${donor}" />
        <input type="text" class="form-control" name="total_amount" value="${total_amount}" placeholder="기부금액" />
      <button>카카오페이로 결제하기</button>
    </form>

<%--    <script>--%>
<%--      $("#check_module").click(function () {--%>
<%--        var IMP = window.IMP; // 생략가능--%>
<%--        IMP.init('가맹점식별코드');--%>
<%--        // i'mport 관리자 페이지 -> 내정보 -> 가맹점식별코드--%>
<%--        // ''안에 띄어쓰기 없이 가맹점 식별코드를 붙여넣어주세요. 안그러면 결제창이 안뜹니다.--%>
<%--        IMP.request_pay({--%>
<%--          pg: 'kakao',--%>
<%--          pay_method: 'card',--%>
<%--          merchant_uid: 'merchant_' + new Date().getTime(),--%>
<%--          /*--%>
<%--           *  merchant_uid에 경우--%>
<%--           *  https://docs.iamport.kr/implementation/payment--%>
<%--           *  위에 url에 따라가시면 넣을 수 있는 방법이 있습니다.--%>
<%--           */--%>
<%--          name: '주문명 : 아메리카노',--%>
<%--          // 결제창에서 보여질 이름--%>
<%--          // name: '주문명 : ${auction.a_title}',--%>
<%--          // 위와같이 model에 담은 정보를 넣어 쓸수도 있습니다.--%>
<%--          amount: 2000,--%>
<%--          // amount: ${bid.b_bid},--%>
<%--          // 가격--%>
<%--          buyer_name: '이름',--%>
<%--          // 구매자 이름, 구매자 정보도 model값으로 바꿀 수 있습니다.--%>
<%--          // 구매자 정보에 여러가지도 있으므로, 자세한 내용은 맨 위 링크를 참고해주세요.--%>
<%--          buyer_postcode: '123-456',--%>
<%--        }, function (rsp) {--%>
<%--          console.log(rsp);--%>
<%--          if (rsp.success) {--%>
<%--            var msg = '결제가 완료되었습니다.';--%>
<%--            msg += '결제 금액 : ' + rsp.paid_amount;--%>
<%--            // success.submit();--%>
<%--            // 결제 성공 시 정보를 넘겨줘야한다면 body에 form을 만든 뒤 위의 코드를 사용하는 방법이 있습니다.--%>
<%--            // 자세한 설명은 구글링으로 보시는게 좋습니다.--%>
<%--          } else {--%>
<%--            var msg = '결제에 실패하였습니다.';--%>
<%--            msg += '에러내용 : ' + rsp.error_msg;--%>
<%--          }--%>
<%--          alert(msg);--%>
<%--        });--%>
<%--      });--%>
<%--    </script>--%>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</body>
</html>
