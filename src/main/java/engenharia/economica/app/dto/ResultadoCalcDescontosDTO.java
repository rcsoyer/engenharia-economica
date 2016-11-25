package engenharia.economica.app.dto;

import java.io.Serializable;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class ResultadoCalcDescontosDTO implements Serializable {
    
    private static final long serialVersionUID = -2914489564169333115L;
    
    @NumberFormat(style = Style.CURRENCY)
    private String vlrDescontado;
    
    @NumberFormat(style = Style.CURRENCY)
    private String vlrCreditado;
    
    public String getVlrDescontado() {
	return vlrDescontado;
    }
    
    public void setVlrDescontado(String vlrDescontado) {
	this.vlrDescontado = vlrDescontado;
    }
    
    public String getVlrCreditado() {
	return vlrCreditado;
    }
    
    public void setVlrCreditado(String vlrCreditado) {
	this.vlrCreditado = vlrCreditado;
    }
}