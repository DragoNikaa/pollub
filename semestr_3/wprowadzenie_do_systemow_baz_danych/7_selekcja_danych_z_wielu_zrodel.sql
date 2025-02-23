-- 7.1.
select productcategoryname "Category Name",
       productsubcategoryname "Subcategory Name"
  from productcategory pc
 inner join productsubcategory ps
on pc.productcategorykey = ps.productcategorykey
 order by 1,
          2 desc;

select productcode
       || ' - '
       || productname "Product",
       productsubcategoryname "Subcategory Name"
  from product p
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 order by 1;

select distinct extract(year from orderdate) "Year",
                productkey "Product"
  from orderheader oh
 inner join orderdetail od
on oh.orderkey = od.orderkey
 where extract(year from orderdate) in ( 2019,
                                         2020 )
 order by 1 desc,
          2;

select productkey,
       transactionprice
  from orderdetail od
 inner join orderheader oh
on od.orderkey = oh.orderkey
 where orderdate between to_date('01.12.2019', 'dd.mm.yyyy') and to_date('24.12.2019', 'dd.mm.yyyy')
   and channelkey = 2
 order by 2 desc;

-- 7.2.
select distinct paymentmethodname "Payment Method"
  from paymentmethod pm
 inner join orderheader oh
on pm.paymentmethodkey = oh.paymentmethodkey
 inner join orderchannel och
on oh.channelkey = och.channelkey
 where channelname = 'Mobile application';

select distinct cs.customerkey "ID Customer",
                lastname "Last Name",
                firstname "First Name"
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join orderstatus os
on oh.orderstatuskey = os.orderstatuskey
 where orderstatusname = 'Canceled'
   and productsubcategoryname = 'Mountain Bikes'
 order by 1;

select distinct cs.customerkey,
                lastname,
                firstname,
                p.productkey,
                productname
  from customer cs
 inner join orderheader oh
on cs.customerkey = oh.customerkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 where lastname = 'Alan'
 order by cs.customerkey,
          p.productkey;

select distinct deliverymethodname
  from deliverymethod dm
 inner join orderheader oh
on dm.deliverymethodkey = oh.deliverymethodkey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 where regexp_like ( lower(productname),
                     '(men|women)''s.*shorts' );

select distinct extract(year from orderdate) "Year",
                salesterritoryname "Region",
                productcode
                || ' - '
                || productname "Product"
  from orderheader oh
 inner join country cn
on oh.countrykey = cn.countrykey
 inner join salesterritory st
on cn.salesterritorykey = st.salesterritorykey
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 order by 1 desc,
          2 desc,
          3 desc;

select distinct cs.customerkey,
                lastname,
                firstname
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
 where extract(year from orderdate) = 2019
   and productcategoryname = 'Bikes'
 order by 1;

select distinct extract(year from orderdate) "Year",
                'Q' || to_char(
                   orderdate,
                   'q'
                ) "Quarter"
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
 order by 1 desc,
          2;

-- 7.3.
select cs.customerkey,
       lastname,
       firstname
  from customer cs
  left join orderheader oh
on cs.customerkey = oh.customerkey
 where orderkey is null
 order by 2,
          3;

select paymentmethodname
  from orderheader oh
 right join paymentmethod pm
on oh.paymentmethodkey = pm.paymentmethodkey
 where orderkey is null;

select countryname
  from country cn
  left join orderheader oh
on cn.countrykey = oh.countrykey
 where orderkey is null
 order by 1;

select productcode,
       productname
  from product p
  left join orderdetail od
on p.productkey = od.productkey
 where orderkey is null
 order by 1;

-- 7.4.
select distinct countryname "Country",
                channelname "Order Channel"
  from country cn
  left join orderheader oh
on cn.countrykey = oh.countrykey
  left join orderchannel och
on oh.channelkey = och.channelkey
 order by 1;

select distinct channelname,
                paymentmethodname
  from orderchannel och
  left join orderheader oh
on och.channelkey = oh.channelkey
  left join paymentmethod pm
on oh.paymentmethodkey = pm.paymentmethodkey
 order by 1;

