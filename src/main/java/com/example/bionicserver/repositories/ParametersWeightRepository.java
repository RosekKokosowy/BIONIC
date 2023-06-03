package com.example.bionicserver.repositories;

import com.example.bionicserver.data.ParametersWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametersWeightRepository extends JpaRepository<ParametersWeight, Long> {
    ParametersWeight findFirstByOrderByIdDesc();
}
