package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Piloto;

import jakarta.transaction.Transactional;

public interface PilotoRepositorio extends JpaRepository<Piloto, Serializable> {
    
    @Bean
    public abstract List<Piloto> findAll();
    public abstract Piloto findById(int id);

    @SuppressWarnings("unchecked")
    public abstract Piloto save(Piloto piloto);
    
    @Transactional
    public abstract void delete(Piloto piloto);
    
    @Transactional
    public abstract void deleteById(int id);
    
}