select distinct cs.customerkey,
                lastname,
                firstname,
                channelname
  from customer cs
  left join orderheader oh
on cs.customerkey = oh.customerkey
  left join orderchannel och
on oh.channelkey = och.channelkey
 where lastname = 'Alan';

-- 7.5.
select paymentmethodname "Payment Method",
       deliverymethodname "Delivery Method"
  from paymentmethod pm
  full join orderheader oh
on pm.paymentmethodkey = oh.paymentmethodkey
  full join deliverymethod dm
on oh.deliverymethodkey = dm.deliverymethodkey
 where orderkey is null
 order by 1;

select productname "Product",
       countryname "Country"
  from product p
  full join orderdetail od
on p.productkey = od.productkey
  full join orderheader oh
on od.orderkey = oh.orderkey
  full join country cn
on oh.countrykey = cn.countrykey
 where oh.orderkey is null
 order by 1;

select cs.customerkey,
       lastname,
       firstname,
       channelname
  from customer cs
  full join orderheader oh
on cs.customerkey = oh.customerkey
  full join orderchannel och
on oh.channelkey = och.channelkey
 where orderkey is null
 order by 2,
          4;

-- 7.6.
select countryname "Country",
       channelname "Channel"
  from country cn
 cross join orderchannel och
  left join orderheader oh
on cn.countrykey = oh.countrykey
   and och.channelkey = oh.channelkey
 where orderkey is null
 order by 1;

select productcategoryname "Product Category",
       dm.deliverymethodname "Delivery Method"
  from productcategory pc
 cross join deliverymethod dm
  left join productsubcategory ps
on pc.productcategorykey = ps.productcategorykey
  left join product p
on ps.productsubcategorykey = p.productsubcategorykey
  left join orderdetail od
on p.productkey = od.productkey
  left join orderheader oh
on od.orderkey = oh.orderkey
   and dm.deliverymethodkey = oh.deliverymethodkey
 group by productcategoryname,
          dm.deliverymethodname
having count(oh.orderkey) = 0;

select cs.customerkey,
       lastname,
       firstname
  from customer cs
 cross join paymentmethod pm
  left join orderheader oh
on cs.customerkey = oh.customerkey
   and pm.paymentmethodkey = oh.paymentmethodkey
 where orderkey is null
   and paymentmethodname = 'PayPal'
 order by 2;

select cs.customerkey,
       lastname,
       firstname
  from customer cs
 cross join productcategory pc
  left join productsubcategory ps
on pc.productcategorykey = ps.productcategorykey
  left join product p
on ps.productsubcategorykey = p.productsubcategorykey
  left join orderdetail od
on p.productkey = od.productkey
  left join orderheader oh
on od.orderkey = oh.orderkey
   and cs.customerkey = oh.customerkey
 where productcategoryname = 'Bikes'
 group by cs.customerkey,
          lastname,
          firstname,
          pc.productcategorykey
having count(oh.orderkey) = 0;

select customerkey,
       lastname,
       firstname
  from customer
 where customerkey not in (
   select distinct customerkey
     from orderheader oh
    inner join orderdetail od
   on od.orderkey = oh.orderkey
    inner join product
   on product.productkey = od.productkey
    inner join productsubcategory psub
   on psub.productsubcategorykey = product.productsubcategorykey
    where productcategorykey = 1
)
 order by lastname;

select cs.customerkey,
       firstname,
       lastname
  from customer cs
 cross join productcategory pc
  left join orderheader oh
on cs.customerkey = oh.customerkey
  left join orderdetail od
on oh.orderkey = od.orderkey
  left join product p
on od.productkey = p.productkey
  left join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 where pc.productcategoryname = 'Bikes'
   and oh.orderkey is null;

select productcategoryname,
       deliverymethodkey,
       oh.orderkey
  from orderheader oh
 inner join orderdetail od
on oh.orderkey = od.orderkey
 inner join product p
on od.productkey = p.productkey
 inner join productsubcategory ps
on p.productsubcategorykey = ps.productsubcategorykey
 inner join productcategory pc
on ps.productcategorykey = pc.productcategorykey
 where pc.productcategorykey = 3
   and deliverymethodkey = 4;