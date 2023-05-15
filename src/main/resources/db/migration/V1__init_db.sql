create sequence public.admins_sequence_id
    increment by 50;

alter sequence public.admins_sequence_id owner to postgres;

create sequence public.advertisements_sequence_id
    increment by 50;

alter sequence public.advertisements_sequence_id owner to postgres;

create sequence public.moderation_sequence_id
    increment by 50;

alter sequence public.moderation_sequence_id owner to postgres;

create sequence public.p_sequence_id
    increment by 50;

alter sequence public.p_sequence_id owner to postgres;

create sequence public.u_sequence_id
    increment by 50;

alter sequence public.u_sequence_id owner to postgres;

create sequence public.v_sequence_id;

alter sequence public.v_sequence_id owner to postgres;

create table if not exists public.admins
(
    adm_id       bigint       not null
    primary key,
    adm_name     varchar(255) not null
    constraint uk_unique_admin_name
    unique,
    adm_password varchar(255) not null
    );

create table if not exists public.users
(
    u_id            bigint       not null
    primary key,
    u_email         varchar(255) not null
    constraint uk_unique_user_email
    unique,
    u_name          varchar(255),
    u_phone         varchar(255) not null
    constraint uk_unique_user_phone
    unique,
    u_creation_date date         not null,
    u_password      varchar(255) not null,
    u_update_time   timestamp(6) not null,
    u_ups           integer      not null,
    u_email_verify  boolean      not null,
    u_phone_verify  boolean      not null
    );

create table if not exists public.payments
(
    p_id                 bigint not null
    primary key,
    p_bank_name          varchar(255),
    p_operation_name     varchar(255),
    p_date_time          timestamp(6),
    p_transaction_number varchar(255),
    u_id                 bigint
    constraint fk_user_payments
    references public.users
    );

create table if not exists public.vehicles
(
    v_id         integer      not null
    primary key,
    v_body_type  varchar(255) not null,
    v_brand      varchar(255) not null,
    v_generation varchar(255) not null,
    v_model      varchar(255) not null
    );

create table if not exists public.advertisements
(
    ad_id                bigint        not null
    primary key,
    ad_vin               varchar(255)  not null,
    ad_car_year          integer       not null,
    ad_creation_date     date          not null,
    ad_description       varchar(1200) not null,
    ad_engine_capacity   integer       not null,
    ad_engine_type       varchar(255)  not null,
    im_name_1            uuid,
    im_name_2            uuid,
    im_name_3            uuid,
    im_name_4            uuid,
    ad_mileage           integer       not null,
    ad_moderated         boolean       not null,
    ad_price             integer       not null,
    ad_region            varchar(255)  not null,
    ad_transmission_type varchar(255)  not null,
    ad_up_time           timestamp(6)  not null,
    u_id                 bigint        not null
    constraint fk_user_advertisements
    references public.users,
    v_id                 integer       not null
    constraint fk_vehicle_advertisements
    references public.vehicles
    );


create table if not exists public.moderation
(
    mo_id            bigint       not null
    primary key,
    mo_creation_date timestamp(6) not null,
    mo_message       varchar(500) not null,
    adm_id           bigint       not null
    constraint fk_admin_moderation
    references public.admins,
    ad_id            bigint       not null
    constraint fk_advertisement_moderation
    references public.advertisements
    );

alter table public.moderation
    owner to postgres;

alter table public.advertisements
    owner to postgres;

alter table public.vehicles
    owner to postgres;

alter table public.payments
    owner to postgres;

alter table public.users
    owner to postgres;

alter table public.admins
    owner to postgres;