const LwebJson = function () {
};

const typeEnmu = {
    String: "text",
    int: "number",
    boolean: "checkbox",
    option: "option"
};

LwebJson.getSelectIndex = function (type) {
    switch (type) {
        case typeEnmu.String:
            return 1;
        case typeEnmu.int:
            return 0;
        case typeEnmu.boolean:
            return 2;
        case typeEnmu.option:
            return 3;
        default:
            return -1;
    }
};

/**
 * 将数据格式化成字符串
 * @param json 数据
 * @param structure 数据结构
 * @param division 分隔符
 * @returns {string} 字符串
 */
LwebJson.jsonToString = function (json, structure, division) {
    let text = "";
    if (structure != null) {
        for (let j = 0; j < structure.length; j++) {
            const exItem = structure[j];
            switch (exItem.type) {
                case typeEnmu.boolean:
                    if (json[exItem.name]) {
                        text += exItem.desc + division;
                    }
                    break;
                default:
                    text += exItem.desc + ":" + json[exItem.name] + division;
                    break;
            }
        }
    }
    text = text.substring(0, text.lastIndexOf(division));
    return text;
};

LwebJson.getExData = function (el) {
    LwebJson.getExData(el, true)
};

/**
 * 获取数据结构
 * @param el 数据结构的表单
 * @param must 是否必须按照必须条件
 */
LwebJson.getExData = function (el, must) {
    let exData = {};
    $(el).each(function (index, el) {
        let val = LwebJson.getVal(el, $(el).attr("type"));
        if (val !== undefined && val !== "") {
            exData[$(el).attr("name")] = val;
        }
        if ((val === undefined || val === "") && $(el).attr("must") === "true" && must) {
            exData = null;
            return null;
        }
    });
    return exData;
};

/**
 * 将数据结构格式化成选项
 * @param structure 结构
 * @param target 目标
 */
LwebJson.getInputBox = function (structure, target) {
    for (let i = 0; i < structure.length; i++) {
        const exItem = structure[i];
        switch (exItem.type) {
            case typeEnmu.option:
                let optionHtml = "";
                for (let j = 0; j < exItem.values.length; j++) {
                    optionHtml += "<option value='" + exItem.values[j] + "'>" + exItem.values[j] + "</option>";
                }
                $(target).append("<label>" + exItem.desc + ":<select class='exBlock' name='" + exItem.name + "' type='option' must='" + exItem.must + "'>" + optionHtml + "</select></label>");
                break;
            default:
                $(target).append("<label>" + exItem.desc + ":<input class='exBlock' name='" + exItem.name + "' type='" + exItem.type + "' must='" + exItem.must + "'/></label>");
                break;
        }
    }
};

/**
 * 生成编辑数据结构的表单
 * @param structure 数据结构
 * @param field 字段class
 * @returns {string} 数据结构的表单
 */
LwebJson.getExDataBox = function (structure, field) {
    let result = "";
    for (let i = 0; i < structure.length; i++) {
        result += "<div class='" + field + "'>";
        if (structure[i].must) {
            result += "<label>必须:<input type='checkbox' class='keyMust' checked /></label>";
        } else {
            result += "<label>必须:<input type='checkbox' class='keyMust' /></label>";
        }
        result += "<label>优先级:<input type='number' class='keyOrder' value='" + structure[i].order + "' /></label>";
        result += "<label>属性:<input type='text' class='keyName' name='key' disabled value='" + structure[i].name + "' required='required'></label>";
        result += "<label> 类型:<select id='exSel-" + i + "' class='keyType' onchange='LwebJson.typeChange(this)'>";
        switch (structure[i].type) {
            case typeEnmu.boolean:
                result += "<option value='text'>字符串</option>" +
                    "<option value='number'>数值</option>" +
                    "<option value='checkbox' selected>布尔值</option>" +
                    "<option value='option'>选项</option>" +
                    "</select></label>" +
                    "<label> 说明:<input type='text' class='keyDesc' name='value' value='" + structure[i].desc + "' required='required' placeholder='字段描述'></label>" +
                    "<div class='exData'></div>";
                break;
            case typeEnmu.int:
                result += "<option value='text'>字符串</option>" +
                    "<option value='number' selected>数值</option>" +
                    "<option value='checkbox'>布尔值</option>" +
                    "<option value='option'>选项</option>" +
                    "</select></label>" +
                    "<label> 说明:<input type='text' class='keyDesc' name='value' value='" + structure[i].desc + "' required='required' placeholder='字段描述'></label>" +
                    "<div class='exData'></div>";
                break;
            case typeEnmu.String:
                result += "<option value='text' selected>字符串</option>" +
                    "<option value='number'>数值</option>" +
                    "<option value='checkbox'>布尔值</option>" +
                    "<option value='option'>选项</option>" +
                    "</select></label>" +
                    "<label> 说明:<input type='text' class='keyDesc' name='value' value='" + structure[i].desc + "' required='required' placeholder='字段描述'></label>" +
                    "<div class='exData'>" +
                    "</div>";
                break;
            case typeEnmu.option:
                let optionHtml = "";
                for (let j = 0; j < structure[i].values.length; j++) {
                    optionHtml += "<option value='" + structure[i].values[j] + "'>" + structure[i].values[j] + "</option>";
                }
                result += "<option value='text'>字符串</option>" +
                    "<option value='number'>数值</option>" +
                    "<option value='checkbox'>布尔值</option>" +
                    "<option value='option' selected>选项</option>" +
                    "</select></label>" +
                    "<label> 说明:<input type='text' class='keyDesc' name='value' value='" + structure[i].desc + "' required='required' placeholder='字段描述'></label>" +
                    "<div class='exData'>" +
                    "<lable> 选项:<input class='exOptionText' type='text'/></lable>" +
                    "<label> 排序:<select class='exDataOrder'>";
                if (structure[i].valOrder === "ASC") {
                    result += "<option>无</option><option value='ASC' selected>升序</option><option value='DESC'>降序</option>";
                } else if (structure[i].valOrder === "DESC") {
                    result += "<option>无</option><option value='ASC'>升序</option><option value='DESC' selected>降序</option>";
                } else {
                    result += "<option selected>无</option><option value='ASC'>升序</option><option value='DESC'>降序</option>";
                }
                result += "</select></label><button onclick='LwebJson.typeOptionAdd(this)'>添加</button>" +
                    "<button onclick='LwebJson.typeOptionRemove(this)'>移除</button>" +
                    "<label> 示例:<select class='exOption'>" + optionHtml + "</select></label>" +
                    "</div>";
                break;
        }
        result += " <button onclick='delKey(this)'>删除</button>" +
            "</div>";
    }
    return result;
};

