package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByCategoryAndKey(String category, String key);
    List<Master> findByCategory(String category);
}
