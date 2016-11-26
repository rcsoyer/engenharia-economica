package engenharia.economica.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import engenharia.economica.app.dto.DadosCalcDescontosDTO;
import engenharia.economica.app.dto.PeriodoDTO;
import engenharia.economica.app.dto.ResultadoCalcDescontosDTO;
import engenharia.economica.app.math.BigDecimalMath;
import engenharia.economica.app.math.MathCommons;

@Service
public class DescontosService {
    
    public ResultadoCalcDescontosDTO calcularResultadoDescontosSimples(DadosCalcDescontosDTO dadosCalcDescontosDTO) {
	ResultadoCalcDescontosDTO resultado = new ResultadoCalcDescontosDTO();
	PeriodoDTO periodo = dadosCalcDescontosDTO.getPeriodoDTO();
	periodo.setVlrPeriodo(MathCommons.converterTemposMesmaProporcao(dadosCalcDescontosDTO.getTaxaDTO().getTipoTempoTaxa(), periodo));
	BigDecimal vlrDescontado = calcularVlrDescontadoSimples(dadosCalcDescontosDTO);
	resultado.setVlrDescontado(vlrDescontado.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	resultado.setVlrCreditado(calcularVlrCreditadoSimples(dadosCalcDescontosDTO.getVlrTitulo(), vlrDescontado).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	periodo = null;
	vlrDescontado = null;
	
	return resultado;
    }
    
    public BigDecimal calcularVlrDescontadoSimples(DadosCalcDescontosDTO dadosCalcDescontosDTO) {
	return dadosCalcDescontosDTO.getVlrTitulo().multiply(dadosCalcDescontosDTO.getTaxaDTO().obterTaxaPor100())
		.multiply(dadosCalcDescontosDTO.getPeriodoDTO().getVlrPeriodo());
    }
    
    private BigDecimal calcularVlrCreditadoSimples(BigDecimal vlrTitulo, BigDecimal vlrDescontado) {
	return vlrTitulo.subtract(vlrDescontado);
    }
    
    public ResultadoCalcDescontosDTO calcularResultadoDescontosCompostos(DadosCalcDescontosDTO dadosCalcDescontosDTO) {
	ResultadoCalcDescontosDTO resultado = new ResultadoCalcDescontosDTO();
	PeriodoDTO periodo = dadosCalcDescontosDTO.getPeriodoDTO();
	periodo.setVlrPeriodo(MathCommons.converterTemposMesmaProporcao(dadosCalcDescontosDTO.getTaxaDTO().getTipoTempoTaxa(), periodo));
	BigDecimal vlrCreditado = calcularVlrCreditadoComposto(dadosCalcDescontosDTO);
	BigDecimal vlrDescontado = calcularVlrDescontadoComposto(dadosCalcDescontosDTO.getVlrTitulo(), vlrCreditado);
	resultado.setVlrDescontado(vlrDescontado.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	resultado.setVlrCreditado(vlrCreditado.setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	periodo = null;
	vlrDescontado = vlrCreditado = null;
	
	return resultado;
    }
    
    public BigDecimal calcularVlrDescontadoComposto(BigDecimal vlrTitulo, BigDecimal vlrCreditado) {
	return vlrTitulo.subtract(vlrCreditado);
    }
    
    private BigDecimal calcularVlrCreditadoComposto(DadosCalcDescontosDTO dadosCalcDescontosDTO) {
	BigDecimal umMenosTxElevadoTempoBgDecimal;
	BigDecimal umMenosTaxa = BigDecimal.ONE.subtract(dadosCalcDescontosDTO.getTaxaDTO().obterTaxaPor100());
	BigDecimal periodo = dadosCalcDescontosDTO.getPeriodoDTO().getVlrPeriodo();
	double umMenosTxElevadoTempoDouble = Math.pow(umMenosTaxa.doubleValue(), periodo.doubleValue());
	
	if(Double.isFinite(umMenosTxElevadoTempoDouble)) {
	    umMenosTxElevadoTempoBgDecimal = BigDecimal.valueOf(umMenosTxElevadoTempoDouble);
	} else {
	    umMenosTxElevadoTempoBgDecimal = BigDecimalMath.pow(umMenosTaxa, periodo);
	}
	
	umMenosTaxa = periodo = null;
	
	return dadosCalcDescontosDTO.getVlrTitulo().multiply(umMenosTxElevadoTempoBgDecimal);
    }
}