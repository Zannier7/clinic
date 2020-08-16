create table especialidad
(
    id   serial not null
        constraint especialidad_pkey
            primary key,
    name varchar(255)
);

alter table especialidad
    owner to postgres;

create table medico
(
    id           serial not null
        constraint medico_pkey
            primary key,
    cmp          varchar(255),
    dni          varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    id_specialty serial not null
        constraint fk2eeyrthi2uttuoq3v0efdqc5j
            references especialidad,
    clave        text,
    rol          varchar
);

alter table medico
    owner to postgres;

create table paciente
(
    id                      serial not null
        constraint paciente_pkey
            primary key,
    dni                     varchar(255),
    first_name              varchar(255),
    last_name               varchar(255),
    numero_historia_clinica varchar(255)
);

alter table paciente
    owner to postgres;

create table medical_consultation
(
    id          serial not null
        constraint medical_consultation_pkey
            primary key,
    create_date date,
    id_doctor   serial not null
        constraint fkhjus7uywlaboewycb44n5yfop
            references medico,
    id_patient  serial not null
        constraint fkreu2h1nv5o306eva8wimy4r1f
            references paciente
);

alter table medical_consultation
    owner to postgres;

create table detail_consultation
(
    id                      serial not null
        constraint detail_consultation_pkey
            primary key,
    diagnostic              varchar(255),
    treatment               varchar(255),
    id_medical_consultation serial not null
        constraint fkekihpubi3btirgl2ki66o2kgf
            references medical_consultation
);

alter table detail_consultation
    owner to postgres;

create unique index paciente_dni_uindex
    on paciente (dni);

create unique index paciente_numero_historia_clinica_uindex
    on paciente (numero_historia_clinica);


INSERT INTO public.especialidad (id, name) VALUES (1, 'CARDIOLOGIA');
INSERT INTO public.especialidad (id, name) VALUES (2, 'DERMATOLOGIA');
INSERT INTO public.especialidad (id, name) VALUES (3, 'MEDICINA GENERAL');

INSERT INTO public.medico (id, cmp, dni, first_name, last_name, id_specialty, clave, rol) VALUES (1, '1001', '77777777', 'ARMANDO ROGELIO', 'POMA ROCA', 3, '$2a$10$xFt/zn3Hit6NG4OeU6o2ROUCuIFiE7t8OFDUCP6g3MY8tCW5TxsEq', 'ADMIN');
INSERT INTO public.medico (id, cmp, dni, first_name, last_name, id_specialty, clave, rol) VALUES (2, '9898', '99999999', 'ARMANDO', 'PAREDES BLANCAS', 1, '$2a$10$OGTVmtKKZh1rfoZmNaDZ0epg0irSKyLcV06nvERvroPuLr/LRZR.2', 'USER');
INSERT INTO public.medico (id, cmp, dni, first_name, last_name, id_specialty, clave, rol) VALUES (3, '909', '89898981', 'JUAN ROMARIO', 'PEREZ CAPCHA', 1, '$2a$10$tCVBbJPZ0iPK9yI5dwWKi.C53BhJys77ojqseu0ET.x7QgusRsdpq', 'USER');
INSERT INTO public.medico (id, cmp, dni, first_name, last_name, id_specialty, clave, rol) VALUES (4, '123', '91218212', 'ARTURO', 'LEZAMA', 3, '$2a$10$VQt6cvJIHKl4p8hUw3eYPOkV20BIzNMucd8CPKj8OCcHnjC4G/Qne', 'USER');

INSERT INTO public.paciente (id, dni, first_name, last_name, numero_historia_clinica) VALUES (2, '89898989', 'ROMINA ANTONIA', 'GONZALES JIMENEZ', '188');


INSERT INTO public.medical_consultation (id, create_date, id_doctor, id_patient) VALUES (10, '2020-08-16', 2, 2);

INSERT INTO public.detail_consultation (id, diagnostic, treatment, id_medical_consultation) VALUES (1, 'TRAUMATISMO EN BRAZO IZQUIERDO', 'DESCANSO MEDICO POR 2 DIAS', 10);

