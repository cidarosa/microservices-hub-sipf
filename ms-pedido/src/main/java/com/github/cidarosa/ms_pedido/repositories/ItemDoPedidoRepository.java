package com.github.cidarosa.ms_pedido.repositories;

import com.github.cidarosa.ms_pedido.entities.ItemDoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ItemDoPedido i WHERE i.pedido.id = :pedidoId")
    void deleteByPedidoId(long pedidoId);
}
