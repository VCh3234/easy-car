INSERT INTO public.users (u_email, u_name, u_phone, u_creation_date, u_password, u_update_time, u_ups, u_email_verify, u_phone_verify)
VALUES ('Sample@gmail.com', 'Alex', '295859887', '2023-05-15', '$2a$10$m4vBzXDm0Sh1pAffqpuSVeHpVr5EGy83aNr6rqxRHybY8KS1INR66', '2023-05-15 16:45:14.494049', 0, false, false);
INSERT INTO public.users (u_email, u_name, u_phone, u_creation_date, u_password, u_update_time, u_ups, u_email_verify, u_phone_verify)
VALUES ('Kate@gmail.com', 'Kate', '445654556', '2023-05-15', '$2a$10$I5wikipWTbQUdqYVxm2xsOMtdtXYFDDPJbDvkg4fASJ19x14Tklym', '2023-05-15 16:51:00.373624', 30, false, false);
INSERT INTO public.users (u_email, u_name, u_phone, u_creation_date, u_password, u_update_time, u_ups, u_email_verify, u_phone_verify)
VALUES ('UladCherap@yandex.by', 'Vlad', '333027607', '2023-05-15', '$2a$10$J3Sm0ESBPfeEmKV7RU2x1Oe.DAudYYdm5CmUtL5yclQtU9qoyIk5a', '2023-05-15 16:57:53.206913', 9, true, false);

INSERT INTO public.vehicles (v_body_type, v_brand, v_generation, v_model)
VALUES ('sedan', 'Mazda', '1', '6');

INSERT INTO public.advertisements (ad_vin, ad_car_year, ad_creation_date, ad_description, ad_engine_capacity, ad_engine_type, im_name_1, im_name_2, im_name_3, im_name_4, ad_mileage, ad_moderated, ad_price, ad_region, ad_transmission_type, ad_up_time, u_id, v_id)
VALUES ('VintestVintestqwe', 2004, '2023-05-15', 'super car', 2300, 'gas', null, null, null, null, 30000, false, 3000, 'Minsk', 'auto', '2023-05-15 16:53:07.129761', 1, 1);
INSERT INTO public.advertisements (ad_vin, ad_car_year, ad_creation_date, ad_description, ad_engine_capacity, ad_engine_type, im_name_1, im_name_2, im_name_3, im_name_4, ad_mileage, ad_moderated, ad_price, ad_region, ad_transmission_type, ad_up_time, u_id, v_id)
VALUES ('VintestVintestqwe', 1999, '2023-05-15', 'third super car', 1500, 'gas', null, null, null, null, 39912, false, 10000, 'Vitebsk', 'manual', '2023-05-15 16:54:08.066820', 1, 1);
INSERT INTO public.advertisements (ad_vin, ad_car_year, ad_creation_date, ad_description, ad_engine_capacity, ad_engine_type, im_name_1, im_name_2, im_name_3, im_name_4, ad_mileage, ad_moderated, ad_price, ad_region, ad_transmission_type, ad_up_time, u_id, v_id)
VALUES ('VintestVintestqwe', 1999, '2023-05-15', 'another super car', 2300, 'diesel', null, null, null, null, 300200, true, 6000, 'Mogilev', 'manual', '2023-05-15 16:57:53.205918', 1, 1);

INSERT INTO public.moderation (mo_creation_date, mo_message, adm_id, ad_id)
VALUES ('2023-05-15 16:56:14.532894', 'You must fill description with information rightfully.', 1, 1);
INSERT INTO public.moderation (mo_creation_date, mo_message, adm_id, ad_id)
VALUES ('2023-05-15 16:56:20.705270', 'Accept advertisement moderation', 1, 2);

INSERT INTO public.payments (p_bank_name, p_operation_name, p_date_time, p_transaction_number, u_id)
VALUES ('SBERBANK', '10_ups', '2023-05-15 16:50:06.175642', '5e6af974-f327-11ed-a05b-0242ac120003', 1);
INSERT INTO public.payments (p_bank_name, p_operation_name, p_date_time, p_transaction_number, u_id)
VALUES ('ALFABANK', '30_ups', '2023-05-15 16:51:00.372623', '5e6af974-f327-11ed-a05b-0242ac120003', 3);

