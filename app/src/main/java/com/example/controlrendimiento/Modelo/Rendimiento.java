package com.example.controlrendimiento.Modelo;

public class Rendimiento {
    private String Id;
    private String fecha;
    private String bloque;
    private String variedad;
    private String cortador;
    private String tallos;
    private String mallas;

    public Rendimiento() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getCortador() {
        return cortador;
    }

    public void setCortador(String cortador) {
        this.cortador = cortador;
    }

    public String getTallos() {
        return tallos;
    }

    public void setTallos(String tallos) {
        this.tallos = tallos;
    }

    public String getMallas() {
        return mallas;
    }

    public void setMallas(String mallas) {
        this.mallas = mallas;
    }

    @Override
    public String toString() {
        return
                fecha +" "+ bloque +" "+variedad +" "+cortador +" "+ tallos +" "+ mallas ;
    }
}
