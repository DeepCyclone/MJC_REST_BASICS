package com.epam.esm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateResponseDto implements Serializable {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<TagResponseDto> associatedTags;
}
