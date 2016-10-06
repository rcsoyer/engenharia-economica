package calories.tracker.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TempoEmprestimoDTO implements Serializable {
    
    private static final long serialVersionUID = -7908193216412715080L;
    
    BigDecimal tempoEmprest;
    
    public BigDecimal getTempoEmprest() {
	return tempoEmprest;
    }
    
    public void setTempoEmprest(BigDecimal tempoEmprest) {
	this.tempoEmprest = tempoEmprest;
    }
}