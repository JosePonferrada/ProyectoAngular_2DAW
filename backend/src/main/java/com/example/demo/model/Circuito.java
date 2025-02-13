package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the circuito database table.
 * 
 */
@Entity
@NamedQuery(name="Circuito.findAll", query="SELECT c FROM Circuito c")
public class Circuito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String foto;

	private String longitud;

	private String nombre;

	private String pais;

	private int vueltas;

	//bi-directional many-to-one association to Carrera
	@OneToMany(mappedBy="circuito")
	private List<Carrera> carreras;

	public Circuito() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getLongitud() {
		return this.longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
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

	public int getVueltas() {
		return this.vueltas;
	}

	public void setVueltas(int vueltas) {
		this.vueltas = vueltas;
	}

	public List<Carrera> getCarreras() {
		return this.carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public Carrera addCarrera(Carrera carrera) {
		getCarreras().add(carrera);
		carrera.setCircuito(this);

		return carrera;
	}

	public Carrera removeCarrera(Carrera carrera) {
		getCarreras().remove(carrera);
		carrera.setCircuito(null);

		return carrera;
	}

}