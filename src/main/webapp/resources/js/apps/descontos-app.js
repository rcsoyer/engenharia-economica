angular.module('descontosApp', ['userService', 'descontosService', 'spring-security-csrf-token-interceptor'])
    .controller('DescontosCtrl', ['$scope' , 'UserService', 'DescontosService', '$timeout',
        function ($scope, UserService, DescontosService, $timeout) {
    	
    		$scope.vm = {
                errorMessages: [],
                infoMessages: [],
                submitted: false,
                errorMessages: []
            };
    	
    		inicializarObjDadosCalcDescontos();
    		inicializarObjResultadoCalcDescontos();
    	
            function inicializarObjDadosCalcDescontos() {
            	$scope.dadosCalcDescontos = {
        			tipoDescontos : '',
        			vlrTitulo : '',
        			taxaDTO : {
        				vlrTaxa : '',
        				tipoTempoTaxa : 'M'
        			},
        			periodoDTO : {
        				vlrPeriodo : '',
        				tipoTempoPeriodo : 'M'
        			}
            	};
            }
            
            function inicializarObjResultadoCalcDescontos() {
            	$scope.resultadoCalcDescontos = {
        			vlrDescontado : '',
        			vlrCreditado : ''
            	};
            }

            function showErrorMessage(errorMessage) {
                clearMessages();
                $scope.vm.errorMessages.push({description: errorMessage});
            }

            function markAppAsInitialized() {
                if ($scope.vm.appReady == undefined) {
                    $scope.vm.appReady = true;
                }
            }

            function clearMessages() {
                $scope.vm.errorMessages = [];
                $scope.vm.infoMessages = [];
            }

            function showInfoMessage(infoMessage) {
                $scope.vm.infoMessages = [];
                $scope.vm.infoMessages.push({description: infoMessage});
                $timeout(function () {
                    $scope.vm.infoMessages = [];
                }, 1000);
            }
            
            function mascararMoedaResultadoCalculos(resultadoCalcDescontos) {
            	var vlrDescontadoResultado = resultadoCalcDescontos.vlrDescontado;
            	var vlrCreditadoResultado = resultadoCalcDescontos.vlrCreditado;
            	var vlrDescontadoMascarado = $economia.fn.mascararMoeda(vlrDescontadoResultado);
            	var vlrCreditadoMascarado = $economia.fn.mascararMoeda(vlrCreditadoResultado);
            	
            	if(_.isEmpty(vlrDescontadoMascarado) || _.isEmpty(vlrCreditadoMascarado)) {
            		$scope.resultadoCalcDescontos.vlrDescontado = 'R$ ' + vlrDescontadoResultado;
            		$scope.resultadoCalcDescontos.vlrCreditado = 'R$ ' + vlrCreditadoResultado;
            	} else {
            		$scope.resultadoCalcDescontos.vlrDescontado = 'R$ ' + vlrDescontadoMascarado;
            		$scope.resultadoCalcDescontos.vlrCreditado = 'R$ ' + vlrCreditadoMascarado;
            	}
            }
            
            $scope.calcularDescontos = function ($event) {
            	$event.preventDefault();
            	$scope.vm.submitted = true;

            	if ($scope.formDescontos.$invalid) {
                    return;
                }
                
                var scopeDadosCalcDescontos = $scope.dadosCalcDescontos;
                var taxaDTO = scopeDadosCalcDescontos.taxaDTO;
                var periodoDTO = scopeDadosCalcDescontos.periodoDTO;
                
	            var dadosCalcDescontos = {
	        		tipoDescontos : scopeDadosCalcDescontos.tipoDescontos,
	    			vlrTitulo : scopeDadosCalcDescontos.vlrTitulo.replace(/\.+/g, '').replace(/\,/, '.'),
	    			taxaDTO : {
	    				vlrTaxa : taxaDTO.vlrTaxa.replace(/\.+/g, '').replace(/\,/, '.'),
	    				tipoTempoTaxa : taxaDTO.tipoTempoTaxa
	    			},
	    			periodoDTO : {
	    				vlrPeriodo : periodoDTO.vlrPeriodo,
	    				tipoTempoPeriodo : periodoDTO.tipoTempoPeriodo
	    			}
	            };
	
	            DescontosService.calcularDescontos(dadosCalcDescontos)
	               .then(function (dataResultadoCalc) {
	            	    mascararMoedaResultadoCalculos(dataResultadoCalc);
	                },
	                function (errorMessage) {
	                    showErrorMessage(errorMessage);
	                }
	            );
            };

            $scope.logout = function () {
                UserService.logout();
            };
            
            $scope.sairDaTela = function () {
            	$economia.fn.voltarParaTelaInicial();
            };
            
            $scope.limparDadosInformados = function () {
            	inicializarObjDadosCalcDescontos();
            	inicializarObjResultadoCalcDescontos();
            	$scope.formDescontos.vlrTaxa.$setValidity('vlrMaxTaxaInv', true);
            	$('#btnDropTempTaxa, #btnDropTemp').html('M&ecirc;s' + ' <span class="caret"></span>');
            	$scope.vm.submitted = false;
            };
        }])
        .directive('maskMoney', $economia.fn.maskMoney)
        .directive('maskInteiro', $economia.fn.maskInteiro)
		.directive('trocarTextTempoTaxa', $economia.fn.trocarTextTipoTempoTaxa)
		.directive('trocarTextTempo', $economia.fn.trocarTextTipoTempo)
		.directive('maskTaxaMax', function () {
					    return {
					    	restrict: 'A',
					    	link: function (scope, element, attrs) {
					   		  element.on('keyup', function() {
					   			var vlr = element.val();
					   			
					   			if(Number(vlr.replace(/\.+/g, '').replace(/\,/, '.')) <= Number('100.00')) {
					   				scope.formDescontos.vlrTaxa.$setValidity('vlrMaxTaxaInv', true);
					   			} else {
					   				scope.formDescontos.vlrTaxa.$setValidity('vlrMaxTaxaInv', false);
					   			}
					   		  });
					        }
					    }
					}
		);