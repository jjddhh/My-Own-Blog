let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
    },

    save: function () {
        //alert('user의 save함수 호출됨')
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        //console.log(data);

        $.ajax({
            type: "POST",
            url: "/api/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8", // body 데이터 타입
            dataType: "json" // 응답 받을 타입
        }).done(function (resp) {
            alert("회원가입이 완료되었습니다.");
            console.log(resp);
            location.href= "/";
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    }
}

index.init();