package br.edu.ifpb.pweb2.bitbank.service;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CorrentistaService implements Service<Correntista, Integer> {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public List<Correntista> findAll() {
        return correntistaRepository.findAll();
    }

    @Override
    public Correntista findById(Integer id) {
        Correntista correntista = null;
        Optional<Correntista> opCorrentista = correntistaRepository.findById(id);
        if (opCorrentista.isPresent()) {
            correntista = opCorrentista.get();
        }
        return correntista;
    }

    @Override
    public Correntista save(Correntista c) {
        return correntistaRepository.save(c);
    }

}


