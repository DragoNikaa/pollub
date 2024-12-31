-- 6.1.
select *
  from customer;

select cityname "City Full Name",
       citykey "ID City",
       countrykey "ID Country"
  from city;

-- 6.2.
select cityname "City Full Name"
  from city;

select countryname "Country Name",
       countrycode "Country Code"
  from country;

-- 6.3.
select distinct citykey "ID City"
  from customer;

select distinct lastname,
                firstname
  from customer;

-- 6.4.
select countryname
       || ' ('
       || countrycode
       || ')' "Country Name (Country Code)"
  from country;

select countryname "Country Name",
       case
          when countryname = 'United States' then
             'North America'
          else
             'Other continent'
       end "Continent Name"
  from country;

select countryname "Country Name",
       case
          when countryname in ( 'United States',
                                'Canada' ) then
             'North America'
          else
             'Other continent'
       end "Continent Name"
  from country;

-- 6.5.
select distinct extract(year from orderdate) "Order Year"
  from orderheader;

select distinct extract(year from orderdate) "Order Year",
                to_char(
                   orderdate,
                   'month'
                ) "Order Month Name"
  from orderheader;

select distinct extract(year from orderdate) "Order Year",
                to_char(
                   orderdate,
                   'day'
                ) "Order Day Name of Week"
  from orderheader;

-- 6.6.
select distinct extract(year from orderdate) "Order Year"
  from orderheader
 order by 1 desc;

select distinct extract(year from orderdate) "Order Year",
                to_char(
                   orderdate,
                   'month'
                ) "Order Month Name"
  from orderheader
 order by "Order Year" desc,
          to_date("Order Month Name",
                  'mm');

-- 6.7.
select distinct customerkey
  from orderheader
 where extract(year from orderdate) = 2019;

select distinct countrykey
  from orderheader
 where to_char(
   orderdate,
   'd'
) = 6;

select distinct customerkey
  from orderheader
 where to_char(
   orderdate,
   'mm.yyyy'
) = '06.2019';

select orderkey
  from orderdetail
 where productkey = 217
 order by orderkey;

select productkey
  from orderdetail
 where transactionprice = catalogprice
 order by productkey;

select orderkey
  from orderdetail
 where productkey = 483
   and quantity * transactionprice > 360;

-- 6.8.
select distinct channelkey
  from orderheader
 where to_char(
   orderdate,
   'd'
) in ( 6,
       7 );

select distinct paymentmethodkey
  from orderheader
 where countrykey = 6;

select distinct orderkey
  from orderdetail
 where productkey in ( 483,
                       528 )
 order by 1;

select productcode
  from product
 where productsubcategorykey in ( 1,
                                  12 )
 order by 1;

select productname
  from product
 where productcode in ( 'RF-9198',
                        'CH-0234' );

-- 6.9.
select distinct customerkey
  from orderheader
 where extract(year from orderdate) between 2017 and 2019
 order by 1;

select distinct customerkey
  from orderheader
 where orderdate between to_date('01.07.2019', 'dd.mm.yyyy') and to_date('30.09.2019', 'dd.mm.yyyy')
 order by 1;

select distinct productkey
  from orderdetail
 where quantity * transactionprice between 1000 and 3000
 order by 1;

-- 6.10.
select productsubcategoryname
  from productsubcategory
 where lower(productsubcategoryname) like '%mountain%'
 order by 1;

select productname
  from product
 where lower(productname) like '%helmet%'
 order by 1;

select productname
  from product
 where lower(productname) like 'women%'
 order by 1;

-- 6.11.
select orderkey,
       orderdate
  from orderheader
 where deliverydate is null
 order by 2 desc;

-- 6.12.
select distinct channelkey
  from orderheader
 where extract(year from orderdate) = 2019
   and countrykey = 1;

select distinct extract(year from orderdate) "Order Year",
                to_char(
                   orderdate,
                   'month'
                ) "Order Month Name",
                countrykey "ID Country"
  from orderheader
 where extract(year from orderdate) = 2019
   and extract(month from orderdate) in ( 5,
                                          6 )
   and channelkey = 2
 order by 1,
          2,
          3;

select distinct paymentmethodkey
  from orderheader
 where orderdate between to_date('01.12.2019', 'dd.mm.yyyy') and to_date('24.12.2019', 'dd.mm.yyyy')
   and paymentmethodkey in ( 2,
                             3 );

select distinct customerkey "ID Customer"
  from orderheader
 where to_char(
      orderdate,
      'q-yyyy'
   ) = '3-2019'
   and countrykey = 3
   and deliverycost = 0
   and deliverymethodkey = 2
 order by 1;

-- 6.13.
select productname
  from product
 where regexp_like ( productname,
                     '[0-9]' )
 order by 1;

select productname
  from product
 where regexp_like ( productname,
                     '-|,' )
 order by 1;

select productname
  from product
 where regexp_like ( productname,
                     '[0-9]$' )
 order by 1;

select productname
  from product
 where regexp_like ( productname,
                     '\s(S|M|L|XL)$' )
 order by 1;

select productname
  from product
 where regexp_like ( lower(productname),
                     '(front|rear) wheel' )
 order by 1;

select productname
  from product
 where regexp_like ( lower(productname),
                     '(mountain|road).*tube' )
 order by 1;