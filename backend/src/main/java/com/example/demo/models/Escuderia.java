package com.example.demo.models;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the escuderias database table.
 * 
 */
@Entity
@Table(name="escuderias")
@NamedQuery(name="Escuderia.findAll", query="SELECT e FROM Escuderia e")
public class Escuderia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String nombre;

	private String pais;

	//bi-directional many-to-one association to Piloto
	@OneToMany(mappedBy="escuderia")
	private List<Piloto> pilotos;

	public Escuderia() {
	}

	public Escuderia(int id, String nombre, String pais) {
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

	public Piloto addPiloto(Piloto piloto) {
		getPilotos().add(piloto);
		piloto.setEscuderia(this);

		return piloto;
	}

	public Piloto removePiloto(Piloto piloto) {
		getPilotos().remove(piloto);
		piloto.setEscuderia(null);

		return piloto;
	}

}