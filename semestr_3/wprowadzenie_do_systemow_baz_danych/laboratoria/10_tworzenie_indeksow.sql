-- 10.1.
create unique index productcategory_name_idx on
   productcategory (
      productcategoryname
   );

create index productsubcategory_cat_idx on
   productsubcategory (
      productcategorykey
   );

create index product_subcategory_idx on
   product (
      productsubcategorykey
   );

-- 10.2.
create index customer_name_idx on
   customer (
      lastname,
      firstname
   );

-- 10.4.
alter table productcategory drop constraint productcategory_name_uq;

drop index customer_name_idx;