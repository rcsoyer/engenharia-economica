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
import engenharia.economica.app.services.SeriePagamentosVlrFuturoService;

@RestController
@RequestMapping("seriePagamentosVlrFuturo")
public class SeriePagamentosVlrFuturoController {
    
    Logger LOGGER = Logger.getLogger(SeriePagamentosVlrFuturoController.class);
    
    @Autowired
    private SeriePagamentosVlrFuturoService seriePagamentosVlrFuturoService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public ResultadoCalcJurosDTO calcularJuros(Principal principal, @RequestBody DadosCalcJurosDTO dadosCalcJuros) {
	return null;
    }
}