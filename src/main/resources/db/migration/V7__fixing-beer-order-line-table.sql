
    drop table if exists beer_order_line;

    create table beer_order_line (
        id varchar(36) not null,
        beer_id varchar(36) default null,
        created_date datetime(6) default null,
        last_modified_date datetime(6) default null,
        order_quantity integer default null,
        quantity_allocated integer default null,
        version bigint default null,
        beer_order_id varchar(36) default null,
        primary key (id),
        constraint foreign key (beer_order_id) references beer_order(id),
        constraint foreign key (beer_id) references beer(id)
    ) engine=InnoDB;
