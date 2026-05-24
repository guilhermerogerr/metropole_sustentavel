package metropolesustentavel.controller;

import metropolesustentavel.model.*;
import metropolesustentavel.model.eventos.*;

import java.util.*;

public class GeradorEventos {
    private final Random random = new Random();
    private final List<Evento> pool = new ArrayList<>();

    public GeradorEventos() {
        pool.add(new CriseHidrica());
        pool.add(new GreveTransporte());
        pool.add(new AumentoPopulacional());
        pool.add(new OndaDeCalor());
    }

    public Evento sortearEvento(Cidade cidade) {
        List<Evento> candidatos = new ArrayList<>();
        for (Evento e : pool)
            if (random.nextDouble() < e.getProbabilidade()) candidatos.add(e);
        if (candidatos.isEmpty()) return null;
        return candidatos.get(random.nextInt(candidatos.size()));
    }
}
