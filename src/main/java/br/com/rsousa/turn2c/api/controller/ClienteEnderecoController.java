package br.com.rsousa.turn2c.api.controller;

import br.com.rsousa.turn2c.domain.dto.cliente.ClienteAtualizacaoDTO;
import br.com.rsousa.turn2c.domain.dto.cliente.ClienteCadastroDTO;
import br.com.rsousa.turn2c.domain.model.Cliente;
import br.com.rsousa.turn2c.domain.service.ClienteEnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteEnderecoController {

    @Autowired
    private ClienteEnderecoService clienteEnderecoService;


    @GetMapping
    public List<Cliente> listarCLientes() {
        return clienteEnderecoService.listar();
    }

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody @Valid ClienteCadastroDTO clienteDTO, UriComponentsBuilder uriComponentsBuilder) {
        Cliente cliente = clienteEnderecoService.salvarCliente(clienteDTO);
        URI uri = uriComponentsBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).body(cliente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteEnderecoService.getClientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody @Valid ClienteAtualizacaoDTO clienteDTO) {
        return ResponseEntity.ok(clienteEnderecoService.atualizarCliente(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteEnderecoService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
