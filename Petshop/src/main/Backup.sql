--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-09-14 10:45:34

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
-- TOC entry 218 (class 1259 OID 16577)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    idcliente integer NOT NULL,
    nome character varying(100) NOT NULL,
    cpf character varying(14) NOT NULL,
    telefone character varying(15),
    email character varying(100),
    datacadastro date DEFAULT CURRENT_DATE
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16576)
-- Name: cliente_idcliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_idcliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cliente_idcliente_seq OWNER TO postgres;

--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 217
-- Name: cliente_idcliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_idcliente_seq OWNED BY public.cliente.idcliente;


--
-- TOC entry 228 (class 1259 OID 16663)
-- Name: clienteservico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clienteservico (
    idcliente integer NOT NULL,
    idservico integer NOT NULL
);


ALTER TABLE public.clienteservico OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16605)
-- Name: descricaoservico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.descricaoservico (
    iddescricaoservico integer NOT NULL,
    servicodescricao character varying(255) NOT NULL,
    valor numeric(10,2) NOT NULL
);


ALTER TABLE public.descricaoservico OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16604)
-- Name: descricaoservico_iddescricaoservico_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.descricaoservico_iddescricaoservico_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.descricaoservico_iddescricaoservico_seq OWNER TO postgres;

--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 221
-- Name: descricaoservico_iddescricaoservico_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.descricaoservico_iddescricaoservico_seq OWNED BY public.descricaoservico.iddescricaoservico;


--
-- TOC entry 226 (class 1259 OID 16635)
-- Name: pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagamento (
    idpagamento integer NOT NULL,
    idservico integer,
    valorpago numeric(10,2),
    datapagamento date DEFAULT CURRENT_DATE,
    formapagamento character varying(50)
);


ALTER TABLE public.pagamento OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16634)
-- Name: pagamento_idpagamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagamento_idpagamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pagamento_idpagamento_seq OWNER TO postgres;

--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 225
-- Name: pagamento_idpagamento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagamento_idpagamento_seq OWNED BY public.pagamento.idpagamento;


--
-- TOC entry 220 (class 1259 OID 16587)
-- Name: pet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pet (
    idpet integer NOT NULL,
    nome character varying(100) NOT NULL,
    idade integer,
    datanascimento date,
    idpetraca integer,
    idcliente integer,
    datacadastro date DEFAULT CURRENT_DATE
);


ALTER TABLE public.pet OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16586)
-- Name: pet_idpet_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pet_idpet_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pet_idpet_seq OWNER TO postgres;

--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 219
-- Name: pet_idpet_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pet_idpet_seq OWNED BY public.pet.idpet;


--
-- TOC entry 227 (class 1259 OID 16647)
-- Name: petdono; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.petdono (
    idpet integer NOT NULL,
    idcliente integer NOT NULL,
    datainicio date DEFAULT CURRENT_DATE,
    datafim date
);


ALTER TABLE public.petdono OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16570)
-- Name: raca; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.raca (
    idpetraca integer NOT NULL,
    descricao character varying(100) NOT NULL
);


ALTER TABLE public.raca OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16569)
-- Name: raca_idpetraca_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.raca_idpetraca_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.raca_idpetraca_seq OWNER TO postgres;

--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 215
-- Name: raca_idpetraca_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.raca_idpetraca_seq OWNED BY public.raca.idpetraca;


--
-- TOC entry 224 (class 1259 OID 16612)
-- Name: servicorealizado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servicorealizado (
    idservico integer NOT NULL,
    data date NOT NULL,
    iddescricaoservico integer,
    idcliente integer,
    idpet integer,
    status character varying(20) DEFAULT 'Agendado'::character varying
);


ALTER TABLE public.servicorealizado OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16611)
-- Name: servicorealizado_idservico_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.servicorealizado_idservico_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.servicorealizado_idservico_seq OWNER TO postgres;

--
-- TOC entry 4927 (class 0 OID 0)
-- Dependencies: 223
-- Name: servicorealizado_idservico_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.servicorealizado_idservico_seq OWNED BY public.servicorealizado.idservico;


--
-- TOC entry 4722 (class 2604 OID 16580)
-- Name: cliente idcliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN idcliente SET DEFAULT nextval('public.cliente_idcliente_seq'::regclass);


--
-- TOC entry 4726 (class 2604 OID 16608)
-- Name: descricaoservico iddescricaoservico; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.descricaoservico ALTER COLUMN iddescricaoservico SET DEFAULT nextval('public.descricaoservico_iddescricaoservico_seq'::regclass);


--
-- TOC entry 4729 (class 2604 OID 16638)
-- Name: pagamento idpagamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento ALTER COLUMN idpagamento SET DEFAULT nextval('public.pagamento_idpagamento_seq'::regclass);


--
-- TOC entry 4724 (class 2604 OID 16590)
-- Name: pet idpet; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet ALTER COLUMN idpet SET DEFAULT nextval('public.pet_idpet_seq'::regclass);


--
-- TOC entry 4721 (class 2604 OID 16573)
-- Name: raca idpetraca; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.raca ALTER COLUMN idpetraca SET DEFAULT nextval('public.raca_idpetraca_seq'::regclass);


--
-- TOC entry 4727 (class 2604 OID 16615)
-- Name: servicorealizado idservico; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado ALTER COLUMN idservico SET DEFAULT nextval('public.servicorealizado_idservico_seq'::regclass);


