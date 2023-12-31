CREATE TABLE photo_product (
    product_id bigint NOT NULL,
    filename varchar(255) NOT NULL,
    description varchar(255),
    content_type varchar(80) NOT NULL,
    filesize int not null,

    PRIMARY KEY (product_id),
    CONSTRAINT fk_photo_product_product FOREIGN KEY (product_id) references product (id)
) engine=InnoDB DEFAULT CHARSET=utf8;