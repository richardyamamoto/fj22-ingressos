package br.com.caelum.ingresso.desconto;

import java.math.BigDecimal;

public class DescontoEstudante implements Desconto{

    @Override
    public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
        return precoOriginal.divide(new BigDecimal("2.0"));
    }

    @Override
    public String getDescricao() {
        return "Estudante";
    }
}