--
-- TOC entry 4906 (class 0 OID 16577)
-- Dependencies: 218
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4916 (class 0 OID 16663)
-- Dependencies: 228
-- Data for Name: clienteservico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4910 (class 0 OID 16605)
-- Dependencies: 222
-- Data for Name: descricaoservico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4914 (class 0 OID 16635)
-- Dependencies: 226
-- Data for Name: pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4908 (class 0 OID 16587)
-- Dependencies: 220
-- Data for Name: pet; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4915 (class 0 OID 16647)
-- Dependencies: 227
-- Data for Name: petdono; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4904 (class 0 OID 16570)
-- Dependencies: 216
-- Data for Name: raca; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4912 (class 0 OID 16612)
-- Dependencies: 224
-- Data for Name: servicorealizado; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 4928 (class 0 OID 0)
-- Dependencies: 217
-- Name: cliente_idcliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_idcliente_seq', 1, false);


--
-- TOC entry 4929 (class 0 OID 0)
-- Dependencies: 221
-- Name: descricaoservico_iddescricaoservico_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.descricaoservico_iddescricaoservico_seq', 1, false);


--
-- TOC entry 4930 (class 0 OID 0)
-- Dependencies: 225
-- Name: pagamento_idpagamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagamento_idpagamento_seq', 1, false);


--
-- TOC entry 4931 (class 0 OID 0)
-- Dependencies: 219
-- Name: pet_idpet_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pet_idpet_seq', 1, false);


--
-- TOC entry 4932 (class 0 OID 0)
-- Dependencies: 215
-- Name: raca_idpetraca_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.raca_idpetraca_seq', 1, false);


--
-- TOC entry 4933 (class 0 OID 0)
-- Dependencies: 223
-- Name: servicorealizado_idservico_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.servicorealizado_idservico_seq', 1, false);


--
-- TOC entry 4735 (class 2606 OID 16585)
-- Name: cliente cliente_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_cpf_key UNIQUE (cpf);


--
-- TOC entry 4737 (class 2606 OID 16583)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (idcliente);


--
-- TOC entry 4749 (class 2606 OID 16667)
-- Name: clienteservico clienteservico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteservico
    ADD CONSTRAINT clienteservico_pkey PRIMARY KEY (idcliente, idservico);


--
-- TOC entry 4741 (class 2606 OID 16610)
-- Name: descricaoservico descricaoservico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.descricaoservico
    ADD CONSTRAINT descricaoservico_pkey PRIMARY KEY (iddescricaoservico);


--
-- TOC entry 4745 (class 2606 OID 16641)
-- Name: pagamento pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_pkey PRIMARY KEY (idpagamento);


--
-- TOC entry 4739 (class 2606 OID 16593)
-- Name: pet pet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT pet_pkey PRIMARY KEY (idpet);


--
-- TOC entry 4747 (class 2606 OID 16652)
-- Name: petdono petdono_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.petdono
    ADD CONSTRAINT petdono_pkey PRIMARY KEY (idpet, idcliente);


--
-- TOC entry 4733 (class 2606 OID 16575)
-- Name: raca raca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.raca
    ADD CONSTRAINT raca_pkey PRIMARY KEY (idpetraca);


--
-- TOC entry 4743 (class 2606 OID 16618)
-- Name: servicorealizado servicorealizado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado
    ADD CONSTRAINT servicorealizado_pkey PRIMARY KEY (idservico);


--
-- TOC entry 4758 (class 2606 OID 16668)
-- Name: clienteservico clienteservico_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteservico
    ADD CONSTRAINT clienteservico_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- TOC entry 4759 (class 2606 OID 16673)
-- Name: clienteservico clienteservico_idservico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clienteservico
    ADD CONSTRAINT clienteservico_idservico_fkey FOREIGN KEY (idservico) REFERENCES public.servicorealizado(idservico);


--
-- TOC entry 4755 (class 2606 OID 16642)
-- Name: pagamento pagamento_idservico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT pagamento_idservico_fkey FOREIGN KEY (idservico) REFERENCES public.servicorealizado(idservico);


--
-- TOC entry 4750 (class 2606 OID 16599)
-- Name: pet pet_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT pet_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- TOC entry 4751 (class 2606 OID 16594)
-- Name: pet pet_idpetraca_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT pet_idpetraca_fkey FOREIGN KEY (idpetraca) REFERENCES public.raca(idpetraca);


--
-- TOC entry 4756 (class 2606 OID 16658)
-- Name: petdono petdono_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.petdono
    ADD CONSTRAINT petdono_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- TOC entry 4757 (class 2606 OID 16653)
-- Name: petdono petdono_idpet_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.petdono
    ADD CONSTRAINT petdono_idpet_fkey FOREIGN KEY (idpet) REFERENCES public.pet(idpet);


--
-- TOC entry 4752 (class 2606 OID 16624)
-- Name: servicorealizado servicorealizado_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado
    ADD CONSTRAINT servicorealizado_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- TOC entry 4753 (class 2606 OID 16619)
-- Name: servicorealizado servicorealizado_iddescricaoservico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado
    ADD CONSTRAINT servicorealizado_iddescricaoservico_fkey FOREIGN KEY (iddescricaoservico) REFERENCES public.descricaoservico(iddescricaoservico);


--
-- TOC entry 4754 (class 2606 OID 16629)
-- Name: servicorealizado servicorealizado_idpet_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado
    ADD CONSTRAINT servicorealizado_idpet_fkey FOREIGN KEY (idpet) REFERENCES public.pet(idpet);


-- Completed on 2024-09-14 10:45:34

--
-- PostgreSQL database dump complete
--

