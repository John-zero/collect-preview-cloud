<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>注册</title>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><!-- IE 兼容模式 -->
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1"><!-- 国产浏览器高速模式 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=yes"><!-- 确保适当的绘制和触屏缩放. user-scalable=no 可以禁用其缩放(zooming)功能 -->

    <meta name="description" content="收藏云预览 | 爬虫抓取"/>
    <meta name="author" content="John_zero"/>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- JQuery -->
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <!-- 自定义JS -->
    <script src="/js/login_register.js" defer></script>
</head>

<body>
    <nav class="navbar navbar-inverse" role="navigation" style="border-radius: 0; margin-bottom: 0;">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">收藏云预览</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">Profile</a></li>
                    <li><a href="#">Help</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">

        <div class="col-md-4 col-md-offset-4" style="margin-top: 20px;">

            <div class="alert alert-dismissible" role="alert" id="register_prompting" style="display: none;">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>提示!</strong>&nbsp;&nbsp;<span id="register_prompting_message"></span>
            </div>

            <form action="javasctipr:void();" method="post" onsubmit="" enctype="">

                <div id="account_scope" class="form-group">
                    <label class="control-label" for="account">登录账号:</label>
                    <input type="text" class="form-control" id="account" placeholder="Account">
                </div>
                <div class="alert alert-warning" role="alert" id="account_prompting" style="display: none;">提示信息...</div>

                <div id="password_scope" class="form-group">
                    <label class="control-label" for="password">登录密码:</label>
                    <input type="password" class="form-control" id="password" placeholder="Password">
                </div>
                <div class="alert alert-warning" role="alert" id="password_prompting" style="display: none;">提示信息...</div>

                <button type="button" class="btn btn-success" id="register">&nbsp;注&nbsp;&nbsp;册&nbsp;</button>
                <button type="button" class="btn btn-default" style="float: right">
                    &nbsp;有账号去登录&nbsp;
                    <span class="glyphicon glyphicon-arrow-right"></span>
                </button>

            </form>
        </div>

    </div>

    <!-- 引入 底部 -->
    <#include "/component/footer.ftl">

</body>

</html>