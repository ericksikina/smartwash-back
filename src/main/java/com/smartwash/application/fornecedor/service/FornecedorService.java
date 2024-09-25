package com.smartwash.application.fornecedor.service;

import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.fornecedor.mapper.FornecedorRequestMapper;
import com.smartwash.application.fornecedor.mapper.FornecedorResponseMapper;
import com.smartwash.application.fornecedor.model.Fornecedor;
import com.smartwash.application.fornecedor.repository.FornecedorRepository;
import com.smartwash.application.fornecedor.request.FornecedorRequest;
import com.smartwash.application.fornecedor.response.FornecedorResponse;
import com.smartwash.application.utils.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    @Autowired
    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<FornecedorResponse> buscarListaDeFornecedores(Status stauts) {
        return FornecedorResponseMapper.toFornecedorResponseList(this.fornecedorRepository.findAllByStatusOrderByDescricaoAsc(stauts));
    }

    @Transactional
    public void cadsatrarForncedor(FornecedorRequest fornecedorRequest) {
        Fornecedor fornecedor = FornecedorRequestMapper.toFornecedor(fornecedorRequest, new Fornecedor());

        this.fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public void atalizarForncedor(UUID id, FornecedorRequest fornecedorRequest) {
        Fornecedor fornecedor = this.buscarFornecedorPorId(id);
        FornecedorRequestMapper.toFornecedor(fornecedorRequest, fornecedor);

        this.fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public void atualizarSitucaoFornecedor(UUID id) {
        Fornecedor fornecedor = this.buscarFornecedorPorId(id);
        fornecedor.setStatus(fornecedor.getStatus().equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO);

        this.fornecedorRepository.save(fornecedor);
    }

    public Fornecedor buscarFornecedorPorId(UUID id){
        return this.fornecedorRepository.findById(id).orElseThrow(() -> new NotFoundException("Fornecedor não encontrado!"));
    }
}