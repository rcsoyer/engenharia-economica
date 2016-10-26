package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaxaJurosDTO implements Serializable {
    
    private static final long serialVersionUID = 6694713191934182151L;
    
    private static final BigDecimal CEM	= new BigDecimal(100);
    private BigDecimal		    vlrTxJuros;
    private String		    tipoTempoTxJuros;
    
    public BigDecimal obterTxJurosPor100() {
	return vlrTxJuros.divide(CEM);
    }
    
    public BigDecimal getVlrTxJuros() {
	return vlrTxJuros;
    }
    
    public void setVlrTxJuros(BigDecimal vlrTxJuros) {
	this.vlrTxJuros = vlrTxJuros;
    }
    
    public String getTipoTempoTxJuros() {
	return tipoTempoTxJuros;
    }
    
    public void setTipoTempoTxJuros(String tipoTempoTxJuros) {
	this.tipoTempoTxJuros = tipoTempoTxJuros;
    }
}