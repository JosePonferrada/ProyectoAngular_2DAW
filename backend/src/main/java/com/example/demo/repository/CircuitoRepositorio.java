package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Circuito;

import jakarta.transaction.Transactional;

public interface CircuitoRepositorio extends JpaRepository<Circuito, Serializable> {

    @Bean
    public abstract List<Circuito> findAll();
    public abstract Circuito findById(int id);

    @SuppressWarnings("unchecked")
    public abstract Circuito save(Circuito circuito);

    @Transactional
    public abstract void delete(Circuito circuito);

    @Transactional
    public abstract void deleteById(int id);

}
