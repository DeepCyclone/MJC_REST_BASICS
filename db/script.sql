create table gift_certificate
(
    gc_id bigint unsigned primary key auto_increment,
    gc_name VARCHAR(50),
    gc_description VARCHAR(100),
    gc_price DECIMAL(20,10),
    gc_duration INT,
    gc_create_date timestamp default current_timestamp,
    gc_last_update_date timestamp default current_timestamp on update current_timestamp
);

create table tag
(
    t_id bigint unsigned primary key auto_increment,
    t_name VARCHAR(50),
    constraint t_name_index
        unique (t_name)
);

create table tag_m2m_gift_certificate
(
    tmgc_t_id bigint unsigned not null,
    tmgc_gc_id bigint unsigned not null,
    primary key(tmgc_t_id,tmgc_gc_id),
    constraint fk_tag_m2m_gift_certificate_tag
        foreign key (tmgc_t_id) references tag (t_id)
            on update cascade on delete cascade,
    constraint fk_tag_m2m_gift_certificate_gift_cert
        foreign key (tmgc_gc_id) references gift_certificate (gc_id)
            on update cascade on delete cascade
)