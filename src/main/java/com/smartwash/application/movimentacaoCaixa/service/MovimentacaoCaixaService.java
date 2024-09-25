package com.smartwash.application.movimentacaoCaixa.service;

import com.smartwash.application.compra.model.Compra;
import com.smartwash.application.movimentacaoCaixa.mapper.MovimentacaoCaixaResponseMapper;
import com.smartwash.application.movimentacaoCaixa.model.MovimentacaoCaixa;
import com.smartwash.application.movimentacaoCaixa.repository.MovimentacaoCaixaRepository;
import com.smartwash.application.movimentacaoCaixa.response.MovimentacaoCaixaRelatorioResponse;
import com.smartwash.application.movimentacaoEstoque.enums.TipoMovimentacao;
import com.smartwash.application.pedido.model.Pedido;
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
public class MovimentacaoCaixaService {
    private final MovimentacaoCaixaRepository movimentacaoCaixaRepository;

    @Autowired
    public MovimentacaoCaixaService(MovimentacaoCaixaRepository movimentacaoCaixaRepository){
        this.movimentacaoCaixaRepository = movimentacaoCaixaRepository;
    }

    public ResponseEntity<byte[]> gerarRelatorioDeMovimentacaoCaixa(
            DataRequest dataRequest) {
        Map<String, Object> parametros = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        List<MovimentacaoCaixaRelatorioResponse> listaDeMovimentacaoCaixaParaRelatorio =
                this.buscarListaDeMovimentacaoCaixaParaRelatorio(dataRequest);
        File file = new File(Objects.requireNonNull(classLoader
                .getResource("relatorios/movimentacao-caixa.jrxml")).getFile());

        JRBeanCollectionDataSource listaDeMovimentacaoCaixa = new JRBeanCollectionDataSource(listaDeMovimentacaoCaixaParaRelatorio);

        parametros.put("movimentacao-caixa", listaDeMovimentacaoCaixa);

        byte[] bytes = RelatorioUtils.geraRelatorioEmPDF(file.getAbsolutePath(), parametros);
        return RelatorioUtils.retornaResponseEntityRelatorio(bytes);
    }

    @Transactional
    public void cadastrarCompra(Compra compra) {
        MovimentacaoCaixa movimentacaoCaixa = new MovimentacaoCaixa();
        movimentacaoCaixa.setCompra(compra);
        movimentacaoCaixa.setDataHora(compra.getDataHora());
        movimentacaoCaixa.setTipoMovimentacao(TipoMovimentacao.SAIDA);

        this.movimentacaoCaixaRepository.save(movimentacaoCaixa);
    }

    @Transactional
    public void cadastrarPedido(Pedido pedido) {
        MovimentacaoCaixa movimentacaoCaixa = new MovimentacaoCaixa();
        movimentacaoCaixa.setPedido(pedido);
        movimentacaoCaixa.setDataHora(pedido.getDataHora());
        movimentacaoCaixa.setTipoMovimentacao(TipoMovimentacao.ENTRADA);

        this.movimentacaoCaixaRepository.save(movimentacaoCaixa);
    }

    private List<MovimentacaoCaixaRelatorioResponse> buscarListaDeMovimentacaoCaixaParaRelatorio(
            DataRequest dataRequest) {
        LocalDateTime dataInicial = dataRequest.dataInicial().atStartOfDay();
        LocalDateTime dataFinal = dataRequest.dataFinal().atTime(23, 59);
        List<MovimentacaoCaixa> listaDeMovimentacaoCaixa = this.movimentacaoCaixaRepository
                .findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(dataInicial, dataFinal);

        return MovimentacaoCaixaResponseMapper.toMovimentacaoCaixaRelatorioResponseList(listaDeMovimentacaoCaixa);
    }
}