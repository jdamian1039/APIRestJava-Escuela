package com.jorged.escuela.controllers;

import com.jorged.escuela.dto.aulas.AulaRequest;
import com.jorged.escuela.dto.aulas.AulaResponse;
import com.jorged.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {
    public AulaController(AulaService service) {
        super(service);
    }
}
