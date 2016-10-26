package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TempoEmprestimoDTO implements Serializable {
    
    private static final long serialVersionUID = 4637882718555090271L;
    
    private BigDecimal tempoEmprest;
    private String     tipoTempoJuros;
    
    public BigDecimal getTempoEmprest() {
	return tempoEmprest;
    }
    
    public void setTempoEmprest(BigDecimal tempoEmprest) {
	this.tempoEmprest = tempoEmprest;
    }
    
    public String getTipoTempoJuros() {
	return tipoTempoJuros;
    }
    
    public void setTipoTempoJuros(String tipoTempoJuros) {
	this.tipoTempoJuros = tipoTempoJuros;
    }
}