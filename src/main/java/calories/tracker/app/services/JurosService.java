package calories.tracker.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import calories.tracker.app.dto.DadosCalcJurosDTO;
import calories.tracker.app.dto.ResultadoCalcJurosDTO;
import calories.tracker.app.dto.TempoEmprestimoDTO;
import calories.tracker.app.enums.TiposTempoJuros;
import calories.tracker.app.math.BigDecimalMath;

@Service
public class JurosService {
    
    private static final BigDecimal DIAS_NO_MES  = new BigDecimal(30);
    private static final BigDecimal MESES_NO_ANO = new BigDecimal(12);
    private static final BigDecimal DIAS_NO_ANO  = new BigDecimal(365);
    
    public ResultadoCalcJurosDTO calcularResultadoJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	converterTemposMesmaProporcao(dadosCalcJurosDTO);
	BigDecimal juros = calcularJurosSimples(dadosCalcJurosDTO);
	resultado.setJuros(juros.toPlainString());
	resultado.setMontante(calcularMontanteJurosSimples(dadosCalcJurosDTO.getCapitalInicial(), juros).toPlainString());
	
	return resultado;
    }
    
    private void converterTemposMesmaProporcao(DadosCalcJurosDTO dadosCalcJurosDTO) {
	String tipoTempoTxJuros = dadosCalcJurosDTO.getTaxaJurosDTO().getTipoTempoTxJuros();
	String tipoTempoEmprest = dadosCalcJurosDTO.getTempoEmprestDTO().getTipoTempoJuros();
	
	if (!tipoTempoEmprest.equals(tipoTempoTxJuros)) {
	    TempoEmprestimoDTO tempoEmprestimoDTO = dadosCalcJurosDTO.getTempoEmprestDTO();
	    
	    if(tipoTempoTxJuros.equals(TiposTempoJuros.M.name())) {
		if(tipoTempoEmprest.equals(TiposTempoJuros.D.name())) {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().divide(DIAS_NO_MES, 2, BigDecimal.ROUND_HALF_EVEN));
		} else {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().multiply(MESES_NO_ANO));
		}
	    } else if(tipoTempoTxJuros.equals(TiposTempoJuros.A.name())) {
		if(tipoTempoEmprest.equals(TiposTempoJuros.D.name())) {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().divide(DIAS_NO_ANO, 2, BigDecimal.ROUND_HALF_EVEN));
		} else {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().divide(MESES_NO_ANO, 2, BigDecimal.ROUND_HALF_EVEN));
		}
	    } else {
		if(tipoTempoEmprest.equals(TiposTempoJuros.M.name())) {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().multiply(DIAS_NO_MES));
		} else {
		    tempoEmprestimoDTO.setTempoEmprest(tempoEmprestimoDTO.getTempoEmprest().multiply(DIAS_NO_ANO));
		}
	    }
	}
    }
    
    public BigDecimal calcularJurosSimples(DadosCalcJurosDTO dadosCalcJurosDTO) {
	return dadosCalcJurosDTO.getCapitalInicial().multiply(dadosCalcJurosDTO.getTaxaJurosDTO().obterTxJurosPor100())
		.multiply(dadosCalcJurosDTO.getTempoEmprestDTO().getTempoEmprest()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public BigDecimal calcularMontanteJurosSimples(BigDecimal capitalInicial, BigDecimal juros) {
	return capitalInicial.add(juros).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
    
    public ResultadoCalcJurosDTO calcularResultadoJurosCompostos(DadosCalcJurosDTO dadosCalcJurosDTO) {
	ResultadoCalcJurosDTO resultado = new ResultadoCalcJurosDTO();
	converterTemposMesmaProporcao(dadosCalcJurosDTO);
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