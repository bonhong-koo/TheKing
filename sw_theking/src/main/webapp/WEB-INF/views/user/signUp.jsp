<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    //DOM����(HTML)������ �ε尡 �Ϸ�Ǹ� ����!
    document.addEventListener("DOMContentLoaded", function(){
        console.log("DOMContentLoaded");
        
        //���� ��ư �̺�Ʈ ó��
        $("#sendBtn").on("click",function(){
            console.log("sendBtn click");
            //alert("sendBtn click");
            
            asyncSend();
        });
        
        //�Լ�
        function asyncSend(){
            
            $.ajax({
                type:"POST",     //GET/ POST
                url:"/ehr/user/doSave.do", //������URL
                asyn:"true",     //�񵿱�
                dataType:"html", //�������� ���� ������ Ÿ��
                data:{           //�Ķ����
                    "userId": $("#userId").val(),
                    "password": $("#password").val(),
                    "name": $("#name").val(),
                    "nickname": $("#nickname").val(),
                    "email": $("#email").val(),
                    "mobile": $("#mobile").val(),
                    "address": $("#address").val()
                },
                success:function(response){ //��û����
                    console.log("success:"+response);
                    var result_str = response;
                    $("#result").html(result_str);
                },
                error:function(response){ //��û ����
                    console.log("error:"+response)
                }
                
            });
        }
        
    });
</script>

    <h1>signUpPage</h1>
    <hr>
    
    <form action="asyncForm">
        <label for="userId">���̵�</label>
        <input type="text" name="userId" id="userId">
        <label for="password">��й�ȣ</label>
        <input type="password" name="password" id="password">
        <label for="name">�̸�</label>
        <input type="text" name="name" id="name">
        <label for="nickname">�г���</label>
        <input type="text" name="nickname" id="nickname">
        <label for="email">�̸���</label>
        <input type="email" name="email" id="email">
        <label for="mobile">��ȭ��ȣ</label>
        <input type="tel" name="mobile" id="mobile">
        <label for="address">�ּ�</label>
        <input type="text" name="address" id="address">
        <input type="button" id="sendBtn" value="����">
    </form>
    <div id="result"></div>
</body>
</html>