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
    <script src="/js/highcharts.js"></script>
    <script src="/js/lweb-json.js"></script>
    <script>
        $(document).ready(function () {
            $.post("/api/itemType.json?action=getAll", function (data) {
                if (data.result) {
                    let types = data.data;
                    for (let i = 0; i < types.length; i++) {
                        let type = types[i];
                        if (type.inIndex) {
                            $("#storeList ul").append("<li><div id='store" + type.id + "' class='top_store'></div></li>");
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
                    let datas = [];
                    for (let i = 0; i < items.length; i++) {
                        let item = items[i];

                        let exData = "";
                        if (type.exData.length > 0) {
                            exData = "(" + LwebJson.jsonToString(item.exData, type.exData, " ") + ")";
                        }
                        let serie = {};
                        serie.name = item.name + exData;
                        serie.y = item.count;
                        datas.push(serie);
                    }
                    let chart = Highcharts.chart("store" + type.id, {
                        chart: {
                            type: 'pie'
                        },
                        title: {
                            text: type.name,
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.y}</b>'
                        }, plotOptions: {
                            pie: {
                                allowPointSelect: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: false
                                },
                                showInLegend: true
                            }
                        }, series: [{
                            name: '库存数量',
                            colorByPoint: true,
                            data: datas
                        }]
                    });
                } else {
                    layer.msg("获取物品库存统计失败", {icon: 5});
                }
            })
        }
    </script>
    <style>
        #storeList ul li {
            list-style: none;
            width: 20%;
            height: 10%;
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