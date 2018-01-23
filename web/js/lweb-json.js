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

LwebJson.getVal = function (el, type) {
    switch (type) {
        case typeEnmu.boolean:
            return $(el).is(':checked');
        default:
            return $(el).val();
    }
};