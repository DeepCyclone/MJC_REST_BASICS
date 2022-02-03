package com.epam.esm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<TagDto> associatedTags;
}
