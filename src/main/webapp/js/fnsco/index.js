$(document).ready(function () {
    var menu = $('#side-menu');
    // 组包发给后台
    $.ajax({
        type: 'POST',
        url: PROJECT_NAME + "/web/auth/menu/userMenuist",
        async: false,//同步加载
        success: function (data) {
            if (data.success) {
                var menuList = data.data;
                for (var i = 0; i < menuList.length; i++) {
                    if (menuList[i].type == 0) {
                        var menuP = menuList[i];
                        var editStr = "<li class=''>" +
                            "<a href='#'>" +
                            "<i class='" + menuP.icon + "'></i>" +
                            "<span class='nav-label'>" + menuP.name + "</span><span class='fa arrow'></span>" +
                            "</a><ul class='nav nav-second-level collapse' aria-expanded='true' style='height: 0px;'>"

                        for (var j = 0; j < menuList.length; j++) {
                            if (menuList[j].parentId == menuP.id) {
                                editStr = editStr + "<li>" +
                                    "<a class='J_menuItem' href=" + menuList[j].url + " data-index=" + j + " id=menu_" + menuList[j].id + ">" + menuList[j].name +
                                    "</a></li>";
                            }
                        }
                        editStr = editStr + "</ul></li>";
                        menu.append(editStr);
                    }
                }
            } else if (!data.success) {
                layer.msg(data.message);
            }
        },
        error: function (data) {
            layer.msg("操作失败");
        }
    });

    //隐藏掉菜单管理页面
//	$('#menu_69').hide();
});