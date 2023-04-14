package com.api.superhero.domain;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "SUPERHERO")
public class SuperheroDAO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name field needed to be completed.")
    private String name;
    
    @Type(StringArrayType.class)
    @Column(
        name = "super_powers",
        columnDefinition = "text[]"
    )
    private String[] superPowers;

    public SuperheroDAO() {
        
    }
            
    public SuperheroDAO(String name) {
        this.name = name;
    }
    
    public SuperheroDAO(String name, String[] superPowers) {
        this.name = name;
        this.superPowers = superPowers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSuperPowers() {
        return superPowers;
    }

    public void setSuperPowers(String[] superPowers) {
        this.superPowers = superPowers;
    }
    
}
