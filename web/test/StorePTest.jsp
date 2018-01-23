<%@ page import="com.everygamer.service.StoreService" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="org.springframework.context.SpringContextHolder" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    int getRD(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
%>
<%
    HashMap<String, String> gpuList = new HashMap<>();

    ArrayList<JSONObject> exDataList = new ArrayList<>();
    JSONObject exData0 = new JSONObject();
    JSONObject exData1 = new JSONObject();
    JSONObject exData2 = new JSONObject();
    JSONObject exData3 = new JSONObject();

    ArrayList<Integer> manuList = new ArrayList<>();

    exData0.accumulate("gb", false);
    exData0.accumulate("rgb", false);

    exData1.accumulate("gb", true);
    exData1.accumulate("rgb", true);

    exData2.accumulate("gb", true);
    exData2.accumulate("rgb", false);

    exData3.accumulate("gb", false);
    exData3.accumulate("rgb", true);

    exDataList.add(exData0);
    exDataList.add(exData1);
    exDataList.add(exData2);
    exDataList.add(exData3);

    manuList.add(1);
    manuList.add(2);
    manuList.add(4);
    manuList.add(5);
    manuList.add(6);
    manuList.add(7);
    manuList.add(8);
    manuList.add(9);
    manuList.add(10);
    manuList.add(11);

    //RX480
    gpuList.put("RX480-6G", "RX480");
    gpuList.put("RX480-6G-Game", "RX480");
    gpuList.put("RX480-6G-OC", "RX480");
    gpuList.put("RX480-8G", "RX480");
    gpuList.put("RX480-8G-Game", "RX480");
    gpuList.put("RX480-8G-OC", "RX480");
    //RX470
    gpuList.put("RX470-8G", "RX470");
    gpuList.put("RX470-8G-Game", "RX470");
    gpuList.put("RX470-8G-OC", "RX470");
    gpuList.put("RX470-6G", "RX470");
    gpuList.put("RX470-6G-Game", "RX470");
    gpuList.put("RX470-6G-OC", "RX470");
    //RX460
    gpuList.put("RX460-6G", "RX460");
    gpuList.put("RX460-6G-Game", "RX460");
    gpuList.put("RX460-4G", "RX460");
    gpuList.put("RX460-4G-Game", "RX460");
    //R9-360
    gpuList.put("R9-360-4G", "R9-360");
    gpuList.put("R9-360-4G-Game", "R9-360");
    gpuList.put("R9-360-4G-OC", "R9-360");
    //R9-370
    gpuList.put("R9-370-4G", "R9-370");
    gpuList.put("R9-370-4G-Game", "R9-370");
    gpuList.put("R9-370-4G-OC", "R9-370");
    gpuList.put("R9-370-6G", "R9-370");
    gpuList.put("R9-370-6G-Game", "R9-370");
    gpuList.put("R9-370-6G-OC", "R9-370");
    //R9-380
    gpuList.put("R9-380-6G", "R9-380");
    gpuList.put("R9-380-6G-Game", "R9-380");
    gpuList.put("R9-380-6G-OC", "R9-380");

    //GTX1080
    gpuList.put("GTX1080-8G", "GTX1080");
    gpuList.put("GTX1080-8G-OC", "GTX1080");
    gpuList.put("GTX1080-8G-Game", "GTX1080");
    //GTX1070
    gpuList.put("GTX1070-8G", "GTX1070");
    gpuList.put("GTX1070-8G-OC", "GTX1070");
    gpuList.put("GTX1070-8G-Game", "GTX1070");
    //GTX1060
    gpuList.put("GTX1060-6G", "GTX1060");
    gpuList.put("GTX1060-6G-OC", "GTX1060");
    gpuList.put("GTX1060-6G-Game", "GTX1060");
    gpuList.put("GTX1060-4G", "GTX1060");
    gpuList.put("GTX1060-4G-OC", "GTX1060");
    gpuList.put("GTX1060-4G-Game", "GTX1060");
    //GTX980
    gpuList.put("GTX980-6G", "GTX980");
    gpuList.put("GTX980-6G-OC", "GTX980");
    gpuList.put("GTX980-6G-Game", "GTX980");
    //GTX970
    gpuList.put("GTX970-6G", "GTX970");
    gpuList.put("GTX970-6G-OC", "GTX970");
    gpuList.put("GTX970-6G-Game", "GTX970");
    //GTX960
    gpuList.put("GTX960-4G", "GTX960");
    gpuList.put("GTX960-4G-OC", "GTX960");
    gpuList.put("GTX960-4G-Game", "GTX960");
    //GTX780
    gpuList.put("GTX780-6G", "GTX780");
    gpuList.put("GTX780-6G-OC", "GTX780");
    gpuList.put("GTX780-6G-Game", "GTX780");
    //GTX770
    gpuList.put("GTX770-4G", "GTX770");
    gpuList.put("GTX770-4G-OC", "GTX770");
    gpuList.put("GTX770-4G-Game", "GTX770");
    //GTX760
    gpuList.put("GTX760-4G", "GTX760");
    gpuList.put("GTX760-4G-OC", "GTX760");
    gpuList.put("GTX760-4G-Game", "GTX760");
    gpuList.put("GTX760-2G", "GTX760");
    gpuList.put("GTX760-2G-OC", "GTX760");
    gpuList.put("GTX760-2G-Game", "GTX760");

    StoreService storeService = SpringContextHolder.getBean("StoreService");
    ArrayList<String> gpuNameList = new ArrayList<>(gpuList.keySet());
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10000; j++) {
            int rdCount = getRD(10, 100);
            int rdPrice = getRD(1500, 3000);

            String name = gpuNameList.get(getRD(0, gpuList.keySet().size()));
            String model = gpuList.get(name);
            int manu = manuList.get(getRD(0, manuList.size()));

            String exData = exDataList.get(getRD(0, exDataList.size())).toString();

            storeService.addItem(name, 2, manu, model, rdCount, BigDecimal.valueOf(rdPrice), exData, null);
//            storeService.addItem("RX480-6G", 5, 6, "RX480", rdCount, rdPrice);
        }
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
