package com.epam.esm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto {
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<TagDto> associatedTags;
}
