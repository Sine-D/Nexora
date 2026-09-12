package com.admin.modules.developer.infrastructure;

import com.admin.modules.developer.domain.DeveloperProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeveloperProfileRepository extends JpaRepository<DeveloperProfile, Long> {
    Optional<DeveloperProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
