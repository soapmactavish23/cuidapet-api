package com.hkprogrammer.api.domain.controllers;

import com.hkprogrammer.api.domain.models.dto.SupplierNearbyMeDto;
import com.hkprogrammer.api.domain.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/fornecedores")
public class SupplierController {

    private final SupplierService service;

    @GetMapping()
    public List<SupplierNearbyMeDto> findNearByMe(
            @RequestParam(required = true) Double lat,
            @RequestParam(required = true) Double lng
    ) {
        return service.findNearByMe(lat, lng);
    }

}
