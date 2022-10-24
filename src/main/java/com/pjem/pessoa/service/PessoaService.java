package com.pjem.pessoa.service;

import com.pjem.pessoa.DTO.PessoaDTO;
import com.pjem.pessoa.entity.Pessoa;
import com.pjem.pessoa.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;

    public ResponseEntity<Object> cadastra(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        pessoaRepository.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro efetuado com sucesso!");
    }

    public ResponseEntity<Object> listaUnico(Integer id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro " + id + " inexistente!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(pessoaOptional);
    }

    public ResponseEntity<List<Pessoa>> lista() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(pessoas);

    }

    public ResponseEntity<String> apaga(Integer id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("\"Cadastro \" + id + \" inexistente!\"");
        }
        pessoaRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cadastro "+ id + " apagado com sucesso!");
    }

    public Object altera(Integer id, PessoaDTO pessoaDTO) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro inexistente");
        }

        pessoaOptional.get().setId(id);
        pessoaOptional.get().setNome(pessoaDTO.getNome());
        pessoaOptional.get().setEmail(pessoaDTO.getEmail());
        pessoaOptional.get().setAniversario(pessoaDTO.getAniversario());

        pessoaRepository.save(pessoaOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Atualização do cadastro " + id + " efetuada com sucesso!");
    }
}
