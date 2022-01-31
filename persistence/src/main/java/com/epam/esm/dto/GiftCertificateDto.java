package com.epam.esm.dto;

import com.epam.esm.repository.model.Tag;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class GiftCertificateDto {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<TagDto> associatedTags;//TODO
}
