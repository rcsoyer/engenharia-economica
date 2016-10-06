package calories.tracker.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosCalcJurosDTO implements Serializable {
    
    private static final long serialVersionUID = 5977885967584018270L;
    
    private BigDecimal	  capitalInicial;
    private BigDecimal	  txJuros;
    private TempoJurosDTO tempoEmprestDTO;
    private String	  tipoJuros;
    
    public BigDecimal getCapitalInicial() {
	return capitalInicial;
    }
    
    public void setCapitalInicial(BigDecimal capitalInicial) {
	this.capitalInicial = capitalInicial;
    }
    
    public BigDecimal getTxJuros() {
	return txJuros;
    }
    
    public void setTxJuros(BigDecimal txJuros) {
	this.txJuros = txJuros;
    }
    
    public TempoJurosDTO getTempoEmprestDTO() {
	return tempoEmprestDTO;
    }
    
    public void setTempoEmprestDTO(TempoJurosDTO tempoEmprestDTO) {
	this.tempoEmprestDTO = tempoEmprestDTO;
    }
    
    public String getTipoJuros() {
	return tipoJuros;
    }
    
    public void setTipoJuros(String tipoJuros) {
	this.tipoJuros = tipoJuros;
    }
}