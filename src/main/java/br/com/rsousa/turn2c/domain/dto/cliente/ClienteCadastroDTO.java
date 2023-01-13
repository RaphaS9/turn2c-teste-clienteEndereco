package br.com.rsousa.turn2c.domain.dto.cliente;

import br.com.rsousa.turn2c.domain.dto.endereco.EnderecoCadastroDTO;
import br.com.rsousa.turn2c.domain.model.Cliente;
import br.com.rsousa.turn2c.domain.model.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCadastroDTO {
    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String cpf;
    @Valid
    private List<EnderecoCadastroDTO> enderecos;

    public Cliente toCliente() {
        Cliente cliente = new Cliente();

        if (this.enderecos != null) {
//            Transformo os dtos de enderecos para entidade Endereco e coloco numa lista
//            Entao set o cliente de cada um desses enderecos para o cliente sendo criado
            List<Endereco> listaEnderecos = this.enderecos.stream().map(dto -> dto.toEndereco()).toList();
            listaEnderecos.forEach(endereco -> endereco.setCliente(cliente));
            cliente.setEnderecos(listaEnderecos);
        }
        BeanUtils.copyProperties(this, cliente, "enderecos");
        return cliente;
    }
}
