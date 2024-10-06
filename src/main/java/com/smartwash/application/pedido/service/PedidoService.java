package com.smartwash.application.pedido.service;

import com.smartwash.application.cliente.model.Cliente;
import com.smartwash.application.cliente.repository.ClienteRepository;
import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.service.FuncionarioService;
import com.smartwash.application.movimentacaoCaixa.service.MovimentacaoCaixaService;
import com.smartwash.application.pagamento.mapper.PagamentoRequestMapper;
import com.smartwash.application.pagamento.model.Pagamento;
import com.smartwash.application.pagamento.request.PagamentoRequest;
import com.smartwash.application.pedido.enums.StatusPedido;
import com.smartwash.application.pedido.mapper.PedidoRequestMapper;
import com.smartwash.application.pedido.mapper.PedidoResponseMapper;
import com.smartwash.application.pedido.model.Pedido;
import com.smartwash.application.pedido.model.PedidoDetalhe;
import com.smartwash.application.pedido.repository.PedidoDetalheRepository;
import com.smartwash.application.pedido.repository.PedidoRepository;
import com.smartwash.application.pedido.request.PedidoRequest;
import com.smartwash.application.pedido.request.PedidoServicoRequest;
import com.smartwash.application.pedido.response.PedidoRelatorioResponse;
import com.smartwash.application.pedido.response.PedidoResponse;
import com.smartwash.application.servico.model.Servico;
import com.smartwash.application.servico.repository.ServicoRepository;
import com.smartwash.application.utils.enums.Resposta;
import com.smartwash.application.utils.relatorio.RelatorioUtils;
import com.smartwash.application.utils.request.DataRequest;
import com.smartwash.application.utils.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioService funcionarioService;
    private final ServicoRepository servicoRepository;
    private final PedidoDetalheRepository pedidoDetalheRepository;
    private final EmailService emailService;
    private final MovimentacaoCaixaService movimentacaoCaixaService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                         FuncionarioService funcionarioService, ServicoRepository servicoRepository,
                         PedidoDetalheRepository pedidoDetalheRepository, EmailService emailService,
                         MovimentacaoCaixaService movimentacaoCaixaService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioService = funcionarioService;
        this.servicoRepository = servicoRepository;
        this.pedidoDetalheRepository = pedidoDetalheRepository;
        this.emailService = emailService;
        this.movimentacaoCaixaService = movimentacaoCaixaService;
    }

    public List<PedidoResponse> buscarListaDePedidos() {
        return PedidoResponseMapper.toPedidoResponseList(this.pedidoRepository.findAllByOrderByDataHoraDesc());
    }

    public ResponseEntity<byte[]> gerarRelatorioDePedido(
            DataRequest dataRequest) {
        Map<String, Object> parametros = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        List<PedidoRelatorioResponse> listaDePedidoParaRelatorio =
                this.buscarListaDePedidoParaRelatorio(dataRequest);
        File file = new File(Objects.requireNonNull(classLoader
                .getResource("relatorios/pedidos.jrxml")).getFile());

        JRBeanCollectionDataSource listaDePedidos = new JRBeanCollectionDataSource(listaDePedidoParaRelatorio);

        parametros.put("pedido", listaDePedidos);

        byte[] bytes = RelatorioUtils.geraRelatorioEmPDF(file.getAbsolutePath(), parametros);
        return RelatorioUtils.retornaResponseEntityRelatorio(bytes);
    }

    @Transactional
    public void cadastrarPedido(PedidoRequest pedidoRequest, HttpServletRequest httpServletRequest) {
        Funcionario funcionario = this.funcionarioService.buscarAuth(httpServletRequest);
        Cliente cliente = this.buscarClientePorId(pedidoRequest.cliente());
        Pedido pedido = PedidoRequestMapper.toPedido(pedidoRequest, new Pedido(), funcionario, cliente);

        this.pedidoRepository.save(pedido);
        this.cadastrarListaDePedidos(pedidoRequest, pedido);
        this.movimentacaoCaixaService.cadastrarPedido(pedido);
    }

    @Transactional
    public void atualizarStatus(UUID id, StatusPedido status) {
        Pedido pedido = this.buscarPedidoPorId(id);
        pedido.setStatus(status);

        this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void adiocionarPagamento(UUID idPedido, PagamentoRequest pagamentoRequest) {
        Pedido pedido = this.buscarPedidoPorId(idPedido);
        Pagamento pagamento = PagamentoRequestMapper.toPagamento(pagamentoRequest, new Pagamento());
        pedido.setPagamento(pagamento);

        this.pedidoRepository.save(pedido);
    }

    public String cacularValorTotalPedido(List<PedidoServicoRequest> listaDeServicos){
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (PedidoServicoRequest pedidoServico: listaDeServicos) {
            Servico servico = this.buscarServicoPorId(pedidoServico.servico());
            valorTotal = valorTotal.add(servico.getPreco().multiply(BigDecimal.valueOf(pedidoServico.quantidade())));
        }

        return valorTotal.toString();
    }

    public void enviarEmail(UUID idPedido){
        Pedido pedido = this.buscarPedidoPorId(idPedido);
        if (pedido.getCliente().getDesejaSerNotificado().equals(Resposta.SIM) &&
                pedido.getStatus().equals(StatusPedido.PRONTO)){
            String mensagem = this.gerarMensagemEmail(pedido);

            this.emailService.enviarEmailTexto(pedido.getCliente().getEmail(), mensagem);
        }
    }

    private Pedido buscarPedidoPorId(UUID id) {
        return this.pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));
    }

    private Servico buscarServicoPorId(UUID id){
        return this.servicoRepository.findById(id).orElseThrow(() -> new NotFoundException("Servico não encontrado!"));
    }

    private Cliente buscarClientePorId(UUID id){
        return this.clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado!"));
    }

    private void cadastrarListaDePedidos(PedidoRequest pedidoRequest, Pedido pedido) {
        pedidoRequest.listaDeServicos().forEach(pedidoServico -> {
            Servico servico = this.buscarServicoPorId(pedidoServico.servico());
            PedidoDetalhe pedidoDetalhe = new PedidoDetalhe(pedido, servico, pedidoServico.quantidade());
            this.pedidoDetalheRepository.save(pedidoDetalhe);
            pedido.getListaDeServicos().add(pedidoDetalhe);
        });
        this.pedidoRepository.save(pedido);
    }

    private String gerarMensagemEmail(Pedido pedido){
        return pedido.getCliente().getNome() +
                ", seu pedido foi concluido e está disponível para retirada. O total do pedido é de R$" +
                pedido.getValorTotal();
    }

    private List<PedidoRelatorioResponse> buscarListaDePedidoParaRelatorio(
            DataRequest dataRequest) {
        LocalDateTime dataInicial = dataRequest.dataInicial().atStartOfDay();
        LocalDateTime dataFinal = dataRequest.dataFinal().atTime(23, 59);
        List<Pedido> listaDePedido = this.pedidoRepository
                .findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(dataInicial, dataFinal);

        return PedidoResponseMapper.toPedidoRelatorioResponseList(listaDePedido);
    }
}