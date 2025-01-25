package com.companystrator.company.controller;

import com.companystrator.company.dto.req.CreateCompanyDTO;
import com.companystrator.company.dto.req.UpdateCompanyDTO;
import com.companystrator.company.dto.res.CompanyDTO;
import com.companystrator.company.dto.res.SuccessResponseDTO;
import com.companystrator.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create-company")
    public ResponseEntity<SuccessResponseDTO> createCompany(@RequestBody @Valid CreateCompanyDTO request) {
        this.companyService.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO("Company " + request.name() + " created successfully"));
    }

    @GetMapping("/get-company-by-nit/{nit}")
    public ResponseEntity<CompanyDTO> getCompanyByNit(@PathVariable("nit") String nit) {
        CompanyDTO response = this.companyService.getCompanyByNit(nit);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get-company-list")
    public ResponseEntity<List<CompanyDTO>> getCompanyList() {
        List<CompanyDTO> response = this.companyService.getCompanyList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update-company/{nit}")
    public ResponseEntity<SuccessResponseDTO> updateCompany(@PathVariable("nit") String nit, @RequestBody @Valid UpdateCompanyDTO request) {
        this.companyService.updateCompany(nit, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponseDTO("Company " + request.name() + " updated successfully"));
    }

    @DeleteMapping("/delete-company/{nit}")
    public ResponseEntity<SuccessResponseDTO> deleteCompany(@PathVariable("nit") String nit) {
        this.companyService.deleteCompany(nit);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDTO("Company with NIT: " + nit + " deleted successfully"));
    }

}
