package com.smartwash.application.movimentacaoEstoque.service;

import com.smartwash.application.baixaEstoque.model.BaixaEstoque;
import com.smartwash.application.compra.model.Compra;
import com.smartwash.application.movimentacaoEstoque.enums.TipoMovimentacao;
import com.smartwash.application.movimentacaoEstoque.mapper.MovimentacaoEstoqueResponseMapper;
import com.smartwash.application.movimentacaoEstoque.model.MovimentacaoEstoque;
import com.smartwash.application.movimentacaoEstoque.repository.MovimentacaoEstoqueRepository;
import com.smartwash.application.movimentacaoEstoque.response.MovimentacaoEstoqueRelatorioResponse;
import com.smartwash.application.utils.relatorio.RelatorioUtils;
import com.smartwash.application.utils.request.DataRequest;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MovimentacaoEstoqueService {
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    @Autowired
    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoEstoqueRepository) {
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    public ResponseEntity<byte[]> gerarRelatorioDeMovimentacaoEstoque(
            DataRequest dataRequest) {
        Map<String, Object> parametros = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        List<MovimentacaoEstoqueRelatorioResponse> listaDeMovimentacaoEstoqueParaRelatorio =
                this.buscarListaDeMovimentacaoEstoqueParaRelatorio(dataRequest);
        File file = new File(Objects.requireNonNull(classLoader
                .getResource("relatorios/movimentacao-estoque.jrxml")).getFile());

        JRBeanCollectionDataSource listaDeMovimentacaoEstoque = new JRBeanCollectionDataSource(listaDeMovimentacaoEstoqueParaRelatorio);

        parametros.put("movimentacao-estoque", listaDeMovimentacaoEstoque);

        byte[] bytes = RelatorioUtils.geraRelatorioEmPDF(file.getAbsolutePath(), parametros);
        return RelatorioUtils.retornaResponseEntityRelatorio(bytes);
    }

    @Transactional
    public void cadastrarBaixaEstoque(BaixaEstoque baixaEstoque) {
        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setBaixaEstoque(baixaEstoque);
        movimentacaoEstoque.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacaoEstoque.setDataHora(baixaEstoque.getDataHora());

        this.movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

    @Transactional
    public void cadastrarCompra(Compra compra) {
        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setCompra(compra);
        movimentacaoEstoque.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        movimentacaoEstoque.setDataHora(compra.getDataHora());

        this.movimentacaoEstoqueRepository.save(movimentacaoEstoque);
    }

    private List<MovimentacaoEstoqueRelatorioResponse> buscarListaDeMovimentacaoEstoqueParaRelatorio(
            DataRequest dataRequest) {
        LocalDateTime dataInicial = dataRequest.dataInicial().atStartOfDay();
        LocalDateTime dataFinal = dataRequest.dataFinal().atTime(23, 59);
        List<MovimentacaoEstoque> listaDeMovimentacaoEstoque = this.movimentacaoEstoqueRepository
                .findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(dataInicial, dataFinal);
        return MovimentacaoEstoqueResponseMapper.toMovimentacaoEstoqueRelatorioResponseList(listaDeMovimentacaoEstoque);
    }
}