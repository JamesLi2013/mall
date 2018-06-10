# 用户表
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id            BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '主键id',
  password      VARCHAR(255) NOT NULL
  COMMENT '密码',
  username      VARCHAR(255) COMMENT '登录用户名',
  phone         VARCHAR(20) COMMENT '手机号码',
  nickname      VARCHAR(255) COMMENT '昵称',
  head          VARCHAR(255) COMMENT '头像',
  promo_code    VARCHAR(40) COMMENT '推广码',
  device_id     VARCHAR(255) COMMENT '设备id',
  platform      VARCHAR(255) COMMENT '设备平台',
  phone_model   VARCHAR(255) COMMENT '手机型号',
  version       VARCHAR(255) COMMENT 'app软件版本',
  salt          VARCHAR(255) COMMENT '盐,用于密码',
  first_login   TINYINT(2) COMMENT '是否第一次登录',
  sex           INTEGER(2) COMMENT '性别:0没有设置;1为男性;2为女性',
  register_time DATETIME(0) COMMENT '注册时间',
  longitude     DOUBLE COMMENT '经度',
  latitude      DOUBLE COMMENT '纬度',
  last_login    DATETIME(0) COMMENT '登录时间',
  created       DATETIME(0)                       DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated       DATETIME(0)                       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '用户表';

# 验证码表
DROP TABLE IF EXISTS verification_code;
CREATE TABLE verification_code (
  id      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT 'id',
  phone   VARCHAR(20) COMMENT '手机号码',
  code    INTEGER COMMENT '验证码',
  type    INTEGER COMMENT '验证码类型:比如 1 注册 ,2 是忘记密码 ,3是抢购,不同类型不可通用',
  valid   TINYINT COMMENT '有效:1有效,0失效',
  created DATETIME(0)                 DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated DATETIME(0)                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '验证码表';

#产品表
DROP TABLE IF EXISTS product;
CREATE TABLE product (
  id              BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '产品id',
  title           VARCHAR(255) COMMENT '产品标题',
  sub_title       VARCHAR(255) COMMENT '产品卖点',
  keyword         VARCHAR(255) COMMENT '搜索关键词',
  props           TEXT(5000) COMMENT '所有属性',
  qualification   VARCHAR(255) COMMENT '资质证书',
  colors          VARCHAR(2000) COMMENT '销售属性:颜色',
  meals           VARCHAR(1000) COMMENT '销售属性:套餐',
  price           BIGINT COMMENT '价格',
  min_price       BIGINT COMMENT '最低价',
  max_price       BIGINT COMMENT '最高价',
  quantity        INTEGER COMMENT '数量',
  sale_count      INTEGER COMMENT '销量',
  departure_place VARCHAR(255) COMMENT '发货地',
  multi_image     TEXT(800) COMMENT '顶部图片,1-5',
  major_image     VARCHAR(255) COMMENT '主图',
  desc_pc         TEXT(5000) COMMENT '产品描述 pc',
  desc_mobile     VARCHAR(255) COMMENT '产品描述 手机端',
  after_sale      VARCHAR(255) COMMENT '售后服务',
  product_status  INTEGER COMMENT '审核中1,预售2,销售中3,下架4,其它状态5',
  start_time      DATETIME COMMENT '开始销售时间',
  sku_ids         VARCHAR(2000) COMMENT '所有sku',
  major_sku       BIGINT COMMENT '默认sku',
  seller_id       BIGINT COMMENT '卖家id',
  seller_name     VARCHAR(255) COMMENT '卖家名称',
  seller_icon     VARCHAR(255) COMMENT '卖家图标',
  self_support    BIT COMMENT '自营',
  created         DATETIME                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated         DATETIME                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '产品表';

# 分类表
DROP TABLE IF EXISTS category;
CREATE TABLE category (
  id        BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '分类id',
  cat_name  VARCHAR(255) COMMENT '分类名称',
  cat_level INTEGER COMMENT '分类层级',
  parent_id BIGINT COMMENT '父分类id',
  big_id    BIGINT COMMENT '大分类id',
  mid_id    BIGINT COMMENT '中分类id',
  created   DATETIME                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated   DATETIME                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '分类表';

#商品属性Key
DROP TABLE IF EXISTS prop_key;
CREATE TABLE prop_key (
  id      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '属性key id',
  p_name  VARCHAR(255) COMMENT '属性key名称',
  created DATETIME                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated DATETIME                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '商品属性key';

#商品属性Value
DROP TABLE IF EXISTS prop_value;
CREATE TABLE prop_value (
  id      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT '属性value id',
  pid     BIGINT COMMENT '属性key id',
  v_name  VARCHAR(255) COMMENT '属性value名称',
  created DATETIME                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated DATETIME                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '商品属性value';

# 访问页面统计表
DROP TABLE IF EXISTS visit_page;
CREATE TABLE visit_page (
  id        BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT
  COMMENT 'id',
  uid       BIGINT COMMENT '用户id',
  page_name VARCHAR(255) COMMENT '页面名称',
  keyword   VARCHAR(255) COMMENT '搜索关键字',
  city      VARCHAR(255) COMMENT '城市名称',
  created   DATETIME                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated   DATETIME                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '访问页面统计表';

#sku表
DROP TABLE IF EXISTS sku;
CREATE TABLE sku (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT 'sku id',
  product_id   BIGINT NOT NULL
  COMMENT '产品id',
  price        BIGINT COMMENT '价格,单位是分',
  color        VARCHAR(255) COMMENT '销售属性一,不一定是颜色',
  meal         VARCHAR(255) COMMENT '销售属性一,不一定是套餐',
  custom_props VARCHAR(255) COMMENT '自定义属性',
  quantity     INTEGER COMMENT '库存数量',
  sale_count   INTEGER COMMENT '销量',
  `status`     INTEGER COMMENT 'sku状态',
  created      DATETIME        DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated      DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'sku表';
