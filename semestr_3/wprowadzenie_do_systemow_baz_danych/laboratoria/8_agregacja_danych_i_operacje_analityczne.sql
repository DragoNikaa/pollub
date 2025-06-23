-- 8.1.
select extract(year from orderdate) "Year",
       salesterritoryname "Region",
       sum(quantity * transactionprice) "Total Orders Value"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join salesterritory st
on cn.salesterritorykey = st.salesterritorykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by extract(year from orderdate),
          salesterritoryname
 order by 1 desc,
          2;

select extract(year from orderdate) "Year",
       to_char(
          orderdate,
          'month'
       ) "Month Name",
       count(*) "#Orders"
  from orderheader
 group by extract(year from orderdate),
          to_char(
             orderdate,
             'month'
          ),
          extract(month from orderdate)
 order by 1,
          extract(month from orderdate);

select productcategoryname "Product Category",
       channelname "Order Channel",
       count(distinct customerkey) "#Customers"
  from productcategory pc
 inner join productsubcategory ps
on pc.productcategorykey = ps.productcategorykey
 inner join product p
on ps.productsubcategorykey = p.productsubcategorykey
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 inner join orderchannel och
on oh.channelkey = och.channelkey
 group by productcategoryname,
          channelname
 order by 1,
          2;

select salesterritoryname "Region",
       countryname "Country",
       extract(year from orderdate) "Year",
       sum(quantity * transactionprice) "Total Orders Value"
  from salesterritory st
 inner join country cn
on st.salesterritorykey = cn.salesterritorykey
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by salesterritoryname,
          countryname,
          extract(year from orderdate)
 order by 1,
          2,
          3;

select extract(year from orderdate) "Year",
       salesterritoryname "Region",
       productcategoryname "Product Category",
       count(*) "#Orders"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join salesterritory st
on cn.salesterritorykey = st.salesterritorykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 group by extract(year from orderdate),
          salesterritoryname,
          productcategoryname
 order by 1,
          2,
          3;

select extract(year from orderdate) "Year",
       'Q' || to_char(
          orderdate,
          'q'
       ) "Quarter",
       countryname "Country",
       channelname "Channel",
       productname "Product",
       sum(quantity) "Total Quantity"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join orderchannel och
on oh.channelkey = och.channelkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 group by extract(year from orderdate),
          to_char(
             orderdate,
             'q'
          ),
          countryname,
          channelname,
          productname
 order by 1,
          2,
          3,
          4,
          5;

-- 8.2.
select extract(year from orderdate) "Year",
       extract(month from orderdate) "Month",
       sum(quantity * transactionprice) "Total Orders Value"
  from orderheader oh
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by extract(year from orderdate),
          rollup(extract(month from orderdate))
 order by 1,
          2 desc;

select productcategoryname "Product Category",
       extract(year from orderdate) "Year",
       sum(quantity * transactionprice) "Total Orders Value"
  from productcategory pc
 inner join productsubcategory ps
on pc.productcategorykey = ps.productcategorykey
 inner join product p
on ps.productsubcategorykey = p.productsubcategorykey
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 group by cube(productcategoryname,
               extract(year from orderdate))
 order by 1 desc,
          2 desc;

select countryname "Country",
       extract(year from orderdate) "Year",
       count(distinct customerkey) "#Customers"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 group by countryname,
          rollup(extract(year from orderdate))
 order by 1,
          2 desc;

select extract(year from orderdate) "Year",
       channelname "Channel",
       countryname "Country",
       sum(quantity * transactionprice) "Total Orders Value"
  from orderheader oh
 inner join orderchannel och
on oh.channelkey = och.channelkey
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by cube(extract(year from orderdate),
               channelname,
               countryname)
 order by 1,
          2,
          3;

select extract(year from orderdate) "Year",
       extract(month from orderdate) "Month",
       countryname "Country",
       count(*) "#Orders"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 group by extract(year from orderdate),
          extract(month from orderdate),
          rollup(countryname)
 order by 1,
          2,
          3 desc;

-- 8.3.
select extract(year from orderdate) "Year",
       count(*) "#Orders"
  from orderheader
 where extract(year from orderdate) = 2019
 group by extract(year from orderdate);

select cs.customerkey,
       lastname,
       firstname,
       count(orderkey) "#Orders"
  from customer cs
  left join orderheader oh
on cs.customerkey = oh.customerkey
   and extract(year from orderdate) = 2019
 group by cs.customerkey,
          lastname,
          firstname
 order by 4 desc;

select od.orderkey,
       count(productkey) "#Products"
  from orderdetail od
 inner join orderheader oh
