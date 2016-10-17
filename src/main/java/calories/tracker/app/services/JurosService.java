package calories.tracker.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import calories.tracker.app.dto.DadosCalcJurosDTO;
import calories.tracker.app.dto.ResultadoCalcJurosDTO;
import calories.tracker.app.dto.TempoEmprestimoDTO;
import calories.tracker.app.enums.TiposTempoJuros;
import calories.tracker.app.math.BigDecimalMath;
import calories.tracker.app.math.MathCommons;

@Service
public class JurosService {
    
    public ResultadoCalcJurosDTO calcularResultadoJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	converterTemposMesmaProporcao(dadosCalcJurosDTO);
	BigDecimal juros = calcularJurosSimples(dadosCalcJurosDTO);
	resultado.setJuros(juros.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	resultado.setMontante(calcularMontanteJurosSimples(dadosCalcJurosDTO.getCapitalInicial(), juros).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	return resultado;
    }
    
    private void converterTemposMesmaProporcao(DadosCalcJurosDTO dadosCalcJurosDTO) {
	String tipoTempoTxJuros = dadosCalcJurosDTO.getTaxaJurosDTO().getTipoTempoTxJuros();
	String tipoTempoEmprest = dadosCalcJurosDTO.getTempoEmprestDTO().getTipoTempoJuros();
	
	if (!tipoTempoEmprest.equals(tipoTempoTxJuros)) {
	    TempoEmprestimoDTO tempoEmprestimoDTO = dadosCalcJurosDTO.getTempoEmprestDTO();
	    
	    if(tipoTempoTxJuros.equals(TiposTempoJuros.M.name())) {
		if(tipoTempoEmprest.equals(TiposTempoJuros.D.name())) {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().divide(MathCommons.DIAS_NO_MES, MathCommons.MATH_CONTEXT));
		} else {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().multiply(MathCommons.MESES_NO_ANO));
		}
	    } else if(tipoTempoTxJuros.equals(TiposTempoJuros.A.name())) {
		if(tipoTempoEmprest.equals(TiposTempoJuros.D.name())) {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().divide(MathCommons.DIAS_NO_ANO, MathCommons.MATH_CONTEXT));
		} else {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().divide(MathCommons.MESES_NO_ANO, MathCommons.MATH_CONTEXT));
		}
	    } else {
		if(tipoTempoEmprest.equals(TiposTempoJuros.M.name())) {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().multiply(MathCommons.DIAS_NO_MES));
		} else {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().multiply(MathCommons.DIAS_NO_ANO));
		}
	    }
	}
    }
    
    public BigDecimal calcularJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	return dadosCalcJurosDTO.getCapitalInicial().multiply(dadosCalcJurosDTO.getTaxaJurosDTO().obterTxJurosPor100())
		.multiply(dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest());
    }
    
    public BigDecimal calcularMontanteJurosSimples(BigDecimal capitalInicial, BigDecimal juros) {
	return capitalInicial.add(juros);
    }
    
    public ResultadoCalcJurosDTO calcularResultadoJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	converterTemposMesmaProporcao(dadosCalcJurosDTO);
	BigDecimal montante = calcularMontanteJurosCompostos(dadosCalcJurosDTO);
	resultado.setMontante(montante.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	resultado.setJuros(calcularJurosCompostos(montante, dadosCalcJurosDTO.getCapitalInicial()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	return resultado;
    }
    	
    public BigDecimal calcularMontanteJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	BigDecimal umTxJurosElevadoAoTempo;
	double umTxJurosElevadoAoTempoDouble = Math.pow(BigDecimal.ONE.add(dadosCalcJurosDTO.getTaxaJurosDTO().obterTxJurosPor100()).doubleValue(), 
							dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest().doubleValue());
	
	if(Double.isFinite(umTxJurosElevadoAoTempoDouble)){
	    umTxJurosElevadoAoTempo = BigDecimal.valueOf(umTxJurosElevadoAoTempoDouble);
	} else {
	    umTxJurosElevadoAoTempo = BigDecimalMath.pow(
		    BigDecimal.ONE.add(dadosCalcJurosDTO.getTaxaJurosDTO().obterTxJurosPor100()), dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest());
	}
	
	return dadosCalcJurosDTO.getCapitalInicial().multiply(umTxJurosElevadoAoTempo);
    }
    
    public BigDecimal calcularJurosCompostos(BigDecimal montante, BigDecimal capitalInicial) {
	return montante.subtract(capitalInicial);
    }
}