package com.epam.esm.repository.query;

public class CertificateQueryHolder {
    public static final String READ_ALL = "SELECT * FROM gift_certificate";
    public static final String ID_FILTER = " WHERE gc_id = ?";
    public static final String READ_BY_ID = READ_ALL + ID_FILTER;
    public static final String DELETE_ENTRY = "DELETE FROM gift_certificate WHERE gc_id = ?";
    public static final String DETACH_ASSOCIATED_TAGS = "DELETE * FROM tag_m2m_gift_certificate WHERE tmgc_gc_id = ?";
    public static final String UPDATE_QUERY = "UPDATE gift_certificate SET gc_name=?, gc_description=?, gc_price=?, gc_duration=? WHERE gc_id=?";
    public static final String JOIN_PARAMS = "SELECT gc_id,gc_name,gc_description,gc_price,gc_duration,gc_create_date,gc_last_update_date,t_id,t_name FROM gift_certificate JOIN" +
            " (SELECT t_name,tmgc_gc_id,t_id FROM tag JOIN" +
            " `tag_m2m_gift_certificate` ON t_id = tmgc_t_id";
    public static final String TAG_NAME_FILTER = " WHERE t_name=?";
    public static final String JOIN_ON_CONDITION = ") AS ix ON gc_id = ix.tmgc_gc_id";
    public static final String PART_NAME_FILTER = " WHERE gc_name like ?";
    public static final String PART_DESCRIPTION_FILTER = " WHERE gc_description like ?";
    public static final String COMBINED_SORT = " ORDER BY gc_name ?,gc_last_update_date ?";
    public static final String REAL_ALL_CERTS_WITH_TAGS = JOIN_PARAMS + JOIN_ON_CONDITION;
    public static final String SEARCH_BY_TAG_NAME = JOIN_PARAMS+TAG_NAME_FILTER+JOIN_ON_CONDITION;
    public static final String SEARCH_BY_GC_NAME_PART = JOIN_PARAMS+JOIN_ON_CONDITION + PART_NAME_FILTER;
    public static final String SEARCH_BY_GC_DESCRIPTION_PART = JOIN_PARAMS+JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER;
    public static final String SEARCH_BY_TAG_AND_NAME = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_NAME_FILTER;
    public static final String SEARCH_BY_TAG_AND_DESCRIPTION = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER;
    public static final String SEARCH_BY_TAG_AND_DESCRIPTION_SORTED = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER + COMBINED_SORT;
    public static final String SEARCH_BY_TAG_AND_NAME_SORTED = JOIN_PARAMS + TAG_NAME_FILTER + JOIN_ON_CONDITION + PART_NAME_FILTER + COMBINED_SORT;
    public static final String SEARCH_BY_NAME_SORTED = JOIN_PARAMS + JOIN_ON_CONDITION + PART_NAME_FILTER + COMBINED_SORT;
    public static final String SEARCH_BY_DESCRIPTION_SORTED = JOIN_PARAMS + JOIN_ON_CONDITION + PART_DESCRIPTION_FILTER + COMBINED_SORT;
}
