package com.everygamer.fake;

import net.sf.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class MakerFake {
    final Calendar fakeDate = Calendar.getInstance();

    HashMap<String, String> gpuList = new HashMap<>();

    ArrayList<JSONObject> exDataList = new ArrayList<>();
    ArrayList<Integer> manuList = new ArrayList<>();
    JSONObject exData0 = new JSONObject();
    JSONObject exData1 = new JSONObject();
    JSONObject exData2 = new JSONObject();
    JSONObject exData3 = new JSONObject();

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        MakerFake fakerData = new MakerFake();
        fakerData.config();
        fakerData.startTest();
    }

    private void startTest() {
        for (int i = 0; i < 10; i++) {
            Thread thr = new Thread(new FakeThr());
            thr.setName("FakeThread " + i);
            thr.start();
        }
    }

    private long getUnixTime() {
        synchronized (fakeDate) {
            long time = fakeDate.getTimeInMillis() / 1000;
            fakeDate.add(Calendar.MILLISECOND, getRD(5 * 1000, 60 * 1000));
            if (fakeDate.get(Calendar.HOUR_OF_DAY) > 20) {
                fakeDate.add(Calendar.DAY_OF_MONTH, 1);
                fakeDate.set(Calendar.HOUR_OF_DAY, 10);
            }
            return time;
        }
    }

    private void config() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/orderms?stringtype=unspecified", "aldaris", "0000");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select max(ins_date) from item_list");
        if (rs.next()) {
            fakeDate.set(Calendar.MILLISECOND, rs.getInt(1) * 1000);
        } else {
            fakeDate.set(2017, Calendar.DECEMBER, 2, 10, 0);
        }
        rs.close();
        st.close();
        conn.close();
        System.out.println(fakeDate.getTime());

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
        //GTX690
        gpuList.put("GTX690-4G", "GTX690");
        gpuList.put("GTX690-4G-OC", "GTX690");
        gpuList.put("GTX690-4G-Game", "GTX690");
        //GTX680
        gpuList.put("GTX680-4G", "GTX680");
        gpuList.put("GTX680-4G-OC", "GTX680");
        gpuList.put("GTX680-4G-Game", "GTX680");
        gpuList.put("GTX680-2G", "GTX680");
        gpuList.put("GTX680-2G-OC", "GTX680");
        gpuList.put("GTX680-2G-Game", "GTX680");
        //GTX670
        gpuList.put("GTX670-4G", "GTX670");
        gpuList.put("GTX670-4G-OC", "GTX670");
        gpuList.put("GTX670-4G-Game", "GTX670");
        gpuList.put("GTX670-2G", "GTX670");
        gpuList.put("GTX670-2G-OC", "GTX670");
        gpuList.put("GTX670-2G-Game", "GTX670");
        //GTX660
        gpuList.put("GTX660-4G", "GTX660");
        gpuList.put("GTX660-4G-OC", "GTX660");
        gpuList.put("GTX660-4G-Game", "GTX660");
        gpuList.put("GTX660-2G", "GTX660");
        gpuList.put("GTX660-2G-OC", "GTX660");
        gpuList.put("GTX660-2G-Game", "GTX660");
    }

    private int getRD(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    class FakeThr implements Runnable {
        @Override
        public void run() {
            try (
                    Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/orderms?stringtype=unspecified", "aldaris", "0000")
            ) {
                ArrayList<String> gpuNameList = new ArrayList<>(gpuList.keySet());
                for (int j = 0; j < 10000; j++) {
                    int rdCount = getRD(10, 100);
                    int rdPrice = getRD(1500, 3000);

                    String name = gpuNameList.get(getRD(0, gpuList.keySet().size()));
                    String model = gpuList.get(name);
                    int manu = manuList.get(getRD(0, manuList.size()));

                    String exData = exDataList.get(getRD(0, exDataList.size())).toString();
                    String sql = "INSERT INTO item_list (name, item_type, manufactor, model, count, remain, price, ex_data, ins_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    pst.setInt(2, 1);
                    pst.setInt(3, manu);
                    pst.setString(4, model);
                    pst.setInt(5, rdCount);
                    pst.setInt(6, rdCount);
                    pst.setInt(7, rdPrice);
                    pst.setString(8, exData);
                    pst.setInt(9, (int) getUnixTime());

                    pst.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finish");
        }
    }
}
