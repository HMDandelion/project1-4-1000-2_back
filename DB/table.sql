CREATE TABLE `tbl_assigned_material` (
    `assigned_material_code` INT NOT NULL AUTO_INCREMENT
        COMMENT '담당자재 코드',
    `spec_code`              INT NOT NULL COMMENT '스펙 코드',
    `client_code`            INT NOT NULL COMMENT '거래처코드',
    PRIMARY KEY (`assigned_material_code`)
) COMMENT = '담당자재';

CREATE TABLE `tbl_authority` (
    `authority_code` INT          NOT NULL COMMENT '권한코드',
    `authority_name` VARCHAR(100) NOT NULL COMMENT '권한명',
    PRIMARY KEY (`authority_code`)
) COMMENT = '권한';
INSERT INTO `tbl_emp_auth`
    (`emp_auth_code`, `authority_code`, `employee_code`)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 4, 4);

INSERT INTO `tbl_authority`
    (`authority_code`, `authority_name`)
VALUES
    (1, '영업팀'),
    (2, '사용자'),
    (3, '생산 관리자'),
    (4, '물류 관리자');

CREATE TABLE `tbl_bom` (
    `bom_code`     INT NOT NULL AUTO_INCREMENT
        COMMENT 'BOM 코드',
    `quantity`     INT NOT NULL UNIQUE KEY
        COMMENT '수량',
    `sequence`     INT NOT NULL COMMENT '조립 순서',
    `product_code` INT NOT NULL COMMENT '상품 코드',
    `spec_code`    INT NOT NULL COMMENT '스펙 코드',
    PRIMARY KEY (`bom_code`)
) COMMENT = 'BOM';

CREATE TABLE `tbl_client` (
    `client_code`         INT                          NOT NULL AUTO_INCREMENT
        COMMENT '거래처코드',
    `client_name`         VARCHAR(100)                 NOT NULL COMMENT '거래처명',
    `address`             VARCHAR(255)                 NOT NULL COMMENT '주소',
    `address_detail`      VARCHAR(255)                 NOT NULL COMMENT '상세주소',
    `postcode`            VARCHAR(10)                  NOT NULL COMMENT '우편번호',
    `representative_name` VARCHAR(100)                 NOT NULL COMMENT '대표명',
    `phone`               VARCHAR(20)                  NOT NULL COMMENT '연락처',
    `client_type`         VARCHAR(20)                  NOT NULL COMMENT '거래처 구분',
    `status`              VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL COMMENT '상태',
    PRIMARY KEY (`client_code`)
) COMMENT = '거래처';

INSERT INTO tbl_client
    (client_code, client_name, address, address_detail, postcode, representative_name, phone, client_type, status)
VALUES
    (1, 'Client A', '123 Main St', 'Suite 100', '12345', 'John Doe', '123-456-7890', 'RAW_MATERIALS', 'ACTIVE'),
    (2, 'Client B', '456 Market St', 'Suite 200', '67890', 'Jane Smith', '987-654-3210', 'PRODUCTS', 'ACTIVE');

ALTER TABLE `tbl_client`
    ADD CONSTRAINT `tbl_client_CK` CHECK ( `client_type` IN ('RAW_MATERIALS', 'PRODUCTS'));

CREATE TABLE `tbl_department` (
    `department_code` INT                          NOT NULL AUTO_INCREMENT
        COMMENT '부서코드',
    `department_name` VARCHAR(100)                 NOT NULL COMMENT '부서명',
    `status`          VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL COMMENT '상태',
    `created_at`      DATETIME                     NOT NULL COMMENT '생성일시',
    `updated_at`      DATETIME COMMENT '수정일시',
    PRIMARY KEY (`department_code`)
) COMMENT = '부서';
INSERT INTO `tbl_department`
    (`department_code`, `department_name`, `status`, `created_at`)
VALUES
    (1, '영업부', 'ACTIVE', NOW()),
    (2, '생산부', 'ACTIVE', NOW()),
    (3, '물류부', 'ACTIVE', NOW());

