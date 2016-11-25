package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaxaDTO implements Serializable {
    
    private static final long serialVersionUID = 6694713191934182151L;
    
    private static final BigDecimal CEM	= new BigDecimal(100);
    private BigDecimal		    vlrTaxa;
    private String		    tipoTempoTaxa;
    
    public BigDecimal obterTaxaPor100() {
	return vlrTaxa.divide(CEM);
    }
    
    public BigDecimal getVlrTaxa() {
	return vlrTaxa;
    }
    
    public void setVlrTaxa(BigDecimal vlrTaxa) {
	this.vlrTaxa = vlrTaxa;
    }
    
    public String getTipoTempoTaxa() {
	return tipoTempoTaxa;
    }
    
    public void setTipoTempoTaxa(String tipoTempoTaxa) {
	this.tipoTempoTaxa = tipoTempoTaxa;
    }
}