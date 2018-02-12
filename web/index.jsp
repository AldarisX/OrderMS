<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "主页";
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
    <script src="/js/Chart.bundle.min.js"></script>
    <script src="/js/lweb-json.js"></script>
    <script>
        $(document).ready(function () {
            $.post("/api/itemType.json?action=getAll", function (data) {
                if (data.result) {
                    let types = data.data;
                    for (let i = 0; i < types.length; i++) {
                        let type = types[i];
                        if (type.inIndex) {
                            $("#storeList ul").append("<li>" + type.name + ":<canvas class='top_store store" + type.id + "' height='40vh' width='80vw'></canvas></li>");
                            loadStoreList(type);
                        }
                    }
                } else {
                    layer.msg("获取物品分类失败", {icon: 5});
                }
            }).fail(function () {
                layer.msg("请求物品分类失败", {icon: 5});
            });
        });

        function loadStoreList(type) {
            $.post("/api/itemStore.json?action=getTop", {
                type: type.id,
                count: 3
            }, function (data) {
                if (data.result) {
                    let items = data.data;
                    if (items.length > 0) {
                        let labels = [];
                        let bgColor = [];
                        let borderColor = [];
                        let datas = [];
                        for (let i = 0; i < items.length; i++) {
                            let item = items[i];

                            let r = Math.floor(-Math.pow(((32 / (6 * 2)) * i), 2) + 255);
                            let g = Math.floor(-Math.pow(((32 / 6) * i - Math.sqrt(255)), 2) + 255);
                            let b = Math.floor(-Math.pow(((32 / (6 * 4)) * i - Math.sqrt(255)), 2) + 255);
                            bgColor.push("rgba(" + r + "," + g + "," + b + ",0.2)");
                            borderColor.push("rgba(" + r + "," + g + "," + b + ",1)");

                            let exData = "";
                            if (type.exData.length > 0) {
                                exData = "(" + LwebJson.jsonToString(item.exData, type.exData, " ") + ")";
                            }
                            labels.push(item.name + exData);
                            datas.push(item.count);
                        }
                        let ctx = $(".store" + type.id).get(0).getContext("2d");
                        let chart = new Chart(ctx, {
                            type: 'pie',
                            data: {
                                labels: labels,
                                datasets: [{
                                    label: type.name,
                                    data: datas,
                                    backgroundColor: bgColor,
                                    borderColor: borderColor,
                                    borderWidth: 1,
                                    hoverBorderWidth: 2
                                }]
                            },
                            option: {}
                        });
                    } else {
                        $(".store" + type.id).parent().append("没有数据");
                    }
                } else {
                    layer.msg("获取物品库存统计失败", {icon: 5});
                }
            })
        }
    </script>
    <style>
        #storeList ul li {
            list-style: none;
            width: 33%;
            height: 25%;
            min-width: 630px;
            min-height: 350px;
            float: left;
        }
    </style>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div id="storeList">
    <p>库存最大</p>
    <ul>

    </ul>
</div>
<div id="outList">

</div>
</body>
</html>
<%
    out.close();
%>