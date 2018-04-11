package com.everygamer.dao.mybatis.handler;

import com.everygamer.bean.OrderState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * OrderItem.State的处理器
 */
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({OrderState.class})
public class StateEnumHandler extends BaseTypeHandler<OrderState> {
    /**
     * 用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrderState parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            parameter = OrderState.Unknow;
        }
        ps.setInt(i, parameter.getId());
    }

    /**
     * 用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public OrderState getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int key = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return OrderState.getEnum(key);
        }
    }

    /**
     * 用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public OrderState getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int key = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return OrderState.getEnum(key);
        }
    }

    /**
     * 用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
     */
    @Override
    public OrderState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int key = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return OrderState.getEnum(key);
        }
    }
}
