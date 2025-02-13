package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the prediccion database table.
 * 
 */
@Entity
@NamedQuery(name="Prediccion.findAll", query="SELECT p FROM Prediccion p")
public class Prediccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int carreraId;

	private int pilotoId;

	private int posicionPredicha;

	private int usuarioId;

	public Prediccion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCarreraId() {
		return this.carreraId;
	}

	public void setCarreraId(int carreraId) {
		this.carreraId = carreraId;
	}

	public int getPilotoId() {
		return this.pilotoId;
	}

	public void setPilotoId(int pilotoId) {
		this.pilotoId = pilotoId;
	}

	public int getPosicionPredicha() {
		return this.posicionPredicha;
	}

	public void setPosicionPredicha(int posicionPredicha) {
		this.posicionPredicha = posicionPredicha;
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

}