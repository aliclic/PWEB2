package br.edu.ifpb.pweb2.bitbank.repository;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

    @Query("SELECT c FROM Conta c JOIN FETCH c.transacoes WHERE c.numero = :numero")
    Conta findByNumeroWithTransacoes(@Param("numero") String numero);

    @Query("SELECT c FROM Conta c JOIN FETCH c.transacoes WHERE c.id = :id")
    Conta findByIdWithTransacoes(@Param("id") Integer id);
}
