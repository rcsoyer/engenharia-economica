package engenharia.economica.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import engenharia.economica.app.dto.DadosCalcSeriePgVlrFuturoDTO;
import engenharia.economica.app.dto.DepositoDTO;
import engenharia.economica.app.dto.ResultadoCalcSeriePgDTO;
import engenharia.economica.app.math.BigDecimalMath;
import engenharia.economica.app.math.MathCommons;

@Service
public class SeriePagamentosVlrFuturoService {
    
    public ResultadoCalcSeriePgDTO calcularVlrResgatado(DadosCalcSeriePgVlrFuturoDTO dadosCalcSeriePgVlrFuturoDTO) {
	ResultadoCalcSeriePgDTO resultadoCalcSeriePg = new ResultadoCalcSeriePgDTO();
	DepositoDTO depositoDTO = dadosCalcSeriePgVlrFuturoDTO.getDepositoDTO();
	BigDecimal taxaConvertida = MathCommons.converterTemposMesmaProporcaoBaseTaxa(dadosCalcSeriePgVlrFuturoDTO.getTaxaDTO(), depositoDTO.getTipoTempoDepositos());
	BigDecimal umMaisTxElevadoQtdDepositosMenosUm = calcularUmMaisTxElevadoQtdDepositosMenos1(dadosCalcSeriePgVlrFuturoDTO, taxaConvertida);
	
	resultadoCalcSeriePg.setResultado(depositoDTO.getVlrDeposito()
	  .multiply(umMaisTxElevadoQtdDepositosMenosUm).divide(taxaConvertida, MathCommons.MATH_CONTEXT_100).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	umMaisTxElevadoQtdDepositosMenosUm = taxaConvertida = null;
	depositoDTO = null;
	
	return resultadoCalcSeriePg;
    }
    
    public ResultadoCalcSeriePgDTO calcularVlrDeposito(DadosCalcSeriePgVlrFuturoDTO dadosCalcSeriePgVlrFuturoDTO) {
	ResultadoCalcSeriePgDTO resultadoCalcSeriePg = new ResultadoCalcSeriePgDTO();
	DepositoDTO depositoDTO = dadosCalcSeriePgVlrFuturoDTO.getDepositoDTO();
	BigDecimal taxaConvertida = MathCommons.converterTemposMesmaProporcaoBaseTaxa(dadosCalcSeriePgVlrFuturoDTO.getTaxaDTO(), depositoDTO.getTipoTempoDepositos());
	BigDecimal umMaisTxElevadoQtdDepositosMenosUm = calcularUmMaisTxElevadoQtdDepositosMenos1(dadosCalcSeriePgVlrFuturoDTO, taxaConvertida);
	
	resultadoCalcSeriePg.setResultado(dadosCalcSeriePgVlrFuturoDTO.getVlrResgatado().multiply(taxaConvertida)
		.divide(umMaisTxElevadoQtdDepositosMenosUm, MathCommons.MATH_CONTEXT_100).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	umMaisTxElevadoQtdDepositosMenosUm = taxaConvertida = null;
	depositoDTO = null;
	
	return resultadoCalcSeriePg;
    }
    
    private BigDecimal calcularUmMaisTxElevadoQtdDepositosMenos1(DadosCalcSeriePgVlrFuturoDTO dadosCalcSeriePgVlrFuturoDTO, BigDecimal taxaConvertida) {
	BigDecimal umMaisTxElevadoQtdDepositos;
	BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaConvertida);
	BigDecimal qtdDepositos = dadosCalcSeriePgVlrFuturoDTO.getQtdDepositos();
	double umMaisTxElevadoQtdDepositosDouble = Math.pow(umMaisTaxa.doubleValue(), qtdDepositos.doubleValue());
	
	if(Double.isFinite(umMaisTxElevadoQtdDepositosDouble)) {
	    umMaisTxElevadoQtdDepositos = BigDecimal.valueOf(umMaisTxElevadoQtdDepositosDouble);
	} else {
	    umMaisTxElevadoQtdDepositos = BigDecimalMath.pow(umMaisTaxa, qtdDepositos);
	}
	
	umMaisTaxa = qtdDepositos = null;
	
	return umMaisTxElevadoQtdDepositos.subtract(BigDecimal.ONE);
    }
    
    public ResultadoCalcSeriePgDTO calcularQtdDepositos(DadosCalcSeriePgVlrFuturoDTO dadosCalcSeriePgVlrFuturoDTO) {
	BigDecimal umMaisTaxaLog10;
	BigDecimal vlrResgatadoMultTxAddDepositoDivideDepositoLog10;
	ResultadoCalcSeriePgDTO resultadoCalcSeriePg = new ResultadoCalcSeriePgDTO();
	DepositoDTO depositoDTO = dadosCalcSeriePgVlrFuturoDTO.getDepositoDTO();
	BigDecimal vlrDeposito = depositoDTO.getVlrDeposito();
	BigDecimal taxaConvertida = MathCommons.converterTemposMesmaProporcaoBaseTaxa(dadosCalcSeriePgVlrFuturoDTO.getTaxaDTO(), depositoDTO.getTipoTempoDepositos());
	BigDecimal vlrResgatadoMultTxAddDepositoDivideDeposito =
		dadosCalcSeriePgVlrFuturoDTO.getVlrResgatado().multiply(taxaConvertida).add(vlrDeposito).divide(vlrDeposito, MathCommons.MATH_CONTEXT_100);
	double vlrResgatadoMultTxAddDepositoDivideDepositoLog10Double = Math.log10(vlrResgatadoMultTxAddDepositoDivideDeposito.doubleValue());
	BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaConvertida);
	double umMaisTaxaLog10Double = Math.log10(umMaisTaxa.doubleValue());
	
	if(Double.isFinite(vlrResgatadoMultTxAddDepositoDivideDepositoLog10Double)) {
	    vlrResgatadoMultTxAddDepositoDivideDepositoLog10 = BigDecimal.valueOf(vlrResgatadoMultTxAddDepositoDivideDepositoLog10Double);
	} else {
	    vlrResgatadoMultTxAddDepositoDivideDepositoLog10 = BigDecimalMath.log(vlrResgatadoMultTxAddDepositoDivideDeposito);
	}
	
	if(Double.isFinite(umMaisTaxaLog10Double)) {
	    umMaisTaxaLog10 = BigDecimal.valueOf(umMaisTaxaLog10Double);
	} else {
	    umMaisTaxaLog10 = BigDecimalMath.log(umMaisTaxa);
	}
	
	resultadoCalcSeriePg.setResultado(
	   vlrResgatadoMultTxAddDepositoDivideDepositoLog10.divide(umMaisTaxaLog10, MathCommons.MATH_CONTEXT_100).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	umMaisTaxaLog10 = vlrResgatadoMultTxAddDepositoDivideDepositoLog10 = vlrDeposito = taxaConvertida = vlrResgatadoMultTxAddDepositoDivideDeposito = umMaisTaxa = null;
	depositoDTO = null;
	
	return resultadoCalcSeriePg;
    }
}