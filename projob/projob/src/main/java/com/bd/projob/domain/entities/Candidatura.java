package com.bd.projob.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Candidatura {
    private int idCandidatura;
    private int codProj;
    private int codCandidato;
}


