package com.qre.pay.dao.sysconfig;

import com.qre.pay.entity.sysconfig.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String> {

    @Query(value = "SELECT prop_value FROM SystemConfig WHERE prop_id = ?1", nativeQuery = true)
    String getPropertyValueByPropId(String propertyId);
}
