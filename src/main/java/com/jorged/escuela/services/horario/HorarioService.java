package com.jorged.escuela.services.horario;

import com.jorged.escuela.dto.horarios.HorarioRequest;
import com.jorged.escuela.dto.horarios.HorarioResponse;
import com.jorged.escuela.entities.Horario;
import com.jorged.escuela.services.CrudService;

public interface HorarioService extends CrudService<HorarioRequest, HorarioResponse> {
}
