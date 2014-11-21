package com.andrew.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by andrew on 21.11.14.
 */
public class SumMapper implements RowMapper<Long> {
    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {

        return resultSet.getLong("sum");
    }
}
