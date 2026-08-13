package com.jorged.escuela.controllers;

import com.jorged.escuela.dto.inscripciones.InscripcionRequest;
import com.jorged.escuela.dto.inscripciones.InscripcionResponse;
import com.jorged.escuela.entities.Inscripcion;
import com.jorged.escuela.services.inscripciones.InscripcionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService>{
    public InscripcionController(InscripcionService service) {
        super(service);
    }
}
