package calories.tracker.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TempoJurosDTO implements Serializable {
    
    private static final long serialVersionUID = -7098172608020680642L;
    
    private BigDecimal tempoEmprest;
    private String     tipoTempoJuros;
    
    public BigDecimal getTempoEmprest() {
	return tempoEmprest;
    }
    
    public void setTempoEmprest(BigDecimal tempoEmprest) {
	this.tempoEmprest = tempoEmprest;
    }
    
    public String getTiposTempoJuros() {
	return tipoTempoJuros;
    }
    
    public void setTiposTempoJuros(String tipoTempoJuros) {
	this.tipoTempoJuros = tipoTempoJuros;
    }
}