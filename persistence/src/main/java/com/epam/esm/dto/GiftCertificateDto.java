package com.epam.esm.dto;

import com.epam.esm.repository.model.Tag;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class GiftCertificateDto {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<TagDto> getAssociatedTags() {
        return associatedTags;
    }

    public void setAssociatedTags(List<TagDto> associatedTags) {
        this.associatedTags = associatedTags;
    }

    public GiftCertificateDto(long id, String name, String description, BigDecimal price, int duration, Timestamp createDate, Timestamp lastUpdateDate, List<TagDto> associatedTags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.associatedTags = associatedTags;
    }

    public GiftCertificateDto() {
    }

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<TagDto> associatedTags;
}
