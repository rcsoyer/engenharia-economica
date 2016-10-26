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

import engenharia.economica.app.dto.DadosCalcJurosDTO;
import engenharia.economica.app.dto.ResultadoCalcJurosDTO;
import engenharia.economica.app.services.JurosService;

@RestController
@RequestMapping("juros")
public class JurosController {
    
    Logger LOGGER = Logger.getLogger(JurosController.class);
    
    @Autowired
    private JurosService jurosService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value="/calcularJuros", method = RequestMethod.POST)
    public ResultadoCalcJurosDTO calcularJuros(Principal principal, @RequestBody DadosCalcJurosDTO dadosCalcJuros) {
	if ("S".equals(dadosCalcJuros.getTipoJuros())) {
	    return this.jurosService.calcularResultadoJurosSimples(dadosCalcJuros);
	}
	
	return this.jurosService.calcularResultadoJurosCompostos(dadosCalcJuros);
    }
}
