--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

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
-- Name: cart; Type: TABLE; Schema: public; Owner: tesbuana
--

CREATE TABLE public.cart (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    description character varying(255),
    mark_for_delete boolean NOT NULL,
    total_products_price double precision,
    fk_users_id character varying(255) NOT NULL
);


ALTER TABLE public.cart OWNER TO tesbuana;

--
-- Name: cart_items; Type: TABLE; Schema: public; Owner: tesbuana
--

CREATE TABLE public.cart_items (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    description character varying(255),
    mark_for_delete boolean NOT NULL,
    product_price double precision NOT NULL,
    product_quantity integer NOT NULL,
    fk_cart_id character varying(255) NOT NULL,
    fk_product_id character varying(255) NOT NULL
);


ALTER TABLE public.cart_items OWNER TO tesbuana;

--
-- Name: product_category; Type: TABLE; Schema: public; Owner: tesbuana
--

CREATE TABLE public.product_category (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    description character varying(255),
    mark_for_delete boolean NOT NULL,
    product_category_name character varying(255) NOT NULL
);


ALTER TABLE public.product_category OWNER TO tesbuana;

--
-- Name: product_review; Type: TABLE; Schema: public; Owner: tesbuana
--

CREATE TABLE public.product_review (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    description character varying(255),
    mark_for_delete boolean NOT NULL,
    comment character varying(255) NOT NULL,
    rating double precision NOT NULL,
    fk_product_id character varying(255) NOT NULL,
    fk_users_id character varying(255) NOT NULL
);


ALTER TABLE public.product_review OWNER TO tesbuana;

--
-- Name: product_stock; Type: TABLE; Schema: public; Owner: tesbuana
--

CREATE TABLE public.product_stock (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    description character varying(255),
    mark_for_delete boolean NOT NULL,
    product_brand character varying(255) NOT NULL,
    product_code character varying(255) NOT NULL,
    product_exp_date timestamp without time zone,
    product_image character varying(255),
    product_name character varying(255) NOT NULL,
    product_net_weight integer,
    product_price double precision NOT NULL,
    product_series character varying(255),
    product_quantity_in_stock integer NOT NULL,
    product_rating double precision,
    product_ready_status boolean NOT NULL,
    product_size character varying(255),
    fk_product_category_id character varying(255) NOT NULL
);


ALTER TABLE public.product_stock OWNER TO tesbuana;

--
-- Name: role; Type: TABLE; Schema: public; Owner: tesbuana
--

CREATE TABLE public.role (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    description character varying(255),
    mark_for_delete boolean NOT NULL,
    role_name character varying(255) NOT NULL
);


ALTER TABLE public.role OWNER TO tesbuana;

--
-- Name: users; Type: TABLE; Schema: public; Owner: tesbuana
--

