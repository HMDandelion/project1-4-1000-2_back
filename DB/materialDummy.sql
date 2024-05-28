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


INSERT INTO tbl_material_order (order_date, delivery_due_date, client_code, status, is_regular_contract, employee_code, arrival_datetime, plan_code, is_deleted, deletion_reason)
SELECT
    DATE_ADD('2023-11-01', INTERVAL FLOOR(RAND() * (DATEDIFF(NOW(), '2023-11-01') + 1)) MONTH) AS order_date,
    DATE_ADD('2023-11-01', INTERVAL FLOOR(RAND() * (DATEDIFF(NOW(), '2023-11-01') + 1)) MONTH) AS delivery_due_date,
    FLOOR(RAND() * 1000) + 1 AS client_code,
    CASE
        WHEN RAND() < 0.3 THEN 'ORDER_COMPLETE'
        WHEN RAND() < 0.6 THEN 'DELIVERY_EXPECTED'
        ELSE 'DELIVERY_COMPLETED'
        END AS status,
    RAND() < 0.5 AS is_regular_contract,
    FLOOR(RAND() * 1000) + 1 AS employee_code,
    CASE
        WHEN RAND() < 0.5 THEN NOW()
        ELSE NULL
        END AS arrival_datetime,
    NULL AS plan_code,
    FALSE AS is_deleted,
    NULL AS deletion_reason
 LIMIT 60; -- 5건씩 총 12개월
