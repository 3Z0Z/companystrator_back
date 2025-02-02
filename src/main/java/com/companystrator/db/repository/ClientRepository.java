package com.companystrator.db.repository;

import com.companystrator.db.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByUserIdAndCompanyNit(Long userId, String companyNit);

    List<Client> findByCompanyNit(String nit);

    List<Client> findByUserId(Long id);

}
