INSERT INTO tbl_material_specification (material_name, remarks, unit, spec_category_code, safety_stock, specification)
VALUES
    ('Material6', 'Sample remarks for Material6', 'kg', 1, 45, 'Spec6'),
    ('Material7', 'Sample remarks for Material7', 'pcs', 2, 25, 'Spec7'),
    ('Material8', 'Sample remarks for Material8', 'm', 1, 35, 'Spec8'),
    ('Material9', 'Sample remarks for Material9', 'g', 3, 15, 'Spec9'),
    ('Material10', 'Sample remarks for Material10', 'kg', 2, 55, 'Spec10'),
    ('Material11', 'Sample remarks for Material11', 'kg', 1, 42, 'Spec11'),
    ('Material12', 'Sample remarks for Material12', 'pcs', 2, 28, 'Spec12'),
    ('Material13', 'Sample remarks for Material13', 'm', 1, 38, 'Spec13'),
    ('Material14', 'Sample remarks for Material14', 'g', 3, 18, 'Spec14'),
    ('Material15', 'Sample remarks for Material15', 'kg', 2, 58, 'Spec15'),
    ('Material16', 'Sample remarks for Material16', 'kg', 1, 47, 'Spec16'),
    ('Material17', 'Sample remarks for Material17', 'pcs', 2, 27, 'Spec17'),
    ('Material18', 'Sample remarks for Material18', 'm', 1, 36, 'Spec18'),
    ('Material19', 'Sample remarks for Material19', 'g', 3, 17, 'Spec19'),
    ('Material20', 'Sample remarks for Material20', 'kg', 2, 53, 'Spec20'),
    ('Material21', 'Sample remarks for Material21', 'kg', 1, 48, 'Spec21'),
    ('Material22', 'Sample remarks for Material22', 'pcs', 2, 29, 'Spec22'),
    ('Material23', 'Sample remarks for Material23', 'm', 1, 39, 'Spec23'),
    ('Material24', 'Sample remarks for Material24', 'g', 3, 19, 'Spec24'),
    ('Material25', 'Sample remarks for Material25', 'kg', 2, 57, 'Spec25'),
    ('Material26', 'Sample remarks for Material26', 'kg', 1, 46, 'Spec26'),
    ('Material27', 'Sample remarks for Material27', 'pcs', 2, 26, 'Spec27'),
    ('Material28', 'Sample remarks for Material28', 'm', 1, 37, 'Spec28'),
    ('Material29', 'Sample remarks for Material29', 'g', 3, 16, 'Spec29'),
    ('Material30', 'Sample remarks for Material30', 'kg', 2, 54, 'Spec30'),
    ('Material31', 'Sample remarks for Material31', 'kg', 1, 49, 'Spec31'),
    ('Material32', 'Sample remarks for Material32', 'pcs', 2, 30, 'Spec32'),
    ('Material33', 'Sample remarks for Material33', 'm', 1, 40, 'Spec33'),
    ('Material34', 'Sample remarks for Material34', 'g', 3, 20, 'Spec34'),
    ('Material35', 'Sample remarks for Material35', 'kg', 2, 60, 'Spec35'),
    ('Material36', 'Sample remarks for Material36', 'kg', 1, 50, 'Spec36'),
    ('Material37', 'Sample remarks for Material37', 'pcs', 2, 31, 'Spec37'),
    ('Material38', 'Sample remarks for Material38', 'm', 1, 41, 'Spec38'),
    ('Material39', 'Sample remarks for Material39', 'g', 3, 21, 'Spec39'),
    ('Material40', 'Sample remarks for Material40', 'kg', 2, 61, 'Spec40'),
    ('Material41', 'Sample remarks for Material41', 'kg', 1, 51, 'Spec41'),
    ('Material42', 'Sample remarks for Material42', 'pcs', 2, 32, 'Spec42'),
    ('Material43', 'Sample remarks for Material43', 'm', 1, 42, 'Spec43'),
    ('Material44', 'Sample remarks for Material44', 'g', 3, 22, 'Spec44'),
    ('Material45', 'Sample remarks for Material45', 'kg', 2, 62, 'Spec45'),
    ('Material46', 'Sample remarks for Material46', 'kg', 1, 52, 'Spec46'),
    ('Material47', 'Sample remarks for Material47', 'pcs', 2, 33, 'Spec47'),
    ('Material48', 'Sample remarks for Material48', 'm', 1, 43, 'Spec48'),
    ('Material49', 'Sample remarks for Material49', 'g', 3, 23, 'Spec49'),
    ('Material50', 'Sample remarks for Material50', 'kg', 2, 63, 'Spec50');

INSERT INTO tbl_spec_category (spec_category_name)
VALUES
    ('Category1'),
    ('Category2'),
    ('Category3'),
    ('Category4'),
    ('Category5');

