package com.everygamer.fake;

import com.everygamer.bean.BaseItem;

import java.sql.*;

public class StaticUpdate {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        try (
                Connection conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/orderms?stringtype=unspecified", "aldaris", "0000")
        ) {
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement("select name,item_type,manufactor,model,sum(count) as count,sum(count*price) as price,ex_data,max(ins_date) as up_date from item_list where remain>0 group by name,item_type,manufactor,model,ex_data", ResultSet.TYPE_FORWARD_ONLY, ResultSet.FETCH_FORWARD);
            pst.setFetchSize(50);
//            pst.setMaxRows(100);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                BaseItem item = packItem(rs);
                checkStatis(item);
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
            PreparedStatement pst = conn.prepareStatement("select * from item_list_statis where name=? and item_type=? and manufactor=? and model=? and ex_data=?");
            pst.setString(1, item.getName());
            pst.setInt(2, Integer.parseInt(item.getItemType()));
            pst.setInt(3, Integer.parseInt(item.getManufactor()));
            pst.setString(4, item.getModel());
            pst.setString(5, item.getExData());

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
