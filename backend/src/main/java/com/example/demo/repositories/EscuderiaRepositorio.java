package com.example.demo.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Escuderia;
import jakarta.transaction.Transactional;

public interface EscuderiaRepositorio extends JpaRepository<Escuderia, Serializable> {

    @Bean
    public abstract List<Escuderia> findAll();
    public abstract Escuderia findById(int id);
    public abstract Escuderia findByNombre(String nombre);

    @Transactional
    public abstract void deleteById(int id);
    @Transactional
    public abstract void delete(Escuderia escuderia);

    @SuppressWarnings("unchecked")
    @Transactional
    public abstract Escuderia save(Escuderia escuderia);
    
    

}
