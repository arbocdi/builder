create table material_stores(
name text not null primary key
);

create table stored_materials(
id text not null primary key,
name text null,
qty int4 not null,
material_store_name text not null references material_stores(name)
);