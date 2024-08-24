package com.hkprogrammer.api.domain.repositories;

import com.hkprogrammer.api.domain.models.Supplier;
import com.hkprogrammer.api.domain.models.dto.SupplierNearbyMeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query(value = "select f.id, f.nome, f.logo, f.categorias_fornecedor_id, \n" +
            "(6371 * acos( cos(RADIANS(?1)) * \n" +
            "cos(radians(ST_X(f.latlng))) * \n" +
            "cos(RADIANS(?2) - radians(ST_Y(f.latlng))) + \n" +
            "sin(RADIANS(?1)) * sin(radians(ST_X(f.latlng))) )) \n" +
            "As distancia from fornecedor f having distancia <= ?3", nativeQuery = true)
    public List<SupplierNearbyMeDto> findNearByPosition(Double lat, Double lng, Integer distance);

}
