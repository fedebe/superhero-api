package com.api.superhero.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.superhero.domain.SuperheroDAO;

@Repository
public interface SuperheroRepository extends JpaRepository<SuperheroDAO, Long> {

}
