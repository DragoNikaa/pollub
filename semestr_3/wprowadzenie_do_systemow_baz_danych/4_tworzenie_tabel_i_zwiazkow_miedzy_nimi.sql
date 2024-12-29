-- 4.1.
CREATE TABLE productcategory (
    productcategorykey  NUMBER(2) NOT NULL,
    productcategoryname VARCHAR2(30) NOT NULL
);

CREATE TABLE productsubcategory (
    productsubcategorykey  NUMBER(3) NOT NULL,
    productsubcategoryname VARCHAR(40) NOT NULL
);

-- 4.2.
CREATE TABLE product (
    productkey  NUMBER(5)
        CONSTRAINT product_pk PRIMARY KEY,
    productcode VARCHAR2(12) NOT NULL
        CONSTRAINT product_code_uq UNIQUE,
    productname VARCHAR2(40) NOT NULL
        CONSTRAINT product_name_uq UNIQUE
);

-- 4.3.
ALTER TABLE productsubcategory ADD productcategorykey NUMBER(2) NOT NULL;

ALTER TABLE product ADD productsubcategorykey NUMBER(3) NOT NULL;

-- 4.4.
ALTER TABLE productcategory ADD CONSTRAINT productcategory_pk PRIMARY KEY ( productcategorykey );

ALTER TABLE productcategory ADD CONSTRAINT productcategory_name_uq UNIQUE ( productcategoryname );

ALTER TABLE productsubcategory ADD CONSTRAINT productsubcategory_pk PRIMARY KEY ( productsubcategorykey );

ALTER TABLE productsubcategory ADD CONSTRAINT productsubcategory_name_uq UNIQUE ( productsubcategoryname );

-- 4.5.
ALTER TABLE productsubcategory
    ADD CONSTRAINT productsubcat_cat_fk FOREIGN KEY ( productcategorykey )
        REFERENCES productcategory ( productcategorykey );

ALTER TABLE product
    ADD CONSTRAINT product_subcategory_fk FOREIGN KEY ( productsubcategorykey )
        REFERENCES productsubcategory ( productsubcategorykey );
        
-- 4.6.
ALTER TABLE productcategory DISABLE PRIMARY KEY CASCADE;

ALTER TABLE productsubcategory DISABLE CONSTRAINT productsubcategory_pk;

ALTER TABLE product DISABLE PRIMARY KEY;

-- 4.7.
ALTER TABLE productcategory ENABLE PRIMARY KEY;

ALTER TABLE productsubcategory ENABLE CONSTRAINT productsubcategory_pk;

ALTER TABLE product ENABLE PRIMARY KEY;

ALTER TABLE productsubcategory ENABLE CONSTRAINT productsubcat_cat_fk;

ALTER TABLE product ENABLE CONSTRAINT product_subcategory_fk;

-- 4.9.
CREATE TABLE orderheader (
    orderkey          NUMBER(12)
        CONSTRAINT order_pk PRIMARY KEY,
    orderdate         DATE NOT NULL,
    deliverydate      DATE,
    customerkey       NUMBER(10) NOT NULL,
    channelkey        NUMBER(1) NOT NULL,
    paymentmethodkey  NUMBER(2) NOT NULL,
    deliverymethodkey NUMBER(1) NOT NULL,
    countrykey        NUMBER(2) NOT NULL,
    orderstatuskey    NUMBER(1) NOT NULL,
    otherinfo         VARCHAR2(100)
);

CREATE TABLE orderdetail (
    orderkey         NUMBER(12) NOT NULL,
    productkey       NUMBER(5) NOT NULL,
    quantity         NUMBER(2) DEFAULT 1 NOT NULL,
    catalogprice     NUMBER(7, 2) NOT NULL,
    discountamount   NUMBER(5, 2),
    discountpctg     NUMBER(2),
    transactionprice NUMBER(7, 2) NOT NULL,
    CONSTRAINT orderdetail_pk PRIMARY KEY ( orderkey,
                                            productkey )
);

ALTER TABLE orderdetail
    ADD CONSTRAINT orderdetail_order_fk FOREIGN KEY ( orderkey )
        REFERENCES orderheader ( orderkey );

ALTER TABLE orderdetail
    ADD CONSTRAINT orderdetail_product_fk FOREIGN KEY ( productkey )
        REFERENCES product ( productkey );

-- 4.10.
ALTER TABLE orderdetail MODIFY
    discountamount NUMBER(7, 2);

-- 4.11.
ALTER TABLE orderheader DROP ( otherinfo );