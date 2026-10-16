package com.taller.poo;

public class Estudiante {
    private String identificacion;
    private String nombreCompleto;
    private double promedioCalificaciones;
    private boolean estaMatriculado;

    public Estudiante(String identificacion, String nombreCompleto,
                      double promedioCalificaciones, boolean estaMatriculado) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.promedioCalificaciones = promedioCalificaciones;
        this.estaMatriculado = estaMatriculado;
    }

    public String obtenerReporteAcademicos() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Estudiante: %s | Promedio: %.2f | Matriculado: %b",
                nombreCompleto, promedioCalificaciones, estaMatriculado));
        return sb.toString();
    }

    public String getIdentificacion() { return identificacion; }
}