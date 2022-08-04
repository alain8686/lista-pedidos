
    create table fornecedor (
       cnpj int4 not null,
        name varchar(255),
        primary key (cnpj)
    )

    create table pedido (
       id  serial not null,
        fornecedor_cnpj int4,
        primary key (id)
    )

    create table pedido_items_pedido (
       pedido_id int4 not null,
        items_pedido_id int4 not null,
        primary key (pedido_id, items_pedido_id)
    )

    create table pedido_item (
       id  serial not null,
        preco float8 not null,
        quantidade int4,
        produto_gtin varchar(255),
        primary key (id)
    )

    create table produto (
       gtin varchar(255) not null,
        nome varchar(255),
        primary key (gtin)
    )

    alter table pedido_items_pedido 
       add constraint UK_82sxj1ioa9wk40cxwk8a9gs2p unique (items_pedido_id)

    alter table pedido 
       add constraint FKqagwv73jv840ubv6uydfcvqma 
       foreign key (fornecedor_cnpj) 
       references fornecedor

    alter table pedido_items_pedido 
       add constraint FKob493xlkgfphmok8mlck9w4f4 
       foreign key (items_pedido_id) 
       references pedido_item

    alter table pedido_items_pedido 
       add constraint FK855jx669vcoq8n5yrq6kajtab 
       foreign key (pedido_id) 
       references pedido

    alter table pedido_item 
       add constraint FK6pw0l23vvww68hg4otgw1wu0 
       foreign key (produto_gtin) 
       references produto

    create table fornecedor (
       cnpj int4 not null,
        name varchar(255),
        primary key (cnpj)
    )

    create table pedido (
       id  serial not null,
        fornecedor_cnpj int4,
        primary key (id)
    )

    create table pedido_items_pedido (
       pedido_id int4 not null,
        items_pedido_id int4 not null,
        primary key (pedido_id, items_pedido_id)
    )

    create table pedido_item (
       id  serial not null,
        preco float8 not null,
        quantidade int4,
        produto_gtin varchar(255),
        primary key (id)
    )

    create table produto (
       gtin varchar(255) not null,
        nome varchar(255),
        primary key (gtin)
    )

    alter table pedido_items_pedido 
       add constraint UK_82sxj1ioa9wk40cxwk8a9gs2p unique (items_pedido_id)

    alter table pedido 
       add constraint FKqagwv73jv840ubv6uydfcvqma 
       foreign key (fornecedor_cnpj) 
       references fornecedor

    alter table pedido_items_pedido 
       add constraint FKob493xlkgfphmok8mlck9w4f4 
       foreign key (items_pedido_id) 
       references pedido_item

    alter table pedido_items_pedido 
       add constraint FK855jx669vcoq8n5yrq6kajtab 
       foreign key (pedido_id) 
       references pedido

    alter table pedido_item 
       add constraint FK6pw0l23vvww68hg4otgw1wu0 
       foreign key (produto_gtin) 
       references produto

    create table fornecedor (
       cnpj int4 not null,
        name varchar(255),
        primary key (cnpj)
    )

    create table pedido (
       id  serial not null,
        fornecedor_cnpj int4,
        primary key (id)
    )

    create table pedido_items_pedido (
       pedido_id int4 not null,
        items_pedido_id int4 not null,
        primary key (pedido_id, items_pedido_id)
    )

    create table pedido_item (
       id  serial not null,
        preco float8 not null,
        quantidade int4,
        produto_gtin varchar(255),
        primary key (id)
    )

    create table produto (
       gtin varchar(255) not null,
        nome varchar(255),
        primary key (gtin)
    )

    alter table pedido_items_pedido 
       add constraint UK_82sxj1ioa9wk40cxwk8a9gs2p unique (items_pedido_id)

    alter table pedido 
       add constraint FKqagwv73jv840ubv6uydfcvqma 
       foreign key (fornecedor_cnpj) 
       references fornecedor

    alter table pedido_items_pedido 
       add constraint FKob493xlkgfphmok8mlck9w4f4 
       foreign key (items_pedido_id) 
       references pedido_item

    alter table pedido_items_pedido 
       add constraint FK855jx669vcoq8n5yrq6kajtab 
       foreign key (pedido_id) 
       references pedido

    alter table pedido_item 
       add constraint FK6pw0l23vvww68hg4otgw1wu0 
       foreign key (produto_gtin) 
       references produto

    create table fornecedor (
       cnpj int4 not null,
        name varchar(255),
        primary key (cnpj)
    )

    create table pedido (
       id  serial not null,
        fornecedor_cnpj int4,
        primary key (id)
    )

    create table pedido_items_pedido (
       pedido_id int4 not null,
        items_pedido_id int4 not null,
        primary key (pedido_id, items_pedido_id)
    )

    create table pedido_item (
       id  serial not null,
        preco float8 not null,
        quantidade int4,
        produto_gtin varchar(255),
        primary key (id)
    )

    create table produto (
       gtin varchar(255) not null,
        nome varchar(255),
        primary key (gtin)
    )

    alter table pedido_items_pedido 
       add constraint UK_82sxj1ioa9wk40cxwk8a9gs2p unique (items_pedido_id)

    alter table pedido 
       add constraint FKqagwv73jv840ubv6uydfcvqma 
       foreign key (fornecedor_cnpj) 
       references fornecedor

    alter table pedido_items_pedido 
       add constraint FKob493xlkgfphmok8mlck9w4f4 
       foreign key (items_pedido_id) 
       references pedido_item

    alter table pedido_items_pedido 
       add constraint FK855jx669vcoq8n5yrq6kajtab 
       foreign key (pedido_id) 
       references pedido

    alter table pedido_item 
       add constraint FK6pw0l23vvww68hg4otgw1wu0 
       foreign key (produto_gtin) 
       references produto
