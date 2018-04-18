package com.everygamer.fake;

import com.everygamer.bean.BaseItem;
import lombok.Cleanup;

import java.sql.*;

public class StaticUpdate {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        try (
                Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/orderms?stringtype=unspecified", "aldaris", "0000")
        ) {
            conn.setAutoCommit(false);
            @Cleanup
            PreparedStatement pst = conn.prepareStatement("SELECT name,item_type,manufactor,model,sum(count) AS count,sum(count*price) AS price,ex_data,max(ins_date) AS up_date FROM item_list WHERE remain>0 GROUP BY name,item_type,manufactor,model,ex_data", ResultSet.TYPE_FORWARD_ONLY, ResultSet.FETCH_FORWARD);
            pst.setFetchSize(100);
//            pst.setMaxRows(100);
            @Cleanup
            ResultSet rs = pst.executeQuery();
            int checkCount = 0;
            while (rs.next()) {
                BaseItem item = packItem(rs);
                checkStatis(item);
                checkCount++;
                if (checkCount % 100 == 0) {
                    System.out.println(checkCount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static BaseItem packItem(ResultSet rs) throws SQLException {
        BaseItem item = new BaseItem();
        item.setName(rs.getString("name"));
        item.setItemType(rs.getInt("item_type") + "");
        item.setManufactor(rs.getInt("manufactor") + "");
        item.setModel(rs.getString("model"));
        item.setCount(rs.getInt("count"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setExData(rs.getString("ex_data"));
        item.setUpDate(rs.getInt("up_date") + "");
        return item;
    }

    public static void checkStatis(BaseItem item) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/orderms?stringtype=unspecified", "aldaris", "0000")
        ) {
//            System.out.println(item.toString());
            @Cleanup
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM item_list_statis WHERE name=? AND item_type=? AND manufactor=? AND model=? AND ex_data=?");
            pst.setString(1, item.getName());
            pst.setInt(2, Integer.parseInt(item.getItemType()));
            pst.setInt(3, Integer.parseInt(item.getManufactor()));
            pst.setString(4, item.getModel());
            pst.setString(5, item.getExData());
            @Cleanup
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                updateStatis(rs.getInt("id"), item);
            } else {
                insertStatis(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStatis(BaseItem item) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/orderms?stringtype=unspecified", "aldaris", "0000")
        ) {
            conn.setAutoCommit(false);
            @Cleanup
            PreparedStatement pst = conn.prepareStatement("INSERT INTO item_list_statis (name,item_type,manufactor,model,count,price,ex_data,up_date) VALUES (?,?,?,?,?,?,?,?)");
            pst.setString(1, item.getName());
            pst.setInt(2, Integer.parseInt(item.getItemType()));
            pst.setInt(3, Integer.parseInt(item.getManufactor()));
            pst.setString(4, item.getModel());
            pst.setInt(5, item.getCount());
            pst.setBigDecimal(6, item.getPrice());
            pst.setString(7, item.getExData());
            pst.setInt(8, Integer.parseInt(item.getUpDate()));

            int crows = pst.executeUpdate();
            if (crows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                System.out.println("插入失败");
                System.out.println(item.toString());
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatis(int id, BaseItem item) {
        try (
                Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/orderms?stringtype=unspecified", "aldaris", "0000")
        ) {
            conn.setAutoCommit(false);
            @Cleanup
            PreparedStatement pst = conn.prepareStatement("UPDATE item_list_statis SET count=?,price=? WHERE id=?");
            pst.setInt(1, item.getCount());
            pst.setBigDecimal(2, item.getPrice());
            pst.setInt(3, id);

            int crows = pst.executeUpdate();
            if (crows == 1) {
                conn.commit();
            } else {
                conn.rollback();
                System.out.println("更新失败");
                System.out.println(item.toString());
                System.exit(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
