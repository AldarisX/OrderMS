<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "入库";
    if (mapper != null) {
        dpName = request.getParameter("dpName");
        String path = SiteConfig.getInstance().getWarLoc() + mapper;
        out = new ConstPageWriter(path, response.getWriter());
    }
%>
<html>
<head>
    <title>库存</title>
    <jsp:include page="head.jsp">
        <jsp:param name="title" value="<%=dpName%>"/>
    </jsp:include>
    <script>
        let itemTypeData;

        $(document).ready(function () {
            loadData();
        });

        function loadData() {
            document.getElementById("manuList").selectedIndex = -1;
            $("#itemTypeList").empty();
            $("#itemTypeList").append("<option selected='selected' disabled='disabled' value='selected'></option>");

            $.get("/store_search.jsp", function (page) {
                $("#searchBlock").append(page);
                pageLoad();
            });
        }

        function loadManuList() {
            const itemTypeSel = $("#itemTypeList").val();

            $("#exData").empty();
            const exData = itemTypeData[$("#itemTypeList").get(0).selectedIndex - 1].exData;
            for (let i = 0; i < exData.length; i++) {
                const exItem = exData[i];
                $("#exData").append("<label>" + exItem.desc + ":<input name='" + exItem.name + "' type='" + exItem.type + "' /></label>");
            }

            $("#manuList").empty();
            $("#manuList").append("<option selected='selected' disabled='disabled' value='selected'></option>");
            $.post("/api/manu.json?action=getAllByItemType", {
                    itemType: itemTypeSel
                }, function (data) {
                    if (data.result) {
                        for (let i = 0; i < data.data.length; i++) {
                            const manu = data.data[i];
                            $("#manuList").append("<option value='" + manu.id + "'>" + manu.name + "</option>");
                        }
                    } else {
                        alert(data.msg);
                    }
                }
            );
        }

        /**
         * 添加物品
         */
        function formAddItem() {
            const itemName = $(":text[name=itemName]").val();
            const itemModel = $(":text[name=itemModel]").val();
            const itemType = $("#itemTypeList").val();
            const manu = $("#manuList").val();
            const itemNum = $("[name=itemNum]").val();
            const itemPrice = $("[name=itemPrice]").val();
            const exData = getExData("#exData input");
            const desc = $("#desc").val();

            if (itemName === "" || itemType == null || manu == null || itemNum === "" || itemPrice === "") {
                alert("请认真填写数据");
                return false;
            }

            $.post("/api/itemStore.json?action=addItem", {
                name: itemName,
                itemType: itemType,
                manu: manu,
                model: itemModel,
                num: itemNum,
                price: itemPrice,
                exData: JSON.stringify(exData),
                desc: desc
            }, function (data) {
                if (data.result) {
                    alert("添加成功");
                    loadData();
                } else {
                    alert(data.msg);
                }
            });
            return false;
        }
    </script>
    <style>
        #itemList td {
            border: 1px solid #6CF;
        }
    </style>
</head>
<body>
<jsp:include page="nav.jsp"/>
<form method="post" onsubmit="return false">
    <label>型号:<input type="text" name="itemName"/></label>
    <label>名称:<input type="text" name="itemModel"></label>
    <label>类型:<select id="itemTypeList" onchange="loadManuList()">
    </select></label>
    <label>厂商:<select id="manuList">
    </select></label><br/>
    <label>数量:<input type="number" name="itemNum"/></label>
    <label>单价:<input type="number" name="itemPrice"/></label><br/>
    <label>备注:<textarea id="desc" cols="40" rows="5"></textarea></label><br/>
    <div id="exData">

    </div>
    <br>
    <button onclick="formAddItem()">添加</button>
</form>
<br/>
<div id="searchBlock">

</div>
</body>
</html>
<%
    out.close();
%>