CREATE TABLE `tbl_defect_detail`
(
    `defect_code`               BIGINT NOT NULL COMMENT '불량상세코드',
    `production_detail_code`    BIGINT NOT NULL COMMENT '생산 상세 코드',
    `defect_reason`             VARCHAR(50) COMMENT '불량 사유',
    `defect_status`             VARCHAR(20) COMMENT '불량 처리',
    `defect_file`               VARCHAR(50) COMMENT '첨부 파일',
    PRIMARY KEY (`defect_code`)
) COMMENT = '불량상세';

CREATE TABLE `tbl_emp_auth` (
    `emp_auth_code`  INT NOT NULL COMMENT '권한부여코드',
    `authority_code` INT NOT NULL COMMENT '권한코드',
    `employee_code`  INT NOT NULL COMMENT '사원코드',
    PRIMARY KEY (`emp_auth_code`)
) COMMENT = '권한부여';

CREATE TABLE `tbl_employee` (
    `employee_code`   INT                          NOT NULL AUTO_INCREMENT
        COMMENT '사원코드',
    `employee_no`     VARCHAR(50)                  NOT NULL COMMENT '사번',
    `employee_name`   VARCHAR(100)                 NOT NULL COMMENT '이름',
    `phone`           VARCHAR(20)                  NOT NULL COMMENT '연락처',
    `email`           VARCHAR(100)                 NOT NULL COMMENT '이메일',
    `password`        VARCHAR(255)                 NOT NULL COMMENT '비밀번호',
    `ssn`             CHAR(14)                     NOT NULL COMMENT '주민등록번호',
    `position_code`   INT                          NOT NULL COMMENT '직급코드',
    `department_code` INT                          NOT NULL COMMENT '부서코드',
    `profile_image`   VARCHAR(255) COMMENT '프로필 이미지',
    `hire_date`       DATE                         NOT NULL COMMENT '입사일시',
    `resign_date`     DATE COMMENT '퇴사일시',
    `status`          VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL COMMENT '상태',
    `created_at`      DATETIME                     NOT NULL COMMENT '생성일시',
    `updated_at`      DATETIME COMMENT '수정일시',
    `refresh_token`   VARCHAR(300) COMMENT '리프레시토큰',
    PRIMARY KEY (`employee_code`)
) COMMENT = '사원';
INSERT INTO `tbl_employee`
    (`employee_code`, `employee_no`, `employee_name`, `phone`, `email`, `password`, `ssn`, `position_code`,
     `department_code`,
     `profile_image`, `hire_date`, `resign_date`, `status`, `created_at`, `updated_at`, `refresh_token`)
VALUES
    (1, 'EMP001', '홍길동', '010-1234-5678', 'hong@example.com', 'password1', '800101-1234567', 1, 1, NULL, '2020-01-01',
     NULL, 'ACTIVE', NOW(), NULL, NULL),
    (2, 'EMP002', '김철수', '010-2345-6789', 'kim@example.com', 'password2', '900202-2345678', 2, 1, NULL, '2021-02-01',
     NULL, 'ACTIVE', NOW(), NULL, NULL),
    (3, 'EMP003', '이영희', '010-3456-7890', 'lee@example.com', 'password3', '950303-3456789', 2, 2, NULL, '2022-03-01',
     NULL, 'ACTIVE', NOW(), NULL, NULL),
    (4, 'EMP004', '박민수', '010-4567-8901', 'park@example.com', 'password4', '850404-4567890', 2, 3, NULL, '2019-04-01',
     NULL, 'ACTIVE', NOW(), NULL, NULL);

CREATE TABLE `tbl_estimate` (
    `estimate_code` INT                          NOT NULL AUTO_INCREMENT
        COMMENT '견적코드',
    `created_at`    DATETIME                     NOT NULL COMMENT '생성일시',
    `updated_at`    DATETIME COMMENT '수정일시',
    `deadline`      DATE                         NOT NULL COMMENT '마감일시',
    `status`        VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL COMMENT '상태',
    `is_ordered`    BOOLEAN     DEFAULT FALSE    NOT NULL COMMENT '주문전환여부',
    `client_code`   INT                          NOT NULL COMMENT '거래처코드',
    PRIMARY KEY (`estimate_code`)
) COMMENT = '상품 견적';

