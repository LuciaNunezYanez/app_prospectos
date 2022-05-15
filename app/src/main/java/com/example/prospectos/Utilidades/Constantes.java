package com.example.prospectos.Utilidades;

public class Constantes {
    public final static String URL_BASE = "http://10.0.2.2:8080/"; // Localhost (emulador)
    public final static String TIPO_VER = "ver";
    public final static String TIPO_EVALUAR = "evaluar";

    // Constantes para la busqueda de archivos
    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String XLS = "application/vnd.ms-excel application/x-excel";
    public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PDF = "application/pdf";

    // ESTATUS DE LOS PROSPECTOS
    public static String ESTATUS_ENVIADO = "enviado";
    public static String ESTATUS_RECHAZADO = "rechazado";
    public static String ESTATUS_ACEPTADO = "aceptado";

    // DIFERENTES TIPOS DE USUARIOS QUE SE PUEDEN LOGUEAR
    public static String DEPARTAMENTO_PROMOCION ="promocion";
    public static String DEPARTAMENTO_EVALUACION ="evaluacion";
}
