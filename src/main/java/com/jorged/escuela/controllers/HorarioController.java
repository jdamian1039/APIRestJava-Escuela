package com.jorged.escuela.controllers;

import com.jorged.escuela.dto.horarios.HorarioRequest;
import com.jorged.escuela.dto.horarios.HorarioResponse;
import com.jorged.escuela.services.horario.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController extends CommonController<HorarioRequest, HorarioResponse, HorarioService>{
    public HorarioController(HorarioService service) {
        super(service);
    }
}
