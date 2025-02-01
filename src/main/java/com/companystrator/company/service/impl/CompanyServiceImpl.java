package com.companystrator.company.service.impl;

import com.companystrator.company.dto.req.CreateCompanyDTO;
import com.companystrator.company.dto.req.UpdateCompanyDTO;
import com.companystrator.company.dto.res.CompanyDTO;
import com.companystrator.company.service.CompanyService;
import com.companystrator.db.model.Company;
import com.companystrator.db.repository.CompanyRepository;
import com.companystrator.exceptions.exception.CompanyNameAlreadyExist;
import com.companystrator.exceptions.exception.CompanyNotFoundException;
import com.companystrator.exceptions.exception.CreateCompanyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    public final CompanyRepository companyRepository;

    @Override
    public void createCompany(CreateCompanyDTO request) {
        Optional<Company> validateCompanyExist = this.companyRepository.findByNitOrName(request.nit(), request.name());
        if (validateCompanyExist.isPresent()) {
            log.error("NIT {} or name {} already exist", request.nit(), request.name());
            throw new CreateCompanyException("Company with NIT " + request.nit() + " or name " + request.name() + " already exists");
        }
        Company newCompany = Company.builder()
            .nit(request.nit())
            .name(request.name())
            .address(request.address())
            .phoneIndicator(request.phoneIndicator())
            .phone(request.phone())
            .build();
        this.companyRepository.save(newCompany);
    }

    @Override
    public CompanyDTO getCompanyByNit(String nit) {
        Company company = this.getCompanyById(nit);
        return this.toCompanyDTO(company);
    }

    @Override
    public List<CompanyDTO> getCompanyList() {
        return this.companyRepository.findAll()
            .stream().map(this::toCompanyDTO)
            .toList();
    }

    @Override
    public void updateCompany(String nit, UpdateCompanyDTO request) {
         this.companyRepository.findByNitOrName(request.name(), request.name())
            .ifPresent(company -> {
                if (!company.getNit().equals(nit)) {
                    throw new CompanyNameAlreadyExist("The name is already taken");
                }
            });
        Company company = this.getCompanyById(nit);
        company.setName(request.name());
        company.setAddress(request.address());
        company.setPhoneIndicator(request.phoneIndicator());
        company.setPhone(request.phone());
        this.companyRepository.saveAndFlush(company);
    }

    @Override
    public void deleteCompany(String nit) {
        Company company = this.getCompanyById(nit);
        this.companyRepository.delete(company);
    }

    private Company getCompanyById(String nit) {
        return this.companyRepository.findById(nit)
            .orElseThrow(() -> new CompanyNotFoundException("Company not found with NIT " + nit));
    }

    private CompanyDTO toCompanyDTO(Company company) {
        return CompanyDTO.builder()
            .nit(company.getNit())
            .name(company.getName())
            .address(company.getAddress())
            .phoneIndicator(company.getPhoneIndicator())
            .phone(company.getPhone())
            .build();
    }

}
