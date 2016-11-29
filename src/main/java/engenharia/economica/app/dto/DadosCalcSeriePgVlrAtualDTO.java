package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosCalcSeriePgVlrAtualDTO implements Serializable {
    
    private static final long serialVersionUID = -5134863501180560219L;
    
    private BigDecimal	 vlrAtual;
    private BigDecimal	 qtdPrestacoes;
    private String	 vrDescobrir;
    private PrestacaoDTO prestacaoDTO;
    private TaxaDTO	 taxaDTO;
    
    public BigDecimal getVlrAtual() {
	return vlrAtual;
    }
    
    public void setVlrAtual(BigDecimal vlrAtual) {
	this.vlrAtual = vlrAtual;
    }
    
    public BigDecimal getQtdPrestacoes() {
	return qtdPrestacoes;
    }
    
    public void setQtdPrestacoes(BigDecimal qtdPrestacoes) {
	this.qtdPrestacoes = qtdPrestacoes;
    }
    
    public String getVrDescobrir() {
	return vrDescobrir;
    }
    
    public void setVrDescobrir(String vrDescobrir) {
	this.vrDescobrir = vrDescobrir;
    }
    
    public PrestacaoDTO getPrestacaoDTO() {
	return prestacaoDTO;
    }
    
    public void setPrestacaoDTO(PrestacaoDTO prestacaoDTO) {
	this.prestacaoDTO = prestacaoDTO;
    }
    
    public TaxaDTO getTaxaDTO() {
	return taxaDTO;
    }
    
    public void setTaxaDTO(TaxaDTO taxaDTO) {
	this.taxaDTO = taxaDTO;
    }
}