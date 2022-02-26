package com.epam.esm.dto.request;

import com.epam.esm.dto.CreateDTO;
import com.epam.esm.dto.PatchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto implements Serializable {
    @Null(groups = CreateDTO.class,message = "ID will be created automatically.Remove it")
    @NotNull(message = "certificate to be patched must have an ID",groups = {PatchDTO.class})
    @Positive(groups = {PatchDTO.class},message = "Certificate ID to be patched must have positive ID")
    private Long id;
    @NotBlank(groups = {CreateDTO.class,PatchDTO.class},message = "name mustn't be blank")
    @Size(min = 5,max = 50,message = "name length constraints = [5,50]",groups = {CreateDTO.class,PatchDTO.class})
    private String name;
    @NotBlank(groups = {CreateDTO.class,PatchDTO.class},message = "description mustn't be blank")
    @Size(min = 5,max = 50,message = "description length constraints = [5,50]",groups = {CreateDTO.class,PatchDTO.class})
    private String description;
    @NotNull(message = "price couldn't be empty",groups = {CreateDTO.class,PatchDTO.class})
    @PositiveOrZero(message = "Price values must be in [0;+inf)",groups = {CreateDTO.class,PatchDTO.class})
    private BigDecimal price;
    @NotNull(message = "duration couldn't be empty",groups = {CreateDTO.class,PatchDTO.class})
    @PositiveOrZero(message = "Duration values must be in [0;+inf)",groups = {CreateDTO.class,PatchDTO.class})
    private Integer duration;
    private List<@Valid TagDto> associatedTags;
}