CREATE TABLE public.users (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    deleted_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    description character varying(255),
    mark_for_delete boolean NOT NULL,
    user_image character varying(255),
    mobile_phone character varying(255),
    names character varying(255),
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    fk_role_id character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO tesbuana;

--
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: tesbuana
--

COPY public.cart (id, created_by, created_date, deleted_date, updated_by, updated_date, description, mark_for_delete, total_products_price, fk_users_id) FROM stdin;
dcaa2fd5-b69b-49ac-acc3-e82df4bf474c	system	2021-12-24 11:07:26.41	\N	system	2021-12-24 11:07:26.41	\N	f	\N	92053d21-2c21-4306-9742-158402e20d24
90b34446-f813-4d46-ace6-5cd5b50c4670	system	2021-12-24 11:10:02.291	\N	system	2021-12-24 11:10:02.291	\N	f	\N	b42faf48-38e2-4403-9e7f-960e275bb7b1
ca7a3d29-00a3-49a2-b676-93d353dcdb1f	system	2021-12-24 12:33:17.632	\N	nadyaaamalia30@gmail.com	2021-12-24 12:51:41.252	\N	f	2870000	cbafe3b1-7a42-4ea4-ae73-c8a479b02b18
\.


--
-- Data for Name: cart_items; Type: TABLE DATA; Schema: public; Owner: tesbuana
--

COPY public.cart_items (id, created_by, created_date, deleted_date, updated_by, updated_date, description, mark_for_delete, product_price, product_quantity, fk_cart_id, fk_product_id) FROM stdin;
c37e81f7-8d3e-47f2-bdaa-d36680643a4e	nadyaaamalia30@gmail.com	2021-12-24 12:39:03.114	\N	nadyaaamalia30@gmail.com	2021-12-24 12:39:08.284	\N	f	2793000	7	ca7a3d29-00a3-49a2-b676-93d353dcdb1f	894a6d9e-072b-4ffa-b41f-d4e37bbd5778
be6d7913-dedb-45bb-b8b1-53b555aae5f2	nadyaaamalia30@gmail.com	2021-12-24 12:51:41.251	\N	nadyaaamalia30@gmail.com	2021-12-24 12:51:41.251	\N	f	77000	2	ca7a3d29-00a3-49a2-b676-93d353dcdb1f	e42bc69b-a19c-422f-9005-42973d8af343
\.


--
-- Data for Name: product_category; Type: TABLE DATA; Schema: public; Owner: tesbuana
--

COPY public.product_category (id, created_by, created_date, deleted_date, updated_by, updated_date, description, mark_for_delete, product_category_name) FROM stdin;
83ba1c2c-d8e9-45ff-98a9-30fece7d3828	fian1@gmail.com	2021-12-24 11:10:45.672	\N	fian1@gmail.com	2021-12-24 11:10:45.672	\N	f	food_snack
ab062c28-694f-472f-8c24-becb4a0d3894	fian1@gmail.com	2021-12-24 11:10:45.673	\N	fian1@gmail.com	2021-12-24 11:10:45.673	\N	f	drink
a3ff6be3-a1a8-49ca-92f5-c0f23a84a0ba	fian1@gmail.com	2021-12-24 11:10:45.673	\N	fian1@gmail.com	2021-12-24 11:10:45.673	\N	f	electronic
bebd049a-2511-4903-b70b-b6a3dd16870d	fian1@gmail.com	2021-12-24 11:10:45.673	\N	fian1@gmail.com	2021-12-24 11:10:45.673	\N	f	household_appliance
\.


--
-- Data for Name: product_review; Type: TABLE DATA; Schema: public; Owner: tesbuana
--

COPY public.product_review (id, created_by, created_date, deleted_date, updated_by, updated_date, description, mark_for_delete, comment, rating, fk_product_id, fk_users_id) FROM stdin;
fb7c3ac8-6daa-4b5f-9878-5de7f4800a08	tomholland1@gmail.com	2021-12-24 11:49:10.008	\N	tomholland1@gmail.com	2021-12-24 11:49:10.008	\N	f	Nice Coffee, the delivery is fast too, best service	4	b8ef2953-ede8-494f-904e-30a561472d08	92053d21-2c21-4306-9742-158402e20d24
d9897b91-299e-4c5c-ae0f-905fe20a1fb2	tomholland1@gmail.com	2021-12-24 11:53:16.655	\N	tomholland1@gmail.com	2021-12-24 11:53:16.655	\N	f	Very Good Pan. Product Matches with the image	5	894a6d9e-072b-4ffa-b41f-d4e37bbd5778	92053d21-2c21-4306-9742-158402e20d24
97495db6-f821-45c9-a95b-605158c884a6	tomholland1@gmail.com	2021-12-24 11:55:04.704	\N	tomholland1@gmail.com	2021-12-24 11:55:04.704	\N	f	Not appropriate with the price	2	efb91f98-1ace-414a-b882-ab28b0e2a286	92053d21-2c21-4306-9742-158402e20d24
7a7430ba-66e2-4486-bf30-9254bb73cd1c	tomholland1@gmail.com	2021-12-24 12:03:19.426	\N	tomholland1@gmail.com	2021-12-24 12:03:19.426	\N	f	Ok, the service is fast, the products packing is good	3	dd84a411-2f58-41f3-99e7-5d12a9ef39d7	92053d21-2c21-4306-9742-158402e20d24
461d2e54-5e38-47c1-8d39-9034e68e7752	fian1@gmail.com	2021-12-24 12:11:35.074	\N	fian1@gmail.com	2021-12-24 12:11:35.074	\N	f	Packing is bad	1	e42bc69b-a19c-422f-9005-42973d8af343	b42faf48-38e2-4403-9e7f-960e275bb7b1
bb9bf410-30df-4ed9-9981-009e82318b55	tomholland1@gmail.com	2021-12-24 12:27:38.763	\N	tomholland1@gmail.com	2021-12-24 12:27:38.763	\N	f	packing is not good, please improve it	2	e42bc69b-a19c-422f-9005-42973d8af343	92053d21-2c21-4306-9742-158402e20d24
90616b62-db54-4b97-93a3-fb16f082e4d8	fian1@gmail.com	2021-12-24 12:28:53.631	\N	fian1@gmail.com	2021-12-24 12:28:53.631	\N	f	Very Good.... Thankyou	5	b8ef2953-ede8-494f-904e-30a561472d08	b42faf48-38e2-4403-9e7f-960e275bb7b1
2cd370bb-340c-4fdb-92fb-19edb1548374	fian1@gmail.com	2021-12-24 12:30:13.979	\N	fian1@gmail.com	2021-12-24 12:30:13.979	\N	f	The packet is arrived due the proper date, but the product is fine	3	efb91f98-1ace-414a-b882-ab28b0e2a286	b42faf48-38e2-4403-9e7f-960e275bb7b1
c7043449-5399-4dde-8c18-a0beb46ab7d8	nadyaaamalia30@gmail.com	2021-12-24 12:34:05.621	\N	nadyaaamalia30@gmail.com	2021-12-24 12:34:05.621	\N	f	Excellent drink	5	dd84a411-2f58-41f3-99e7-5d12a9ef39d7	cbafe3b1-7a42-4ea4-ae73-c8a479b02b18
5b9a288b-976e-4a29-a069-dde4e61fb2f2	nadyaaamalia30@gmail.com	2021-12-24 12:34:52.147	\N	nadyaaamalia30@gmail.com	2021-12-24 12:34:52.147	\N	f	Yee, pan from good brand. Packing is so secure and fast	4	894a6d9e-072b-4ffa-b41f-d4e37bbd5778	cbafe3b1-7a42-4ea4-ae73-c8a479b02b18
7847fdc4-b84a-4bc6-ad5d-b76c763c2432	nadyaaamalia30@gmail.com	2021-12-24 12:52:21.693	\N	nadyaaamalia30@gmail.com	2021-12-24 12:52:21.693	\N	f	delicious!	3	e42bc69b-a19c-422f-9005-42973d8af343	cbafe3b1-7a42-4ea4-ae73-c8a479b02b18
6fcc83cb-f7c7-4054-bc40-68158bf7c11d	fian1@gmail.com	2021-12-24 13:51:13.443	\N	fian1@gmail.com	2021-12-24 13:51:13.443	\N	f	Best Design and can keep temp low	4	73523d2a-5bdd-4188-b4d2-6f6bca99730a	b42faf48-38e2-4403-9e7f-960e275bb7b1
\.


--
-- Data for Name: product_stock; Type: TABLE DATA; Schema: public; Owner: tesbuana
--

COPY public.product_stock (id, created_by, created_date, deleted_date, updated_by, updated_date, description, mark_for_delete, product_brand, product_code, product_exp_date, product_image, product_name, product_net_weight, product_price, product_series, product_quantity_in_stock, product_rating, product_ready_status, product_size, fk_product_category_id) FROM stdin;
efb91f98-1ace-414a-b882-ab28b0e2a286	fian1@gmail.com	2021-12-24 11:12:18.119	\N	fian1@gmail.com	2021-12-24 12:30:13.981	\N	f	Maspion	MAT67Y0P	\N	https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full/maspion_maspion-power-fan-pw-500s-hitam_full02.jpg	power_fan_maspion	9000	650000	PW2001RC	35	2.5	t	blade(20inch)	a3ff6be3-a1a8-49ca-92f5-c0f23a84a0ba
dd84a411-2f58-41f3-99e7-5d12a9ef39d7	fian1@gmail.com	2021-12-24 11:12:18.12	\N	nadyaaamalia30@gmail.com	2021-12-24 12:34:05.624	\N	f	Coca_Cola	CCCY445GH	2022-08-11 07:00:00	https://cemilku.com/wp-content/uploads/2016/09/coca-cola-zero1.jpg	coca_cola_zero	480	7000	\N	82	4	t	330ml	ab062c28-694f-472f-8c24-becb4a0d3894
894a6d9e-072b-4ffa-b41f-d4e37bbd5778	fian1@gmail.com	2021-12-24 11:12:18.12	\N	nadyaaamalia30@gmail.com	2021-12-24 12:34:52.149	\N	f	OUMBÄRLIG	KOP25109H	\N	https://d2xjmi1k71iy2m.cloudfront.net/dairyfarm/id/images/128/0712829_PE729092_S5.jpg	pan	1230	399000	\N	76	4.5	t	height(15cm),diameter(23cm)	bebd049a-2511-4903-b70b-b6a3dd16870d
e42bc69b-a19c-422f-9005-42973d8af343	fian1@gmail.com	2021-12-24 11:12:18.12	\N	nadyaaamalia30@gmail.com	2021-12-24 12:52:21.695	\N	f	Oreo	RRP1352VB	2023-01-10 07:00:00	https://cf.shopee.co.id/file/01321c564bd09c84e1de13e3b6e7bbe6	big_cans_oreo_selection_biscuit	313	38500	\N	50	2	t	\N	83ba1c2c-d8e9-45ff-98a9-30fece7d3828
73523d2a-5bdd-4188-b4d2-6f6bca99730a	fian1@gmail.com	2021-12-24 13:49:39.347	\N	fian1@gmail.com	2021-12-24 13:51:13.444	This Cooling Pad has a unique design that has six fans that work simultaneously so that your laptop will stay protected from overheating	f	Masteron	MM150CLP	\N	https://images.tokopedia.net/img/cache/900/hDjmkQ/2021/1/18/7238a1cd-0a28-4881-b695-d257412f3184.jpg	Cooling Pad Laptop 6 Fan	1400	168100	Blue HV-F2063AAA	40	4	t	36.5 x 28 x 2 cm	a3ff6be3-a1a8-49ca-92f5-c0f23a84a0ba
b8ef2953-ede8-494f-904e-30a561472d08	fian1@gmail.com	2021-12-24 11:12:18.119	\N	fian1@gmail.com	2021-12-24 13:52:30.831	Special Kapal Api Coffee.\n100% Pure Coffee.	f	kapal_api	KA124GHY	2022-04-23 07:00:00	https://www.static-src.com/wcsstore/Indraprastha/images/catalog/full/MTA-3977995/kapal-api_kapal-api-kopi-special--165-g-_full03.jpg	kapal api special	165	14000		100	4.5	t		83ba1c2c-d8e9-45ff-98a9-30fece7d3828
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: tesbuana
--

COPY public.role (id, created_by, created_date, deleted_date, updated_by, updated_date, description, mark_for_delete, role_name) FROM stdin;
55c5595f-5411-44a3-baa6-bdd6a524a1ce	system	2021-12-24 10:51:17.936	\N	system	2021-12-24 10:51:17.936	\N	f	admin
14172682-9948-4661-a99e-884384317c1e	system	2021-12-24 10:51:17.95	\N	system	2021-12-24 10:51:17.95	\N	f	customer
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: tesbuana
--

COPY public.users (id, created_by, created_date, deleted_date, updated_by, updated_date, description, mark_for_delete, user_image, mobile_phone, names, password, username, fk_role_id) FROM stdin;
b42faf48-38e2-4403-9e7f-960e275bb7b1	system	2021-12-24 11:10:02.29	\N	system	2021-12-24 11:10:02.29	\N	f	\N	\N	Fian Ilham	1000:964ca4f7ddcb74f07ea4c5eb6daf9a75:d49c3f1829c14506cd3db9d74012815056a232696cb258222b267cb511651060d5727e2d5e32d9f06c7341ce1163a5f432c814726df464766d6d3ad7e3a48537	fian1@gmail.com	55c5595f-5411-44a3-baa6-bdd6a524a1ce
92053d21-2c21-4306-9742-158402e20d24	system	2021-12-24 11:07:26.386	\N	tomholland1@gmail.com	2021-12-24 11:30:57.821	\N	f	https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tom_Holland_by_Gage_Skidmore.jpg/800px-Tom_Holland_by_Gage_Skidmore.jpg	+6288777513890	Tom Holland	1000:2dce23844535e50ae1f14315f3fd12f0:98262362d4201a4623bc5744d5b53d53e32a2565ea1a52c586e2709a9d14995e58d988d2dd8b5e99ff6aa3f91933f877b67d2aacc694c8a222386316bdfe15a3	tomholland1@gmail.com	14172682-9948-4661-a99e-884384317c1e
cbafe3b1-7a42-4ea4-ae73-c8a479b02b18	system	2021-12-24 12:33:17.616	\N	system	2021-12-24 12:33:17.616	\N	f	https://lh3.googleusercontent.com/a-/AOh14GhOyXplgzMX3HwpY1zJPxT-yIQQ-qJUeh00tO5T1w=s96-c	\N	29. Nadya Amalia Tsani	1000:2673a472e0fcef14209c56383d3936da:55d2d3841e0c133d8f5c989d6e611b1d4d5374b1d839f96df4e47eda9391ce0f86b84d829ca71ec21f8fda7c764f3166cbcb9110cb3fec0e4355e5aab4472022	nadyaaamalia30@gmail.com	14172682-9948-4661-a99e-884384317c1e
\.


--
-- Name: cart_items cart_items_pkey; Type: CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_pkey PRIMARY KEY (id);


--
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);


