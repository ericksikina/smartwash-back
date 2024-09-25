package com.smartwash.application.funcionario.service;

import com.smartwash.application.auth.infra.security.TokenService;
import com.smartwash.application.auth.mapper.AuthRequestMapper;
import com.smartwash.application.auth.model.Auth;
import com.smartwash.application.auth.repositories.AuthRepository;
import com.smartwash.application.endereco.mapper.EnderecoRequestMapper;
import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.exceptions.customs.BadRequestException;
import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.funcionario.mapper.FuncionarioAtualizacaoRequestMapper;
import com.smartwash.application.funcionario.mapper.FuncionarioRequestMapper;
import com.smartwash.application.funcionario.mapper.FuncionarioResponseMapper;
import com.smartwash.application.funcionario.mapper.NovoSalarioRequestMapper;
import com.smartwash.application.funcionario.model.Funcionario;
import com.smartwash.application.funcionario.repository.FuncionarioRepository;
import com.smartwash.application.funcionario.request.AtualizarFuncionarioRequest;
import com.smartwash.application.funcionario.request.CadastrarFuncionarioRequest;
import com.smartwash.application.funcionario.request.NovoSalarioRequest;
import com.smartwash.application.funcionario.response.FuncionarioRelatorioResponse;
import com.smartwash.application.funcionario.response.FuncionarioResponse;
import com.smartwash.application.utils.enums.Status;
import com.smartwash.application.utils.relatorio.RelatorioUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final TokenService tokenService;
    private final AuthRepository authRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, TokenService tokenService,
                              AuthRepository authRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.tokenService = tokenService;
        this.authRepository = authRepository;
    }

    public List<FuncionarioResponse> buscarListaDeFuncionarios(Status status) {
        return FuncionarioResponseMapper.toFuncionarioResponseList(this.funcionarioRepository.findAllByStatusOrderByNomeAsc(status));
    }

    @Transactional
    public void cadastraFuncionario(CadastrarFuncionarioRequest cadastrarFuncionarioRequest) {
        this.validarCpfEEmailJaCadastrados(cadastrarFuncionarioRequest.cpf(), cadastrarFuncionarioRequest.auth().login());
        Endereco endereco = EnderecoRequestMapper.toEndereco(cadastrarFuncionarioRequest.endereco(), new Endereco());
        Auth auth = AuthRequestMapper.toAuth(cadastrarFuncionarioRequest.auth(), new Auth());
        Funcionario funcionario = FuncionarioRequestMapper
                .toFuncionario(cadastrarFuncionarioRequest, new Funcionario(), endereco, auth);

        this.funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void atualizarFuncionario(UUID id, AtualizarFuncionarioRequest atualizarFuncionarioRequest) {
        Funcionario funcionario = this.buscarFuncionarioPorId(id);

        if (!atualizarFuncionarioRequest.cpf().equals(funcionario.getCpf()) && this.cpfJaCadastrado(atualizarFuncionarioRequest.cpf())){
            throw new BadRequestException("O cpf " + atualizarFuncionarioRequest.cpf() + " já está cadastrado no sistema!");
        }

        Endereco endereco = EnderecoRequestMapper.toEndereco(atualizarFuncionarioRequest.endereco(), new Endereco());
        FuncionarioAtualizacaoRequestMapper.toFuncionario(atualizarFuncionarioRequest, funcionario, endereco);

        this.funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void atualizarSituacaoFuncionario(UUID id) {
        Funcionario funcionario = this.buscarFuncionarioPorId(id);
        funcionario.setStatus(funcionario.getStatus().equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO);

        this.funcionarioRepository.save(funcionario);
    }

    @Transactional
    public void concederAumentoDeSalario(UUID id, NovoSalarioRequest novoSalarioRequest) {
        Funcionario funcionario = this.buscarFuncionarioPorId(id);
        NovoSalarioRequestMapper.toFuncionario(novoSalarioRequest, funcionario);

        this.funcionarioRepository.save(funcionario);
    }

    public ResponseEntity<byte[]> gerarRelatorioDeFuncionario(){
        Map<String, Object> parametros = new HashMap<>();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("relatorios/funcionarios.jrxml")).getFile());

        List<FuncionarioRelatorioResponse> listaDeFuncionariosParaRelatorio = this.buscarListaDeFuncionariosParaRelatorio();
        JRBeanCollectionDataSource listaDeFuncionarios = new JRBeanCollectionDataSource(listaDeFuncionariosParaRelatorio);

        parametros.put("funcionarios", listaDeFuncionarios);

        byte[] bytes = RelatorioUtils.geraRelatorioEmPDF(file.getAbsolutePath(), parametros);

        return RelatorioUtils.retornaResponseEntityRelatorio(bytes);
    }

    public Funcionario buscarFuncionarioPorId(UUID id) {
        return this.funcionarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Funcionário não encontrado"));
    }

    public Funcionario buscarAuth(HttpServletRequest request) {
        String token = this.recuperarToken(request);
        String login = this.tokenService.getLoginFromToken(token);
        Auth auth = this.authRepository.findByLogin(login);

        return this.funcionarioRepository.findByAuth(auth);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null)
            return null;

        return authHeader.replace("Bearer ", "");
    }

    private void validarCpfEEmailJaCadastrados(String cpf, String email){
        if (this.cpfJaCadastrado(cpf)){
            throw new BadRequestException("O cpf " + cpf + " já está cadastrado no sistema!");
        }
        if (this.emailJaCadastrado(email)){
            throw new BadRequestException("O e-mail " + email + " já está cadastrado no sistema!");
        }
    }

    private boolean cpfJaCadastrado(String cpf) {
        Optional<Funcionario> funcionario = this.funcionarioRepository.findByCpf(cpf);
        return funcionario.isPresent();
    }

    private boolean emailJaCadastrado(String email) {
        Optional<Funcionario> funcionario = this.funcionarioRepository.findByAuth_Login(email);
        return funcionario.isPresent();
    }

    private List<FuncionarioRelatorioResponse> buscarListaDeFuncionariosParaRelatorio() {
        List<Funcionario> listaDeFuncioarios = this.funcionarioRepository.findAllByStatusOrderByNomeAsc(Status.ATIVO);
        return FuncionarioResponseMapper.toFuncionarioRelatorioResponseList(listaDeFuncioarios);
    }
}