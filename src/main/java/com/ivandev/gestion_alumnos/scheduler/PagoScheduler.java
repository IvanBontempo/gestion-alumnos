package com.ivandev.gestion_alumnos.scheduler;

import com.ivandev.gestion_alumnos.model.EstadoInscripcion;
import com.ivandev.gestion_alumnos.repository.InscripcionRepository;
import com.ivandev.gestion_alumnos.repository.PagoRepository;
import com.ivandev.gestion_alumnos.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagoScheduler {

    private final InscripcionRepository inscripcionRepository;
    private final PagoRepository pagoRepository;
    private final EmailService emailService;

    // Se ejecuta el día 5 de cada mes a las 9:00 AM
    @Scheduled(cron = "0 0 9 5 * *")
    public void recordatoriosPago() {
        int mes = LocalDate.now().getMonthValue();
        int anio = LocalDate.now().getYear();

        log.info("Ejecutando recordatorios de pago para {}/{}", mes, anio);

        inscripcionRepository.findByEstado(EstadoInscripcion.ACTIVO)
                .forEach(inscripcion -> {
                    var alumno = inscripcion.getAlumno();

                    if (alumno.getEmail() == null) return;

                    boolean pago = pagoRepository
                            .findByAlumnoIdAndMesAndAnio(alumno.getId(), mes, anio)
                            .isPresent();

                    if (!pago) {
                        emailService.enviarRecordatorioPago(
                                alumno.getEmail(),
                                alumno.getNombre(),
                                mes,
                                anio);
                        log.info("Recordatorio enviado a {}", alumno.getEmail());
                    }
                });
    }
}