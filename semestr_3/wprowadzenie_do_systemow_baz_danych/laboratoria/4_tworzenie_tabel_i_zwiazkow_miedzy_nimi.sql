-- 4.1.
create table productcategory (
   productcategorykey  number(2) not null,
   productcategoryname varchar2(30) not null
);

create table productsubcategory (
   productsubcategorykey  number(3) not null,
   productsubcategoryname varchar(40) not null
);

-- 4.2.
create table product (
   productkey  number(5)
      constraint product_pk primary key,
   productcode varchar2(12) not null
      constraint product_code_uq unique,
   productname varchar2(40) not null
      constraint product_name_uq unique
);

-- 4.3.
alter table productsubcategory add productcategorykey number(2) not null;

alter table product add productsubcategorykey number(3) not null;

-- 4.4.
alter table productcategory add constraint productcategory_pk primary key ( productcategorykey );

alter table productcategory add constraint productcategory_name_uq unique ( productcategoryname );

alter table productsubcategory add constraint productsubcategory_pk primary key ( productsubcategorykey );

alter table productsubcategory add constraint productsubcategory_name_uq unique ( productsubcategoryname );

-- 4.5.
alter table productsubcategory
   add constraint productsubcat_cat_fk foreign key ( productcategorykey )
      references productcategory ( productcategorykey );

alter table product
   add constraint product_subcategory_fk foreign key ( productsubcategorykey )
      references productsubcategory ( productsubcategorykey );
        
-- 4.6.
alter table productcategory disable primary key cascade;

alter table productsubcategory disable constraint productsubcategory_pk;

alter table product disable primary key;

-- 4.7.
alter table productcategory enable primary key;

alter table productsubcategory enable constraint productsubcategory_pk;

alter table product enable primary key;

alter table productsubcategory enable constraint productsubcat_cat_fk;

alter table product enable constraint product_subcategory_fk;

-- 4.9.
create table orderheader (
   orderkey          varchar2(10)
      constraint order_pk primary key,
   orderdate         date not null,
   deliverydate      date,
   customerkey       number(10) not null,
   channelkey        number(1) not null,
   paymentmethodkey  number(2) not null,
   deliverymethodkey number(1) not null,
   countrykey        number(2) not null,
   orderstatuskey    number(1) not null,
   otherinfo         varchar2(100)
);

create table orderdetail (
   orderkey         varchar2(10) not null,
   productkey       number(5) not null,
   quantity         number(2) default 1 not null,
   catalogprice     number(7, 2) not null,
   discountamount   number(5, 2),
   discountpctg     number(2),
   transactionprice number(7, 2) not null,
   constraint orderdetail_pk primary key ( orderkey,
                                           productkey )
);

alter table orderdetail
   add constraint orderdetail_order_fk foreign key ( orderkey )
      references orderheader ( orderkey );

alter table orderdetail
   add constraint orderdetail_product_fk foreign key ( productkey )
      references product ( productkey );

-- 4.10.
alter table orderdetail modify
   discountamount number(7, 2);

-- 4.11.
alter table orderheader drop ( otherinfo );