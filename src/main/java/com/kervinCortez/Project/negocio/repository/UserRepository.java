package com.kervinCortez.Project.negocio.repository;

import com.kervinCortez.Project.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<Usuario,String>{

	Usuario findByEmail(String email);
}
