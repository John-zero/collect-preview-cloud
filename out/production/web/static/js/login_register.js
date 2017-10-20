function checkAccount ()
{
    var account = $('#account').val();
    if(account == null || account == "")
    {
        if($('#register_prompting').length > 0) // 注册页面
        {
            $('#register_prompting').hide();
        }

        $('#account_prompting').text('请输入登录账号');
        $('#account_scope').addClass('has-warning');
        $('#account_prompting').show();
        return false;
    }
    else
    {
        if($('#register_prompting').length > 0) // 注册页面
        {
            $("#register").prop("disabled", true);
            $.ajax({
                type: "POST",
                contentType: "application/x-www-form-urlencoded",
                url: "/check_account",
                data: {userName : account},
                dataType: 'json',
                cache: false,
                async: false, // 同步
                timeout: 600000,
                success: function (response) {
                    if(response.result == "true" || response.result == true)
                    {
                        $('#register_prompting_message').html(response.message);

                        $('#register_prompting').removeClass('alert-warning');
                        $('#register_prompting').removeClass('alert-danger');
                        $('#register_prompting').addClass('alert-success');
                        $('#register_prompting').show();
                    }
                    else
                    {
                        $('#register_prompting_message').html(response.message);

                        $('#register_prompting').removeClass('alert-warning');
                        $('#register_prompting').removeClass('alert-success');
                        $('#register_prompting').addClass('alert-danger');
                        $('#register_prompting').show();
                    }

                    $("#register").prop("disabled", false);
                },
                error: function (e) {
                    console.log("ERROR : ", e);

                    $("#register").prop("disabled", false);
                }
            });
        }

        $('#account_scope').removeClass('has-warning');
        $('#account_scope').addClass('has-success');
        $('#account_prompting').hide();
        return true;
    }
}

function checkPassword ()
{
    var password = $('#password').val();
    if(password == null || password == "")
    {
        $('#password_prompting').text('请输入登录密码');
        $('#password_scope').addClass('has-warning');
        $('#password_prompting').show();
        return false;
    }
    else
    {
        $('#password_scope').removeClass('has-warning');
        $('#password_scope').addClass('has-success');
        $('#password_prompting').hide();
        return true;
    }
}

$(document).ready(function () {
    $('#account').blur(function() {
        checkAccount();
    });
    $('#password').blur(function() {
        checkPassword();
    });
    $('#login').click(function() {
        var check_account = checkAccount ();
        if(!check_account)
            return;

        var check_password = checkPassword ();
        if(!check_password)
            return;

        $("#login").prop("disabled", true);

        $.ajax({
            type: "POST",
            contentType: "application/json;charset=utf-8",
            url: "/login",
            data: JSON.stringify({"userName" : $('#account').val(), "passWord" : $('#password').val()}),
            dataType: 'json',
            cache: false,
            async: false, // 同步
            timeout: 600000,
            success: function (response) {
                if(response.result == "true" || response.result == true)
                {
                    console.info("登录成功, 重定向到 index 首页");
                    window.location.href = response.redirect_url;
                }
                else
                {
                    $('#login_prompting_message').html(response.message);
                    $('#login_prompting').show();
                }

                $("#login").prop("disabled", false);
            },
            error: function (e) {
                console.log("ERROR : ", e);

                $("#login").prop("disabled", false);
            }
        });
    });
    $('#register').click(function() {
        var check_account = checkAccount ();
        if(!check_account)
            return;

        var check_password = checkPassword ();
        if(!check_password)
            return;

        $("#register").prop("disabled", true);

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            url: "/register",
            data: {userName : $('#account').val(), passWord : $('#password').val()},
            dataType: 'json',
            cache: false,
            async: false, // 同步
            timeout: 600000,
            success: function (response) {
                if(response.result == "true" || response.result == true)
                {
                    console.info("登录成功, 重定向到 login 登录页");
                    window.location.href = response.redirect_url;
                }
                else
                {
                    $('#register_prompting_message').html(response.message);

                    $('#register_prompting').removeClass('alert-success');
                    $('#register_prompting').removeClass('alert-danger');
                    $('#register_prompting').addClass('alert-warning');
                    $('#register_prompting').show();
                }

                $("#register").prop("disabled", false);
            },
            error: function (e) {
                console.log("ERROR : ", e);

                $("#register").prop("disabled", false);
            }
        });
    });
});