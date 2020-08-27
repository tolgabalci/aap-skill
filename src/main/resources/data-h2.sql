DROP TABLE IF EXISTS "AAP_CATALOG";

CREATE TABLE "AAP_CATALOG"
   (    "ID" NUMBER NOT NULL,
    "DOMAIN" VARCHAR(20),
    "SUMMARY" VARCHAR(32));

insert into AAP_CATALOG (ID, "DOMAIN", SUMMARY) values (1, 'domain1', 'summary1');
insert into AAP_CATALOG (ID, "DOMAIN", SUMMARY) values (1, 'domain1', 'summary2');
insert into AAP_CATALOG (ID, "DOMAIN", SUMMARY) values (1, 'domain1', 'summary3');
insert into AAP_CATALOG (ID, "DOMAIN", SUMMARY) values (1, 'domain2', 'summary21');
insert into AAP_CATALOG (ID, "DOMAIN", SUMMARY) values (1, 'domain3', 'summary3');