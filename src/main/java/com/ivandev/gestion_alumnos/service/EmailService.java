package com.ivandev.gestion_alumnos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mensaje.setFrom("noreply@gestionalumnos.com");
        mailSender.send(mensaje);
    }

    public void enviarBienvenida(String destinatario, String nombreAlumno) {
        String asunto = "¡Bienvenido/a!";
        String cuerpo = "Hola " + nombreAlumno + ",\n\n" +
                "Te confirmamos que tu inscripción fue registrada exitosamente.\n\n" +
                "¡Te esperamos!\n\n" +
                "Equipo de Gestión de Alumnos";
        enviarEmail(destinatario, asunto, cuerpo);
    }

    public void enviarPromovido(String destinatario, String nombreAlumno, String nombreGrupo) {
        String asunto = "¡Tienes un lugar disponible!";
        String cuerpo = "Hola " + nombreAlumno + ",\n\n" +
                "Te informamos que se liberó un lugar en el grupo \"" + nombreGrupo + "\" " +
                "y fuiste promovido/a de la lista de espera.\n\n" +
                "¡Te esperamos!\n\n" +
                "Equipo de Gestión de Alumnos";
        enviarEmail(destinatario, asunto, cuerpo);
    }

    public void enviarRecordatorioPago(String destinatario, String nombreAlumno, int mes, int anio) {
        String[] meses = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String asunto = "Recordatorio de pago - " + meses[mes] + " " + anio;
        String cuerpo = "Hola " + nombreAlumno + ",\n\n" +
                "Te recordamos que aún no registramos tu pago correspondiente a " +
                meses[mes] + " " + anio + ".\n\n" +
                "Por favor, regularizá tu situación a la brevedad.\n\n" +
                "Equipo de Gestión de Alumnos";
        enviarEmail(destinatario, asunto, cuerpo);
    }
}