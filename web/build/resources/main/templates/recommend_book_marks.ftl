<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>推荐书签</title>
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
    <!-- 弹窗插件 http://layer.layui.com/ -->
    <script src="/plugins/layer-v3.0.3/layer/layer.js"></script>

    <!-- 多层级下拉菜单插件 -->
    <link rel="stylesheet" href="/plugins/bootstrap-submenu-2.0.4/dist/css/bootstrap-submenu.min.css">
    <script src="/plugins/bootstrap-submenu-2.0.4/dist/js/bootstrap-submenu.min.js" defer></script>

</head>

<body>

    <!-- 引入 头部 -->
    <#include "/component/head.ftl">

    <!-- 引入 书签 -->
    <#include "/component/book_marks_menu.ftl">

    <!-- 路径导航 -->
    <div class="panel panel-default">
        <div class="panel-body">
            <ol class="breadcrumb" style="padding: 8px 15px; margin-bottom: 0px;">
                <li><a href="/">首页</a></li>
                <li><a href="javascript:void();">功能</a></li>
                <li class="active">推荐书签</li>
            </ol>
        </div>
    </div>

    <div class="panel panel-default">

        <div class="panel-body">

            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="col-md-1">大分类标签</th>
                    <th class="col-md-1">小分类标签</th>
                    <th class="col-md-3">站点/博客</th>
                    <th class="col-md-1">文章数量</th>
                    <th class="col-md-6">推荐描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <span class="glyphicon glyphicon-folder-open"></span>&nbsp;技术
                    </td>
                    <td>Java</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <!-- 引入 底部 -->
    <#include "/component/footer.ftl">

</body>

</html>