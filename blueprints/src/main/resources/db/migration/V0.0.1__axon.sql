CREATE  SCHEMA if not exists public;


CREATE TABLE public.association_value_entry (
    id bigint NOT NULL,
    association_key character varying(255) NOT NULL,
    association_value character varying(255),
    saga_id character varying(255) NOT NULL,
    saga_type character varying(255)
);


CREATE SEQUENCE public.association_value_entry_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.domain_event_entry (
    global_index bigint NOT NULL,
    event_identifier character varying(255) NOT NULL,
    meta_data bytea,
    payload bytea NOT NULL,
    payload_revision character varying(255),
    payload_type character varying(255) NOT NULL,
    time_stamp character varying(255) NOT NULL,
    aggregate_identifier character varying(255) NOT NULL,
    sequence_number bigint NOT NULL,
    type character varying(255)
);

CREATE SEQUENCE public.domain_event_entry_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.saga_entry (
    saga_id character varying(255) NOT NULL,
    revision character varying(255),
    saga_type character varying(255),
    serialized_saga bytea
);

CREATE TABLE public.snapshot_event_entry (
    aggregate_identifier character varying(255) NOT NULL,
    sequence_number bigint NOT NULL,
    type character varying(255) NOT NULL,
    event_identifier character varying(255) NOT NULL,
    meta_data bytea,
    payload bytea NOT NULL,
    payload_revision character varying(255),
    payload_type character varying(255) NOT NULL,
    time_stamp character varying(255) NOT NULL
);

CREATE TABLE public.token_entry (
    processor_name character varying(255) NOT NULL,
    segment integer NOT NULL,
    owner character varying(255),
    "timestamp" character varying(255) NOT NULL,
    token bytea,
    token_type character varying(255)
);


ALTER TABLE ONLY public.association_value_entry
    ADD CONSTRAINT association_value_entry_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.domain_event_entry
    ADD CONSTRAINT domain_event_entry_pkey PRIMARY KEY (global_index);

ALTER TABLE ONLY public.saga_entry
    ADD CONSTRAINT saga_entry_pkey PRIMARY KEY (saga_id);

ALTER TABLE ONLY public.snapshot_event_entry
    ADD CONSTRAINT snapshot_event_entry_pkey PRIMARY KEY (aggregate_identifier, sequence_number, type);

ALTER TABLE ONLY public.token_entry
    ADD CONSTRAINT token_entry_pkey PRIMARY KEY (processor_name, segment);

ALTER TABLE ONLY public.domain_event_entry
    ADD CONSTRAINT uk8s1f994p4la2ipb13me2xqm1w UNIQUE (aggregate_identifier, sequence_number);

ALTER TABLE ONLY public.snapshot_event_entry
    ADD CONSTRAINT uk_e1uucjseo68gopmnd0vgdl44h UNIQUE (event_identifier);

ALTER TABLE ONLY public.domain_event_entry
    ADD CONSTRAINT uk_fwe6lsa8bfo6hyas6ud3m8c7x UNIQUE (event_identifier);


CREATE INDEX idxgv5k1v2mh6frxuy5c0hgbau94 ON public.association_value_entry USING btree (saga_id, saga_type);

CREATE INDEX idxk45eqnxkgd8hpdn6xixn8sgft ON public.association_value_entry USING btree (saga_type, association_key, association_value);
