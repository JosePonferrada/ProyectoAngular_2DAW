package com.example.demo.models;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the pilotos database table.
 * 
 */
@Entity
@Table(name="pilotos")
@NamedQuery(name="Piloto.findAll", query="SELECT p FROM Piloto p")
public class Piloto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int dorsal;

	private String nombre;

	//bi-directional many-to-many association to Circuito
	@ManyToMany(mappedBy="pilotos")
	private List<Circuito> circuitos;

	//bi-directional many-to-one association to Escuderia
	@ManyToOne
	private Escuderia escuderia;

	public Piloto() {
	}

	public Piloto(int id, int dorsal, String nombre, Escuderia escuderia) {
		super();
		this.id = id;
		this.dorsal = dorsal;
		this.nombre = nombre;
		this.escuderia = escuderia;
	}

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

	public List<Circuito> getCircuitos() {
		return this.circuitos;
	}

	public void setCircuitos(List<Circuito> circuitos) {
		this.circuitos = circuitos;
	}

	public Escuderia getEscuderia() {
		return this.escuderia;
	}

	public void setEscuderia(Escuderia escuderia) {
		this.escuderia = escuderia;
	}

}