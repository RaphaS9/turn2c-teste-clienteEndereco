package br.com.rsousa.turn2c.domain.dto.cliente;

import br.com.rsousa.turn2c.domain.dto.endereco.EnderecoCadastroDTO;
import br.com.rsousa.turn2c.domain.model.Cliente;
import br.com.rsousa.turn2c.domain.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteAtualizacaoDTO {
    private String nome;
    @Email
    private String email;
    private String cpf;
    @Valid
    private List<EnderecoCadastroDTO> enderecos;

    public Cliente atualizarClienteSalvo(Cliente clienteSalvo) {
        if (this.nome != null) {
            clienteSalvo.setNome(this.nome);
        }
        if (this.email != null) {
            clienteSalvo.setEmail(this.email);
        }
        if (this.cpf != null) {
            clienteSalvo.setCpf(this.cpf);
        }
        if (this.enderecos != null) {
//            Em nosso endpoint de atualizacao de cliente {PUT /cliente/id} se recebermos junto da requisicao informacoes
//            de enderecos, entenderemos que os enderecos anteriores devem ser apagados e os novos colocados no lugar
//            por isso apagamos todos os enderecos salvos e adicionamos o que veio na requisicao
            List<Endereco> listaEnderecos = this.enderecos.stream().map(dto -> dto.toEndereco()).toList();
            listaEnderecos.forEach(endereco -> endereco.setCliente(clienteSalvo));

            clienteSalvo.getEnderecos().clear();
            clienteSalvo.getEnderecos().addAll(listaEnderecos);
        }
        return clienteSalvo;
    }
}
