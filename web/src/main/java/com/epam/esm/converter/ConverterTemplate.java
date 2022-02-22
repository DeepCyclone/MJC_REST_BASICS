package com.epam.esm.converter;


import java.util.List;

/*
* This interfaces specifies a bunch of ways an model to be converted to use in different layers
* */
interface ConverterTemplate <A,B,C>{
    A convertFromRequestDto(B dto);
    List<A> convertFromRequestDtos(List<B> dtos);
    C convertToResponseDto(A object);
    List<C> convertToResponseDtos(List<A> objects);
}