-- 오더 코드를 1부터 50까지 순차적으로 생성하고 specCode는 1부터 45까지의 랜덤한 값으로 설정합니다.
INSERT INTO tbl_material_stock (division, spec_code, warehouse_code, incoming_quantity, actual_quantity, storage_datetime, remarks, inspection_datetime, modification_datetime, modification_reason, order_code)
SELECT
    'STOCK',
    FLOOR(RAND() * 45) + 1, -- specCode를 1부터 45까지의 랜덤한 값으로 설정
    1, -- 임의의 창고 코드
    100, -- 임의의 입고 수량
    80, -- 임의의 실수량
    '2024-05-01 08:00:00', -- 임의의 적재 일시
    '비고', -- 임의의 비고
    '2024-05-03 10:00:00', -- 임의의 점검 일시
    '2024-05-05 14:00:00', -- 임의의 수정 일시
    '수정사유', -- 임의의 수정 사유
    t.n + 1 as order_code -- 오더 코드를 1부터 50까지 순차적으로 생성
  FROM
      (SELECT n FROM (SELECT 0 as n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) as nums) as t
          CROSS JOIN
      (SELECT n FROM (SELECT 0 as n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) as nums) as t2;


INSERT INTO tbl_material_order (order_date, delivery_due_date, client_code, status, is_regular_contract, employee_code)
VALUES
    ('2024-01-01', '2024-01-04', 1, 'ORDER_COMPLETED', FALSE, 1001),
    ('2024-01-04', '2024-01-07', 2, 'ORDER_COMPLETED', TRUE, 1002),
    ('2024-01-07', '2024-01-10', 3, 'DELIVERY_EXPECTED', FALSE, 1003),
    ('2024-01-10', '2024-01-13', 4, 'ORDER_COMPLETED', TRUE, 1004),
    ('2024-01-13', '2024-01-16', 5, 'DELIVERY_EXPECTED', FALSE, 1005),
    ('2024-01-16', '2024-01-19', 6, 'ORDER_COMPLETED', TRUE, 1006),
    ('2024-01-19', '2024-01-22', 7, 'DELIVERY_EXPECTED', FALSE, 1007),
    ('2024-01-22', '2024-01-25', 8, 'ORDER_COMPLETED', TRUE, 1008),
    ('2024-01-25', '2024-01-28', 9, 'DELIVERY_EXPECTED', FALSE, 1009),
    ('2024-01-28', '2024-01-31', 10, 'ORDER_COMPLETED', TRUE, 1010),
    ('2024-02-01', '2024-02-04', 11, 'DELIVERY_EXPECTED', FALSE, 1011),
    ('2024-02-04', '2024-02-07', 12, 'ORDER_COMPLETED', TRUE, 1012),
    ('2024-02-07', '2024-02-10', 13, 'DELIVERY_EXPECTED', FALSE, 1013),
    ('2024-02-10', '2024-02-13', 14, 'ORDER_COMPLETED', TRUE, 1014),
    ('2024-02-13', '2024-02-16', 15, 'DELIVERY_EXPECTED', FALSE, 1015),
    ('2024-02-16', '2024-02-19', 16, 'ORDER_COMPLETED', TRUE, 1016),
    ('2024-02-19', '2024-02-22', 17, 'DELIVERY_EXPECTED', FALSE, 1017),
    ('2024-02-22', '2024-02-25', 18, 'ORDER_COMPLETED', TRUE, 1018),
    ('2024-02-25', '2024-02-28', 19, 'DELIVERY_EXPECTED', FALSE, 1019),
    ('2024-02-28', '2024-03-02', 20, 'ORDER_COMPLETED', TRUE, 1020),
    ('2024-03-01', '2024-03-04', 21, 'DELIVERY_EXPECTED', FALSE, 1021),
    ('2024-03-04', '2024-03-07', 22, 'ORDER_COMPLETED', TRUE, 1022),
    ('2024-03-07', '2024-03-10', 23, 'DELIVERY_EXPECTED', FALSE, 1023),
    ('2024-03-10', '2024-03-13', 24, 'ORDER_COMPLETED', TRUE, 1024),
    ('2024-03-13', '2024-03-16', 25, 'DELIVERY_EXPECTED', FALSE, 1025),
    ('2024-03-16', '2024-03-19', 26, 'ORDER_COMPLETED', TRUE, 1026),
    ('2024-03-19', '2024-03-22', 27, 'DELIVERY_EXPECTED', FALSE, 1027),
    ('2024-03-22', '2024-03-25', 28, 'ORDER_COMPLETED', TRUE, 1028),
    ('2024-03-25', '2024-03-28', 29, 'DELIVERY_EXPECTED', FALSE, 1029),
    ('2024-03-28', '2024-03-31', 30, 'ORDER_COMPLETED', TRUE, 1030),
    ('2024-04-01', '2024-04-04', 31, 'DELIVERY_EXPECTED', FALSE, 1031),
    ('2024-04-04', '2024-04-07', 32, 'ORDER_COMPLETED', TRUE, 1032),
    ('2024-04-07', '2024-04-10', 33, 'DELIVERY_EXPECTED', FALSE, 1033),
    ('2024-04-10', '2024-04-13', 34, 'ORDER_COMPLETED', TRUE, 1034);

ALTER TABLE tbl_material_order AUTO_INCREMENT = 1;
