package com.epam.esm.service.converter;


//@params A - entity,B - dto
interface ConverterTemplate <A,B>{
    A convertFromDto(B dto);
    B convertToDto(A object);
}
