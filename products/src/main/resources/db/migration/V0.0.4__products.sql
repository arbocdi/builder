create table product_stores(
    name text not null primary key,
    stored_products jsonb not null
);