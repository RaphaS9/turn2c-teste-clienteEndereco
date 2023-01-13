package br.com.rsousa.turn2c.api.controller;

import br.com.rsousa.turn2c.domain.dto.endereco.EnderecoAtualizacaoDTO;
import br.com.rsousa.turn2c.domain.dto.endereco.EnderecoCadastroDTO;
import br.com.rsousa.turn2c.domain.model.Endereco;
import br.com.rsousa.turn2c.domain.service.ClienteEnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente/{idCliente}/endereco")
public class EnderecoController {

    @Autowired
    private ClienteEnderecoService clienteEnderecoService;


    @GetMapping
    public List<Endereco> listarTodosEnderecosPorCliente(@PathVariable Long idCliente) {
        return clienteEnderecoService.listarEnderecosPorIdDoCliente(idCliente);
    }

    @PostMapping()
    public ResponseEntity<Endereco> salvarNovoEnderecoParaUmCliente(@PathVariable Long idCliente, @RequestBody @Valid EnderecoCadastroDTO enderecoDTO, UriComponentsBuilder uriComponentsBuilder) {
        Endereco endereco = clienteEnderecoService.salvarNovoEndereco(idCliente, enderecoDTO);

        URI uri = uriComponentsBuilder.path("/cliente/{idCliente}/endereco/{idEndereco}").buildAndExpand(
                endereco.getCliente().getId(), endereco.getId()).toUri();

        return ResponseEntity.created(uri).body(endereco);
    }

    @GetMapping("{idEndereco}")
    public Endereco listarEnderecoDeUmClientePorId(@PathVariable Long idCliente, @PathVariable Long idEndereco) {
        return clienteEnderecoService.listarEnderecoPorId(idCliente, idEndereco);
    }

    @PutMapping("{idEndereco}")
    public Endereco editarEnderecoDeUmCliente(@PathVariable Long idCliente, @PathVariable Long idEndereco, @RequestBody @Valid EnderecoAtualizacaoDTO enderecoDTO) {
        return clienteEnderecoService.atualizarEndereco(idCliente, idEndereco, enderecoDTO);
    }

    @DeleteMapping("{idEndereco}")
    public ResponseEntity<Void> deletarEnderecoDeUmCliente(@PathVariable Long idCliente, @PathVariable Long idEndereco) {
        clienteEnderecoService.deletarEndereco(idCliente, idEndereco);
        return ResponseEntity.noContent().build();
    }
}
