--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

-- Started on 2024-09-16 22:27:51

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

INSERT INTO public.cliente VALUES (1, 'Carlos Silva', '12345678901', '{(47)912345678}', '{carlos@email.com}');
INSERT INTO public.cliente VALUES (2, 'Ana Souza', '23456789012', '{(47)923456789}', '{ana@email.com}');
INSERT INTO public.cliente VALUES (3, 'João Lima', '34567890123', '{(47)934567890}', '{joao@email.com}');
INSERT INTO public.cliente VALUES (4, 'Maria Oliveira', '45678901234', '{(41)945678901}', '{maria@email.com}');
INSERT INTO public.cliente VALUES (5, 'Fernanda Costa', '56789012345', '{(41)956789012}', '{fernanda@email.com}');
INSERT INTO public.cliente VALUES (6, 'José Santos', '67890123456', '{(47)967890123}', '{jose@email.com}');
INSERT INTO public.cliente VALUES (7, 'Paula Ferreira', '78901234567', '{(47)978901234}', '{paula@email.com}');
INSERT INTO public.cliente VALUES (8, 'Rafael Almeida', '89012345678', '{(47)989012345}', '{rafael@email.com}');
INSERT INTO public.cliente VALUES (9, 'Juliana Souza', '90123456789', '{(41)990123456}', '{juliana@email.com}');
INSERT INTO public.cliente VALUES (10, 'Roberto Lima', '01234567890', '{(47)900234567}', '{roberto@email.com}');


--
-- TOC entry 4872 (class 0 OID 16863)
-- Dependencies: 217
-- Data for Name: descricaoservico; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.descricaoservico VALUES (1, 'Banho', 30.00, 1);
INSERT INTO public.descricaoservico VALUES (2, 'Banho', 40.00, 2);
INSERT INTO public.descricaoservico VALUES (3, 'Tosa', 50.00, 1);
INSERT INTO public.descricaoservico VALUES (4, 'Banho', 50.00, 4);
INSERT INTO public.descricaoservico VALUES (5, 'Tosa', 150.00, 3);
INSERT INTO public.descricaoservico VALUES (6, 'Banho', 100.00, 3);
INSERT INTO public.descricaoservico VALUES (7, 'Corte de Unhas', 40.00, 2);


--
-- TOC entry 4873 (class 0 OID 16873)
-- Dependencies: 218
-- Data for Name: pet; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pet VALUES (1, 'Rex', 1, '2020-05-15');
INSERT INTO public.pet VALUES (2, 'Mia', 2, '2019-03-22');
INSERT INTO public.pet VALUES (3, 'Luna', 3, '2021-07-30');
INSERT INTO public.pet VALUES (4, 'Max', 4, '2018-12-05');
INSERT INTO public.pet VALUES (5, 'Bella', 5, '2022-01-10');
INSERT INTO public.pet VALUES (6, 'Charlie', 3, '2020-08-25');


--
-- TOC entry 4874 (class 0 OID 16883)
-- Dependencies: 219
-- Data for Name: petdono; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.petdono VALUES (1, 1);
INSERT INTO public.petdono VALUES (1, 2);
INSERT INTO public.petdono VALUES (2, 3);
INSERT INTO public.petdono VALUES (2, 4);
INSERT INTO public.petdono VALUES (3, 5);
INSERT INTO public.petdono VALUES (3, 6);


--
-- TOC entry 4871 (class 0 OID 16858)
-- Dependencies: 216
-- Data for Name: raca; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.raca VALUES (1, 'Cão Pequeno');
INSERT INTO public.raca VALUES (2, 'Cão Médio');
INSERT INTO public.raca VALUES (3, 'Cão Grande');
INSERT INTO public.raca VALUES (4, 'Gato Pequeno');
INSERT INTO public.raca VALUES (5, 'Gato Grande');


--
-- TOC entry 4875 (class 0 OID 16898)
-- Dependencies: 220
-- Data for Name: servicorealizado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.servicorealizado VALUES (1, 1, 1, '2024-09-01', 'Concluído');
INSERT INTO public.servicorealizado VALUES (2, 2, 2, '2024-09-05', 'Em andamento');
INSERT INTO public.servicorealizado VALUES (3, 3, 3, '2024-09-10', 'Concluído');
INSERT INTO public.servicorealizado VALUES (4, 4, 4, '2024-09-15', 'Cancelado');
INSERT INTO public.servicorealizado VALUES (5, 5, 5, '2024-09-20', 'Concluído');
INSERT INTO public.servicorealizado VALUES (6, 6, 6, '2024-09-25', 'Em andamento');


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


-- Completed on 2024-09-16 22:27:52

--
-- PostgreSQL database dump complete
--

