package com.epam.esm.repository.query;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repository.query.CertificateQueryHolder.CERTIFICATE_NAME_SEARCH;
import static com.epam.esm.repository.query.CertificateQueryHolder.CERTIFICATE_UPDATE_DATE_SEARCH;
import static com.epam.esm.repository.query.CertificateQueryHolder.JOIN_ON_CONDITION;
import static com.epam.esm.repository.query.CertificateQueryHolder.JOIN_PARAMS;
import static com.epam.esm.repository.query.CertificateQueryHolder.ORDER_BY;
import static com.epam.esm.repository.query.CertificateQueryHolder.TAG_NAME_FILTER;

public class ComplexParamMapProcessor {
    public static final String tagName = "tagName";
    public static final String namePart = "namePart";
    public static final String descriptionPart = "descriptionPart";
    public static final String nameSortOrder = "nameSortOrder";
    public static final String dateSortOrder = "dateSortOrder";
    public static final String COLON = ":";
    public static final String PERCENT = "%";
    public static final String COMMA = ",";


    public static String buildQuery(Map<String,String> params){
        StringBuilder query = new StringBuilder(JOIN_PARAMS);
        Optional.ofNullable(params.get(tagName)).ifPresent(val -> query.append(TAG_NAME_FILTER).append(COLON).append(tagName));
        query.append(JOIN_ON_CONDITION);
        Optional.ofNullable(params.get(namePart)).ifPresent(val -> query.append(CERTIFICATE_NAME_SEARCH).append(COLON).append(namePart));
        Optional.ofNullable(params.get(descriptionPart)).ifPresent(val -> query.append(CERTIFICATE_UPDATE_DATE_SEARCH).append(COLON).append(descriptionPart));
        query.append(appendQueryWithSorting(params));
        return query.toString();
    }

    private static String appendQueryWithSorting(Map<String,String> params){
        if((params.containsKey(nameSortOrder)) || params.containsKey(dateSortOrder)) {
            StringBuilder orderQuery = new StringBuilder(ORDER_BY);
            Optional<String> nameSortOrderValue = Optional.ofNullable(params.get(nameSortOrder));
            Optional<String> dateSortOrderValue = Optional.ofNullable(params.get(dateSortOrder));
            boolean complexSort = nameSortOrderValue.isPresent() && dateSortOrderValue.isPresent();
            nameSortOrderValue.ifPresent(order -> orderQuery.append("gc_name ").append(order));
            if(complexSort){
                orderQuery.append(COMMA);
            }
            dateSortOrderValue.ifPresent(order -> orderQuery.append("gc_last_update_date ").append(order));
            return orderQuery.toString();
        }
        return "";
    }

}
