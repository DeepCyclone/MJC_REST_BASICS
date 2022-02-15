package com.epam.esm.dto.request;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import com.epam.esm.dto.PatchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto implements Serializable {
    private long id;
    @Size(min = 3,max = 10,message = "ABC")
    private String name;
}
