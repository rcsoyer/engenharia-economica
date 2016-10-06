package calories.tracker.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResultadoCalcJurosDTO implements Serializable {
    
    private static final long serialVersionUID = 3990143497827054680L;
    
    private BigDecimal juros;
    private BigDecimal montante;
    
    public BigDecimal getJuros() {
	return juros;
    }
    
    public void setJuros(BigDecimal juros) {
	this.juros = juros;
    }
    
    public BigDecimal getMontante() {
	return montante;
    }
    
    public void setMontante(BigDecimal montante) {
	this.montante = montante;
    }
}