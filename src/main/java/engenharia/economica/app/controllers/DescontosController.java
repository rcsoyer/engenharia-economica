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

import engenharia.economica.app.dto.DadosCalcDescontosDTO;
import engenharia.economica.app.dto.ResultadoCalcDescontosDTO;
import engenharia.economica.app.services.DescontosService;

@RestController
@RequestMapping("descontos")
public class DescontosController {
    
    Logger LOGGER = Logger.getLogger(JurosController.class);
    
    @Autowired
    private DescontosService descontosService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public ResultadoCalcDescontosDTO calcularDescontos(Principal principal, @RequestBody DadosCalcDescontosDTO dadosCalcDescontosDTO) {
	if ("S".equals(dadosCalcDescontosDTO.getTipoDescontos())) {
	    return this.descontosService.calcularResultadoDescontosSimples(dadosCalcDescontosDTO);
	}
	
	return this.descontosService.calcularResultadoDescontosCompostos(dadosCalcDescontosDTO);
    }
}
