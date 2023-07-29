<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<html>
<head>
    <title>봉사활동 지원 / 공고 상세화면</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer"/>
<style>
    .table{
        border: 1px solid black;
    }

    th{
        background-color: #55A44E;
    }
    td{
        text-align: left;
    }

    #target-textarea {
        box-sizing: border-box;
        resize: none;
        /*border: 1px solid rgb(42, 42, 42);*/
        width: 100%;
        background-color: white;
        overflow: hidden;
        border: none
    }

    /*신청하기 팝업*/
    .popup-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        visibility: hidden;
        opacity: 0;
        transition: visibility 0s, opacity 0.3s ease;
    }
    .popup-content {
        width: 400px;
        padding: 20px;
        background-color: #fff;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    }
    .popup-content h2 {
        text-align: center;
        margin-top: 0;
    }
    .popup-content form {
        display: flex;
        flex-direction: column;
    }
    .popup-content input[type="text"],
    .popup-content input[type="password"],
    .popup-content input[type="email"],
    .popup-content select {
        margin-bottom: 10px;
        padding: 8px;
        border-radius: 3px;
        border: 1px solid #ccc;
    }
    .popup-content input[type="submit"] {
        padding: 8px 12px;
        border: none;
        border-radius: 3px;
        background-color: #3f51b5;
        color: #fff;
        cursor: pointer;
    }
    .popup-content input[type="submit"]:hover {
        background-color: #303f9f;
    }
    .popup-content .close-btn {
        position: absolute;
        top: 10px;
        right: 10px;
        cursor: pointer;
    }
</style>
</head>
<body>
    <my:navbar current="recruitList"></my:navbar>

    <div class="container-lg">

        <!-- .row.justify-content-center>.col-12.col-md-8.col-lg-6 -->
        <div class="row justify-content-center">
            <div class="col-12 col-md-8 col-lg-10">
                <%--            <div class="d-flex">--%>
                <div>
                    <div class="me-auto">
                        <h1>
                            <span id="recruitIdText"> ${recruit.title }</span>
                        </h1>
                    </div>
                    <div class="mb-3">
                        <label for="" class="form-label">작성일시</label>
                        <input type="text" readonly class="form-control" value="${recruit.inserted }" style="border: none"/>
                    </div>
                    <sec:authorize access="hasAuthority('volunteer')">
                        <button onclick="openPopup()">신청하기</button>
                    </sec:authorize>
                    <div id="popup" class="popup-overlay">
                        <div class="popup-content">
                            <button class="close-btn btn btn-danger" onclick="closePopup()">&times;</button>
                            <h2>봉사활동 신청하기</h2>
                            <form action="/member/applyRecruit/${member.id}" method="post">
                                <input type="text" name="title" value="${recruit.title}" readonly>
                                <input type="text" name="recruitId" value="${recruit.id}" class="d-none">
                                <input type="text" class="d-done" placeholder="아이디" name="id" value="${member.id}">
                                <input type="text" placeholder="이름" name="name" value="${member.name}">
                                <input type="email" placeholder="이메일" name="email" value="${member.email}">
                                <input type="text" placeholder="연락처" name="phoneNum" value="${member.phoneNum}">
                                <input type="text" placeholder="성별" name="gender" value="${member.gender}">
                                <input type="text" class="d-none" name="participation" value="승인 대기중">
                                <input type="submit" value="신청">
                            </form>
                        </div>
                    </div>
                    <div class="content">
                        <!-- 그림 파일 출력 -->
<%--                        <div class="mb-3">--%>
<%--                            <c:forEach items="${recruit.fileName }" var="fileName">--%>
<%--                                <div class="mb-3" style="height: 400px; width: 500px">--%>
<%--                                    <img class="img-thumbnail img-fluid" src="${bucketUrl }/recruit/${recruit.id }/${fileName}" alt=""/>--%>
<%--                                </div>--%>
<%--                            </c:forEach>--%>
<%--                        </div>--%>
                        <table class="table">
                            <tbody>
                            <tr>
                                <th>봉사장소</th>
                                <td>${recruit.writer}</td>
                                <th>봉사분야</th>
                                <td>${recruit.VField}</td>
                            </tr>
                            <tr>
                                <th>봉사기간</th>
                                <td>${recruit.VStartDate} ~ ${recruit.VEndDate}</td>
                                <th>봉사시간</th>
                                <td>${recruit.VStartTime} ~ ${recruit.VEndTime}</td>
                            </tr>
                            <tr>
                                <th>주소</th>
                                <td>${recruit.VPlace}</td>
                                <th>등록기관</th>
                                <td>${recruit.addressSggNm}</td>
                            </tr>
                            <tr>
                                <th>연락처</th>
                                <td>${recruit.tel}</td>
                                <td colspan="3"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                        <div class="mb-3" id="wrapper">
<%--                            <label for="" class="form-label">본문</label>--%>
                            <textarea class="form-control" id="target-textarea" disabled>${recruit.body }</textarea>
                        </div>
                    <hr>
                        <div id="map" style="width:100%;height:500px;"></div>
                    <hr>
                </div>

                <div>
                    <sec:authorize access="isAuthenticated()">
                        <c:if test="${member.nickName eq recruit.writer }">
                            <div>

                                <a class="btn btn-secondary" href="/recruit/modify/${recruit.id }">수정</a>
                                <button id="removeButton" class="btn btn-danger" data-bs-toggle="modal"
                                        data-bs-target="#deleteConfirmModal">삭제
                                </button>
                            </div>
                        </c:if>
                    </sec:authorize>

                    <div class="d-none">
                        <form action="/recruit/remove/${recruit.id}" method="post" id="removeForm">
                            <input type="text" name="id" value="${recruit.id }"/>
                        </form>
                    </div>

                    <div class="modal fade" id="deleteConfirmModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="exampleModalLabel">삭제 확인</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">삭제 하시겠습니까?</div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                                    <button type="submit" class="btn btn-danger" form="removeForm">삭제</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <script>
        function openPopup() {
            var popup = document.getElementById("popup");
            popup.style.visibility = "visible";
            popup.style.opacity = "1";
        }

        function closePopup() {
            var popup = document.getElementById("popup");
            popup.style.visibility = "hidden";
            popup.style.opacity = "0";
        }
    </script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bda72dd7c59b7d94fe8ae4094af53543&libraries=services"></script>
    <script>
        // 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
        var infowindow = new kakao.maps.InfoWindow({zIndex:1});

        var mapContainer = document.getElementById('map'), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
                level: 3 // 지도의 확대 레벨
            };

        // 지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        // 장소 검색 객체를 생성합니다
        var ps = new kakao.maps.services.Places();

        // 키워드로 장소를 검색합니다
        ps.keywordSearch('${recruit.writer}', placesSearchCB);

        // 키워드 검색 완료 시 호출되는 콜백함수 입니다
        function placesSearchCB (data, status, pagination) {
            if (status === kakao.maps.services.Status.OK) {

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
                // LatLngBounds 객체에 좌표를 추가합니다
                var bounds = new kakao.maps.LatLngBounds();

                for (var i=0; i<data.length; i++) {
                    displayMarker(data[i]);
                    bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
                }

                // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
                map.setBounds(bounds);
            }
        }

        // 지도에 마커를 표시하는 함수입니다
        function displayMarker(place) {

            // 마커를 생성하고 지도에 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(place.y, place.x)
            });

            // 마커에 클릭이벤트를 등록합니다
            kakao.maps.event.addListener(marker, 'click', function() {
                // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
                infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
                infowindow.open(map, marker);
            });
        }
    </script>
    <script>
        function resize() {
            let textarea = document.getElementById("target-textarea");

            textarea.style.height = "0px";

            let scrollHeight = textarea.scrollHeight;
            let style = window.getComputedStyle(textarea);
            let borderTop = parseInt(style.borderTop);
            let borderBottom = parseInt(style.borderBottom);

            textarea.style.height = (scrollHeight + borderTop + borderBottom) + "px";
        }

        window.addEventListener("load", resize);
        window.onresize = resize;
    </script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>