/**
 * 当编辑数据结构中的数据类型改变时
 * @param el 元素
 */
LwebJson.typeChange = function (el) {
    let exBlock = $(el).parent().parent().find(".exData");
    exBlock.empty();
    let exContent = "";
    switch ($(el).val()) {
        case typeEnmu.option:
            exContent = "<lable> 选项:<input class='exOptionText' type='text'/></lable>" +
                "<label> 排序:<select class='exDataOrder'><option>无</option><option value='ASC'>升序</option><option value='DESC'>降序</option></select></label>" +
                "<button onclick='LwebJson.typeOptionAdd(this)'>添加</button>" +
                "<button onclick='LwebJson.typeOptionRemove(this)'>移除</button>" +
                "<label> 示例:<select class='exOption'></select></label>";
            break;
    }
    exBlock.append(exContent);
};

/**
 * 数据类型-选项的添加方法
 * @param el  元素
 */
LwebJson.typeOptionAdd = function (el) {
    let exOption = $(el).parent().find(".exOption");
    let exOptionText = $(el).parent().find(".exOptionText").val();
    let size = $(exOption).find("option").length;
    exOption.append("<option value='" + exOptionText + "'>" + exOptionText + "</option>");
    exOption.get(0).selectedIndex = size;
};

LwebJson.addExStruct = function (target, field) {
    $(target).append("<div class='" + field + "'>" +
        "<label>必须:<input type='checkbox' class='keyMust' /></label>" +
        "<label>优先级:<input type='number' class='keyOrder' value='10' /></label>" +
        "<label>属性:<input type='text' class='keyName' name='key' required='required'" +
        " placeholder='英文, 不重'></label>" +
        "<label> 类型:<select class='keyType'  onchange='LwebJson.typeChange(this)'>" +
        "<option value='text'>字符串</option>" +
        "<option value='number'>数值</option>" +
        "<option value='checkbox' selected>布尔值</option>" +
        "<option value='option'>选项</option>" +
        "</select></label>" +
        "<label> 说明:<input type='text' class='keyDesc' name='value' required='required' placeholder='字段描述'></label>" +
        "<div class='exData'></div>" +
        " <button onclick='delKey(this)'>删除</button>" +
        "</div>");
};

/**
 * 数据类型-选项的移除方法
 * @param el  元素
 */
LwebJson.typeOptionRemove = function (el) {
    let exOption = $(el).parent().find(".exOption");
    $(exOption).find("option[value='" + exOption.val() + "']").remove();
};

/**
 * 获取编辑数据结构的数据结构数据
 * @param box 表单
 * @param field  元素class
 * @returns {Array} 数据结构
 */
LwebJson.getExStruct = function (box, field) {
    const keyList = [];
    $(box).find(field).each(function (index, el) {
        const keyObj = {};
        const name = $(el).find(".keyName").val();
        if (name !== "") {
            keyObj.name = name;
            keyObj.order = $(el).find(".keyOrder").val();
            keyObj.type = $(el).find(".keyType").val();
            keyObj.desc = $(el).find(".keyDesc").val();
            keyObj.must = $(el).find(".keyMust").is(':checked');
            switch (keyObj.type) {
                case typeEnmu.option:
                    keyObj.valOrder = $(el).find(".exDataOrder").val();
                    let exOptions = [];
                    let exOption = $(el).find(".exOption");
                    $(exOption).find("option").each(function () {
                        exOptions.push($(this).val());
                    });
                    //对选项排序
                    if (keyObj.valOrder === "ASC") {
                        exOptions.sort();
                    } else if (keyObj.valOrder === "DESC") {
                        exOptions.sort().reverse();
                    }
                    keyObj.values = exOptions;
                    break;
            }
            keyList.push(keyObj);
        }
    });
    //排序
    LwebJson.sortObject(keyList, "ASC", "order");
    return keyList;
};

/**
 * 获取数据的值
 * @param el 元素
 * @param type 数据类型
 * @returns {*} 数据值
 */
LwebJson.getVal = function (el, type) {
    switch (type) {
        case typeEnmu.int:
            let num = parseInt($(el).val());
            if (isNaN(num)) {
                return undefined;
            }
            return parseInt($(el).val());
        case typeEnmu.boolean:
            return $(el).is(':checked');
        default:
            return $(el).val();
    }
};

/**
 * 排序 数据结构
 * @param obj 目标json
 * @param order 排序方式
 * @param field 目标属性
 */
LwebJson.sortObject = function (obj, order, field) {
    if (order !== null) {
        let asc = function (x, y) {
            return (x[field] > y[field]) ? 1 : -1
        };
        let desc = function (x, y) {
            return (x[field] < y[field]) ? 1 : -1
        };
        let targetOrder = asc;
        if (order === "DESC") {
            targetOrder = desc;
        }
        return obj.sort(targetOrder);
    }
};