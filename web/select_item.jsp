<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "选择物品";
    if (mapper != null) {
        dpName = request.getParameter("dpName");
        String path = SiteConfig.getInstance().getWarLoc() + mapper;
        out = new ConstPageWriter(path, response.getWriter());
    }
%>
<script src="/js/lweb-json.js"></script>
<script src="/js/lweb-tool-format.js"></script>
<script>
    let itemTypeData;
    let nowPage = 1;
    let pages;
    let nowExData;

    function pageLoad() {
        loadData();
    }

    function loadData() {
        $.post("/api/itemType.json?action=getAll", function (data) {
            if (data.result) {
                $("#searchType").append("<option selected='selected' disabled='disabled' value='selected'></option>");
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
    }

    function loadStore() {
        $("#itemList").empty();
        $("#lb_searchTip").empty();

        const type = $("#searchType").val();
        const manu = $("#searchManu").val();
        const kw = $("#searchKW").val();
        const pageSize = $("#searchPageSize").val();
        let sExData = getExData("#searchExData input");

        $.post("/api/itemStore.json?action=search", {
            type: type,
            manu: manu,
            kw: kw,
            page: nowPage,
            pageSize: pageSize,
            exData: JSON.stringify(sExData)
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

                $("#searchTips").css("display", "block");
                $("#lb_searchTip").append("一共找到" + data.count + "条记录，当前第" + nowPage + "页，共" + pages + "页");
                $("#itemList").append("<tr><td>型号</td><td>类型</td><td>厂商</td><td>库存</td><td>平均价</td><td>附加属性</td><td>最后更新时间</td><td></td></tr>");

                for (let i = 0; i < data.data.length; i++) {
                    const item = data.data[i];
                    $("#itemList").append("<tr><td>" + item.name + "</td><td>" + item.itemType + "</td><td>" + item.manufactor + "</td><td>" + item.count + "</td><td>" + (item.price / item.count).toFixed(2) + "</td><td>" + LwebJson.jsonToString(item.exData, nowExData, "</br>") + "</td><td>" + formatUnixTime(item.upDate) + "</td><td><button onclick='itemSelect(" + JSON.stringify(item) + ")'>选择</button></td></tr>");
                }
            } else {
                alert(data.msg);
            }
        });
    }

    //物品选择
    //参数写好后会调用父页面的selectItem(item, count, price)函数
    function itemSelect(item) {
        let count;
        while (true) {
            count = prompt("数量", 1);
            if (count == null) {
                return;
            }
            else if (!isNaN(parseInt(count))) {
                count = parseInt(count);
                if (count > item.count) {
                    alert("库存不足");
                    continue;
                }
                break;
            }
        }

        let price;
        while (true) {
            price = prompt("单价", 0);
            if (price == null) {
                return;
            } else if (!isNaN(parseFloat(price))) {
                price = parseFloat(price);
                break;
            }
        }

        selectItem(item, count, price);
    }

    function btn_search() {
        nowPage = 1;
        loadStore();
    }

    /**
     * 当搜索类型改变时
     */
    function searchTypeChange() {
        nowPage = 1;
        $("#searchBtn").removeAttr("disabled");
        $("#search_isExData").prop("checked", false);
        const itemTypeSel = $("#searchType").val();

        $.post("/api/manu.json?action=getAllByItemType", {
                itemType: itemTypeSel
            }, function (data) {
                if (data.result) {
                    $("#btn_search").removeAttr("disabled");
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
                    $("#searchExData").empty();
                    nowExData = data.exData;
                    for (let i = 0; i < nowExData.length; i++) {
                        const exItem = nowExData[i];
                        $("#searchExData").append("<label>" + exItem.desc + ":<input name='" + exItem.name + "' type='" + exItem.type + "' /></label>");
                    }
                } else {
                    alert(data.msg);
                }
            }
        );
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
            loadStore();
        }
    }

    //下一页
    function btn_nextPage() {
        if (nowPage < pages) {
            nowPage++;
            loadStore();
        }
    }

    function searchPageSizeChange() {
        nowPage = 1;
    }

    function getExData(el) {
        const exData = {};
        $(el).each(function (index, el) {
            exData[$(el).attr("name")] = LwebJson.getVal(el, $(el).attr("type"));
        });
        return exData;
    }
</script>
<div>
    <div id="searchBox">
        <label>类型:<select id="searchType" onchange="searchTypeChange()"></select></label>
        <label>厂商:<select id="searchManu"></select></label>
        <input id="searchKW" type="text" placeholder="关键字"/>
        <label>每页显示<select id="searchPageSize" onchange="searchPageSizeChange()">
            <option value="20">20</option>
            <option value="30">30</option>
            <option value="50" selected>50</option>
            <option value="70">70</option>
            <option value="100">100</option>
        </select>条记录</label>
        <button id="btn_search" onclick="btn_search()" disabled>检索</button>
        <div id="searchExData" style="height: 20px">
        </div>
    </div>
    <div id="searchTips" style="display: none;">
        <label id="lb_searchTip"></label>
        <button id="btn_prePage" onclick="btn_prePage()" disabled>上一页</button>
        <button id="btn_nextPage" onclick="btn_nextPage()">下一页</button>
        <label>跳转到第<input type="number" id="search_tarPage" style="width: 50px">页
            <button onclick="jumpToPage()">跳转</button>
        </label>
    </div>
    <table id="itemList" border="1">

    </table>
</div>
<%
    out.close();
%>