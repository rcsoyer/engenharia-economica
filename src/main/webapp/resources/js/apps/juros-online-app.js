angular.module('jurosOnlineApp', ['userService', 'jurosService', 'spring-security-csrf-token-interceptor'])
    .controller('JurosOnlineCtrl', ['$scope' , 'UserService', 'JurosService', '$timeout',
        function ($scope, UserService, JurosService, $timeout) {
    	
    		$scope.vm = {
                errorMessages: [],
                infoMessages: [],
                submitted: false,
                errorMessages: []
            };
    	
    		inicializarObjDadosCalcJuros();
    		inicializarObjResultadoCalcJuros();
    	
            function inicializarObjDadosCalcJuros() {
            	$scope.dadosCalcJuros = {
        			tipoJuros : '',
        			capitalInicial : '',
        			taxaJurosDTO : {
        				vlrTaxa : '',
        				tipoTempoTaxa : 'M'
        			},
        			tempoEmprestDTO : {
        				vlrPeriodo : '',
        				tipoTempoPeriodo : 'M'
        			}
            	};
            }
            
            function inicializarObjResultadoCalcJuros() {
            	$scope.resultadoCalcJuros = {
        			juros : '',
        			montante : ''
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
            
            function mascararMoedaResultadoCalculos(resultadoCalcJuros) {
            	var jurosResultado = resultadoCalcJuros.juros;
            	var montanteResultado = resultadoCalcJuros.montante;
            	var jurosMascarado = $economia.fn.mascararMoeda(jurosResultado);
            	var montanteMascarado = $economia.fn.mascararMoeda(montanteResultado);
            	
            	if(_.isEmpty(jurosMascarado) || _.isEmpty(montanteMascarado)) {
            		$scope.resultadoCalcJuros.juros = 'R$ ' + jurosResultado;
            		$scope.resultadoCalcJuros.montante = 'R$ ' + montanteResultado;
            	} else {
            		$scope.resultadoCalcJuros.juros = 'R$ ' + jurosMascarado;
            		$scope.resultadoCalcJuros.montante = 'R$ ' + montanteMascarado;
            	}
            }
            
            $scope.calcularJuros = function ($event) {
            	$event.preventDefault();
            	$scope.vm.submitted = true;

            	if ($scope.formJuros.$invalid) {
                    return;
                }
                
                var scopeDadosCalcJuros = $scope.dadosCalcJuros;
                
	            var dadosCalcJuros = {
	        		tipoJuros : scopeDadosCalcJuros.tipoJuros,
	    			capitalInicial : scopeDadosCalcJuros.capitalInicial.replace(/\.+/g, '').replace(/\,/, '.'),
	    			taxaJurosDTO : {
	    				vlrTaxa : scopeDadosCalcJuros.taxaJurosDTO.vlrTaxa.replace(/\.+/g, '').replace(/\,/, '.'),
	    				tipoTempoTaxa : scopeDadosCalcJuros.taxaJurosDTO.tipoTempoTaxa
	    			},
	    			tempoEmprestDTO : {
	    				vlrPeriodo : scopeDadosCalcJuros.tempoEmprestDTO.vlrPeriodo,
	    				tipoTempoPeriodo : scopeDadosCalcJuros.tempoEmprestDTO.tipoTempoPeriodo
	    			}
	            };
	
	            JurosService.calcularJuros(dadosCalcJuros)
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
            	inicializarObjDadosCalcJuros();
            	inicializarObjResultadoCalcJuros();
            	$('#btnDropTempTaxa, #btnDropTemp').html('M&ecirc;s' + ' <span class="caret"></span>');
            	$scope.vm.submitted = false;
            };
        }])
        .directive('maskMoney', $economia.fn.maskMoney)
        .directive('maskInteiro', $economia.fn.maskInteiro)
        .directive('trocarTextTempoTaxa', $economia.fn.trocarTextTipoTempoTaxa)
		.directive('trocarTextTempo', $economia.fn.trocarTextTipoTempo);