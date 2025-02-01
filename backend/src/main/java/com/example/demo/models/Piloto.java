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

    // Constructores
    public Piloto() {
    }

    public Piloto(int id, int dorsal, String nombre, Escuderia equipo, int puntos) {
        this.id = id;
        this.dorsal = dorsal;
        this.nombre = nombre;
        this.equipo = equipo;
        this.puntos = puntos;
    }

    // Getters y Setters
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDorsal() {
        return this.dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Escuderia getEquipo() {
        return this.equipo;
    }

    public void setEquipo(Escuderia equipo) {
        this.equipo = equipo;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

}
