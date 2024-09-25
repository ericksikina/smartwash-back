package com.smartwash.application.baixaEstoque.service;

import com.smartwash.application.baixaEstoque.mapper.BaixaEstoqueRequestMapper;
import com.smartwash.application.baixaEstoque.model.BaixaEstoque;
import com.smartwash.application.baixaEstoque.model.BaixaEstoqueDetalhe;
import com.smartwash.application.baixaEstoque.repository.BaixaEstoqueDetalheRepository;
import com.smartwash.application.baixaEstoque.repository.BaixaEstoqueRepository;
import com.smartwash.application.baixaEstoque.request.BaixaEstoqueRequest;
import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.service.FuncionarioService;
import com.smartwash.application.movimentacaoEstoque.service.MovimentacaoEstoqueService;
import com.smartwash.application.produto.model.Produto;
import com.smartwash.application.produto.repository.ProdutoRepository;
import com.smartwash.application.utils.request.DataRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BaixaEstoqueService {
    private final BaixaEstoqueRepository baixaEstoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final FuncionarioService funcionarioService;
    private final BaixaEstoqueDetalheRepository baixaEstoqueDetalheRepository;
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Autowired
    public BaixaEstoqueService(BaixaEstoqueRepository baixaEstoqueRepository, ProdutoRepository produtoRepository,
                               FuncionarioService funcionarioService,
                               BaixaEstoqueDetalheRepository baixaEstoqueDetalheRepository,
                               MovimentacaoEstoqueService movimentacaoEstoqueService){
        this.baixaEstoqueRepository = baixaEstoqueRepository;
        this.produtoRepository = produtoRepository;
        this.funcionarioService = funcionarioService;
        this.baixaEstoqueDetalheRepository = baixaEstoqueDetalheRepository;
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    public List<BaixaEstoque> buscarListaDeBaixaEstoque() {
        return this.baixaEstoqueRepository.findAllByOrderByDataHoraDesc();
    }

    public List<BaixaEstoque> buscarListaDeBaixaEstoqueFiltradoPorData(DataRequest dataRequest) {
        LocalDateTime dataInicial = dataRequest.dataInicial().atStartOfDay();
        LocalDateTime dataFinal = dataRequest.dataFinal().atTime(23, 59);
        return this.baixaEstoqueRepository.findAllByDataHoraGreaterThanEqualAndDataHoraLessThanEqualOrderByDataHoraDesc(dataInicial, dataFinal);
    }

    @Transactional
    public void cadastrarBaixaEstoque(BaixaEstoqueRequest baixaEstoqueRequest, HttpServletRequest httpServletRequest){
        Funcionario funcionario = this.funcionarioService.buscarAuth(httpServletRequest);
        BaixaEstoque baixaEstoque = BaixaEstoqueRequestMapper.toBaixaEstoque(new BaixaEstoque(), funcionario);

        baixaEstoque = this.baixaEstoqueRepository.save(baixaEstoque);

        this.cadastrarListaDeProdutos(baixaEstoqueRequest, baixaEstoque);
        this.movimentacaoEstoqueService.cadastrarBaixaEstoque(baixaEstoque);
    }

    private Produto buscarProdutoPorId(UUID idProduto){
        return this.produtoRepository.findById(idProduto)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
    }

    @Transactional
    public void cadastrarListaDeProdutos(BaixaEstoqueRequest baixaEstoqueRequest, BaixaEstoque baixaEstoque){
        baixaEstoqueRequest.listaDeProdutos().forEach(baixaEstoqueProduto -> {
            Produto produto = this.buscarProdutoPorId(baixaEstoqueProduto.produto());
            BaixaEstoqueDetalhe baixaEstoqueDetalhe = new BaixaEstoqueDetalhe(baixaEstoque, produto,
                    baixaEstoqueProduto.quantidade());
            this.baixaEstoqueDetalheRepository.save(baixaEstoqueDetalhe);
            baixaEstoque.getListaDeBaixaEstoque().add(baixaEstoqueDetalhe);

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - baixaEstoqueProduto.quantidade().intValue());
            this.produtoRepository.save(produto);
        });

        this.baixaEstoqueRepository.save(baixaEstoque);
    }

    public List<BaixaEstoque> buscarListaDeBaixaEstoqueFiltradoPorFuncionario(String nome) {
        return this.baixaEstoqueRepository.findAllByFuncionario_NomeContainingIgnoreCase(nome);
    }
}