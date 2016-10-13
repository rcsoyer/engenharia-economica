package calories.tracker.app.dto;

import java.io.Serializable;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class ResultadoCalcJurosDTO implements Serializable {
    
    private static final long serialVersionUID = 3990143497827054680L;
    
    @NumberFormat(style=Style.CURRENCY)
    private String juros;
    
    @NumberFormat(style=Style.CURRENCY)
    private String montante;
    
    public String getJuros() {
	return juros;
    }
    
    public void setJuros(String juros) {
	this.juros = juros;
    }
    
    public String getMontante() {
	return montante;
    }
    
    public void setMontante(String montante) {
	this.montante = montante;
    }
}