create table gift_certificate
(
    gc_id               bigint unsigned auto_increment
        primary key,
    gc_name             varchar(50)                         not null,
    gc_description      varchar(200)                        not null,
    gc_price            decimal(20, 10)                     not null,
    gc_duration         int                                 not null,
    gc_create_date      timestamp default CURRENT_TIMESTAMP not null,
    gc_last_update_date timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create table tag
(
    t_id   bigint unsigned auto_increment
        primary key,
    t_name varchar(50) not null
);

create table tag_m2m_gift_certificate
(
    tmgc_t_id  bigint unsigned not null,
    tmgc_gc_id bigint unsigned not null,
    primary key (tmgc_t_id, tmgc_gc_id),
    constraint fk_tag_m2m_gift_certificate_gift_cert
        foreign key (tmgc_gc_id) references gift_certificate (gc_id)
            on update cascade on delete cascade,
    constraint fk_tag_m2m_gift_certificate_tag
        foreign key (tmgc_t_id) references tag (t_id)
            on update cascade on delete cascade
);

