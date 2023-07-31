<!DOCTYPE html>
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
    <style>
        .container-lg {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            text-align: center;
            margin-bottom: 30px;
        }

        .form-control {
            margin-bottom: 10px;
            border: 2px solid #007bff;
            border-radius: 5px;
            padding: 10px;
            font-size: 16px;
        }

        .form-control:focus {
            outline: none;
            box-shadow: 0 0 5px #007bff;
        }

        .btn-group {
            display: flex;
            flex-wrap: wrap;
            margin-bottom: 10px;
            gap: 10px;
        }

        .donation-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 100px;
            height: 50px;
            background-color: #007bff;
            color: #fff;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        #result {
            margin-bottom: 10px;
            text-align: center;
            font-size: 20px;
            font-weight: bold;
            color: #007bff;
        }

        button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<my:navbar></my:navbar>
<div class="container-lg">
    <h1>기부 페이지 </h1>

    <form method="post" action="/kakaoPay">
        <input type="text" class="form-control" name="campaignId" value="${campaignId}" readonly placeholder="캠페인 ID">
        <input type="text" class="form-control" name="campaignName" value="${campaignName}" placeholder="캠페인 이름">
        <input type="text" class="form-control" name="donor" value="${donor}" placeholder="기부자 이름">
        <div class="btn-group">
            <input type='button' id="100" onclick='count("plus")' class="donation-btn" value='100'/>
            <input type='button' id="500" onclick='count("plus")' class="donation-btn" value='500'/>
            <input type='button' id="1000" onclick='count("plus")' class="donation-btn" value='1000'/>
            <input type='button' id="5000" onclick='count("plus")' class="donation-btn" value='5000'/>
            <input type='button' id="10000" onclick='count("plus")' class="donation-btn" value='10000'/>
            <input type='button' id="50000" onclick='count("plus")' class="donation-btn" value='50000'/>
            <input type='button' id="x" onclick='count("minus")' class="donation-btn" value='X'/>
        </div>
        <input id="result" type="text" class="form-control" name="total_amount" value="0" placeholder="기부금액">
        <button>카카오페이로 결제하기</button>
    </form>
</div>
<script>
    function count(type) {
        const resultElement = document.getElementById('result');
        let number = parseInt(resultElement.value);

        if (type === 'plus') {
            const id = event.target.id;
            console.log(id)
            if (id === '100') {
                number += 100;
            } else if (id === '500') {
                number += 500;
            } else if (id === '1000') {
                number += 1000;
            } else if (id === '5000') {
                number += 5000;
            } else if (id === '10000') {
                number += 10000;
            } else if (id === '50000') {
                number += 50000;
            }
        } else if (type === 'minus') {
            number = 0;
        }

        resultElement.value = number.toString();
    }
</script>

</body>
</html>
