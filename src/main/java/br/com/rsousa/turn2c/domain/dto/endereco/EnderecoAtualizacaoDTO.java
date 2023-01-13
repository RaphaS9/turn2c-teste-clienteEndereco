package br.com.rsousa.turn2c.domain.dto.endereco;

import br.com.rsousa.turn2c.domain.model.Endereco;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EnderecoAtualizacaoDTO {
    private String bairro;
    private String logradouro;
    private Integer cep;
    private Integer numero;
    private String cidade;
    @Size(max = 2)
    private String uf;


    public Endereco atualizarEndereco(Endereco enderecoSalvo) {
        if (this.getBairro() != null) {
            enderecoSalvo.setBairro(this.getBairro());
        }
        if (this.getLogradouro() != null) {
            enderecoSalvo.setLogradouro(this.getLogradouro());
        }
        if (this.getCep() != null) {
            enderecoSalvo.setCep(this.getCep());
        }
        if (this.getNumero() != null) {
            enderecoSalvo.setCep(this.getNumero());
        }
        if (this.getCidade() != null) {
            enderecoSalvo.setCidade(this.getCidade());
        }
        if (this.getUf() != null) {
            enderecoSalvo.setUf(this.getUf());
        }

        return enderecoSalvo;
    }
}
