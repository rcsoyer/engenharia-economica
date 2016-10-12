package calories.tracker.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import calories.tracker.app.dto.DadosCalcJurosDTO;
import calories.tracker.app.dto.ResultadoCalcJurosDTO;
import calories.tracker.app.math.BigDecimalMath;

@Service
public class JurosService {
    
    public ResultadoCalcJurosDTO calcularResultadoJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	
	if(dadosCalcJurosDTO.getTempoEmprestDTO().getTipoTempoJuros().equals(dadosCalcJurosDTO.getTaxaJurosDTO().getTipoTempoTxJuros())) {
	    
	}
	
	BigDecimal juros = calcularJurosSimples(dadosCalcJurosDTO);
	resultado.setJuros(juros.toPlainString());
	resultado.setMontante(calcularMontanteJurosSimples(dadosCalcJurosDTO.getCapitalInicial(), juros).toPlainString());
	
	return resultado;
    }
    
    public BigDecimal calcularJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	return dadosCalcJurosDTO.getCapitalInicial().multiply(dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest())
		.multiply(dadosCalcJurosDTO.getTaxaJurosDTO().obterTxJurosPor100()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal calcularMontanteJurosSimples(BigDecimal capitalInicial, BigDecimal juros) {
	return capitalInicial.add(juros).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public ResultadoCalcJurosDTO calcularResultadoJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	BigDecimal montante = calcularMontanteJurosCompostos(dadosCalcJurosDTO);
	resultado.setMontante(montante.toPlainString());
	resultado.setJuros(calcularJurosCompostos(montante, dadosCalcJurosDTO.getCapitalInicial()).toPlainString());
	
	return resultado;
    }
    
    public BigDecimal calcularMontanteJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	BigDecimal umTxJurosElevadoAoTempo = BigDecimalMath.pow(
		BigDecimal.ONE.add(dadosCalcJurosDTO.getTaxaJurosDTO().obterTxJurosPor100()), dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest());
	return dadosCalcJurosDTO.getCapitalInicial().multiply(umTxJurosElevadoAoTempo).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal calcularJurosCompostos(BigDecimal montante, BigDecimal capitalInicial) {
	return montante.subtract(capitalInicial).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}