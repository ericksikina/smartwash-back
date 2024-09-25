package com.smartwash.application.endereco.service;

import com.smartwash.application.endereco.mapper.EnderecoViaCepResponseMapper;
import com.smartwash.application.endereco.repository.EnderecoRepository;
import com.smartwash.application.endereco.request.EnderecoViaCepRequest;
import com.smartwash.application.endereco.response.EnderecoViaCepResponse;
import com.smartwash.application.exceptions.customs.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public void excluirEndereco(UUID id){
        this.enderecoRepository.deleteById(id);
    }

    public EnderecoViaCepResponse buscarDadosDeEnderecoPeloCep(String cep){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json";

        EnderecoViaCepRequest endereco = restTemplate.getForObject(url, EnderecoViaCepRequest.class);
        if(Objects.isNull(endereco)){
            throw new NotFoundException("Nenhum endereco encontrado com o CEP: " + cep);
        }

        return EnderecoViaCepResponseMapper.toEnderecoViaCepResponse(endereco);
    }
}
