package com.example.mantenimientoclientes.entidades;

public class Cliente {
    private String nombre,apellidos,dni;
    private Integer idProvincia;
    private boolean vip;
    private Double cord1,cord2;

    public Cliente() {
    }

    public Cliente(String dni, String nombre, String apellidos , Integer idProvincia, boolean vip, Double cord1, Double cord2) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.idProvincia = idProvincia;
        this.vip = vip;
        this.cord1 = cord1;
        this.cord2 = cord2;
    }

    @Override
    public String toString() {
        return apellidos+", "+nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getidProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public Double getCord1() {
        return cord1;
    }

    public void setCord1(Double cord1) {
        this.cord1 = cord1;
    }

    public Double getCord2() {
        return cord2;
    }

    public void setCord2(Double cord2) {
        this.cord2 = cord2;
    }
}
