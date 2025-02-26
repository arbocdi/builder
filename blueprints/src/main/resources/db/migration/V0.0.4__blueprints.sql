create table blueprints(
    name text not null primary key,
    input_materials jsonb not null,
    output_materials jsonb not null
);