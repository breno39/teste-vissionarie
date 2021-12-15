package com.vissionarie.codetest.service;

import com.vissionarie.codetest.domain.Cliente;
import com.vissionarie.codetest.repository.ClienteRepository;
import com.vissionarie.codetest.web.rest.errors.BadRequestAlertException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private static final String ENTITY_NAME = "cliente";

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente save(Cliente cliente) {
        log.info("saving cliente {}", cliente.hashCode());
        checkIfClienteIdIsNull(cliente);
        checkIfClienteCpfExists(cliente);
        Cliente result = clienteRepository.save(cliente);
        return result;
    }

    public Cliente update(Cliente cliente, String id) {
        log.info("updating cliente {} with {}", id, cliente.hashCode());

        checkIfClienteIdIsNull(cliente);

        if (!Objects.equals(id, cliente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cliente result = clienteRepository.save(cliente);

        return result;
    }

    public Optional<Cliente> patch(Cliente cliente, String id) {
        log.info("patching cliente {} with {}", id, cliente.hashCode());
        if (cliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cliente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cliente> result = clienteRepository
            .findById(cliente.getId())
            .map(existingCliente -> {
                if (cliente.getCpf() != null) {
                    existingCliente.setCpf(cliente.getCpf());
                }
                if (cliente.getNome() != null) {
                    existingCliente.setNome(cliente.getNome());
                }
                if (cliente.getCidade() != null) {
                    existingCliente.setCidade(cliente.getCidade());
                }
                if (cliente.getEstado() != null) {
                    existingCliente.setEstado(cliente.getEstado());
                }

                return existingCliente;
            })
            .map(clienteRepository::save);

        return result;
    }

    public List<Cliente> findAll() {
        log.info("finding all clientes");
        var result = clienteRepository.findAll();
        return result;
    }

    public Optional<Cliente> findById(String id) {
        log.info("finding cliente {} ", id);
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente;
    }

    public void delete(String id) {
        log.info("deleting cliente {}", id);
        clienteRepository.deleteById(id);
    }

    private void checkIfClienteIdIsNull(Cliente cliente) {
        if (cliente.getId() != null) {
            throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
    }

    private void checkIfClienteCpfExists(Cliente cliente) {
        var result = clienteRepository.findByCpf(cliente.getCpf());
        result.ifPresent(a -> {
            throw new BadRequestAlertException("cliente já cadastrado com esse CPF", ENTITY_NAME, "CPFexists");
        });
    }
}
