const LwebJson = function () {
};

const typeEnmu = {
    String: "text",
    int: "number",
    boolean: "checkbox"
};

LwebJson.getSelectIndex = function (type) {
    switch (type) {
        case typeEnmu.String:
            return 1;
        case typeEnmu.int:
            return 0;
        case typeEnmu.boolean:
            return 2;
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