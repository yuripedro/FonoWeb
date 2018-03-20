--
-- PostgreSQL database dump
--

-- Dumped from database version 10.3
-- Dumped by pg_dump version 10.3

-- Started on 2018-03-20 16:38:22 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 13241)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 201 (class 1259 OID 16404)
-- Name: arquivo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.arquivo (
    id bigint NOT NULL,
    data_inclusao date NOT NULL,
    descricao character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    descritor_id bigint,
    usuario_id bigint,
    resultado_analise character varying(255)
);


ALTER TABLE public.arquivo OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16412)
-- Name: banco_descritor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banco_descritor (
    id bigint NOT NULL,
    ativo boolean NOT NULL,
    data_ativacao date,
    descricao character varying(255)
);


ALTER TABLE public.banco_descritor OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16417)
-- Name: descritor_voz; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.descritor_voz (
    id bigint NOT NULL,
    data_ativacao date,
    descricao character varying(255),
    descritor bytea,
    arquivo_id bigint,
    banco_id bigint
);


ALTER TABLE public.descritor_voz OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16425)
-- Name: resultado; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resultado (
    id bigint NOT NULL,
    acerto double precision NOT NULL,
    data_resultado date NOT NULL,
    diagnostico character varying(255),
    usuario_id bigint
);


ALTER TABLE public.resultado OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16394)
-- Name: seq_arquivo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_arquivo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_arquivo OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16396)
-- Name: seq_bancodescritor; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_bancodescritor
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_bancodescritor OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16398)
-- Name: seq_descritorvoz; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_descritorvoz
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_descritorvoz OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 16400)
-- Name: seq_resultados; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_resultados
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_resultados OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16402)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_usuario OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16430)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id bigint NOT NULL,
    cpf character varying(255) NOT NULL,
    nome character varying(255) NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 3155 (class 0 OID 16404)
-- Dependencies: 201
-- Data for Name: arquivo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.arquivo (id, data_inclusao, descricao, nome, descritor_id, usuario_id, resultado_analise) FROM stdin;
11	2018-03-20	teste.wav	c5a602298c800292e8950c9122d358fcb9327e19f9657bb733679e0810e7b08a.wav	\N	7	Indicios de voz doente
12	2018-03-20	teste.wav	c5a602298c800292e8950c9122d358fcb9327e19f9657bb733679e0810e7b08a.wav	\N	1	Indicios de voz doente
\.


--
-- TOC entry 3156 (class 0 OID 16412)
-- Dependencies: 202
-- Data for Name: banco_descritor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.banco_descritor (id, ativo, data_ativacao, descricao) FROM stdin;
\.


--
-- TOC entry 3157 (class 0 OID 16417)
-- Dependencies: 203
-- Data for Name: descritor_voz; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.descritor_voz (id, data_ativacao, descricao, descritor, arquivo_id, banco_id) FROM stdin;
\.


--
-- TOC entry 3158 (class 0 OID 16425)
-- Dependencies: 204
-- Data for Name: resultado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resultado (id, acerto, data_resultado, diagnostico, usuario_id) FROM stdin;
\.


--
-- TOC entry 3159 (class 0 OID 16430)
-- Dependencies: 205
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id, cpf, nome) FROM stdin;
1	008.429.994-00	Sales Filho
7	111111	Yuri Pedro peba
\.


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 196
-- Name: seq_arquivo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_arquivo', 12, true);


--
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 197
-- Name: seq_bancodescritor; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_bancodescritor', 1, false);


--
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 198
-- Name: seq_descritorvoz; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_descritorvoz', 1, false);


--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 199
-- Name: seq_resultados; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_resultados', 1, false);


--
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 200
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_usuario', 8, true);


--
-- TOC entry 3015 (class 2606 OID 16411)
-- Name: arquivo arquivo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.arquivo
    ADD CONSTRAINT arquivo_pkey PRIMARY KEY (id);


--
-- TOC entry 3017 (class 2606 OID 16416)
-- Name: banco_descritor banco_descritor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banco_descritor
    ADD CONSTRAINT banco_descritor_pkey PRIMARY KEY (id);


--
-- TOC entry 3019 (class 2606 OID 16424)
-- Name: descritor_voz descritor_voz_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.descritor_voz
    ADD CONSTRAINT descritor_voz_pkey PRIMARY KEY (id);


--
-- TOC entry 3021 (class 2606 OID 16429)
-- Name: resultado resultado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resultado
    ADD CONSTRAINT resultado_pkey PRIMARY KEY (id);


--
-- TOC entry 3023 (class 2606 OID 16437)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 3026 (class 2606 OID 16448)
-- Name: descritor_voz fkedg2psrr2h5t1nqd4m2narvs7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.descritor_voz
    ADD CONSTRAINT fkedg2psrr2h5t1nqd4m2narvs7 FOREIGN KEY (arquivo_id) REFERENCES public.arquivo(id);


--
-- TOC entry 3028 (class 2606 OID 16458)
-- Name: resultado fkkwsoymaotsnaqtsjkrb36dlrj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resultado
    ADD CONSTRAINT fkkwsoymaotsnaqtsjkrb36dlrj FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3027 (class 2606 OID 16453)
-- Name: descritor_voz fklahpjhd5vpdrpf62d1nlh4jhh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.descritor_voz
    ADD CONSTRAINT fklahpjhd5vpdrpf62d1nlh4jhh FOREIGN KEY (banco_id) REFERENCES public.banco_descritor(id);


--
-- TOC entry 3025 (class 2606 OID 16443)
-- Name: arquivo fkmgbv1gml6vjcfx3iqqwm4on98; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.arquivo
    ADD CONSTRAINT fkmgbv1gml6vjcfx3iqqwm4on98 FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);


--
-- TOC entry 3024 (class 2606 OID 16438)
-- Name: arquivo fkt40evntkt8qkxif75npy53tp0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.arquivo
    ADD CONSTRAINT fkt40evntkt8qkxif75npy53tp0 FOREIGN KEY (descritor_id) REFERENCES public.descritor_voz(id);


-- Completed on 2018-03-20 16:38:23 -03

--
-- PostgreSQL database dump complete
--

