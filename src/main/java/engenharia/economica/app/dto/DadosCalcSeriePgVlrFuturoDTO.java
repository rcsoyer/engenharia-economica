package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DadosCalcSeriePgVlrFuturoDTO implements Serializable {
    
    private static final long serialVersionUID = 8351108809056127730L;
    
    private BigDecimal	vlrResgatado;
    private BigDecimal	qtdDepositos;
    private DepositoDTO	depositoDTO;
    private TaxaDTO	taxaDTO;
    private String	vrDescobrir;
    
    public BigDecimal getVlrResgatado() {
	return vlrResgatado;
    }
    
    public void setVlrResgatado(BigDecimal vlrResgatado) {
	this.vlrResgatado = vlrResgatado;
    }
    
    public BigDecimal getQtdDepositos() {
	return qtdDepositos;
    }
    
    public void setQtdDepositos(BigDecimal qtdDepositos) {
	this.qtdDepositos = qtdDepositos;
    }
    
    public DepositoDTO getDepositoDTO() {
	return depositoDTO;
    }
    
    public void setDepositoDTO(DepositoDTO depositoDTO) {
	this.depositoDTO = depositoDTO;
    }
    
    public TaxaDTO getTaxaDTO() {
	return taxaDTO;
    }
    
    public String getVrDescobrir() {
	return vrDescobrir;
    }
    
    public void setVrDescobrir(String vrDescobrir) {
	this.vrDescobrir = vrDescobrir;
    }
    
    public void setTaxaDTO(TaxaDTO taxaDTO) {
	this.taxaDTO = taxaDTO;
    }
}