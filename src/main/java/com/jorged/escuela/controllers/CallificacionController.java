package com.jorged.escuela.controllers;

import com.jorged.escuela.dto.calificaciones.CalificacionRequest;
import com.jorged.escuela.dto.calificaciones.CalificacionResponse;
import com.jorged.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CallificacionController extends CommonController<CalificacionRequest, CalificacionResponse,
        CalificacionService>{
    public CallificacionController(CalificacionService service) {
        super(service);
    }
}