on od.orderkey = oh.orderkey
 where to_char(
   orderdate,
   'q-yyyy'
) = '1-2019'
 group by od.orderkey
 order by 1;

select extract(year from orderdate) "Year",
       extract(month from orderdate) "Month Number",
       count(*) "#Orders"
  from orderheader
 group by extract(year from orderdate),
          rollup(extract(month from orderdate))
 order by 1,
          2;

select salesterritoryname "Region",
       extract(year from orderdate) "Year",
       extract(month from orderdate) "Month",
       count(*) "#Orders"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join salesterritory st
on cn.salesterritorykey = st.salesterritorykey
 group by salesterritoryname,
          extract(year from orderdate),
          extract(month from orderdate)
 order by 1,
          2,
          3;

select salesterritoryname "Region",
       countryname "Country",
       count(*) "#Orders"
  from salesterritory st
 inner join country cn
on st.salesterritorykey = cn.salesterritorykey
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 group by salesterritoryname,
          rollup(countryname)
 order by 1,
          2;

select extract(year from orderdate) "Year",
       productcategoryname "Product Category",
       count(*) "#Orders"
  from orderheader oh
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 group by extract(year from orderdate),
          productcategoryname
 order by 1,
          2;

select productcategoryname "Product Category",
       productsubcategoryname "Product Subcategory",
       count(*) "#Orders"
  from orderheader oh
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 group by productcategoryname,
          rollup(productsubcategoryname)
 order by 1,
          2;

select countryname "Country",
       channelname "Channel",
       count(orderkey) "#Orders"
  from country cn
  left join orderheader oh
on cn.countrykey = oh.countrykey
  full join orderchannel och
on oh.channelkey = och.channelkey
 group by countryname,
          channelname
 order by 1,
          2;

select countryname "Country",
       channelname "Channel",
       count(orderkey) "#Orders"
  from country cn
 cross join orderchannel och
  left join orderheader oh
on cn.countrykey = oh.countrykey
   and och.channelkey = oh.channelkey
 group by countryname,
          channelname
 order by 1,
          2;

select cs.customerkey,
       lastname,
       firstname,
       count(*) "#Canceled"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 inner join orderstatus os
on oh.orderstatuskey = os.orderstatuskey
 where orderstatusname = 'Canceled'
 group by cs.customerkey,
          lastname,
          firstname
 order by 4 desc;

select extract(year from orderdate) "Year",
       countryname "Country",
       count(distinct customerkey) "#Customers"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 group by extract(year from orderdate),
          countryname
 order by 1,
          2;

select cs.customerkey,
       lastname,
       firstname,
       count(distinct productkey) "#Products"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 where extract(year from orderdate) = 2019
 group by cs.customerkey,
          lastname,
          firstname
 order by 4 desc;

select extract(year from orderdate) "Year",
       salesterritoryname "Region",
       countryname "Country",
       count(distinct customerkey) "#Customers"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join salesterritory st
on cn.salesterritorykey = st.salesterritorykey
 group by extract(year from orderdate),
          rollup(salesterritoryname,
                 countryname)
 order by 1,
          2,
          4;

-- 8.4.
select sum(quantity * transactionprice) "Total Orders Value"
  from orderdetail;

select extract(year from orderdate) "Year",
       sum(quantity * transactionprice) "Total Value"
  from orderheader oh
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by extract(year from orderdate)
 order by 1;

select productcategoryname "Product Category",
       extract(year from orderdate) "Year",
       extract(month from orderdate) "Month",
       sum(quantity * transactionprice) "Total Value"
  from productcategory pc
 inner join productsubcategory ps
on pc.productcategorykey = ps.productcategorykey
 inner join product p
on ps.productsubcategorykey = p.productsubcategorykey
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 group by cube(productcategoryname,
               extract(year from orderdate),
               extract(month from orderdate))
 order by 1,
          2,
          3;

select extract(year from orderdate) "Year",
       countryname "Country",
       sum(quantity * transactionprice) "Total Value"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 group by extract(year from orderdate),
          rollup(countryname)
 order by 1 desc,
          3 desc;

select productcategoryname "Product Category",
       to_char(
          orderdate,
          'month'
       ) "Month",
       sum(quantity * transactionprice) "Total Value"
  from productcategory pc
 inner join productsubcategory ps
on pc.productcategorykey = ps.productcategorykey
 inner join product p
on ps.productsubcategorykey = p.productsubcategorykey
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 inner join orderchannel och
on oh.channelkey = och.channelkey
 where extract(year from orderdate) = 2019
   and channelname = 'Mobile application'
 group by productcategoryname,
          to_char(
             orderdate,
             'month'
          )
 order by 3 desc;

