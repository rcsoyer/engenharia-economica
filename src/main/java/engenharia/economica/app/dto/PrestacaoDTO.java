package engenharia.economica.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrestacaoDTO implements Serializable {
    
    private static final long serialVersionUID = -554255467412376958L;
    
    private BigDecimal vlrPrestacao;
    private String     tipoTempoPrestacoes;
    
    public BigDecimal getVlrPrestacao() {
	return vlrPrestacao;
    }
    
    public void setVlrPrestacao(BigDecimal vlrPrestacao) {
	this.vlrPrestacao = vlrPrestacao;
    }
    
    public String getTipoTempoPrestacoes() {
	return tipoTempoPrestacoes;
    }
    
    public void setTipoTempoPrestacoes(String tipoTempoPrestacoes) {
	this.tipoTempoPrestacoes = tipoTempoPrestacoes;
    }
}