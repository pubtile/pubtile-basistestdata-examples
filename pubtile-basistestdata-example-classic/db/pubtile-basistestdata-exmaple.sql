DROP DATABASE IF EXISTS pubtile_basistestdata_example;

CREATE DATABASE pubtile_basistestdata_example  DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

use pubtile_basistestdata_example;

DROP TABLE IF EXISTS bill_order;
CREATE TABLE bill_order(
    id BIGINT NOT NULL   COMMENT 'ID' ,
    order_number VARCHAR(32) NOT NULL   COMMENT '订单编号' ,
    status INT NOT NULL   COMMENT '订单状态(0:未支付,10,已支付,20已发货,50已收货)' ,
    customer_id BIGINT NOT NULL   COMMENT 'customer ID' ,
    total_qty DECIMAL(24,6)   DEFAULT 0 COMMENT 'total_qty' ,
    total_amount DECIMAL(24,6)   DEFAULT 0 COMMENT 'total_amount' ,
    order_address VARCHAR(900)    COMMENT '订单地址' ,
    tenant_id BIGINT NOT NULL   COMMENT '租户号' ,
    revision BIGINT   DEFAULT 0 COMMENT '乐观锁' ,
    created_by BIGINT NOT NULL   COMMENT '创建人' ,
    created_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    updated_by BIGINT NOT NULL   COMMENT '更新人' ,
    updated_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '订单主表';

DROP TABLE IF EXISTS bill_order_detail;
CREATE TABLE bill_order_detail(
    id BIGINT NOT NULL   COMMENT 'ID' ,
    order_number VARCHAR(32) NOT NULL   COMMENT '订单编号' ,
    sku_id BIGINT NOT NULL   COMMENT 'SKU ID' ,
    qty DECIMAL(24,6) NOT NULL   COMMENT '购买数量' ,
    order_id BIGINT NOT NULL   COMMENT '订单ID' ,
    tenant_id BIGINT NOT NULL   COMMENT '租户号' ,
    revision BIGINT   DEFAULT 0 COMMENT '乐观锁' ,
    created_by BIGINT NOT NULL   COMMENT '创建人ID' ,
    created_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    updated_by BIGINT NOT NULL   COMMENT '更新人' ,
    updated_time DATETIME   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '订单明细表';

DROP TABLE IF EXISTS stock;
CREATE TABLE stock(
    id BIGINT NOT NULL   COMMENT 'ID' ,
    sku_id BIGINT NOT NULL   COMMENT 'SKU ID' ,
    qty INT NOT NULL   COMMENT '库存数量' ,
    tenant_id BIGINT NOT NULL   COMMENT '租户号' ,
    revision BIGINT NOT NULL  DEFAULT 0 COMMENT '乐观锁' ,
    created_by BIGINT NOT NULL   COMMENT '创建人' ,
    created_time DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    updated_by BIGINT NOT NULL   COMMENT '更新人' ,
    updated_time DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '库存';

DROP TABLE IF EXISTS customer;
CREATE TABLE customer(
    id BIGINT NOT NULL   COMMENT 'ID' ,
    status VARCHAR(32) COMMENT '状态' ,
    name VARCHAR(32)    COMMENT '用户名' ,
    gender VARCHAR(1)    COMMENT '性别(M:male 男,F:female 女)' ,
    mobile VARCHAR(32)    COMMENT '手机电话号码' ,
    email VARCHAR(32)    COMMENT 'EMAIL' ,

    tenant_id BIGINT NOT NULL   COMMENT '租户号' ,
    revision BIGINT NOT NULL  DEFAULT 0 COMMENT '乐观锁' ,
    created_by BIGINT NOT NULL   COMMENT '创建人' ,
    created_time DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    updated_by BIGINT NOT NULL   COMMENT '更新人' ,
    updated_time DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '用户';

DROP TABLE IF EXISTS tenant;
CREATE TABLE tenant(
    id BIGINT NOT NULL   COMMENT 'ID' ,
    name VARCHAR(32)    COMMENT '租户名称' ,
    revision BIGINT NOT NULL  DEFAULT 0 COMMENT '乐观锁' ,
    created_by BIGINT NOT NULL   COMMENT '创建人' ,
    created_time DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    updated_by BIGINT NOT NULL   COMMENT '更新人' ,
    updated_time DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
    PRIMARY KEY (id)
)  COMMENT = '租户';



insert into bill_order (id,order_number,status,customer_id,order_address,tenant_id,created_by,updated_by) values (1,'so1',0,1,null,1,1,1),(2,'so2',0,2,null,1,1,1),(3,'so3',10,1,null,1,1,1),(4,'so4',10,3,null,1,1,1),(5,'so5',20,4,null,1,1,1),(6,'so6',20,1,null,1,1,1),(7,'so7',50,1,null,1,1,1),(8,'so8',50,2,null,1,1,1);
insert into `bill_order_detail` (id,order_number,sku_id,qty,order_id,tenant_id,created_by,updated_by)values (1,'so1',1,1,1,1,1,1),(2,'so2',1,1,2,1,1,1),(3,'so2',2,1,2,1,1,1),(4,'so3',3,1,3,1,1,1),(5,'so4',2,2,4,1,1,1),(6,'so4',4,1,4,1,1,1),(7,'so5',2,1,5,1,1,1),(8,'so6',2,1,6,1,1,1),(9,'so6',4,1,6,1,1,1),(10,'so7',5,2,7,1,1,1),(11,'so8',3,1,8,1,1,1),(12,'so8',4,1,8,1,1,1);
insert into `stock` (id,sku_id,qty,tenant_id,created_by,updated_by) values (1,1,1,1,1,1),(2,2,2,1,1,1),(3,3,3,1,1,1),(4,4,4,1,1,1),(5,5,5,1,1,1);
insert into `customer` (id, status, name, gender,mobile,email,tenant_id,created_by,updated_by) values (1,'ACTIVE','张三','m','13611111111','zhangshan@publictile.com',1,1,1),(2,'ACTIVE','李四','m','13622222222','lisi@publictile.com',1,1,1),(3,'ACTIVE','王五','m','13633333333','wangwu@publictile.com',1,1,1),(4,'ACTIVE','赵六','f','1364444444','zhaoliu@publictile.com',1,1,1);
insert into `tenant` (id, name,created_by,upDATED_BY) VALUES (1,'上海蓝天集团',1,1),(2,'北京兴隆有限公司',1,1);