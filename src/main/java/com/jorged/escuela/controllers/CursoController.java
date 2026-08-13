package com.jorged.escuela.controllers;

import com.jorged.escuela.dto.aulas.AulaRequest;
import com.jorged.escuela.dto.aulas.AulaResponse;
import com.jorged.escuela.dto.cursos.CursoRequest;
import com.jorged.escuela.dto.cursos.CursoResponse;
import com.jorged.escuela.services.aulas.AulaService;
import com.jorged.escuela.services.cursos.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends CommonController<CursoRequest, CursoResponse, CursoService>{
    public CursoController(CursoService service) {
        super(service);
    }
}
