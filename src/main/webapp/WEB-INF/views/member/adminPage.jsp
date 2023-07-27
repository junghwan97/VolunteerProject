<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

<my:navbar current="adminPage"></my:navbar>
<my:alert></my:alert>
<div class="container-lg">
    <table class="table">
        <thead>
        <tr>
            <th>Id</th>
            <th>Nickname</th>
            <th>Tel</th>
            <th>Email</th>
            <th>증빙 자료</th>
            <th>확인</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${preMember}" var="member">
                <tr>
                    <td><a href="/member/myPage?id=${member.id}"><span class="title"> ${member.id}</span> <br></a></td>
                    <td>${member.nickName }</td>
                    <td>${member.phoneNum }</td>
                    <td>${member.email }</td>
                    <td><a href="${bucketUrl}/member/${member.id}/${member.fileName}" download>${member.fileName }</td>
                        <td><button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#giveAuthorityModal">승인</button></td>
                </tr>
                <div class="d-none">
                    <form action="/member/giveAuthority/${member.id}" method="post" id="giveAuthority">
                        <div class="d-none"><input type="form-control" name="id" value="${member.id}"></div>
                    </form>
                </div>
            </c:forEach>
        </tbody>
    </table>
</div>

<div class="modal fade" id="giveAuthorityModal" tabindex="-1" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">권한 부여</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">권한을 부여하시겠습니까?</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                <button type="submit" class="btn btn-danger" form="giveAuthority">부여</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>
