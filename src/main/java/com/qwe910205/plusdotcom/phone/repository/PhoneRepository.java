package com.qwe910205.plusdotcom.phone.repository;

import com.qwe910205.plusdotcom.phone.domain.PhoneModel;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelId;
import com.qwe910205.plusdotcom.phone.domain.wrapper.PhoneModelName;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends JpaRepository<PhoneModel, Long> {

    @EntityGraph(attributePaths = {"manufacturer", "networkTech"})
    @Override
    List<PhoneModel> findAll();

    Optional<PhoneModel> findByName(PhoneModelName phoneModelName);

    @EntityGraph(attributePaths = {"manufacturer", "networkTech"})
    Optional<PhoneModel> findByPhoneModelId(PhoneModelId phoneModelId);
}
