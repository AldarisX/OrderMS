<%@ page import="cn.misakanet.site.SiteConfig" %>
<%@ page import="cn.misakanet.tool.ConstPageWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mapper = request.getParameter("mapper");
    String dpName = "物品类型";
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
    <script src="/js/lweb-json.js"></script>
    <script src="/js/lweb-tool-format.js"></script>
    <script>
        let isEdit = false;
        let editItem = -1;
        $(document).ready(function () {
            loadData();
        });

        function loadData() {
            $("#itemTypeList").empty();
            $("#itemTypeList").append("<tr><td>ID</td><td>物品类型</td><td>添加时间</td><td>额外数据</td><td>操作</td></tr>");
            $.post("/api/itemType.json?action=getAll", function (data) {
                if (data.result) {
                    for (let i = 0; i < data.data.length; i++) {
                        const itemType = data.data[i];
                        let exData = "";
                        for (let j = 0; j < itemType.exData.length; j++) {
                            exData += itemType.exData[j].desc + "<br>";
                        }
                        $("<tr><td>" + itemType.id + "</td><td>" + itemType.name + "</td><td>" + formatUnixTime(itemType.insDate) + "</td><td>" + exData + "</td><td><button onclick='editItemType(" + itemType.id + ")'>编辑</button><button onclick='delItemType(" + itemType.id + ")'>删除</button></td></tr>").appendTo("#itemTypeList");
                    }
                } else {
                    alert(data.msg);
                }
            });
        }

        function editItemType(itemType) {
            isEdit = true;
            editItem = itemType;
            $.post("/api/itemType.json?action=getItemTypeById", {
                id: itemType
            }, function (data) {
                if (data.result) {
                    $("select[id^='exSel-']").parent().parent().remove();

                    $(":text[name=itemType]").val(data.data.name);
                    const exDatas = data.data.exData;
                    if (exDatas !== "") {
                        $("#isExData").find(":checkbox[name=isExData]").attr("checked", "true");
                        $("#exData").css("display", "block");
                        for (let i = 0; i < exDatas.length; i++) {
                            const exStr = "<div class='mui-textfield'>" +
                                "<label>属性:<input type='text' class='keyName' name='key' disabled value='" + exDatas[i].name + "' required='required'></label>" +
                                "<label> 类型:<select id='exSel-" + i + "' class='keyType'>" +
                                "<option value='text'>字符串</option>" +
                                "<option value='number'>数值</option>" +
                                "<option value='checkbox' selected>布尔值</option>" +
                                "</select></label>" +
                                "<label> 说明:<input type='text' class='keyDesc' name='value' value='" + exDatas[i].desc + "' required='required' placeholder='字段描述'></label>" +
                                " <button onclick='delKey(this)'>删除</button>" +
                                "</div>";
                            $("#keysBox").prepend(exStr);
                            $("#exSel-" + i).get(0).selectedIndex = LwebJson.getSelectIndex(exDatas[i].type);
                        }
                    } else {
                        $("#isExData").find(":checkbox[name=isExData]").removeAttr("checked");
                        $("#exData").css("display", "none");
                    }
                } else {
                    alert(data.msg);
                }
            })
        }

        function delItemType(id) {
            if (confirm("确定删除？")) {
                $.post("/api/itemType.json?action=delItemType", {
                    id: id
                }, function (data) {
                    if (data.result) {
                        loadData();
                    } else {
                        alert(data.msg);
                    }
                });
            }
        }

        function postItemType() {
            const itemType = $(":text[name=itemType]").val();
            let exData = getExData();
            if (!$("#isExData").find(":checkbox[name=isExData]").is(':checked')) {
                exData = "";
            }
            let action = "addItemType";
            if (isEdit) {
                action = "updateItemType&id=" + editItem;
                alert("请耐心等待");
            }
            if (itemType.length > 0) {
                $.post("/api/itemType.json?action=" + action, {
                    name: itemType,
                    exData: JSON.stringify(exData)
                }, function (data) {
                    if (data.result) {
                        loadData();
                        alert(data.msg);
                    } else {
                        alert(data.msg);
                    }
                })
            } else {
                alert("请认真填写");
            }
            return false;
        }

        function isExDataChange() {
            if ($("#isExData").find(":checkbox[name=isExData]").is(':checked')) {
                $("#exData").css("display", "block");
            } else {
                $("#exData").css("display", "none");
            }
        }

        function addKey() {
            $("#keysBox").append("<div class='mui-textfield'>" +
                "<label>属性:<input type='text' class='keyName' name='key' required='required'" +
                " placeholder='无法修改,英文, 不要重复'></label>" +
                "<label> 类型:<select class='keyType'>" +
                "<option value='text'>字符串</option>" +
                "<option value='number'>数值</option>" +
                "<option value='checkbox' selected>布尔值</option>" +
                "</select></label>" +
                "<label> 说明:<input type='text' class='keyDesc' name='value' required='required' placeholder='字段描述'></label>" +
                " <button onclick='delKey(this)'>删除</button>" +
                "</div>");
        }

        function delKey(event) {
            $(event).parent().remove();
        }

        function getExData() {
            const keyList = [];
            $("#keysBox").find(".mui-textfield").each(function (index, el) {
                const keyObj = {};
                const name = $(el).find(".keyName").val();
                if (name !== "") {
                    keyObj.name = name;
                    keyObj.type = $(el).find(".keyType").val();
                    keyObj.desc = $(el).find(".keyDesc").val();
                    keyList.push(keyObj);
                }
            });
            console.log(keyList);
            return keyList;
        }
    </script>
</head>
<body>
<jsp:include page="nav.jsp"/>
<div>
    <form action="/api/itemType.json?action=addItemType" method="post" onsubmit="return postItemType()">
        <label>物品类型:<input type="text" name="itemType" placeholder="CPU...硬盘...显示器"/></label><br/>

        <div id="isExData">
            <label>附加数据<input name="isExData" type="checkbox" onchange="isExDataChange()"/></label>
        </div>
        <div id="exData" style="display: none">
            <div id="keysBox">
                <div class="mui-textfield">
                    <label>属性:<input type="text" class="keyName" name="key" placeholder="无法修改,英文, 不要重复"/></label>
                    <label>类型:<select class="keyType">
                        <option value="text">字符串</option>
                        <option value="number">数值</option>
                        <option value="checkbox" selected>布尔值</option>
                    </select></label>
                    <label>说明:<input type="text" class="keyDesc" name="value" placeholder="字段描述"></label>
                    <button onclick="delKey(this)">删除</button>
                </div>
            </div>
            <button id="btn_addKey" type="button" onclick="addKey()">添加字段</button>
            <br>
        </div>

        <button>保存</button>
    </form>
    <table id="itemTypeList" border="1">
    </table>
</div>
</body>
</html>
<%
    out.close();
%>
