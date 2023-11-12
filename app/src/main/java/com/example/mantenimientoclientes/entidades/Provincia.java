package com.example.mantenimientoclientes.entidades;

import java.util.Objects;

public class Provincia {
    private String nomProvincia;
    private Integer idProvincia;

    public Provincia(String nomProvincia, Integer idProvincia) {
        this.nomProvincia = nomProvincia;
        this.idProvincia = idProvincia;
    }

    @Override
    public String toString() {
        return nomProvincia;
    }

    public String getNomProvincia() {
        return nomProvincia;
    }

    public void setNomProvincia(String nomProvincia) {
        this.nomProvincia = nomProvincia;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provincia provincia = (Provincia) o;
        return Objects.equals(nomProvincia, provincia.nomProvincia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomProvincia);
    }
}
