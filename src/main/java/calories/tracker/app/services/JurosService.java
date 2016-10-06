package calories.tracker.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import calories.tracker.app.dto.DadosCalcJurosDTO;
import calories.tracker.app.dto.ResultadoCalcJurosDTO;

@Service
public class JurosService {
    
    public ResultadoCalcJurosDTO calcResultadoJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	resultado.setJuros(calcularJurosSimples(dadosCalcJurosDTO));
	resultado.setMontante(calcularMontanteJurosSimples(dadosCalcJurosDTO.getCapitalInicial(), resultado.getJuros()));
	
	return resultado;
    }
    
    public BigDecimal calcularJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	return dadosCalcJurosDTO.getCapitalInicial().multiply(dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest())
		.multiply(dadosCalcJurosDTO.getTxJuros()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal calcularMontanteJurosSimples(BigDecimal capitalInicial, BigDecimal juros) {
	return capitalInicial.add(juros).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public ResultadoCalcJurosDTO calcResultadoJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	resultado.setMontante(calcularMontanteJurosCompostos(dadosCalcJurosDTO));
	resultado.setJuros(calcularJurosCompostos(resultado.getMontante(), dadosCalcJurosDTO.getCapitalInicial()));
	
	return resultado;
    }
    
    public BigDecimal calcularMontanteJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	double umTxJurosElevadoAoTempo = Math.pow(BigDecimal.ONE.add(dadosCalcJurosDTO.getTxJuros()).doubleValue(),
		dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest().doubleValue());
	return dadosCalcJurosDTO.getCapitalInicial().multiply(
		BigDecimal.valueOf(umTxJurosElevadoAoTempo)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal calcularJurosCompostos(BigDecimal montante, BigDecimal capitalInicial) {
	return montante.subtract(capitalInicial).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}