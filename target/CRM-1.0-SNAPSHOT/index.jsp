<%--
  Created by IntelliJ IDEA.
  User: cthwmh
  Date: 2020/12/5
  Time: 9:15
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() +
            "://" +
            request.getServerName() +
            ":" +
            request.getServerPort() +
            request.getContextPath() +
            "/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">
        $(function () {
            //日历控件
            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });
            //查询（分页，多条件）
            $("#queryBtn").click(function () {
                $("#hidden-name").val($.trim($("#search-name").val()));
                $("#hidden-owner").val($.trim($("#search-owner").val()));
                $("#hidden-startDate").val($.trim($("#search-startDate").val()));
                $("#hidden-endDate").val($.trim($("#search-endDate").val()));
                //分页刷新
                pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                    , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
            })
            //分页的灵活控制
            // $("#activityPage").bs_pagination({
            //     currentPage: pageNo, // 页码
            //     rowsPerPage: pageSize, // 每页显示的记录条数
            //     maxRowsPerPage: 20, // 每页最多显示的记录条数
            //     totalPages: pages, // 总页数
            //     totalRows: data.pageTotal, // 总记录条数
            //
            //     visiblePageLinks: 3, // 显示几个卡片
            //
            //     showGoToPage: true,
            //     showRowsPerPage: true,
            //     showRowsInfo: true,
            //     showRowsDefaultInfo: true,
            //
            //     onChangePage: function (event, data) {
            //         pageList(data.currentPage, data.rowsPerPage);
            //     }
            // });
            //全选与全不选
            $("#checkAll").click(function () {
                $("input[name=check]").prop("checked", this.checked);
                // $("input[name=check]").on("click",function () {//绑定事件
                //     $("#checkAll").prop("checked",$("input[name=check]").length==$("input[name=check]:checked").length);
                // })
            })
            $("#tBody").on("click", $("input[name=check]"), function () {
                $("#checkAll").prop("checked", $("input[name=check]").length == $("input[name=check]:checked").length);
            })

        })

        //分页查询函数
        function pageList(pageNo, pageSize) {
            $("#checkAll").prop("checked", false);
            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $("#search-endDate").val($.trim($("#hidden-endDate").val()));
            $.ajax({
                url: "workbench/Activity/pageList.do",
                type: "get",
                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "name": $.trim($("#hidden-name").val()),
                    "owner": $.trim($("#hidden-owner").val()),
                    "startDate": $.trim($("#hidden-startDate").val()),
                    "endDate": $.trim($("#hidden-startDate").val()),
                },
                dataType: "json",
                success: function (data) {
                    var html = "";
                    $.each(data.dataList, function (i, e) {
                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="check" value="' + e.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id=' + e.id + '\';">' + e.name + '</a></td>';
                        html += '<td>' + e.owner + '</td>';
                        html += '<td>' + e.startDate + '</td>';
                        html += '<td>' + e.endDate + '</td>';
                        html += '</tr>';
                    })
                    $("#tBody").html(html);

                    var pages = data.pageTotal % pageSize == 0 ? data.pageTotal / pageSize : parseInt(data.pageTotal / pageSize) + 1;
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: pages, // 总页数
                        totalRows: data.pageTotal, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }
            })
        }
    </script>
</head>
<body>
<div class="form-group">
    <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
    <div class="col-sm-10" style="width: 300px;">
        <input type="text" class="form-control time" readonly id="create-startDate">
    </div>
    <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
    <div class="col-sm-10" style="width: 300px;">
        <input type="text" class="form-control time" readonly id="create-endDate">
    </div>
</div>
    <div id="activityPage">

    </div>

</body>
</html>
