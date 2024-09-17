--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-09-16 22:15:06

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
-- TOC entry 215 (class 1259 OID 16849)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    idcliente integer NOT NULL,
    nome character varying(50) NOT NULL,
    cpf character(11) NOT NULL,
    telefone character varying(15)[],
    email character varying(50)[]
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16863)
-- Name: descricaoservico; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.descricaoservico (
    iddescricaoservico integer NOT NULL,
    servicodescricao character varying(50) NOT NULL,
    valor numeric(10,2) NOT NULL,
    idpetraca integer
);


ALTER TABLE public.descricaoservico OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16873)
-- Name: pet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pet (
    idpet integer NOT NULL,
    nome character varying(50) NOT NULL,
    idpetraca integer,
    datanascimento date
);


ALTER TABLE public.pet OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16883)
-- Name: petdono; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.petdono (
    idcliente integer NOT NULL,
    idpet integer NOT NULL
);


ALTER TABLE public.petdono OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16858)
-- Name: raca; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.raca (
    idpetraca integer NOT NULL,
    descricao character varying(50) NOT NULL
);


ALTER TABLE public.raca OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16898)
-- Name: servicorealizado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servicorealizado (
    idservico integer NOT NULL,
    iddescricaoservico integer NOT NULL,
    idpet integer NOT NULL,
    data date NOT NULL,
    status character varying(50) NOT NULL
);


ALTER TABLE public.servicorealizado OWNER TO postgres;

--
-- TOC entry 4870 (class 0 OID 16849)
-- Dependencies: 215
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (idcliente, nome, cpf, telefone, email) FROM stdin;
1	Carlos Silva	12345678901	{(47)912345678}	{carlos@email.com}
2	Ana Souza	23456789012	{(47)923456789}	{ana@email.com}
3	João Lima	34567890123	{(47)934567890}	{joao@email.com}
4	Maria Oliveira	45678901234	{(41)945678901}	{maria@email.com}
5	Fernanda Costa	56789012345	{(41)956789012}	{fernanda@email.com}
6	José Santos	67890123456	{(47)967890123}	{jose@email.com}
7	Paula Ferreira	78901234567	{(47)978901234}	{paula@email.com}
8	Rafael Almeida	89012345678	{(47)989012345}	{rafael@email.com}
9	Juliana Souza	90123456789	{(41)990123456}	{juliana@email.com}
10	Roberto Lima	01234567890	{(47)900234567}	{roberto@email.com}
\.


--
-- TOC entry 4872 (class 0 OID 16863)
-- Dependencies: 217
-- Data for Name: descricaoservico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.descricaoservico (iddescricaoservico, servicodescricao, valor, idpetraca) FROM stdin;
1	Banho	30.00	1
2	Banho	40.00	2
3	Tosa	50.00	1
4	Banho	50.00	4
5	Tosa	150.00	3
6	Banho	100.00	3
7	Corte de Unhas	40.00	2
\.


--
-- TOC entry 4873 (class 0 OID 16873)
-- Dependencies: 218
-- Data for Name: pet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pet (idpet, nome, idpetraca, datanascimento) FROM stdin;
\.


--
-- TOC entry 4874 (class 0 OID 16883)
-- Dependencies: 219
-- Data for Name: petdono; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.petdono (idcliente, idpet) FROM stdin;
\.


--
-- TOC entry 4871 (class 0 OID 16858)
-- Dependencies: 216
-- Data for Name: raca; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.raca (idpetraca, descricao) FROM stdin;
1	Cão Pequeno
2	Cão Médio
3	Cão Grande
4	Gato Pequeno
5	Gato Grande
\.


--
-- TOC entry 4875 (class 0 OID 16898)
-- Dependencies: 220
-- Data for Name: servicorealizado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.servicorealizado (idservico, iddescricaoservico, idpet, data, status) FROM stdin;
\.


--
-- TOC entry 4708 (class 2606 OID 16857)
-- Name: cliente cliente_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_cpf_key UNIQUE (cpf);


--
-- TOC entry 4710 (class 2606 OID 16855)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (idcliente);


--
-- TOC entry 4714 (class 2606 OID 16867)
-- Name: descricaoservico descricaoservico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.descricaoservico
    ADD CONSTRAINT descricaoservico_pkey PRIMARY KEY (iddescricaoservico);


--
-- TOC entry 4716 (class 2606 OID 16877)
-- Name: pet pet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT pet_pkey PRIMARY KEY (idpet);


--
-- TOC entry 4718 (class 2606 OID 16887)
-- Name: petdono petdono_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.petdono
    ADD CONSTRAINT petdono_pkey PRIMARY KEY (idcliente, idpet);


--
-- TOC entry 4712 (class 2606 OID 16862)
-- Name: raca raca_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.raca
    ADD CONSTRAINT raca_pkey PRIMARY KEY (idpetraca);


--
-- TOC entry 4720 (class 2606 OID 16902)
-- Name: servicorealizado servicorealizado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado
    ADD CONSTRAINT servicorealizado_pkey PRIMARY KEY (idservico);


--
-- TOC entry 4721 (class 2606 OID 16868)
-- Name: descricaoservico descricaoservico_idpetraca_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.descricaoservico
    ADD CONSTRAINT descricaoservico_idpetraca_fkey FOREIGN KEY (idpetraca) REFERENCES public.raca(idpetraca);


--
-- TOC entry 4722 (class 2606 OID 16878)
-- Name: pet pet_idpetraca_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pet
    ADD CONSTRAINT pet_idpetraca_fkey FOREIGN KEY (idpetraca) REFERENCES public.raca(idpetraca);


--
-- TOC entry 4723 (class 2606 OID 16888)
-- Name: petdono petdono_idcliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.petdono
    ADD CONSTRAINT petdono_idcliente_fkey FOREIGN KEY (idcliente) REFERENCES public.cliente(idcliente);


--
-- TOC entry 4724 (class 2606 OID 16893)
-- Name: petdono petdono_idpet_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.petdono
    ADD CONSTRAINT petdono_idpet_fkey FOREIGN KEY (idpet) REFERENCES public.pet(idpet);


--
-- TOC entry 4725 (class 2606 OID 16903)
-- Name: servicorealizado servicorealizado_iddescricaoservico_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado
    ADD CONSTRAINT servicorealizado_iddescricaoservico_fkey FOREIGN KEY (iddescricaoservico) REFERENCES public.descricaoservico(iddescricaoservico);


--
-- TOC entry 4726 (class 2606 OID 16908)
-- Name: servicorealizado servicorealizado_idpet_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicorealizado
    ADD CONSTRAINT servicorealizado_idpet_fkey FOREIGN KEY (idpet) REFERENCES public.pet(idpet);


-- Completed on 2024-09-16 22:15:07

--
-- PostgreSQL database dump complete
--

