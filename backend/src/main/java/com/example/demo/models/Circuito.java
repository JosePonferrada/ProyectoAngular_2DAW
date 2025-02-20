package com.example.demo.models;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the circuitos database table.
 * 
 */
@Entity
@Table(name="circuitos")
@NamedQuery(name="Circuito.findAll", query="SELECT c FROM Circuito c")
public class Circuito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nombre;

	private String pais;

	//bi-directional many-to-many association to Piloto
	@ManyToMany
	@JoinTable(
		name="participaciones"
		, joinColumns={
			@JoinColumn(name="circuito_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="piloto_id")
			}
		)
	private List<Piloto> pilotos;

	public Circuito() {
	}

	public Circuito(int id, String nombre, String pais) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
	}

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

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<Piloto> getPilotos() {
		return this.pilotos;
	}

	public void setPilotos(List<Piloto> pilotos) {
		this.pilotos = pilotos;
	}

}