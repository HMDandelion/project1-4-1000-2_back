package com.hmdandelion.project_1410002.inventory.domian.repository.warehouse;

import com.hmdandelion.project_1410002.inventory.domian.entity.warehouse.Warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{
}