CREATE TABLE `tbl_estimate_product` (
    `estimate_product_code` INT NOT NULL AUTO_INCREMENT
        COMMENT '견적상품코드',
    `quantity`              INT NOT NULL COMMENT '수량',
    `price`                 INT NOT NULL COMMENT '단가',
    `estimate_code`         INT NOT NULL COMMENT '견적코드',
    `product_code`          INT NOT NULL COMMENT '상품 코드',
    PRIMARY KEY (`estimate_product_code`)
) COMMENT = '견적 목록';

CREATE TABLE `tbl_line` (
    `line_code`       BIGINT      NOT NULL COMMENT '라인 코드',
    `line_name`       VARCHAR(50) NOT NULL COMMENT '라인 이름',
    `line_production` INT COMMENT '생산량',
    `line_status`     VARCHAR(20) COMMENT '상태',
    `employee_code`   INT         NOT NULL COMMENT '담당자코드',
    PRIMARY KEY (`line_code`)
) COMMENT = 'tbl_line';

CREATE TABLE `tbl_material_order` (
    `order_code`          INT                        NOT NULL AUTO_INCREMENT
        COMMENT '계약 코드',
    `order_date`          DATE                       NOT NULL COMMENT '계약 일자',
    `delivery_due_date`   DATE                       NOT NULL COMMENT '배송 예정 일자',
    `client_code`         INT                        NOT NULL COMMENT '거래처코드',
    `status`              VARCHAR(20) DEFAULT '계약완료' NOT NULL COMMENT '상태',
    `is_regular_contract` BOOLEAN                    NOT NULL COMMENT '정기계약여부',
    `employee_code`       INT                        NOT NULL COMMENT '사원코드',
    `arrival_datetime`    DATETIME COMMENT '입고일자',
    `plan_code`           INT COMMENT '생산 계획 코드',
    `is_deleted`          BOOLEAN     DEFAULT FALSE  NOT NULL COMMENT '삭제여부',
    `deletion_reason`     VARCHAR(100) COMMENT '삭제사유',
    PRIMARY KEY (`order_code`)
) COMMENT = '원자재 주문';

ALTER TABLE `tbl_material_order`
    ADD CONSTRAINT `tbl_material_order_CK` CHECK ( `status` IN
                                                   ('CONTRACT_COMPLETED', 'DELIVERY_EXPECTED', 'DELIVERY_COMPLETED',
                                                    'CONTRACT_TERMINATION'));

CREATE TABLE `tbl_material_specification` (
    `spec_code`          INT          NOT NULL AUTO_INCREMENT
        COMMENT '스펙 코드',
    `material_name`      VARCHAR(50)  NOT NULL COMMENT '자재 이름',
    `remarks`            VARCHAR(200) COMMENT '비고',
    `unit`               VARCHAR(20)  NOT NULL COMMENT '측정단위',
    `spec_category_code` INT          NOT NULL COMMENT '스펙 분류 코드',
    `safety_stock`       INT          NOT NULL COMMENT '안전재고 기준량',
    `specification`      VARCHAR(100) NOT NULL COMMENT '스펙',
    PRIMARY KEY (`spec_code`)
) COMMENT = '원자재 스펙';


CREATE TABLE `tbl_material_stock` (
    `stock_code`            INT                      NOT NULL AUTO_INCREMENT
        COMMENT '재고 코드',
    `division`              VARCHAR(20) DEFAULT '양품' NOT NULL COMMENT '구분',
    `spec_code`             INT                      NOT NULL COMMENT '스펙 코드',
    `warehouse_code`        INT                      NOT NULL COMMENT '창고 코드',
    `incoming_quantity`     INT         DEFAULT 0    NOT NULL COMMENT '입고 수량',
    `actual_quantity`       INT         DEFAULT 0    NOT NULL COMMENT '실수량',
    `storage_datetime`      DATETIME                 NOT NULL COMMENT '적재 일시',
    `remarks`               VARCHAR(200) COMMENT '비고',
    `inspection_datetime`   DATETIME COMMENT '점검일시',
    `modification_datetime` DATETIME COMMENT '수정 일시',
    `modification_reason`   VARCHAR(200) COMMENT '수정사유',
    `order_code`            INT                      NOT NULL COMMENT '계약 코드',
    PRIMARY KEY (`stock_code`)
) COMMENT = '원자재 재고';

