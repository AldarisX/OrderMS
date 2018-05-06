package com.everygamer.dao.mybatis.handler;

import com.everygamer.logger.DBLogLevel;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBLogLevel的处理器
 */
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes({DBLogLevel.class})
public class DBLogLevelEnumHandler extends BaseTypeHandler<DBLogLevel> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DBLogLevel parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            parameter = DBLogLevel.Info;
        }
        ps.setInt(i, parameter.getId());
    }

    @Override
    public DBLogLevel getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int key = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return DBLogLevel.getEnum(key);
        }
    }

    @Override
    public DBLogLevel getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int key = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return DBLogLevel.getEnum(key);
        }
    }

    @Override
    public DBLogLevel getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int key = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return DBLogLevel.getEnum(key);
        }
    }
}