select salesterritoryname "Region",
       countryname "Country",
       sum(quantity * transactionprice) "Total Value"
  from salesterritory st
 inner join country cn
on st.salesterritorykey = cn.salesterritorykey
  left join orderheader oh
on cn.countrykey = oh.countrykey
  left join orderdetail od
on oh.orderkey = od.orderkey
 group by salesterritoryname,
          rollup(countryname)
 order by 1,
          3 desc;

select countryname "Country",
       channelname "Channel",
       sum(quantity * transactionprice) "Total Value"
  from country cn
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 right join orderchannel och
on oh.channelkey = och.channelkey
 group by countryname,
          channelname
 order by 1,
          3 desc;

select extract(year from orderdate) "Year",
       productname "Product",
       sum(quantity) "#Items"
  from orderheader oh
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 group by extract(year from orderdate),
          productname
 order by 1 desc,
          3 desc;

select extract(year from orderdate) "Year",
       countryname "Country",
       channelname "Channel",
       productname "Product",
       sum(quantity) "#Items"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join orderchannel och
on oh.channelkey = och.channelkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 group by extract(year from orderdate),
          countryname,
          channelname,
          productname
 order by 1,
          2,
          3,
          5;

-- 8.5.
select extract(year from orderdate) "Year",
       countryname "Country",
       round(avg(deliverydate - orderdate)) "#Avg Days"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 group by extract(year from orderdate),
          countryname
 order by 1,
          2;

select productname "Product",
       extract(year from orderdate) "Year",
       'Q' || to_char(
          orderdate,
          'q'
       ) "Quarter",
       round(
          avg(transactionprice),
          2
       ) "Avg Trans Price"
  from product p
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 group by productname,
          extract(year from orderdate),
          to_char(
             orderdate,
             'q'
          )
 order by 1,
          2,
          3;

select productsubcategoryname "Product",
       extract(year from orderdate) "Year",
       round(
          avg(discountamount),
          2
       ) "Avg Discount"
  from productsubcategory ps
 inner join product p
on ps.productsubcategorykey = p.productsubcategorykey
 inner join orderdetail od
on p.productkey = od.productkey
 inner join orderheader oh
on od.orderkey = oh.orderkey
 where lower(productsubcategoryname) like '%bikes%'
 group by productsubcategoryname,
          extract(year from orderdate)
 order by 1,
          2;

select productsubcategoryname "Product",
       extract(year from orderdate) "Year",
       round(
          avg(transactionprice),
          2
       ) "Avg Trans Price"
  from productsubcategory ps
  left join product p
on ps.productsubcategorykey = p.productsubcategorykey
  left join orderdetail od
on p.productkey = od.productkey
  left join orderheader oh
on od.orderkey = oh.orderkey
 where lower(productsubcategoryname) like '%frames%'
 group by productsubcategoryname,
          extract(year from orderdate)
 order by 1,
          2;

select productsubcategoryname "Product",
       extract(year from orderdate) "Year",
       'Q' || to_char(
          orderdate,
          'q'
       ) "Quarter",
       round(
          avg(transactionprice),
          2
       ) "Avg Trans Price"
  from productsubcategory ps
 inner join product p
on ps.productsubcategorykey = p.productsubcategorykey
  left join orderdetail od
on p.productkey = od.productkey
  left join orderheader oh
on od.orderkey = oh.orderkey
 where lower(productsubcategoryname) like '%bikes%'
 group by productsubcategoryname,
          rollup(extract(year from orderdate),
                 'Q' || to_char(
                    orderdate,
                    'q'
                 ))
having avg(transactionprice) is not null
 order by 1,
          2,
          3;

-- 8.6.
select countryname "Country",
       extract(year from orderdate) "Year",
       to_char(
          min(orderdate),
          'dd-mm-yyyy'
       ) "First Order Date"
  from country cn
 inner join orderheader oh
on cn.countrykey = oh.countrykey
 group by countryname,
          extract(year from orderdate)
 order by 1,
          2 desc;

select cs.customerkey "Customer ID",
       lastname
       || ' '
       || firstname "Customer Name",
       'Q' || to_char(
          orderdate,
          'q'
       ) "Quarter",
       to_char(
          max(orderdate),
          'dd-mm-yyyy'
       ) "Last Purchase Date"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 where extract(year from orderdate) = 2019
 group by cs.customerkey,
          lastname
          || ' '
          || firstname,
          to_char(
             orderdate,
             'q'
          )
 order by 1,
          3;