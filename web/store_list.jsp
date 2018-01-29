<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "入库记录";
    if (mapper != null) {
        dpName = request.getParameter("dpName");
        String path = SiteConfig.getInstance().getWarLoc() + mapper;
        out = new ConstPageWriter(path, response.getWriter());
    }
%>
<html>
<head>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="<%=dpName%>"/>
    </jsp:include>
    <script src="/js/lweb-tool-format.js"></script>
    <script src="/js/lweb-json.js"></script>
    <script>
        let itemTypeData;
        let showExData = false;
        let nowPage = 1;
        let pages;
        let nowExData;

        $(document).ready(function () {
            $("#startDate").val(new Date().format("yyyy-MM-dd"));
            $("#endDate").val(new Date().format("yyyy-MM-dd"));

            $.post("/api/itemType.json?action=getAll", function (data) {
                if (data.result) {
                    $("#searchType").append("<option></option>");
                    itemTypeData = data.data;
                    for (let i = 0; i < data.data.length; i++) {
                        const itemType = data.data[i];
                        $("#searchType").append("<option value='" + itemType.id + "'>" + itemType.name + "</option>");
                    }
                } else {
                    alert(data.msg);
                }
            });

            $.post("/api/manu.json?action=getAll", function (data) {
                    if (data.result) {
                        $("#searchManu").empty();
                        $("#searchManu").append("<option selected='selected' value=''></option>");
                        for (let i = 0; i < data.data.length; i++) {
                            const manu = data.data[i];
                            $("#searchManu").append("<option value='" + manu.id + "'>" + manu.name + "</option>");
                        }
                    } else {
                        alert(data.msg);
                    }
                }
            );
        });

        function loadStoreList() {
            $("#storeList").empty();
            $("#searchTip").empty();
            $(".searchTips").css("display", "none");

            const type = $("#searchType").val();
            const manu = $("#searchManu").find("option:selected").text();
            const pageSize = $("#searchPageSize").val();
            let sExData = getExData("#searchExDataBox input");
            let startTime = toUnixTime($("#startDate").val());
            let endTime = toUnixTime($("#endDate").val(), 1);
            if (!showExData) {
                sExData = "";
            }
            if (isNaN(startTime)) {
                startTime = "";
            }
            if (isNaN(endTime)) {
                endTime = "";
            }

            $.post("/api/itemStore.json?action=listStore", {
                type: type,
                kw: $("#searchKW").val(),
                manu: manu,
                page: nowPage,
                pageSize: pageSize,
                exData: JSON.stringify(sExData),
                startTime: startTime,
                endTime: endTime
            }, function (data) {
                if (data.result) {
                    if (nowPage === pages) {
                        $("#btn_nextPage").attr("disabled", "");
                    }
                    if (nowPage < pages) {
                        $("#btn_nextPage").removeAttr("disabled");
                    }
                    if (nowPage === 1) {
                        $("#btn_prePage").attr("disabled", "");
                    }
                    if (nowPage > 1) {
                        $("#btn_prePage").removeAttr("disabled");
                    }
                    pages = data.pages;

                    $(".searchTips").css("display", "block");
                    $("#searchTip").append("一共找到" + data.count + "条记录，当前第" + nowPage + "页，共" + pages + "页");

                    $("#storeList").append("<tr><td>ID</td><td>型号</td><td>类型</td><td>厂商</td><td>数量</td><td>剩余</td><td>单价</td><td>附加属性</td><td>备注</td><td>插入时间</td><td>操作</td></tr>");
                    for (let i = 0; i < data.data.length; i++) {
                        let item = data.data[i];
                        let exData = "";
                        if (showExData) {
                            exData = LwebJson.jsonToString(item.exData, nowExData, "</br>");
                        }
                        $("#storeList").append("<tr><td>" + item.id + "</td><td>" + item.name + "</td><td>" + item.itemType + "</td><td>" + item.manufactor + "</td><td>" + item.count + "</td><td>" + item.remain + "</td><td>" + item.price + "</td><td>" + exData + "</td><td>" + item.desc + "</td><td>" + formatUnixTime(item.insDate) + "</td><td><button onclick='delItem(" + item.id + ")'>删除</button></td></tr>");
                    }
                } else {
                    alert(data.msg);
                }
            })
        }

        function delItem(id) {
            $.post("/api/itemStore.json?action=delItem", {
                id: id
            }, function (data) {
                if (data.result) {

                } else {
                    alert(data.msg);
                }
            })
        }

        function jumpToPage() {
            const tarPage = $("#search_tarPage").val();
            if (tarPage >= 1 && tarPage <= pages) {
                nowPage = tarPage;
                loadStore();
            } else {
                alert("页数不合理");
            }
        }

        //上一页
        function btn_prePage() {
            if (nowPage > 1) {
                nowPage--;
                loadStoreList();
            }
        }

        //下一页
        function btn_nextPage() {
            if (nowPage < pages) {
                nowPage++;
                loadStoreList();
            }
        }

        function getExData(el) {
            const exData = {};
            $(el).each(function (index, el) {
                exData[$(el).attr("name")] = LwebJson.getVal(el, $(el).attr("type"));
            });
            return exData;
        }

        /**
         * 当搜索类型改变时
         */
        function searchTypeChange() {
            nowPage = 1;
            $("#searchBtn").removeAttr("disabled");
            $("#search_isExData").prop("checked", false);
            search_isExDataChange();

            const itemTypeSel = $("#searchType").val();
            if (itemTypeSel === "") {
                $("#searchManu").empty();
            } else {
                $.post("/api/manu.json?action=getAllByItemType", {
                        itemType: itemTypeSel
                    }, function (data) {
                        if (data.result) {
                            $("#searchManu").empty();
                            $("#searchManu").append("<option selected='selected' value=''></option>");
                            for (let i = 0; i < data.data.length; i++) {
                                const manu = data.data[i];
                                $("#searchManu").append("<option value='" + manu.id + "'>" + manu.name + "</option>");
                            }
                        } else {
                            alert(data.msg);
                        }
                    }
                );

                $.post("/api/itemType.json?action=getExData", {
                        type: itemTypeSel
                    }, function (data) {
                        if (data.result) {
                            nowExData = data.exData;
                            if (data.exData.length > 0) {
                                $("#searchExData").css("display", "block");
                            }
                        } else {
                            alert(data.msg);
                        }
                    }
                );
            }
        }

        function search_isExDataChange() {
            $("#searchExDataBox").empty();
            if ($("#search_isExData").is(':checked')) {
                showExData = true;
                for (let i = 0; i < nowExData.length; i++) {
                    const exItem = nowExData[i];
                    $("#searchExDataBox").append("<label>" + exItem.desc + ":<input name='" + exItem.name + "' type='" + exItem.type + "' /></label>");
                }
                $("#searchExDataBox").css("display", "block");
            } else {
                showExData = false;
                $("#searchExDataBox").css("display", "none");
            }
        }

        function searchPageSizeChange() {
            nowPage = 1;
        }
    </script>
    <style>
        .searchBlock {
            height: 80px;
        }

        .searchTips {
            display: none;
        }

        #search_tarPage {
            width: 50px;
        }

        #storeList td {
            max-width: 450px;
        }
    </style>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div>
    <div class="searchBlock">
        <span>时间范围&nbsp;&nbsp;</span>
        <label>开始日期:<input id="startDate" type="date"/></label>
        <label>结束日期:<input id="endDate" type="date"/></label>
        <label>类型:<select id="searchType" onchange="searchTypeChange()"></select></label>
        <label>厂商:<select id="searchManu"></select></label>
        <input id="searchKW" type="text" placeholder="关键字"/>
        <label>每页显示<select id="searchPageSize" onchange="searchPageSizeChange()">
            <option value="20">20</option>
            <option value="30">30</option>
            <option value="50">50</option>
            <option value="70">70</option>
            <option value="100">100</option>
        </select>条记录</label>
        <button onclick="loadStoreList()">检索</button>
        <div id="searchExData" style="display: none">
            <label>附加数据:<input id="search_isExData" type="checkbox" onchange="search_isExDataChange()"></label>
            <div id="searchExDataBox" style="display: none"></div>
        </div>
    </div>
    <div class="searchTips">
        <div id="searchTip">

        </div>
        <div>
            <button id="btn_prePage" onclick="btn_prePage()" disabled>上一页</button>
            <button id="btn_nextPage" onclick="btn_nextPage()">下一页</button>
            <label>跳转到第<input type="number" id="search_tarPage">页
                <button onclick="jumpToPage()">跳转</button>
            </label>
        </div>

    </div>
    <div>
        <table id="storeList" border="1">

        </table>
    </div>
</div>
</body>
</html>
<%
    out.close();
%>