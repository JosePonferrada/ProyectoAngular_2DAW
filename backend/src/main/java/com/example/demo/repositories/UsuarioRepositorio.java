package com.example.demo.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Usuario;

import jakarta.transaction.Transactional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Serializable> {
    
    @Bean
    public abstract List<Usuario> findAll();

    public abstract Usuario findById(int id);
    public abstract Usuario findByUsername(String username);
    public abstract Usuario findByEmail(String email);
    public abstract Usuario findByRole(String role);

    @Transactional
    public abstract void deleteById(int id);
    @Transactional
    public abstract void deleteByUsername(String username);
    @Transactional
    public abstract void deleteByEmail(String email);
    @Transactional
    public abstract void deleteByRole(String role);
    
    public abstract void delete(Usuario usuario);

    @SuppressWarnings("unchecked")
    public abstract Usuario save(Usuario usuario);

}
