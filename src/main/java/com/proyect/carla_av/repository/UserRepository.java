package com.proyect.carla_av.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyect.carla_av.model.Person;

public interface UserRepository extends JpaRepository<Person, Long> {

	Person findByEmailAndEdad(String email, Integer edad);
	
	@Query("select u "
			  + "from person u  "
		      + "where u.email = :email")
		Person findUserByEmail(@Param("email") String email);

}
