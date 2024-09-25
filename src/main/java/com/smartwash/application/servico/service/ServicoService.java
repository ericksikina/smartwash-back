package com.smartwash.application.servico.service;

import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.servico.mapper.ServicoRequestMapper;
import com.smartwash.application.servico.mapper.ServicoResponseMapper;
import com.smartwash.application.servico.model.Servico;
import com.smartwash.application.servico.repository.ServicoRepository;
import com.smartwash.application.servico.request.ServicoRequest;
import com.smartwash.application.servico.response.ServicoRelatorioResponse;
import com.smartwash.application.servico.response.ServicoResponse;
import com.smartwash.application.utils.enums.Status;
import com.smartwash.application.utils.relatorio.RelatorioUtils;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;

    @Autowired
    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<ServicoResponse> buscarListaDeServicos(Status status) {
        return ServicoResponseMapper.toServicoResponseList(this.servicoRepository.findAllByStatusOrderByDescricaoAsc(status));
    }

    public ResponseEntity<byte[]> gerarRelatorioDeServico(){
        Map<String, Object> parametros = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        List<ServicoRelatorioResponse> listaDeServicosParaRelatorio = this.buscarListaDeProdutosParaRelatorio();
        File file = new File(Objects.requireNonNull(classLoader.getResource("relatorios/servicos.jrxml")).getFile());

        JRBeanCollectionDataSource listaDeProdutos = new JRBeanCollectionDataSource(listaDeServicosParaRelatorio);
        parametros.put("servicos", listaDeProdutos);
        byte[] bytes = RelatorioUtils.geraRelatorioEmPDF(file.getAbsolutePath(), parametros);

        return RelatorioUtils.retornaResponseEntityRelatorio(bytes);
    }

    @Transactional
    public void cadastrarServico(ServicoRequest servicoRequest) {
        Servico servico = ServicoRequestMapper.toServico(servicoRequest, new Servico());

        this.servicoRepository.save(servico);
    }

    @Transactional
    public void atualizarServico(UUID id, ServicoRequest servicoRequest) {
        Servico servico = this.buscarServicoPorId(id);
        ServicoRequestMapper.toServico(servicoRequest, servico);

        this.servicoRepository.save(servico);
    }

    @Transactional
    public void atualizarSituacaoServico(UUID id) {
        Servico servico = this.buscarServicoPorId(id);
        servico.setStatus(servico.getStatus().equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO);

        this.servicoRepository.save(servico);
    }

    public Servico buscarServicoPorId(UUID id){
        return this.servicoRepository.findById(id).orElseThrow(() -> new NotFoundException("Servico não encontrado!"));
    }

    private List<ServicoRelatorioResponse> buscarListaDeProdutosParaRelatorio() {
        List<Servico> listaDeServicos = this.servicoRepository.findAllByStatusOrderByDescricaoAsc(Status.ATIVO);
        return ServicoResponseMapper.toServicoRelatorioResponseList(listaDeServicos);
    }
}