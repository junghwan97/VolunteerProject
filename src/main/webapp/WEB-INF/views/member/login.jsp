<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%--<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<style>
    * {
        padding: 0;
        margin: 0;
        border: none;
    }

    body {
        font-size: 14px;
        font-family: 'Roboto', sans-serif;
    }

    .login-wrapper {
        width: 400px;
        height: 350px;
        padding: 40px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        position: absolute;
        top: 40%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    .login-wrapper>h1 {
        font-size: 50px;
        color: #55A44E;
        margin-bottom: 20px;
        font-weight: bold;
    }

    #login-form>input {
        width: 100%;
        height: 48px;
        padding: 0 10px;
        box-sizing: border-box;
        margin-bottom: 16px;
        border-radius: 6px;
        background-color: #F8F8F8;
    }

    #login-form>input::placeholder {
        color: #D2D2D2;
    }

    #login-form>input[type="submit"] {
        color: #fff;
        font-size: 16px;
        background-color: #55A44E;
        margin-top: 20px;
    }

    #login-form>input[type="button"] {
        color: #fff;
        font-size: 16px;
        background-color: #55A44E;
        /* 	margin-top: 20px; */
    }

    .button-wrapper {
        display: flex;
        justify-content: space-between;
        margin-top: 10px;
    }

    .find-buttons input[type="button"] {
        margin-top: 0;
        width: calc(50% - 5px); /* 버튼 사이의 간격을 조정 */
        color: #fff;
        font-size: 16px;
        background-color: #55A44E;
    }
</style>
</head>
<body>

<div class="login-wrapper">
    <h1>Login</h1>
    <form method="post" id="login-form">
        <input id="inputUsername" type="text" name="username" placeholder="ID">
        <input id="inputPassword" type="password" name="password" placeholder="Password">
        <input type="submit" value="Login">
        <input type="button" value="Sign Up" onclick="location.href='/member/signup'" />
<%--        <div class="find-buttons">--%>
<%--            <input type="button" value="아이디 찾기" onclick="location.href='/member/userSearch '" />--%>
<%--            <input type="button" value="임시 비밀번호 발급" onclick="location.href='/member/sendTempPw'" />--%>
<%--        </div>--%>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>