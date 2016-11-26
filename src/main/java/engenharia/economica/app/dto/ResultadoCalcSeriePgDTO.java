package engenharia.economica.app.dto;

import java.io.Serializable;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class ResultadoCalcSeriePgDTO implements Serializable {
    
    private static final long serialVersionUID = -1081865022530734701L;
    
    @NumberFormat(style = Style.CURRENCY)
    private String resultado;
    
    public String getResultado() {
	return resultado;
    }
    
    public void setResultado(String resultado) {
	this.resultado = resultado;
    }
}