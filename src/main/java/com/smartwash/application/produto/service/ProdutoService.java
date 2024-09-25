package com.smartwash.application.produto.service;

import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.produto.mapper.ProdutoRequestMapper;
import com.smartwash.application.produto.mapper.ProdutoResponseMapper;
import com.smartwash.application.produto.model.Produto;
import com.smartwash.application.produto.repository.ProdutoRepository;
import com.smartwash.application.produto.request.AtualizarProdutoRequest;
import com.smartwash.application.produto.request.CadastrarProdutoRequest;
import com.smartwash.application.produto.response.ProdutoRelatorioResponse;
import com.smartwash.application.produto.response.ProdutoResponse;
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
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponse> buscarListaDeProdutos(Status status) {
        return ProdutoResponseMapper.toProdutoResponseList(this.produtoRepository.findAllByStatusOrderByDescricaoAsc(status));
    }

    public ResponseEntity<byte[]> gerarRelatorioDeProduto(boolean estoqueBaixo){
        Map<String, Object> parametros = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        List<ProdutoRelatorioResponse> listaDeProdutosParaRelatorio;
        File file = new File(Objects.requireNonNull(classLoader.getResource("relatorios/produtos.jrxml")).getFile());
        if(estoqueBaixo){
            listaDeProdutosParaRelatorio = this.buscarListaDeProdutosComEstoqueBaixoParaRelatorio();
        } else {
            listaDeProdutosParaRelatorio = this.buscarListaDeProdutosParaRelatorio();
        }

        JRBeanCollectionDataSource listaDeProdutos = new JRBeanCollectionDataSource(listaDeProdutosParaRelatorio);

        parametros.put("produtos", listaDeProdutos);
        parametros.put("estoqueBaixo", estoqueBaixo);

        byte[] bytes = RelatorioUtils.geraRelatorioEmPDF(file.getAbsolutePath(), parametros);

        return RelatorioUtils.retornaResponseEntityRelatorio(bytes);
    }

    @Transactional
    public void cadastrarProduto(CadastrarProdutoRequest cadastrarProdutoRequest) {
        Produto produto = ProdutoRequestMapper.cadastrarProdutoRequestToProduto(cadastrarProdutoRequest, new Produto());

        this.produtoRepository.save(produto);
    }

    @Transactional
    public void atualizarProduto(UUID id, AtualizarProdutoRequest atualizarProdutoRequest) {
        Produto produto = this.buscarProdutoPorId(id);
        ProdutoRequestMapper.atualizarProdutoRequestToProduto(atualizarProdutoRequest, produto);

        this.produtoRepository.save(produto);
    }

    @Transactional
    public void atualizarSituacaoProduto(UUID id) {
        Produto produto = this.buscarProdutoPorId(id);
        produto.setStatus(produto.getStatus().equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO);

        this.produtoRepository.save(produto);
    }

    public Produto buscarProdutoPorId(UUID id) {
        return this.produtoRepository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
    }

    private List<ProdutoRelatorioResponse> buscarListaDeProdutosParaRelatorio() {
        List<Produto> listaDeProdutos = this.produtoRepository.findAllByStatusOrderByDescricaoAsc(Status.ATIVO);
        return ProdutoResponseMapper.toProdutoRelatorioResponseList(listaDeProdutos);
    }

    private List<ProdutoRelatorioResponse> buscarListaDeProdutosComEstoqueBaixoParaRelatorio() {
        List<Produto> listaDeProdutos = this.produtoRepository.findByStatusAndQuantidadeEstoqueLessThanEstoqueMinimo(Status.ATIVO);
        return ProdutoResponseMapper.toProdutoRelatorioResponseList(listaDeProdutos);
    }
}