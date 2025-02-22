package com.bwardweb.spring6restmvc.repostitories;

import com.bwardweb.spring6restmvc.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
