package com.epam.esm.repository.query;

public class TagQueryHolder {
    public static final String FETCH_ASSOCIATED_CERTIFICATES = "SELECT * FROM gift_certificate WHERE gc_id IN (SELECT tmgc_gc_id FROM tag_m2m_gift_certificate WHERE tmgc_t_id = ?)";
}
