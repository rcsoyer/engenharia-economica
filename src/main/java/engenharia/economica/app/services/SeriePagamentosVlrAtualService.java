package engenharia.economica.app.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import engenharia.economica.app.dto.DadosCalcSeriePgVlrAtualDTO;
import engenharia.economica.app.dto.PrestacaoDTO;
import engenharia.economica.app.dto.ResultadoCalcSeriePgDTO;
import engenharia.economica.app.math.BigDecimalMath;
import engenharia.economica.app.math.MathCommons;

@Service
public class SeriePagamentosVlrAtualService {
    
    public ResultadoCalcSeriePgDTO calcularVlrAtual(DadosCalcSeriePgVlrAtualDTO dadosCalcSeriePgVlrAtualDTO) {
	ResultadoCalcSeriePgDTO resultadoCalcSeriePg = new ResultadoCalcSeriePgDTO();
	PrestacaoDTO prestacaoDTO = dadosCalcSeriePgVlrAtualDTO.getPrestacaoDTO();
	BigDecimal taxaConvertida = MathCommons.converterTemposMesmaProporcaoBaseTaxa(dadosCalcSeriePgVlrAtualDTO.getTaxaDTO(), prestacaoDTO.getTipoTempoPrestacoes());
	BigDecimal umMaisTxElevadoQtdPrestacoes = calcularUmMaisTxElevadoQtdPrestacoes(dadosCalcSeriePgVlrAtualDTO, taxaConvertida);
	
	resultadoCalcSeriePg.setResultado(prestacaoDTO.getVlrPrestacao()
	  .multiply(umMaisTxElevadoQtdPrestacoes.subtract(BigDecimal.ONE))
	  .divide(umMaisTxElevadoQtdPrestacoes.multiply(taxaConvertida), MathCommons.MATH_CONTEXT_100000).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	prestacaoDTO = null;
	taxaConvertida = umMaisTxElevadoQtdPrestacoes = null;
	
	return resultadoCalcSeriePg;
    }
    
    public ResultadoCalcSeriePgDTO calcularVlrPrestacao(DadosCalcSeriePgVlrAtualDTO dadosCalcSeriePgVlrAtualDTO) {
	ResultadoCalcSeriePgDTO resultadoCalcSeriePg = new ResultadoCalcSeriePgDTO();
	BigDecimal taxaConvertida = 
	 MathCommons.converterTemposMesmaProporcaoBaseTaxa(dadosCalcSeriePgVlrAtualDTO.getTaxaDTO(), dadosCalcSeriePgVlrAtualDTO.getPrestacaoDTO().getTipoTempoPrestacoes());
	BigDecimal umMaisTxElevadoQtdPrestacoes = calcularUmMaisTxElevadoQtdPrestacoes(dadosCalcSeriePgVlrAtualDTO, taxaConvertida);
	
	resultadoCalcSeriePg.setResultado(dadosCalcSeriePgVlrAtualDTO.getVlrAtual().multiply(umMaisTxElevadoQtdPrestacoes.multiply(taxaConvertida))
		.divide(umMaisTxElevadoQtdPrestacoes.subtract(BigDecimal.ONE), MathCommons.MATH_CONTEXT_100000).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	umMaisTxElevadoQtdPrestacoes = taxaConvertida = null;
	
	return resultadoCalcSeriePg;
    }
    
    private BigDecimal calcularUmMaisTxElevadoQtdPrestacoes(DadosCalcSeriePgVlrAtualDTO dadosCalcSeriePgVlrAtualDTO, BigDecimal taxaConvertida) {
	BigDecimal umMaisTxElevadoQtdPrestacoes;
	BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaConvertida);
	BigDecimal qtdPrestacoes = dadosCalcSeriePgVlrAtualDTO.getQtdPrestacoes();
	double umMaisTxElevadoQtdPrestacoesDouble = Math.pow(umMaisTaxa.doubleValue(), qtdPrestacoes.doubleValue());
	
	if(Double.isFinite(umMaisTxElevadoQtdPrestacoesDouble)) {
	    umMaisTxElevadoQtdPrestacoes = BigDecimal.valueOf(umMaisTxElevadoQtdPrestacoesDouble);
	} else {
	    umMaisTxElevadoQtdPrestacoes = BigDecimalMath.pow(umMaisTaxa, qtdPrestacoes);
	}
	
	umMaisTaxa = qtdPrestacoes = null;
	
	return umMaisTxElevadoQtdPrestacoes;
    }
    
    public ResultadoCalcSeriePgDTO calcularQtdPrestacoes(DadosCalcSeriePgVlrAtualDTO dadosCalcSeriePgVlrAtualDTO) {
	BigDecimal umMaisTaxaLog10;
	BigDecimal vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10;
	ResultadoCalcSeriePgDTO resultadoCalcSeriePg = new ResultadoCalcSeriePgDTO();
	PrestacaoDTO prestacaoDTO = dadosCalcSeriePgVlrAtualDTO.getPrestacaoDTO();
	BigDecimal vlrPrestacao = prestacaoDTO.getVlrPrestacao();
	BigDecimal taxaConvertida = MathCommons.converterTemposMesmaProporcaoBaseTaxa(dadosCalcSeriePgVlrAtualDTO.getTaxaDTO(), prestacaoDTO.getTipoTempoPrestacoes());
	BigDecimal vlrPrestacaoDividePrestacaoSubAtualMutlTaxa =
		vlrPrestacao.divide(vlrPrestacao.subtract(dadosCalcSeriePgVlrAtualDTO.getVlrAtual().multiply(taxaConvertida)), MathCommons.MATH_CONTEXT_100000);
	double vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10Double = Math.log10(vlrPrestacaoDividePrestacaoSubAtualMutlTaxa.doubleValue());
	BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaConvertida);
	double umMaisTaxaLog10Double = Math.log10(umMaisTaxa.doubleValue());
	
	if(Double.isFinite(vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10Double)) {
	    vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10 = BigDecimal.valueOf(vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10Double);
	} else {
	    vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10 = BigDecimalMath.log(vlrPrestacaoDividePrestacaoSubAtualMutlTaxa);
	}
	
	if(Double.isFinite(umMaisTaxaLog10Double)) {
	    umMaisTaxaLog10 = BigDecimal.valueOf(umMaisTaxaLog10Double);
	} else {
	    umMaisTaxaLog10 = BigDecimalMath.log(umMaisTaxa);
	}
	
	resultadoCalcSeriePg.setResultado(
	  vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10.divide(umMaisTaxaLog10, MathCommons.MATH_CONTEXT_100000).setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString());
	
	umMaisTaxaLog10 = vlrPrestacaoDividePrestacaoSubAtualMutlTaxaLog10 = vlrPrestacao = taxaConvertida = umMaisTaxa = vlrPrestacaoDividePrestacaoSubAtualMutlTaxa = null;
	prestacaoDTO = null;
	
	return resultadoCalcSeriePg;
    }
}
