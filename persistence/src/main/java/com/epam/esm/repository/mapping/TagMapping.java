package com.epam.esm.repository.mapping;

import com.epam.esm.repository.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapping implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong("t_id"));
        tag.setName(rs.getString("t_name"));
        return tag;
    }
}