ALTER TABLE `tbl_material_stock`
    ADD CONSTRAINT `tbl_material_stock_CK` CHECK ( `division` IN ('STOCK', 'LOSS'));

CREATE TABLE `tbl_material_usage` (
    `usage_code`      INT                       NOT NULL AUTO_INCREMENT
        COMMENT '사용 코드',
    `usage_datetime`  DATETIME                  NOT NULL COMMENT '사용 일시',
    `employee_code`   INT                       NOT NULL COMMENT '사원코드',
    `work_order_code` INT                       NOT NULL COMMENT '작업 지시서 코드',
    `status`          VARCHAR(20) DEFAULT '준비됨' NOT NULL COMMENT '상태',
    PRIMARY KEY (`usage_code`)
) COMMENT = '원자재 사용';

CREATE TABLE `tbl_order` (
    `order_code`     INT                                  NOT NULL AUTO_INCREMENT
        COMMENT '주문코드',
    `order_datetime` DATETIME    DEFAULT NOW()            NOT NULL COMMENT '주문일시',
    `updated_at`     DATETIME COMMENT '수정일시',
    `deadline`       DATE                                 NOT NULL COMMENT '마감일시',
    `status`         VARCHAR(20) DEFAULT 'ORDER_RECEIVED' NOT NULL COMMENT '상태',
    `completed_at`   DATETIME COMMENT '완료일시',
    `client_code`    INT                                  NOT NULL COMMENT '거래처코드',
    `estimate_code`  INT                                  NOT NULL COMMENT '견적코드',
    PRIMARY KEY (`order_code`)
) COMMENT = '상품 주문';

CREATE TABLE `tbl_order_product` (
    `order_product_code` INT NOT NULL AUTO_INCREMENT
        COMMENT '주문상품코드',
    `quantity`           INT NOT NULL COMMENT '수량',
    `price`              INT NOT NULL COMMENT '단가',
    `order_code`         INT NOT NULL COMMENT '주문코드',
    `product_code`       INT NOT NULL COMMENT '상품 코드',
    PRIMARY KEY (`order_product_code`)
) COMMENT = '주문 목록';

CREATE TABLE `tbl_order_spec` (
    `order_spec_code` INT           NOT NULL AUTO_INCREMENT
        COMMENT '주문목록 코드',
    `order_quantity`  INT DEFAULT 0 NOT NULL COMMENT '주문수량',
    `price`           INT DEFAULT 0 NOT NULL COMMENT '가격',
    `order_code`      INT           NOT NULL COMMENT '계약 코드',
    `spec_code`       INT           NOT NULL COMMENT '스펙 코드',
    PRIMARY KEY (`order_spec_code`)
) COMMENT = '주문목록';

CREATE TABLE `tbl_planned_order_list` (
    `order_code`        INT NOT NULL COMMENT '주문코드',
    `plan_code`         INT NOT NULL COMMENT '생산 계획 코드',
    `planned_list_code` INT NOT NULL COMMENT '계획 주문 목록 코드',
    PRIMARY KEY (`planned_list_code`)
) COMMENT = '계획 주문 목록';

CREATE TABLE `tbl_position` (
    `position_code` INT                          NOT NULL AUTO_INCREMENT
        COMMENT '직급코드',
    `position_name` VARCHAR(100)                 NOT NULL COMMENT '직급명',
    `rank`          INT                          NOT NULL COMMENT '순위',
    `status`        VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL COMMENT '상태',
    `created_at`    DATETIME                     NOT NULL UNIQUE KEY
        COMMENT '생성일시',
    `updated_at`    DATETIME COMMENT '수정일시',
    PRIMARY KEY (`position_code`)
) COMMENT = '직급';

CREATE TABLE `tbl_product` (
    `product_code` INT                          NOT NULL AUTO_INCREMENT
        COMMENT '상품 코드',
    `product_name` VARCHAR(100)                 NOT NULL COMMENT '이름',
    `launch_date`  DATETIME                     NOT NULL COMMENT '출시일',
    `price`        INT                          NOT NULL COMMENT '정가',
    `unit`         VARCHAR(20)                  NOT NULL COMMENT '단위',
    `updated_at`   DATETIME COMMENT '상태 수정 날짜',
    `status`       VARCHAR(20) DEFAULT 'ACTIVE' NOT NULL COMMENT '상태',
    PRIMARY KEY (`product_code`)
) COMMENT = '상품';

