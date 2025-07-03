package com.example.auth.domain.port.out;

import com.example.auth.adapters.out.persistence.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenPort extends JpaRepository<RefreshTokenEntity, String> {}