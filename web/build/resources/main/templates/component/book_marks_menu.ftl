<script>
    function assemblySubMenu (childrenFolders)
    {
        var $subMenuItem = '';

        for(var k = 0; k < childrenFolders.length; k++)
        {
            var childrenFolderVO = childrenFolders[k];
            if(childrenFolderVO.bookMarkNodeTypeEnum == 'FOLDER')
            {
                $subMenuItem += '<li class="dropdown-submenu">';
                $subMenuItem +=     '<a tabindex="0">' + childrenFolderVO.folderName + '</a>';
                $subMenuItem +=      '<ul class="dropdown-menu">';

                if(childrenFolderVO.childrenFolders.length > 0)
                    $subMenuItem += assemblySubMenu (childrenFolderVO.childrenFolders);

                $subMenuItem +=     '</ul>';
                $subMenuItem += '</li>';

                $subMenuItem += '<li class="divider"></li>';
            }
            else if(childrenFolderVO.bookMarkNodeTypeEnum == 'FILE')
            {
                $subMenuItem += '<li><a tabindex="0" href="' + childrenFolderVO.href + '" target="_blank">' + childrenFolderVO.webSite + '</a></li>';

                $subMenuItem += '<li class="divider"></li>';
            }
        }

        return $subMenuItem;
    }

    $(document).ready(function () {

        $.ajax({
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            url: "/book_marks/component/book_marks_menu",
            dataType: 'json',
            cache: false,
            async: false, // 同步
            timeout: 600000,
            success: function (folderVOs) {
                var color = ['success', 'info', 'warning', 'danger'];

                for(var i = 0; i < folderVOs.length; i++)
                {
                    var folderVO = folderVOs[i];

                    var $dropdown = '';
                    $dropdown += '<div class="dropdown" style="position: relative; float: left; margin: 5px 10px 5px 0px;">';
                    $dropdown +=    '<button class="btn btn-' + color[i % color.length] + ' dropdown-toggle" type="button" data-toggle="dropdown" data-submenu>';
                    $dropdown +=        folderVO.folderName + ' <span class="caret"></span>';
                    $dropdown +=    '</button>';
                        var childrenFolders = folderVO.childrenFolders;
                        if(childrenFolders.length > 0)
                        {
                            $dropdown += '<ul class="dropdown-menu">';

                            $dropdown += assemblySubMenu (childrenFolders);

                            $dropdown += '</ul>';
                        }
                        else
                        {
                            $dropdown += '<ul class="dropdown-menu">';
                            $dropdown += '</ul>';
                        }
                    $dropdown += '</div>';

                    $('#book_marks_menu_list').append($dropdown);
                }

            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });

        /*
         多层级下拉菜单插件
            GitHub: https://github.com/vsn4ik/bootstrap-submenu
         */
        $('[data-submenu]').submenupicker();
    });
</script>

<div class="panel panel-default">
    <div class="panel-body" id="book_marks_menu_list">

    </div>
</div>