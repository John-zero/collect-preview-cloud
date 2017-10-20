<script>
    $(document).ready(function () {
        $('#head_search_book_marks').bind('keydown',function(event) {
            if(event.keyCode == "13")
            {
                console.info("头部搜索");
            }
        });
    });
</script>

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

                <#if Session.session_user?exists>
                    <li class="dropdown" style="margin: 0px;">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${Session.session_user.userName} <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#">我的信息</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">我的收藏</a></li>
                        </ul>
                    </li>
                </#if>

                <li class="dropdown" style="margin: 0px;">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <span class="glyphicon glyphicon-fire" aria-hidden="true"></span> Function <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="/book_marks/_import">Import BookMarks</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="/book_marks/_recommend_book_marks">Recommend BookMarks</a></li>
                    </ul>
                </li>
                <li><a href="javascript:void();">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" id="head_search_book_marks" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>
