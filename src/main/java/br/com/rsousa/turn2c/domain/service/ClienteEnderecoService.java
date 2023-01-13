package br.com.rsousa.turn2c.domain.service;

import br.com.rsousa.turn2c.domain.dto.cliente.ClienteAtualizacaoDTO;
import br.com.rsousa.turn2c.domain.dto.cliente.ClienteCadastroDTO;
import br.com.rsousa.turn2c.domain.dto.endereco.EnderecoAtualizacaoDTO;
import br.com.rsousa.turn2c.domain.dto.endereco.EnderecoCadastroDTO;
import br.com.rsousa.turn2c.domain.model.Cliente;
import br.com.rsousa.turn2c.domain.model.Endereco;
import br.com.rsousa.turn2c.domain.repository.ClienteRepository;
import br.com.rsousa.turn2c.domain.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteEnderecoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente getClientePorId(Long id) {
        return getClienteOuExcecao(id);
    }

    public Cliente salvarCliente(ClienteCadastroDTO clienteDTO) {
        Cliente cliente = clienteDTO.toCliente();
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(Long id, ClienteAtualizacaoDTO clienteAtualizadoDTO) {
        Cliente clienteSalvo = getClienteOuExcecao(id);
        clienteSalvo = clienteAtualizadoDTO.atualizarClienteSalvo(clienteSalvo);
        return clienteRepository.save(clienteSalvo);
    }

    public void deletarCliente(Long id) {
        Cliente cliente = getClienteOuExcecao(id);

        clienteRepository.delete(cliente);
    }

    // GET ENDERECO
    public List<Endereco> listarEnderecosPorIdDoCliente(Long id) {
        Cliente cliente = getClienteOuExcecao(id);

        return cliente.getEnderecos();
    }

    // POST ENDERECO
    public Endereco salvarNovoEndereco(Long idCliente, EnderecoCadastroDTO enderecoCadastroDTO) {
        Cliente cliente = getClienteOuExcecao(idCliente);
        Endereco enderecoEntidade = enderecoCadastroDTO.toEndereco();

        enderecoEntidade.setCliente(cliente);
        cliente.addEndereco(enderecoEntidade);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteSalvo.getEnderecos().get(clienteSalvo.getEnderecos().size() - 1);
    }

    // GET ENDERECO/ID
    public Endereco listarEnderecoPorId(Long idCLiente, Long idEndereco) {
        return getEnderecoOuExcecao(idCLiente, idEndereco);
    }

    // PUT ENDERECO/ID
    public Endereco atualizarEndereco(Long idCLiente, Long idEndereco, EnderecoAtualizacaoDTO atualizacaoDTO) {
        Endereco endereco = getEnderecoOuExcecao(idCLiente, idEndereco);
        endereco = atualizacaoDTO.atualizarEndereco(endereco);
        return enderecoRepository.save(endereco);
    }

    // DELETE ENDERECO/ID
    public void deletarEndereco(Long idCLiente, Long idEndereco) {
        Endereco endereco = getEnderecoOuExcecao(idCLiente, idEndereco);
        Cliente cliente = getClienteOuExcecao(idCLiente);
        cliente.removeEndereco(endereco);

        enderecoRepository.delete(endereco);
    }


    // METODOS ADICIONAIS
    private Cliente getClienteOuExcecao(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cliente de id " + id + " não encontrado"));
    }

    private Endereco getEnderecoOuExcecao(Long idCliente, Long idEndereco) {
        Cliente clienteSalvo = getClienteOuExcecao(idCliente);
        Endereco enderecoASerProcurado = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new EntityNotFoundException("Endereço de id " + idEndereco + " não encontrado"));
        if (clienteSalvo.getEnderecos().contains(enderecoASerProcurado)) {
            return enderecoASerProcurado;
        } else {
            throw new EntityNotFoundException("Não foi possível achar o endereco " + idEndereco);
        }
    }
}
