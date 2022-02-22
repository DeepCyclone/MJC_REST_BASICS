package com.epam.esm.repository.mapping;

import com.epam.esm.repository.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapping implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = Tag.builder().
                  id(rs.getLong("t_id")).
                  name(rs.getString("t_name")).
                  build();
        return tag;
    }
}
