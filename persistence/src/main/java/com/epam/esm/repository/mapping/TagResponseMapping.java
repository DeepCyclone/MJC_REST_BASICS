package com.epam.esm.repository.mapping;

import com.epam.esm.dto.response.TagResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagResponseMapping implements RowMapper<TagResponseDto> {
    @Override
    public TagResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        TagResponseDto tag = new TagResponseDto();
        tag.setId(rs.getLong("t_id"));
        tag.setName(rs.getString("t_name"));
        return tag;
    }
}