ALTER TABLE `tbl_product`
    ADD CONSTRAINT `tbl_product_CK` CHECK ( `status` IN ('IN_PRODUCTION', 'PRODUCTION_DISCONTINUED'));

CREATE TABLE `tbl_product_spec` (
    `code`         INT         NOT NULL AUTO_INCREMENT
        COMMENT '상품 스펙 코드',
    `color`        VARCHAR(50) NOT NULL COMMENT '컬러',
    `type`         VARCHAR(50) NOT NULL COMMENT '종류',
    `product_code` INT COMMENT '상품 코드',
    `size`         VARCHAR(50) COMMENT '사이즈',
    PRIMARY KEY (`code`)
) COMMENT = '상품 스펙';


CREATE TABLE `tbl_production_detail` (
    `production_detail_code` BIGINT NOT NULL AUTO_INCREMENT COMMENT '생산 상세 코드',
    `work_order_code` INT NOT NULL COMMENT '작업 지시서 코드',
    `production_status_code` BIGINT NOT NULL COMMENT '생산 현황 코드',
    `inspection_date` DATETIME NOT NULL COMMENT '품질 검수 일자',
    `production_quantity` INT COMMENT '현재 생산량',
    `defect_quantity` INT COMMENT '불량 수량',
    `completely_quantity` INT COMMENT '양품 수',
    `production_memo` VARCHAR(50) COMMENT '비고',
    `production_status`  VARCHAR(20) NOT NULL COMMENT '상태',
    PRIMARY KEY (`production_detail_code`)
) COMMENT = '생산 상세';


CREATE TABLE IF NOT EXISTS `tbl_production_management`
(
    `production_status_code`    BIGINT NOT NULL COMMENT '생산 현황 코드',
    `start_at`    DATETIME NOT NULL COMMENT '생산 시작 일시',
    `completed_at`    DATETIME NOT NULL COMMENT '생산 마감 일시',
    `total_production_quantity`  INT NOT NULL COMMENT '총 샌산 수량',
    `production_file`    VARCHAR(50) COMMENT '생산 관리 서류(첨부 서류)',
    `production_status`    VARCHAR(20) NOT NULL COMMENT '상태',
    `inspection_status`    VARCHAR(20) NOT NULL COMMENT '품질 검수 처리',
    PRIMARY KEY ( `production_status_code` )
) COMMENT = '일일 생산 보고서';

ALTER TABLE `tbl_production_management`
    ADD CONSTRAINT `tbl_production_management_CK` CHECK ( `production_status` IN
                                                          ('PRODUCTION_COMPLETED', 'ADDITIONAL_PRODUCTION',
                                                           'PRODUCTION_HALT', 'IN_PRODUCTION', 'WAIT'));

CREATE TABLE `tbl_production_plan` (
    `plan_code`   INT      NOT NULL COMMENT '생산 계획 코드',
    `creation_at` DATETIME NOT NULL COMMENT '생성 일자',
    `start_at`    DATE     NOT NULL COMMENT '시작일자',
    `description` VARCHAR(255) COMMENT '적요',
    `updated_at`  DATETIME NOT NULL COMMENT '수정 일시',
    `end_at`      DATE     NOT NULL COMMENT '종료일자',
    PRIMARY KEY (`plan_code`)
) COMMENT = '생산 계획';

CREATE TABLE `tbl_production_plan_list` (
    `plan_list_code`    INT NOT NULL COMMENT '생산 계획 목로 코드',
    `required_quantity` INT NOT NULL COMMENT '생산 필요 수량',
    `panned_quantity`   INT NOT NULL COMMENT '생산 계획 수량',
    `plan_code`         INT NOT NULL COMMENT '생산 계획 코드',
    `product_code`      INT NOT NULL COMMENT '상품 코드',
    PRIMARY KEY (`plan_list_code`)
) COMMENT = '생산 계획 목록';

CREATE TABLE `tbl_release` (
    `release_code` BIGINT                      NOT NULL COMMENT '출고 코드',
    `status`       VARCHAR(255) DEFAULT 'WAIT' NOT NULL COMMENT '상태',
    `order_code`   INT                         NOT NULL COMMENT '주문코드',
    `created_at`   DATETIME UNIQUE KEY
        COMMENT '생성 일시',
    PRIMARY KEY (`release_code`)
) COMMENT = '상품 출고';


