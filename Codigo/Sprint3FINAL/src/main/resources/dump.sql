--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)
-- Dumped by pg_dump version 13.6 (Ubuntu 13.6-0ubuntu0.21.10.1)

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

--
-- Name: produto; Type: TABLE; Schema: public; Owner: ti2
--

CREATE TABLE produto (
    id int primary key,
    nome VARCHAR(255),
    descricao text,
    imagem VARCHAR(255),
    categoria VARCHAR(255)
);


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: ti2
--

CREATE TABLE usuario (
    id int primary key,
    nome VARCHAR(255),
    email VARCHAR(255),
    dat_nasc date,
    senha VARCHAR(255)
);


--
-- Name: preços; Type: TABLE; Schema: public; Owner: ti2
--

CREATE TABLE precos (
    idPrecos int primary key,
    valor float
);


--
-- PostgreSQL database dump complete
--    
