package com.jorged.escuela.controllers;

import com.jorged.escuela.dto.grupos.GrupoRequest;
import com.jorged.escuela.dto.grupos.GrupoResponse;
import com.jorged.escuela.services.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController <GrupoRequest, GrupoResponse, GrupoService>{
    public GrupoController(GrupoService service) {
        super(service);
    }
}
