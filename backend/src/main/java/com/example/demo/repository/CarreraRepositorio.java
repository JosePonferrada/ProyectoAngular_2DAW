package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Carrera;

import jakarta.transaction.Transactional;

public interface CarreraRepositorio extends JpaRepository<Carrera, Serializable> {
    
    @Bean
    public abstract List<Carrera> findAll();
    public abstract Carrera findById(int id);

    @SuppressWarnings("unchecked")
    public abstract Carrera save(Carrera carrera);
    
    @Transactional
    public abstract void delete(Carrera carrera);
    
    @Transactional
    public abstract void deleteById(int id);

}
