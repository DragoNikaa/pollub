-- 9.1.
select orderkey "Order ID"
  from orderdetail
 where quantity * transactionprice = (
   select max(quantity * transactionprice)
     from orderdetail
)
 order by 1;

select cs.customerkey "Customer ID",
       lastname
       || ' '
       || firstname "Customer Name",
       orderdate "Order Date"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 where orderdate = (
   select max(orderdate)
     from orderheader
)
 order by 2;

select cs.customerkey "Customer ID",
       lastname
       || ' '
       || firstname "Customer Name",
       count(orderkey) "#Orders"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 group by cs.customerkey,
          lastname
          || ' '
          || firstname
having count(orderkey) = (
   select max(count(orderkey))
     from orderheader
    group by customerkey
)
 order by 2;

select productname "Product",
       count(od.orderkey) "#Orders"
  from product p
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 where extract(year from orderdate) = 2019
 group by productname
having count(od.orderkey) = (
   select max(count(od.orderkey))
     from orderdetail od
    inner join orderheader oh
   on od.orderkey = oh.orderkey
    where extract(year from orderdate) = 2019
    group by productkey
);

select productname "Product",
       count(distinct cs.customerkey) "#Customer"
  from product p
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 inner join customer cs
on oh.customerkey = cs.customerkey
 group by productname
having count(distinct cs.customerkey) = (
   select max(count(distinct cs.customerkey))
     from product p
    inner join orderdetail od
   on p.productkey = od.productkey
    inner join orderheader oh
   on od.orderkey = oh.orderkey
    inner join customer cs
   on oh.customerkey = cs.customerkey
    group by productname
);

select countryname "Country",
       count(oh.orderkey) "#Orders"
  from country cn
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 where productcategoryname = 'Bikes'
 group by countryname
having count(oh.orderkey) = (
   select max(count(oh.orderkey))
     from country cn
    inner join orderheader oh
   on cn.countrykey = oh.countrykey
    inner join orderdetail od
   on oh.orderkey = od.orderkey
    inner join product p
   on od.productkey = p.productkey
    inner join productsubcategory ps
   on p.productsubcategorykey = ps.productsubcategorykey
    inner join productcategory pc
   on ps.productcategorykey = pc.productcategorykey
    where productcategoryname = 'Bikes'
    group by countryname
);

select channelname "Channel",
       count(distinct customerkey) "#Customers"
  from orderchannel och
 inner join orderheader oh
on och.channelkey = oh.channelkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 where productcategoryname = 'Accessories'
 group by channelname
having count(distinct customerkey) = (
   select max(count(distinct customerkey))
     from orderchannel och
    inner join orderheader oh
   on och.channelkey = oh.channelkey
    inner join orderdetail od
   on oh.orderkey = od.orderkey
    inner join product p
   on od.productkey = p.productkey
    inner join productsubcategory ps
   on p.productsubcategorykey = ps.productsubcategorykey
    inner join productcategory pc
   on ps.productcategorykey = pc.productcategorykey
    where productcategoryname = 'Accessories'
    group by channelname
);

select to_char(
   orderdate,
   'month'
) "Month",
       count(distinct cs.customerkey) "#Customers"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 where extract(year from orderdate) = 2019
 group by to_char(
   orderdate,
   'month'
)
having count(distinct cs.customerkey) = (
   select max(count(distinct cs.customerkey))
     from customer cs
    inner join orderheader oh
   on cs.customerkey = oh.customerkey
    where extract(year from orderdate) = 2019
    group by to_char(
      orderdate,
      'month'
   )
);

select countryname "Country",
       count(oh.orderkey) "#Canceled Orders"
  from country cn
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 inner join orderstatus os
on oh.orderstatuskey = os.orderstatuskey
 where orderstatusname = 'Canceled'
 group by countryname
having count(oh.orderkey) = (
   select max(count(oh.orderkey))
     from country cn
    inner join orderheader oh
   on cn.countrykey = oh.countrykey
    inner join orderstatus os
   on oh.orderstatuskey = os.orderstatuskey
    where orderstatusname = 'Canceled'
    group by countryname
);

select oh.orderkey "Order Number",
       quantity * transactionprice "Order Value"
  from country cn
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by oh.orderkey,
          quantity * transactionprice
having quantity * transactionprice > (
   select avg(quantity * transactionprice)
     from orderheader oh
    inner join orderdetail od
   on oh.orderkey = od.orderkey
)
 order by 2 desc;

-- 9.2.
select cs.customerkey "Customer ID",
       lastname "Last Name",
       firstname "First Name",
       max(orderdate) "Last Order Date"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 inner join country cn
on oh.countrykey = cn.countrykey
 where countryname = 'Australia'
 group by cs.customerkey,
          lastname,
          firstname
having ( cs.customerkey,
         max(orderdate) ) in (
   select customerkey,
          max(orderdate)
     from orderheader
    group by customerkey
)
 order by 2;

select countryname "Country",
       max(orderdate) "Last Order Date"
  from country cn
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 where productcategoryname = 'Bikes'
 group by countryname
having max(orderdate) = (
   select max(orderdate)
     from country cn
    inner join orderheader oh
   on cn.countrykey = oh.countrykey
    inner join orderdetail od
   on oh.orderkey = od.orderkey
    inner join product p
   on od.productkey = p.productkey
    inner join productsubcategory ps
   on p.productsubcategorykey = ps.productsubcategorykey
    inner join productcategory pc
   on ps.productcategorykey = pc.productcategorykey
    where productcategoryname = 'Bikes'
)
 order by 1;

select productname "Product",
       max(orderdate) "Last Order Date"
  from product p
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 group by productname
having max(orderdate) = (
   select max(orderdate)
     from orderheader oh
    inner join orderchannel och
   on oh.channelkey = och.channelkey
    where channelname = 'Mobile application'
      and extract(year from orderdate) = 2020
)
 order by 1;

select cs.customerkey "Customer ID",
       lastname "Last Name",
       firstname "First Name",
       min(orderdate) "First Order Date"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 where productcategoryname = 'Bikes'
 group by cs.customerkey,
          lastname,
          firstname
having min(orderdate) = (
   select min(orderdate)
     from orderheader oh
    inner join orderdetail od
   on oh.orderkey = od.orderkey
    inner join product p
   on od.productkey = p.productkey
    inner join productsubcategory ps
   on p.productsubcategorykey = ps.productsubcategorykey
    inner join productcategory pc
   on ps.productcategorykey = pc.productcategorykey
    where productcategoryname = 'Bikes'
)
 order by 2;

select channelname "Channel",
       max(orderdate) "Last Order Date"
  from orderchannel och
 inner join orderheader oh
on och.channelkey = oh.channelkey
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join salesterritory st
on cn.salesterritorykey = st.salesterritorykey
 where salesterritoryname = 'Europe'
 group by channelname
having max(orderdate) = (
   select max(orderdate)
     from orderheader oh
    inner join country cn
   on oh.countrykey = cn.countrykey
    inner join salesterritory st
   on cn.salesterritorykey = st.salesterritorykey
    where salesterritoryname = 'Europe'
);

-- 9.3.
select oh.orderkey "Order ID",
       cs.customerkey "Customer",
       cn.countrykey "Country"
  from orderheader oh
 inner join customer cs
on oh.customerkey = cs.customerkey
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by oh.orderkey,
          cs.customerkey,
          cn.countrykey
having sum(quantity * transactionprice) = (
   select max(sum(quantity * transactionprice))
     from orderdetail od
    inner join orderheader oh
   on od.orderkey = oh.orderkey
    group by oh.orderkey
);