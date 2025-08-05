package com.github.cidarosa.ms_pedido.dto;

import com.github.cidarosa.ms_pedido.entities.ItemDoPedido;
import com.github.cidarosa.ms_pedido.entities.Pedido;
import com.github.cidarosa.ms_pedido.entities.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PedidoDTO {

    private Long id;

    @NotEmpty(message = "Nome requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    // @CPF(message = "CPF Inválido")
    @NotEmpty(message = "CPF requerido")
    @Size(min = 14, max = 14, message = "CPF deve ter 14 caracteres - máscara")
    private String cpf;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatusDTO status;

    private BigDecimal valorTotal;

    @NotEmpty(message = "Deve ter pelo menos um item do pedido")
    private List<@Valid ItemDoPedidoDTO> itens = new ArrayList<>();

    public PedidoDTO(Pedido entity) {
        id = entity.getId();
        nome = entity.getNome();
        cpf = entity.getCpf();
        data = entity.getData();
        status = new StatusDTO( entity.getStatus());
        valorTotal = entity.getValorTotal();

        for (ItemDoPedido item : entity.getItens()) {
            ItemDoPedidoDTO itemDTO = new ItemDoPedidoDTO(item);
            itens.add(itemDTO);
        }
    }
}
