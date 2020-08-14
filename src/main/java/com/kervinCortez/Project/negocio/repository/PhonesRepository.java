package com.kervinCortez.Project.negocio.repository;

import com.kervinCortez.Project.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhonesRepository extends JpaRepository<Phone,String> {

}
