package com.companystrator.company.service;

import com.companystrator.company.dto.req.CreateCompanyDTO;
import com.companystrator.company.dto.req.UpdateCompanyDTO;
import com.companystrator.company.dto.res.CompanyDTO;

import java.util.List;

public interface CompanyService {

    void createCompany(CreateCompanyDTO request);

    CompanyDTO getCompanyByNit(String nit);

    List<CompanyDTO> getCompanyList();

    void updateCompany(String nit, UpdateCompanyDTO request);

    void deleteCompany(String nit);

}
