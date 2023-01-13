package br.com.rsousa.turn2c.domain.dto.endereco;

import br.com.rsousa.turn2c.domain.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCadastroDTO {
    @NotBlank
    private String bairro;

    @NotBlank
    private String logradouro;

    @NotNull
    @Pattern(regexp = "\\d{8}", message = "O cep deve conter 8 digitos")
    private Integer cep;

    @NotNull
    private Integer numero;

    @NotBlank
    private String cidade;

    @NotBlank
    @Size(max = 2)
    private String uf;

    public Endereco toEndereco() {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(this, endereco);
        return endereco;
    }
}
