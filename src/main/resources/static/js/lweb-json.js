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
    LwebJson.getExDataBox(el, true)
};

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

LwebJson.getInputBox = function (structure, target) {
    for (let i = 0; i < structure.length; i++) {
        const exItem = structure[i];
        switch (exItem.type) {
            case typeEnmu.option:
                let optionHtml = "<option></option>";
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

LwebJson.getExDataBox = function (structure, field) {
    let result = "";
    for (let i = 0; i < structure.length; i++) {
        result += "<div class='" + field + "'>" +
            "<label>属性:<input type='text' class='keyName' name='key' disabled value='" + structure[i].name + "' required='required'></label>" +
            "<label> 类型:<select id='exSel-" + i + "' class='keyType' onchange='LwebJson.typeChange(this)'>";
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
                let optionHtml = "<option value=''></option>";
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
                    "<lable>选项:<input class='exOptionText' type='text'/></lable>" +
                    "<label>示例:<select class='exOption'>" + optionHtml + "</select></label>" +
                    "<button onclick='LwebJson.typeOptionAdd(this)'>添加</button>" +
                    "<button onclick='LwebJson.typeOptionRemove(this)'>移除</button>" +
                    "</div>";
                break;
        }
        if (structure[i].must) {
            result += "<label>必须:<input type='checkbox' class='keyMust' checked /></label>";
        } else {
            result += "<label>必须:<input type='checkbox' class='keyMust' /></label>";
        }
        result += " <button onclick='delKey(this)'>删除</button>" +
            "</div>";
    }
    return result;
};

LwebJson.typeChange = function (el) {
    let exBlock = $(el).parent().parent().find(".exData");
    exBlock.empty();
    let exContent = "";
    switch ($(el).val()) {
        case typeEnmu.option:
            exContent = "<lable>选项:<input class='exOptionText' type='text'/></lable><label>示例:<select class='exOption'></select></label><button onclick='LwebJson.typeOptionAdd(this)'>添加</button><button onclick='LwebJson.typeOptionRemove(this)'>移除</button>";
            break;
    }
    exBlock.append(exContent);
};

LwebJson.typeOptionAdd = function (el) {
    let exOption = $(el).parent().find(".exOption");
    let exOptionText = $(el).parent().find(".exOptionText").val();
    let size = $(exOption).find("option").length;
    exOption.append("<option value='" + exOptionText + "'>" + exOptionText + "</option>");
    exOption.get(0).selectedIndex = size;
};

LwebJson.typeOptionRemove = function (el) {
    let exOption = $(el).parent().find(".exOption");
    $(exOption).find("option[value='" + exOption.val() + "']").remove();
};

LwebJson.getExStruct = function (box, field) {
    const keyList = [];
    $(box).find(field).each(function (index, el) {
        const keyObj = {};
        const name = $(el).find(".keyName").val();
        if (name !== "") {
            keyObj.name = name;
            keyObj.type = $(el).find(".keyType").val();
            keyObj.desc = $(el).find(".keyDesc").val();
            keyObj.must = $(el).find(".keyMust").is(':checked');
            switch (keyObj.type) {
                case typeEnmu.option:
                    let exOptions = [];
                    let exOption = $(el).find(".exOption");
                    $(exOption).find("option").each(function () {
                        exOptions.push($(this).val());
                    });
                    keyObj.values = exOptions;
                    break;
            }
            keyList.push(keyObj);
        }
    });
    return keyList;
};

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