ALTER TABLE `tbl_release`
    ADD CONSTRAINT `tbl_release_CK` CHECK ( `status` IN ('WAIT', 'SHIPPING', 'DELIVERY_COMPLETED'));

CREATE TABLE `tbl_release_change` (
    `release_change_code` INT                             NOT NULL AUTO_INCREMENT
        COMMENT '출고 상태 변경 이력 코드',
    `status`              VARCHAR(255) DEFAULT 'SHIPPING' NOT NULL COMMENT '상태',
    `change_at`           VARCHAR(255)                    NOT NULL COMMENT '시간',
    `release_code`        BIGINT                          NOT NULL COMMENT '출고 코드',
    PRIMARY KEY (`release_change_code`)
) COMMENT = '출고 상태 변경 이력';

ALTER TABLE `tbl_release_change`
    ADD CONSTRAINT `tbl_release_change_CK` CHECK ( `status` IN ('SHIPPING', 'DELIVERY_COMPLETED'));

CREATE TABLE `tbl_return` (
    `return_code`     INT                                       NOT NULL AUTO_INCREMENT
        COMMENT '반품코드',
    `return_datetime` DATETIME    DEFAULT NOW()                 NOT NULL COMMENT '반품신청일시',
    `client_code`     INT                                       NOT NULL COMMENT '거래처코드',
    `order_code`      INT                                       NOT NULL COMMENT '주문코드',
    `manage_type`     VARCHAR(20)                               NOT NULL COMMENT '관리유형',
    `manage_status`   VARCHAR(20) DEFAULT 'RETURN_RECEIVED'     NOT NULL COMMENT '관리진행상태',
    `return_status`   VARCHAR(20) DEFAULT 'AWAITING_INSPECTION' NOT NULL COMMENT '반환상품상태',
    `exchange_order`  INT COMMENT '교환주문코드',
    `updated_at`      DATETIME COMMENT '수정일시',
    PRIMARY KEY (`return_code`)
) COMMENT = '상품 반품';

ALTER TABLE `tbl_return`
    ADD CONSTRAINT `tbl_return_CK` CHECK ( `manage_type` IN ('EXCHANGE', 'REFUND'));

CREATE TABLE `tbl_return_product` (
    `return_product_code` INT                   NOT NULL AUTO_INCREMENT
        COMMENT '반품상품코드',
    `quantity`            INT                   NOT NULL COMMENT '전체수량',
    `refund_price`        INT                   NOT NULL COMMENT '환불단가',
    `return_code`         INT                   NOT NULL COMMENT '반품코드',
    `product_code`        INT                   NOT NULL COMMENT '상품 코드',
    `defective_quantity`  INT COMMENT '불량 수량',
    `inspection_status`   BOOLEAN DEFAULT FALSE NOT NULL COMMENT '검수여부',
    PRIMARY KEY (`return_product_code`)
) COMMENT = '반품 목록';

CREATE TABLE `tbl_spec_category` (
    `spec_category_code` INT         NOT NULL AUTO_INCREMENT
        COMMENT '스펙 분류 코드',
    `spec_category_name` VARCHAR(50) NOT NULL COMMENT '스펙 분류 명',
    PRIMARY KEY (`spec_category_code`)
) COMMENT = '스펙 분류';

CREATE TABLE `tbl_stock` (
    `stock_code`   INT                            NOT NULL AUTO_INCREMENT
        COMMENT '재고 코드',
    `quantity`     INT                            NOT NULL UNIQUE KEY
        COMMENT '개수',
    `created_at`   DATETIME                       NOT NULL COMMENT '생성일',
    `is_delete`    BOOLEAN     DEFAULT FALSE      NOT NULL UNIQUE KEY
        COMMENT '삭제 여부',
    `type`         VARCHAR(50) DEFAULT 'PRODUCTS' NOT NULL COMMENT '종류',
    `product_code` INT                            NOT NULL COMMENT '상품 코드',
    PRIMARY KEY (`stock_code`)
) COMMENT = '상품 재고';

