-- Table: public.person

-- DROP TABLE IF EXISTS public.person;

CREATE TABLE IF NOT EXISTS public.person
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999 CACHE 1 ),
    username character varying(200) COLLATE pg_catalog."default",
    password character varying(200) COLLATE pg_catalog."default",
    firstname character varying(200) COLLATE pg_catalog."default",
    lastname character varying(200) COLLATE pg_catalog."default",
    email character varying(200) COLLATE pg_catalog."default",
    flagdeleted boolean,
    role character varying(200) COLLATE pg_catalog."default",
    CONSTRAINT "User_pkey" PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.person
    OWNER to postgres;

-- Table: public.annual_leave

-- DROP TABLE IF EXISTS public.annual_leave;

CREATE TABLE IF NOT EXISTS public.annual_leave
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999 CACHE 1 ),
    user_id integer NOT NULL,
    supervisor_id integer NOT NULL,
    remaining_days integer NOT NULL,
    spent_days integer NOT NULL,
    employment_date date NOT NULL,
    CONSTRAINT annual_leave_pkey PRIMARY KEY (id),
    CONSTRAINT supervisor_id FOREIGN KEY (supervisor_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_id FOREIGN KEY (user_id)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.annual_leave
    OWNER to postgres;

-- Table: public.application

-- DROP TABLE IF EXISTS public.application;

CREATE TABLE IF NOT EXISTS public.application
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    user_id integer NOT NULL,
    supervisor_id integer NOT NULL,
    days_off integer NOT NULL,
    status character varying(200) COLLATE pg_catalog."default",
    comment character varying(200) COLLATE pg_catalog."default",
    flag_deleted boolean DEFAULT false,
    start_date date,
    end_date date,
    CONSTRAINT application_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.application
    OWNER to postgres;

