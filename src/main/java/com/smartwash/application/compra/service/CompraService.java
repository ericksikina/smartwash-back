package com.smartwash.application.compra.service;
import com.smartwash.application.compra.mapper.CompraRequestMapper;
import com.smartwash.application.compra.model.Compra;
import com.smartwash.application.compra.model.CompraDetalhe;
import com.smartwash.application.compra.repository.CompraDetalheRepository;
import com.smartwash.application.compra.repository.CompraRepository;
import com.smartwash.application.compra.request.CompraProdutoRequest;
import com.smartwash.application.compra.request.CompraRequest;
import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.fornecedor.model.Fornecedor;
import com.smartwash.application.fornecedor.repository.FornecedorRepository;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.service.FuncionarioService;
import com.smartwash.application.movimentacaoCaixa.service.MovimentacaoCaixaService;
import com.smartwash.application.movimentacaoEstoque.service.MovimentacaoEstoqueService;
import com.smartwash.application.produto.model.Produto;
import com.smartwash.application.produto.repository.ProdutoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CompraService {
    private final CompraRepository compraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final FuncionarioService funcionarioService;
    private final ProdutoRepository produtoRepository;
    private final CompraDetalheRepository compraDetalheRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    private final MovimentacaoCaixaService movimentacaoCaixaService;

    @Autowired
    public CompraService(CompraRepository compraRepository, FornecedorRepository fornecedorRepository,
                         FuncionarioService funcionarioService, ProdutoRepository produtoRepository,
                         CompraDetalheRepository compraDetalheRepository,
                         MovimentacaoEstoqueService movimentacaoEstoqueService,
                         MovimentacaoCaixaService movimentacaoCaixaService) {
        this.compraRepository = compraRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.funcionarioService = funcionarioService;
        this.produtoRepository = produtoRepository;
        this.compraDetalheRepository = compraDetalheRepository;
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
        this.movimentacaoCaixaService = movimentacaoCaixaService;
    }

    public List<Compra> buscarListaDeCompra() {
        return this.compraRepository.findAllByOrderByDataHoraDesc();
    }

    @Transactional
    public void cadastrarCompra(CompraRequest compraRequest, HttpServletRequest httpServletRequest) {
        Funcionario funcionario = this.funcionarioService.buscarAuth(httpServletRequest);
        Fornecedor fornecedor = this.buscarFornecedorPorId(compraRequest.fornecedor());
        Compra compra = CompraRequestMapper.toCompra(compraRequest, new Compra(), fornecedor, funcionario);
        this.compraRepository.save(compra);

        this.cadastrarListaDeProdutos(compraRequest, compra);
        this.movimentacaoEstoqueService.cadastrarCompra(compra);
        this.movimentacaoCaixaService.cadastrarCompra(compra);
    }

    public Fornecedor buscarFornecedorPorId(UUID id) {
        return this.fornecedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fornecedor não encontrado!"));
    }

    private Produto buscarProdutoPorId(UUID idProduto){
        return this.produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
    }

    @Transactional
    public void cadastrarListaDeProdutos(CompraRequest compraRequest,
                                          Compra compra){
        compraRequest.listaDeProdutos().forEach(compraProduto -> {
            Produto produto = this.buscarProdutoPorId(compraProduto.produto());
            CompraDetalhe compraDetalhe = new CompraDetalhe(compra, produto, new BigDecimal(compraProduto.precoUnitario()), compraProduto.quantidade());
            this.compraDetalheRepository.save(compraDetalhe);
            compra.getListaDeCompra().add(compraDetalhe);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + compraProduto.quantidade().intValue());
            this.produtoRepository.save(produto);
        });
        this.compraRepository.save(compra);
    }

    public String cacularValorTotalCompra(List<CompraProdutoRequest> listaDeCompraProduto){
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (CompraProdutoRequest compraProduto: listaDeCompraProduto) {
            valorTotal = valorTotal.add(new BigDecimal(compraProduto.precoUnitario())
                    .multiply(BigDecimal.valueOf(compraProduto.quantidade())));
        }

        return valorTotal.toString();
    }
}