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
            fakeDate.add(Calendar.MILLISECOND, getRD(20 * 1000, 80 * 1000));
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
            fakeDate.set(2018, Calendar.FEBRUARY, 2, 10, 0);
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
        gpuList.put("RX480", "RX480");
        gpuList.put("RX480-Game", "RX480");
        gpuList.put("RX480-OC", "RX480");
        //RX470
        gpuList.put("RX470", "RX470");
        gpuList.put("RX470-Game", "RX470");
        gpuList.put("RX470-OC", "RX470");
        //RX460
        gpuList.put("RX460", "RX460");
        gpuList.put("RX460-OC", "RX460");
        gpuList.put("RX460-Game", "RX460");
        //R9-360
        gpuList.put("R9-360", "R9-360");
        gpuList.put("R9-360-Game", "R9-360");
        gpuList.put("R9-360-OC", "R9-360");
        //R9-370
        gpuList.put("R9-370", "R9-370");
        gpuList.put("R9-370-Game", "R9-370");
        gpuList.put("R9-370-OC", "R9-370");
        //R9-380
        gpuList.put("R9-380", "R9-380");
        gpuList.put("R9-380-Game", "R9-380");
        gpuList.put("R9-380-OC", "R9-380");

        //GTX1080
        gpuList.put("GTX1080", "GTX1080");
        gpuList.put("GTX1080-OC", "GTX1080");
        gpuList.put("GTX1080-Game", "GTX1080");
        //GTX1070
        gpuList.put("GTX1070", "GTX1070");
        gpuList.put("GTX1070-OC", "GTX1070");
        gpuList.put("GTX1070-Game", "GTX1070");
        //GTX1060
        gpuList.put("GTX1060", "GTX1060");
        gpuList.put("GTX1060-OC", "GTX1060");
        gpuList.put("GTX1060-Game", "GTX1060");
        //GTX980
        gpuList.put("GTX980", "GTX980");
        gpuList.put("GTX980-OC", "GTX980");
        gpuList.put("GTX980-Game", "GTX980");
        //GTX970
        gpuList.put("GTX970", "GTX970");
        gpuList.put("GTX970-OC", "GTX970");
        gpuList.put("GTX970-Game", "GTX970");
        //GTX960
        gpuList.put("GTX960", "GTX960");
        gpuList.put("GTX960-OC", "GTX960");
        gpuList.put("GTX960-Game", "GTX960");
        //GTX780
        gpuList.put("GTX780", "GTX780");
        gpuList.put("GTX780-OC", "GTX780");
        gpuList.put("GTX780-Game", "GTX780");
        //GTX770
        gpuList.put("GTX770", "GTX770");
        gpuList.put("GTX770-OC", "GTX770");
        gpuList.put("GTX770-Game", "GTX770");
        //GTX760
        gpuList.put("GTX760", "GTX760");
        gpuList.put("GTX760-OC", "GTX760");
        gpuList.put("GTX760-Game", "GTX760");
        //GTX690
        gpuList.put("GTX690", "GTX690");
        gpuList.put("GTX690-OC", "GTX690");
        gpuList.put("GTX690-Game", "GTX690");
        //GTX680
        gpuList.put("GTX680", "GTX680");
        gpuList.put("GTX680-OC", "GTX680");
        gpuList.put("GTX680-Game", "GTX680");
        //GTX670
        gpuList.put("GTX670", "GTX670");
        gpuList.put("GTX670-OC", "GTX670");
        gpuList.put("GTX670-Game", "GTX670");
        //GTX660
        gpuList.put("GTX660", "GTX660");
        gpuList.put("GTX660-OC", "GTX660");
        gpuList.put("GTX660-Game", "GTX660");
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

                    JSONObject exData = JSONObject.fromObject(exDataList.get(getRD(0, exDataList.size())).toString());
                    exData.accumulate("mem", getRD(2, 8));
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
                    pst.setString(8, exData.toString());
                    pst.setInt(9, (int) getUnixTime());

                    pst.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " finish");
        }
    }

    private int getRD(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
}
