--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1 (Debian 16.1-1.pgdg120+1)
-- Dumped by pg_dump version 16.1

CREATE DATABASE testSQL;

\c testsql;

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client; Type: TABLE; Schema: public; Owner: GreenPulse
--

CREATE TABLE public.client (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    address character varying(50) NOT NULL,
    phone character varying(50) NOT NULL,
    is_professional boolean NOT NULL,
    discount_rate double precision DEFAULT 0.0
);


ALTER TABLE public.client OWNER TO "GreenPulse";

--
-- Name: client_id_seq; Type: SEQUENCE; Schema: public; Owner: GreenPulse
--

CREATE SEQUENCE public.client_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.client_id_seq OWNER TO "GreenPulse";

--
-- Name: client_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: GreenPulse
--

ALTER SEQUENCE public.client_id_seq OWNED BY public.client.id;


--
-- Name: component; Type: TABLE; Schema: public; Owner: GreenPulse
--

CREATE TABLE public.component (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    component_type character varying(50) NOT NULL,
    project_id integer NOT NULL
);


ALTER TABLE public.component OWNER TO "GreenPulse";

--
-- Name: component_id_seq; Type: SEQUENCE; Schema: public; Owner: GreenPulse
--

CREATE SEQUENCE public.component_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.component_id_seq OWNER TO "GreenPulse";

--
-- Name: component_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: GreenPulse
--

ALTER SEQUENCE public.component_id_seq OWNED BY public.component.id;


--
-- Name: labor; Type: TABLE; Schema: public; Owner: GreenPulse
--

CREATE TABLE public.labor (
    hours_worked double precision NOT NULL,
    hourly_rate double precision NOT NULL,
    productivity double precision NOT NULL
)
INHERITS (public.component);


ALTER TABLE public.labor OWNER TO "GreenPulse";

--
-- Name: material; Type: TABLE; Schema: public; Owner: GreenPulse
--

CREATE TABLE public.material (
    unit_cost double precision NOT NULL,
    quantity integer NOT NULL,
    transport_cost double precision NOT NULL,
    quality_coefficient double precision NOT NULL
)
INHERITS (public.component);


ALTER TABLE public.material OWNER TO "GreenPulse";

--
-- Name: project; Type: TABLE; Schema: public; Owner: GreenPulse
--

CREATE TABLE public.project (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    profit_margin double precision NOT NULL,
    total_cost double precision NOT NULL,
    status character varying(50) DEFAULT 'IN_PROGRESS'::character varying NOT NULL,
    client_id integer NOT NULL,
    area double precision,
    tva double precision DEFAULT 0.0
);


ALTER TABLE public.project OWNER TO "GreenPulse";

--
-- Name: project_id_seq; Type: SEQUENCE; Schema: public; Owner: GreenPulse
--

CREATE SEQUENCE public.project_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.project_id_seq OWNER TO "GreenPulse";

--
-- Name: project_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: GreenPulse
--

ALTER SEQUENCE public.project_id_seq OWNED BY public.project.id;


--
-- Name: quote; Type: TABLE; Schema: public; Owner: GreenPulse
--

CREATE TABLE public.quote (
    id integer NOT NULL,
    estimated_cost double precision NOT NULL,
    issue_date date NOT NULL,
    validity_date date,
    is_accepted boolean DEFAULT true,
    project_id integer NOT NULL
);


ALTER TABLE public.quote OWNER TO "GreenPulse";

--
-- Name: quote_id_seq; Type: SEQUENCE; Schema: public; Owner: GreenPulse
--

CREATE SEQUENCE public.quote_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.quote_id_seq OWNER TO "GreenPulse";

--
-- Name: quote_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: GreenPulse
--

ALTER SEQUENCE public.quote_id_seq OWNED BY public.quote.id;


--
-- Name: client id; Type: DEFAULT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.client ALTER COLUMN id SET DEFAULT nextval('public.client_id_seq'::regclass);


--
-- Name: component id; Type: DEFAULT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.component ALTER COLUMN id SET DEFAULT nextval('public.component_id_seq'::regclass);


--
-- Name: labor id; Type: DEFAULT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.labor ALTER COLUMN id SET DEFAULT nextval('public.component_id_seq'::regclass);


--
-- Name: material id; Type: DEFAULT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.material ALTER COLUMN id SET DEFAULT nextval('public.component_id_seq'::regclass);


--
-- Name: project id; Type: DEFAULT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.project ALTER COLUMN id SET DEFAULT nextval('public.project_id_seq'::regclass);


--
-- Name: quote id; Type: DEFAULT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.quote ALTER COLUMN id SET DEFAULT nextval('public.quote_id_seq'::regclass);


--
-- Name: client client_pk; Type: CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pk UNIQUE (name);


--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- Name: component component_pkey; Type: CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT component_pkey PRIMARY KEY (id);


--
-- Name: project project_pk; Type: CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pk UNIQUE (name);


--
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- Name: quote quote_pkey; Type: CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.quote
    ADD CONSTRAINT quote_pkey PRIMARY KEY (id);


--
-- Name: component component_project_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT component_project_id_fkey FOREIGN KEY (project_id) REFERENCES public.project(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: project project_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: quote quote_project_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: GreenPulse
--

ALTER TABLE ONLY public.quote
    ADD CONSTRAINT quote_project_id_fkey FOREIGN KEY (project_id) REFERENCES public.project(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

