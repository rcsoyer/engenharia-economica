package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PeriodoDTO implements Serializable {
    
    private static final long serialVersionUID = 4637882718555090271L;
    
    private BigDecimal vlrPeriodo;
    private String     tipoTempoPeriodo;
    
    public BigDecimal getVlrPeriodo() {
	return vlrPeriodo;
    }
    
    public void setVlrPeriodo(BigDecimal vlrPeriodo) {
	this.vlrPeriodo = vlrPeriodo;
    }
    
    public String getTipoTempoPeriodo() {
	return tipoTempoPeriodo;
    }
    
    public void setTipoTempoPeriodo(String tipoTempoPeriodo) {
	this.tipoTempoPeriodo = tipoTempoPeriodo;
    }
}