ALTER TABLE `tbl_stock`
    ADD CONSTRAINT `tbl_stock_CK` CHECK ( `type` IN ('PRODUCTS', 'RE_INSPECTION'));

INSERT INTO `tbl_stock`
    (`quantity`, `created_at`, `type`, `product_code`)
VALUES
    (100, NOW(), 'PRODUCTS', 1),
    (150, NOW(), 'PRODUCTS', 2),
    (200, NOW(), 'PRODUCTS', 3),
    (50, NOW(), 'PRODUCTS', 4);

CREATE TABLE `tbl_stock_usage` (
    `stock_usage_code`    INT NOT NULL AUTO_INCREMENT
        COMMENT '재고_사용 코드',
    `used_quantity`       INT     DEFAULT 0 COMMENT '사용 수량',
    `stock_code`          INT NOT NULL COMMENT '재고 코드',
    `usage_code`          INT NOT NULL COMMENT '사용 코드',
    `transmission_status` BOOLEAN DEFAULT TRUE COMMENT '전달여부',
    PRIMARY KEY (`stock_usage_code`)
) COMMENT = '원자재 재고_사용';

CREATE TABLE `tbl_storage` (
    `storage_code`     INT         NOT NULL AUTO_INCREMENT
        COMMENT '창고 보관 코드',
    `initial_quantity` INT         NOT NULL COMMENT '초기수량',
    `destroy_quantity` INT         NOT NULL COMMENT '파손수량',
    `is_delete`        VARCHAR(50) NOT NULL COMMENT '삭제 여부',
    `updated_at`       DATETIME COMMENT '수정 일시',
    `stock_code`       INT COMMENT '재고 코드',
    `warehouse_code`   INT         NOT NULL COMMENT '창고 코드',
    `actual_quantity`  INT COMMENT '실수량',
    `created_at`       DATETIME COMMENT '생성일',
    PRIMARY KEY (`storage_code`)
) COMMENT = '상품 창고 보관';

CREATE TABLE `tbl_warehouse` (
    `warehouse_code` INT AUTO_INCREMENT NOT NULL COMMENT '창고 코드',
    `name`           VARCHAR(50)        NOT NULL COMMENT '창고 이름',
    `location`       VARCHAR(50)        NOT NULL UNIQUE KEY
        COMMENT '창고 위치',
    `volume`         INT                NOT NULL COMMENT '창고 수용량',
    `employee_code`  INT                NOT NULL COMMENT '사원코드',
    PRIMARY KEY (`warehouse_code`)
) COMMENT = '창고';

CREATE TABLE `tbl_work_order` (
    `work_order_code`    INT                               NOT NULL COMMENT '작업 지시서 코드',
    `work_written_date`  DATETIME                          NOT NULL COMMENT '작성 날짜',
    `ordered_quantity`   INT                               NOT NULL COMMENT '지시 수량',
    `completion_status`  VARCHAR(20) DEFAULT 'IN_PROGRESS' NOT NULL COMMENT '종결 여부',
    `work_modified_date` DATETIME COMMENT '수정 일시',
    `line_code`          BIGINT                            NOT NULL COMMENT '라인 코드',
    `work_order_date`    DATE                              NOT NULL COMMENT '작업 지시 일자',
    `product_code`       INT                               NOT NULL COMMENT '상품 코드',
    PRIMARY KEY (`work_order_code`)
) COMMENT = '작업 지시서';

ALTER TABLE `tbl_work_order`
    ADD CONSTRAINT `tbl_work_order_CK` CHECK ( `completion_status` IN ('IN_PROGRESS', 'DONE'));

ALTER TABLE tbl_bom DROP INDEX quantity;

ALTER TABLE tbl_stock DROP INDEX is_delete;
ALTER TABLE tbl_stock DROP INDEX quantity;

ALTER TABLE tbl_stock
    ADD COLUMN assignment_status ENUM('NOT_ASSIGNED', 'PARTIALLY_ASSIGNED', 'FULLY_ASSIGNED')
DEFAULT 'NOT_ASSIGNED';

ALTER TABLE tbl_release_change DROP FOREIGN KEY tbl_release_change_fk1;
ALTER TABLE tbl_release MODIFY COLUMN release_code INT AUTO_INCREMENT;