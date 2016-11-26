package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DepositoDTO implements Serializable {
    
    private static final long serialVersionUID = -515378685896930040L;
    
    private BigDecimal vlrDeposito;
    private String     tipoTempoDepositos;
    
    public BigDecimal getVlrDeposito() {
	return vlrDeposito;
    }
    
    public void setVlrDeposito(BigDecimal vlrDeposito) {
	this.vlrDeposito = vlrDeposito;
    }
    
    public String getTipoTempoDepositos() {
	return tipoTempoDepositos;
    }
    
    public void setTipoTempoDepositos(String tipoTempoDepositos) {
	this.tipoTempoDepositos = tipoTempoDepositos;
    }
}