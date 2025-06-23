create table salesterritory (
   salesterritorykey  number(3)
      constraint salesterritory_pk primary key,
   salesterritoryname varchar2(50) not null
      constraint salesterritory_name_uq unique
);

create table country (
   countrykey        number(2)
      constraint country_pk primary key,
   countryname       varchar2(50) not null
      constraint country_name_uq unique,
   countrycode       char(2)
      constraint country_code_uq unique,
   salesterritorykey number(3) not null,
   constraint country_salesterritory_fk foreign key ( salesterritorykey )
      references salesterritory ( salesterritorykey )
);

create table city (
   citykey    number(3)
      constraint city_pk primary key,
   cityname   varchar2(30) not null,
   countrykey number(2) not null,
   constraint city_country_fk foreign key ( countrykey )
      references country ( countrykey )
);

create table customer (
   customerkey number(10)
      constraint customer_pk primary key,
   firstname   varchar2(25) not null,
   lastname    varchar2(45) not null,
   citykey     number(3) not null,
   constraint customer_city_fk foreign key ( citykey )
      references city ( citykey )
);

create table orderchannel (
   channelkey  number(1)
      constraint orderchannel_pk primary key,
   channelname varchar2(20) not null
      constraint orderchannel_name_uq unique
);

create table paymentmethod (
   paymentmethodkey  number(2)
      constraint paymentmethod_pk primary key,
   paymentmethodname varchar2(20) not null
      constraint payment_name_un unique
);

create table orderstatus (
   orderstatuskey  number(1)
      constraint orderstatus_pk primary key,
   orderstatusname varchar2(20) not null
      constraint orderstatus_name_uq unique
);

create table deliverymethod (
   deliverymethodkey  number(1)
      constraint deliverymethod_pk primary key,
   deliverymethodname varchar2(20) not null
      constraint delivery_name_uq unique
);

alter table orderheader add deliverycost number(5, 2);

alter table orderheader
   add constraint order_customer_fk foreign key ( customerkey )
      references customer ( customerkey )
   add constraint order_orderchannel_fk foreign key ( channelkey )
      references orderchannel ( channelkey )
   add constraint order_paymentmethod_fk foreign key ( paymentmethodkey )
      references paymentmethod ( paymentmethodkey )
   add constraint order_deliverymethod_fk foreign key ( deliverymethodkey )
      references deliverymethod ( deliverymethodkey )
   add constraint order_country_fk foreign key ( countrykey )
      references country ( countrykey )
   add constraint order_status_fk foreign key ( orderstatuskey )
      references orderstatus ( orderstatuskey );

-- 5.4.
insert into customer values ( 30000,
                              'Ethan',
                              'Hunt',
                              522 );

insert into customer (
   firstname,
   lastname,
   customerkey,
   citykey
) values ( 'Peter',
           'Maverick',
           30001,
           360 );

-- 5.5.
insert into orderdetail (
   orderkey,
   productkey,
   quantity,
   catalogprice,
   transactionprice
) values ( 'SO56205',
           228,
           2,
           49.99,
           49.99 );

-- 5.6.
insert all into deliverymethod values ( 4,
                                        'DPD Courier' ) into deliverymethod values ( 5,
                                                                                     'Parcel GLS' ) select *
                 from dual;

-- 5.7.
insert into salesterritory
   select *
     from salesterritorybckp;

-- 5.8.
update orderdetail
   set discountamount = 0,
       discountpctg = 0;

-- 5.9.
update orderdetail
   set
   quantity = 5
 where orderkey = 'SO56205'
   and productkey = 217;

update orderdetail
   set
   discountpctg = 15
 where orderkey = 'SO56205'
   and productkey = 217;

update orderdetail
   set
   discountamount = catalogprice * discountpctg / 100
 where orderkey = 'SO56205'
   and productkey = 217;

update orderdetail
   set
   transactionprice = catalogprice - discountamount
 where orderkey = 'SO56205'
   and productkey = 217;

-- 5.10.
update orderdetail
   set
   discountamount = (
      select min(discountamount)
        from orderdetail
       where productkey = 222
         and discountamount > 0
   )
 where productkey = 222
   and discountamount = 0;

-- 5.12.
delete from orderdetail
 where orderkey = 'SO9998';

delete from orderheader
 where orderstatuskey = 5;

-- 5.13.
delete from salesterritory
 where salesterritorykey not in (
   select distinct salesterritorykey
     from country
);

delete from productcategory
 where productcategorykey not in (
   select distinct productcategorykey
     from productsubcategory
    where productsubcategorykey in (
      select distinct productsubcategorykey
        from product
   )
);