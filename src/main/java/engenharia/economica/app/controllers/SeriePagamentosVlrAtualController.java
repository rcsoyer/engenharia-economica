package engenharia.economica.app.controllers;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import engenharia.economica.app.dto.DadosCalcSeriePgVlrAtualDTO;
import engenharia.economica.app.dto.ResultadoCalcSeriePgDTO;
import engenharia.economica.app.services.SeriePagamentosVlrAtualService;

@RestController
@RequestMapping("seriePgVlrAtual")
public class SeriePagamentosVlrAtualController {
    
    Logger LOGGER = Logger.getLogger(SeriePagamentosVlrAtualController.class);

    @Autowired
    private SeriePagamentosVlrAtualService seriePagamentosVlrAtualService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public ResultadoCalcSeriePgDTO calcularJuros(Principal principal, @RequestBody DadosCalcSeriePgVlrAtualDTO dadosCalcSeriePgVlrAtualDTO) {
	ResultadoCalcSeriePgDTO resultado = null;
	
	switch (dadosCalcSeriePgVlrAtualDTO.getVrDescobrir()) {
	    case "VA": {
		resultado = this.seriePagamentosVlrAtualService.calcularVlrAtual(dadosCalcSeriePgVlrAtualDTO);
		break;
	    }
	    case "QP": {
		resultado = this.seriePagamentosVlrAtualService.calcularQtdPrestacoes(dadosCalcSeriePgVlrAtualDTO);
		break;
	    }
	    case "VP": {
		resultado = this.seriePagamentosVlrAtualService.calcularVlrPrestacao(dadosCalcSeriePgVlrAtualDTO);
		break;
	    }
	}
	
	return resultado;
    }
}