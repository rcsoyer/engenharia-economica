package engenharia.economica.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import engenharia.economica.app.dto.DadosCalcJurosDTO;
import engenharia.economica.app.dto.PeriodoDTO;
import engenharia.economica.app.dto.ResultadoCalcJurosDTO;
import engenharia.economica.app.math.BigDecimalMath;
import engenharia.economica.app.math.MathCommons;

@Service
public class JurosService {
    
    public ResultadoCalcJurosDTO calcularResultadoJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	PeriodoDTO periodo = dadosCalcJurosDTO.getTempoEmprestDTO();
	periodo.setVlrPeriodo(MathCommons.converterTemposMesmaProporcao(
		dadosCalcJurosDTO.getTaxaJurosDTO().getTipoTempoTaxa(), periodo.getTipoTempoPeriodo(), periodo.getVlrPeriodo()));
	BigDecimal juros = calcularJurosSimples(dadosCalcJurosDTO);
	resultado.setJuros(juros.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	resultado.setMontante(calcularMontanteJurosSimples(dadosCalcJurosDTO.getCapitalInicial(), juros).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	periodo = null;
	juros = null;
	
	return resultado;
    }
    
    public BigDecimal calcularJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	return dadosCalcJurosDTO.getCapitalInicial().multiply(dadosCalcJurosDTO.getTaxaJurosDTO().obterTaxaPor100())
		.multiply(dadosCalcJurosDTO.getTempoEmprestDTO().getVlrPeriodo());
    }
    
    private BigDecimal calcularMontanteJurosSimples(BigDecimal capitalInicial, BigDecimal juros) {
	return capitalInicial.add(juros);
    }
    
    public ResultadoCalcJurosDTO calcularResultadoJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	PeriodoDTO periodo = dadosCalcJurosDTO.getTempoEmprestDTO();
	periodo.setVlrPeriodo(MathCommons.converterTemposMesmaProporcao(
		dadosCalcJurosDTO.getTaxaJurosDTO().getTipoTempoTaxa(), periodo.getTipoTempoPeriodo(), periodo.getVlrPeriodo()));
	BigDecimal montante = calcularMontanteJurosCompostos(dadosCalcJurosDTO);
	resultado.setMontante(montante.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	resultado.setJuros(calcularJurosCompostos(montante, dadosCalcJurosDTO.getCapitalInicial()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	periodo = null;
	montante = null;
	
	return resultado;
    }
    	
    private BigDecimal calcularMontanteJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	BigDecimal umTxJurosElevadoAoTempo;
	BigDecimal umMaisTxJuros = BigDecimal.ONE.add(dadosCalcJurosDTO.getTaxaJurosDTO().obterTaxaPor100());
	BigDecimal tempoEmprestimo = dadosCalcJurosDTO.getTempoEmprestDTO().getVlrPeriodo();
	double umTxJurosElevadoAoTempoDouble = Math.pow(umMaisTxJuros.doubleValue(), tempoEmprestimo.doubleValue());
	
	if(Double.isFinite(umTxJurosElevadoAoTempoDouble)) {
	    umTxJurosElevadoAoTempo = BigDecimal.valueOf(umTxJurosElevadoAoTempoDouble);
	} else {
	    umTxJurosElevadoAoTempo = BigDecimalMath.pow(umMaisTxJuros, tempoEmprestimo);
	}
	
	umMaisTxJuros = tempoEmprestimo = null;
	
	return dadosCalcJurosDTO.getCapitalInicial().multiply(umTxJurosElevadoAoTempo);
    }
    
    private BigDecimal calcularJurosCompostos(BigDecimal montante, BigDecimal capitalInicial) {
	return montante.subtract(capitalInicial);
    }
}