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

    // Constructores
    public Escuderia() {
    }

    public Escuderia(int id, String nombre, int puntos) {
        this.id = id;
        this.nombre = nombre;
        this.puntos = puntos;
    }

    // Getters y Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
    
}
