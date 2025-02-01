package com.example.demo.models;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "Piloto.findAll", query = "SELECT p FROM Piloto p")
public class Piloto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // Atributos
    @Id
    private int id;

    @Column(name = "dorsal", nullable = false)
    private int dorsal;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = true)
    private Escuderia equipo;

    @Column(name = "puntos", nullable = false)
    private int puntos;

}
