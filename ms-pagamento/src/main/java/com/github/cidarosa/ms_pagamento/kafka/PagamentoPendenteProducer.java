package com.github.cidarosa.ms_pagamento.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PagamentoPendenteProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void enviarPagamentoPendente(String pagamentoId){
        kafkaTemplate.send("pagamento-pendente", pagamentoId);
    }
}
