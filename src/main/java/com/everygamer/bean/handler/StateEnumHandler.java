package com.everygamer.bean.handler;

import com.everygamer.bean.OrderItem;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * OrderItem.State的处理器
 */
public class StateEnumHandler extends BaseTypeHandler<OrderItem.State> {
    /**
     * 用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrderItem.State parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getName());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public OrderItem.State getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String key = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return OrderItem.State.valueOf(key);
        }
    }

    /**
     * 用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public OrderItem.State getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String key = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return OrderItem.State.valueOf(key);
        }
    }

    /**
     * 用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public OrderItem.State getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String key = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return OrderItem.State.valueOf(key);
        }
    }
}