--
-- Name: product_category product_category_pkey; Type: CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.product_category
    ADD CONSTRAINT product_category_pkey PRIMARY KEY (id);


--
-- Name: product_review product_review_pkey; Type: CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.product_review
    ADD CONSTRAINT product_review_pkey PRIMARY KEY (id);


--
-- Name: product_stock product_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.product_stock
    ADD CONSTRAINT product_stock_pkey PRIMARY KEY (id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: cart_items fk40lva603a95s6cc4iry6rjcnj; Type: FK CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT fk40lva603a95s6cc4iry6rjcnj FOREIGN KEY (fk_cart_id) REFERENCES public.cart(id);


--
-- Name: users fk6nh2w9718ctuh8742tjmkog7l; Type: FK CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk6nh2w9718ctuh8742tjmkog7l FOREIGN KEY (fk_role_id) REFERENCES public.role(id);


--
-- Name: product_review fk8hoo376uq3g4fpcad8y3q13te; Type: FK CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.product_review
    ADD CONSTRAINT fk8hoo376uq3g4fpcad8y3q13te FOREIGN KEY (fk_users_id) REFERENCES public.users(id);


--
-- Name: cart fkdbwo317ehvfrqbkq7e1hbjrd3; Type: FK CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT fkdbwo317ehvfrqbkq7e1hbjrd3 FOREIGN KEY (fk_users_id) REFERENCES public.users(id);


--
-- Name: product_review fkdynculwsuvmcwe2gm2hilbf9f; Type: FK CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.product_review
    ADD CONSTRAINT fkdynculwsuvmcwe2gm2hilbf9f FOREIGN KEY (fk_product_id) REFERENCES public.product_stock(id);


--
-- Name: product_stock fkqa6c4xnrqtoxhqhfrr32abw9c; Type: FK CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.product_stock
    ADD CONSTRAINT fkqa6c4xnrqtoxhqhfrr32abw9c FOREIGN KEY (fk_product_category_id) REFERENCES public.product_category(id);


--
-- Name: cart_items fkt7ir74t5uipsr26hiqot3ko7u; Type: FK CONSTRAINT; Schema: public; Owner: tesbuana
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT fkt7ir74t5uipsr26hiqot3ko7u FOREIGN KEY (fk_product_id) REFERENCES public.product_stock(id);


--
-- PostgreSQL database dump complete
--

