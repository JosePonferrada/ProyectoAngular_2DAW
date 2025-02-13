package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the resultado database table.
 * 
 */
@Entity
@NamedQuery(name="Resultado.findAll", query="SELECT r FROM Resultado r")
public class Resultado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int posicion;

	private String tiempo;

	private int vueltas;

	//bi-directional many-to-one association to Carrera
	@ManyToOne
	@JoinColumn(name="carreraId")
	private Carrera carrera;

	//bi-directional many-to-one association to Escuderia
	@ManyToOne
	@JoinColumn(name="equipoId")
	private Escuderia escuderia;

	//bi-directional many-to-one association to Piloto
	@ManyToOne
	@JoinColumn(name="pilotoId")
	private Piloto piloto;

	public Resultado() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPosicion() {
		return this.posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getTiempo() {
		return this.tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public int getVueltas() {
		return this.vueltas;
	}

	public void setVueltas(int vueltas) {
		this.vueltas = vueltas;
	}

	public Carrera getCarrera() {
		return this.carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public Escuderia getEscuderia() {
		return this.escuderia;
	}

	public void setEscuderia(Escuderia escuderia) {
		this.escuderia = escuderia;
	}

	public Piloto getPiloto() {
		return this.piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

}