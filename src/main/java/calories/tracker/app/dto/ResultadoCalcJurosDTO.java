package calories.tracker.app.dto;

import java.io.Serializable;

public class ResultadoCalcJurosDTO implements Serializable {
    
    private static final long serialVersionUID = 3990143497827054680L;
    
    private String juros;
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