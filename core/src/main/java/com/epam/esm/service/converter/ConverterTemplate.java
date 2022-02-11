package com.epam.esm.service.converter;


import java.util.List;

/*
*
*
* */
interface ConverterTemplate <A,B,C>{
    A convertFromRequestDto(B dto);
    C convertToResponseDto(A object);

    List<C> convertToResponseDtos(List<A> objects);
}
