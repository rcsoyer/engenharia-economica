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

import engenharia.economica.app.dto.DadosCalcSeriePgVlrFuturoDTO;
import engenharia.economica.app.dto.ResultadoCalcSeriePgDTO;
import engenharia.economica.app.services.SeriePagamentosVlrFuturoService;

@RestController
@RequestMapping("seriePgVlrFuturo")
public class SeriePagamentosVlrFuturoController {
    
    Logger LOGGER = Logger.getLogger(SeriePagamentosVlrFuturoController.class);

    @Autowired
    private SeriePagamentosVlrFuturoService seriePagamentosVlrFuturoService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public ResultadoCalcSeriePgDTO calcularJuros(Principal principal, @RequestBody DadosCalcSeriePgVlrFuturoDTO dadosCalcSeriePgVlrFuturoDTO) {
	ResultadoCalcSeriePgDTO resultado = null;
	
	switch (dadosCalcSeriePgVlrFuturoDTO.getVrDescobrir()) {
	    case "VR": {
		resultado = this.seriePagamentosVlrFuturoService.calcularVlrResgatado(dadosCalcSeriePgVlrFuturoDTO);
		break;
	    }
	    case "QD": {
		resultado = this.seriePagamentosVlrFuturoService.calcularQtdDepositos(dadosCalcSeriePgVlrFuturoDTO);
		break;
	    }
	    case "VD": {
		resultado = this.seriePagamentosVlrFuturoService.calcularVlrDeposito(dadosCalcSeriePgVlrFuturoDTO);
		break;
	    }
	}
	
	return resultado;
    }
}