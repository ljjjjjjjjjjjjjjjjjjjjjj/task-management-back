package com.devbridge.learning.Apptasks.configuration.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UuidSetTypeHandler extends BaseTypeHandler<Set<UUID>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<UUID> parameter, JdbcType jdbcType) throws SQLException {
        Array array = ps.getConnection().createArrayOf("UUID", parameter.toArray(new UUID[0]));
        ps.setArray(i, array);
    }

    @Override
    public Set<UUID> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getSet(rs.getArray(columnName));
    }

    @Override
    public Set<UUID> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getSet(rs.getArray(columnIndex));
    }

    @Override
    public Set<UUID> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getSet(cs.getArray(columnIndex));
    }

    private Set<UUID> getSet(Array array) throws SQLException {
        if (array == null) {
            return null;
        }
        UUID[] uuidArray = (UUID[]) array.getArray();
        Set<UUID> uuidSet = new HashSet<>();
        for (UUID uuid : uuidArray) {
            uuidSet.add(uuid);
        }
        return uuidSet;
    }
}