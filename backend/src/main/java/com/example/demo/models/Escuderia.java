package com.example.demo.models;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Escuderia.findAll", query = "SELECT e FROM Escuderia e")
public class Escuderia implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos
    @Id
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "puntos", nullable = false)
    private int puntos;
    
}
