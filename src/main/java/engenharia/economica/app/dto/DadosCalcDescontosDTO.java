package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosCalcDescontosDTO implements Serializable {
    
    private static final long serialVersionUID = -8943600282940385430L;
    
    private BigDecimal vlrTitulo;
    private TaxaDTO    taxaDTO;
    private String     tipoDescontos;
    private PeriodoDTO periodoDTO;
    
    public BigDecimal getVlrTitulo() {
	return vlrTitulo;
    }
    
    public void setVlrTitulo(BigDecimal vlrTitulo) {
	this.vlrTitulo = vlrTitulo;
    }
    
    public TaxaDTO getTaxaDTO() {
	return taxaDTO;
    }
    
    public void setTaxaDTO(TaxaDTO taxaDTO) {
	this.taxaDTO = taxaDTO;
    }
    
    public String getTipoDescontos() {
	return tipoDescontos;
    }
    
    public void setTipoDescontos(String tipoDescontos) {
	this.tipoDescontos = tipoDescontos;
    }
    
    public PeriodoDTO getPeriodoDTO() {
	return periodoDTO;
    }
    
    public void setPeriodoDTO(PeriodoDTO periodoDTO) {
	this.periodoDTO = periodoDTO;
    }
}