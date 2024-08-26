package com.hkprogrammer.api.domain.controllers;

import com.hkprogrammer.api.domain.models.Supplier;
import com.hkprogrammer.api.domain.models.dto.SupplierNearbyMeDto;
import com.hkprogrammer.api.domain.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> findById(@PathVariable("id") Integer id) {
        Supplier obj = service.findById(id);
        return ResponseEntity.ok(obj);
    }

}
