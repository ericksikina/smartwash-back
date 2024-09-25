package com.smartwash.application.cliente.service;

import com.smartwash.application.cliente.mapper.ClienteRequestMapper;
import com.smartwash.application.cliente.mapper.ClienteResponseMapper;
import com.smartwash.application.cliente.model.Cliente;
import com.smartwash.application.cliente.repository.ClienteRepository;
import com.smartwash.application.cliente.request.ClienteRequest;
import com.smartwash.application.cliente.response.ClienteResponse;
import com.smartwash.application.endereco.mapper.EnderecoRequestMapper;
import com.smartwash.application.endereco.model.Endereco;
import com.smartwash.application.exceptions.customs.BadRequestException;
import com.smartwash.application.exceptions.customs.NotFoundException;
import com.smartwash.application.utils.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponse> buscarListaDeClientes(Status status) {
        return ClienteResponseMapper.toClienteResponseList(this.clienteRepository.findAllByStatusOrderByNome(status));
    }

    @Transactional
    public void cadastrarCliente(ClienteRequest clienteRequest) {
        this.validarCpfEEmailJaCadastrados(clienteRequest.cpf(), clienteRequest.email());
        Endereco endereco = EnderecoRequestMapper.toEndereco(clienteRequest.endereco(), new Endereco());
        Cliente cliente = ClienteRequestMapper.ClienteRequestToCliente(clienteRequest, new Cliente(), endereco);

        this.clienteRepository.save(cliente);
    }

    @Transactional
    public void atualizarCliente(UUID id, ClienteRequest clienteRequest) {
        Cliente cliente = this.buscarClientePorId(id);

        if(!clienteRequest.cpf().equals(cliente.getCpf()) ||
                !clienteRequest.email().equals(cliente.getEmail())) {
            this.validarCpfEEmailJaCadastrados(clienteRequest.cpf(), clienteRequest.email());
        }

        Endereco endereco = EnderecoRequestMapper.toEndereco(clienteRequest.endereco(), cliente.getEndereco());
        ClienteRequestMapper.ClienteRequestToCliente(clienteRequest, cliente, endereco);

        this.clienteRepository.save(cliente);
    }

    @Transactional
    public void atualizarSituacaoCliente(UUID id) {
        Cliente cliente = this.buscarClientePorId(id);
        cliente.setStatus(cliente.getStatus().equals(Status.ATIVO) ? Status.INATIVO : Status.ATIVO);

        this.clienteRepository.save(cliente);
    }

    private Cliente buscarClientePorId(UUID id) {
        return this.clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado!"));
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
        Optional<Cliente> cliente = this.clienteRepository.findByCpf(cpf);
        return cliente.isPresent();
    }

    private boolean emailJaCadastrado(String email) {
       Optional<Cliente> cliente = this.clienteRepository.findByEmail(email);
       return cliente.isPresent();
    }
}