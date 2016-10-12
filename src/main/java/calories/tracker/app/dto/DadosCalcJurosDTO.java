package calories.tracker.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosCalcJurosDTO implements Serializable {
    
    private static final long serialVersionUID = 5977885967584018270L;
    
    private BigDecimal	       capitalInicial;
    private TaxaJurosDTO       taxaJurosDTO;
    private TempoEmprestimoDTO tempoEmprestDTO;
    private String	       tipoJuros;
    
    public BigDecimal getCapitalInicial() {
	return capitalInicial;
    }
    
    public void setCapitalInicial(BigDecimal capitalInicial) {
	this.capitalInicial = capitalInicial;
    }
    
    public TaxaJurosDTO getTaxaJurosDTO() {
	return taxaJurosDTO;
    }
    
    public void setTaxaJurosDTO(TaxaJurosDTO taxaJurosDTO) {
	this.taxaJurosDTO = taxaJurosDTO;
    }
    
    public TempoEmprestimoDTO getTempoEmprestDTO() {
	return tempoEmprestDTO;
    }
    
    public void setTempoEmprestDTO(TempoEmprestimoDTO tempoEmprestDTO) {
	this.tempoEmprestDTO = tempoEmprestDTO;
    }
    
    public String getTipoJuros() {
	return tipoJuros;
    }
    
    public void setTipoJuros(String tipoJuros) {
	this.tipoJuros = tipoJuros;
    }
}