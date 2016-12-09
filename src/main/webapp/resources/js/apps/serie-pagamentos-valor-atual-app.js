angular.module('seriePagamentosVlrAtualApp', ['userService', 'seriePgVlrAtualService', 'spring-security-csrf-token-interceptor'])
    .controller('SeriePagamentosVlrAtualCtrl', ['$scope' , 'UserService', 'SeriePgVlrAtualService', '$timeout',
        function ($scope, UserService, SeriePgVlrAtualService, $timeout) {

    		inicializarObjDadosCalcSeriePgVlrAtual();
    		inicializarObjResultadoCalcSeriePgVlrAtual();
    		inicializarScopeVM();
    		
    		function inicializarScopeVM() {
    			$scope.vm = {
	                errorMessages: [],
	                infoMessages: [],
	                submitted: false,
	                errorMessages: [],
	                labelResultado : 'Valor Atual'
	            };
            }
    	
            function inicializarObjDadosCalcSeriePgVlrAtual() {
            	$scope.dadosCalcSeriePgVlrAtual = {
            		vlrAtual : '',
            		qtdPrestacoes : '',
            		vrDescobrir : 'VA',
            		prestacaoDTO : {
            			vlrPrestacao : '',
            			tipoTempoPrestacoes : 'M'
            		},
            		taxaDTO : {
            			vlrTaxa : '',
            			tipoTempoTaxa : 'M'
        			}
            	};
            }
            
            function inicializarObjResultadoCalcSeriePgVlrAtual() {
            	$scope.resultadoCalcSeriePg = {
            		resultado : ''
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
            
            function mascararMoedaResultadoCalculos(resultadoCalcSeriePg) {
            	var resultado = resultadoCalcSeriePg.resultado;
            	var resultadoMascarado = $economia.fn.mascararMoeda(resultado);
            	
            	if(_.isEmpty(resultadoMascarado)) {
            		$scope.resultadoCalcSeriePg.resultado = 'R$ ' + resultado;
            	} else {
            		$scope.resultadoCalcSeriePg.resultado = 'R$ ' + resultadoMascarado;
            	}
            }
            
            function obterObjParaEnvioRest() {
            	var scopeDadosCalcSeriePgVlrAtual = $scope.dadosCalcSeriePgVlrAtual;
                var taxaDTO = scopeDadosCalcSeriePgVlrAtual.taxaDTO;
                var prestacaoDTO = scopeDadosCalcSeriePgVlrAtual.prestacaoDTO;
            	
            	return {
            		vlrAtual : scopeDadosCalcSeriePgVlrAtual.vlrAtual.replace(/\.+/g, '').replace(/\,/, '.'),
            		qtdPrestacoes : scopeDadosCalcSeriePgVlrAtual.qtdPrestacoes,
            		vrDescobrir : scopeDadosCalcSeriePgVlrAtual.vrDescobrir,
            		prestacaoDTO : {
            			vlrPrestacao : prestacaoDTO.vlrPrestacao.replace(/\.+/g, '').replace(/\,/, '.'),
            			tipoTempoPrestacoes : prestacaoDTO.tipoTempoPrestacoes
            		},
            		taxaDTO : {
            			vlrTaxa : taxaDTO.vlrTaxa.replace(/\.+/g, '').replace(/\,/, '.'),
            			tipoTempoTaxa : taxaDTO.tipoTempoTaxa
        			}
	            };
            }
            
            function verificarTxVlrAtualMenorQValorPrestacao(dadosCalcSeriePgVlrAtual) {
            	var vlrAtual = dadosCalcSeriePgVlrAtual.vlrAtual;
            	var vlrTaxa = dadosCalcSeriePgVlrAtual.taxaDTO.vlrTaxa;
            	var vlrPrestacao = dadosCalcSeriePgVlrAtual.prestacaoDTO.vlrPrestacao;
            	
            	if(!_.isEmpty(vlrAtual) && !_.isEmpty(vlrTaxa) && !_.isEmpty(vlrPrestacao)) {
            		if(_.multiply(vlrAtual, vlrTaxa) < vlrPrestacao) {
            			$scope.formSeriePagamentosVlrAtual.vlrPrestacao.$setValidity('vlrMinimo', true);
            		} else {
            			$scope.formSeriePagamentosVlrAtual.vlrPrestacao.$setValidity('vlrMinimo', false);
            		}
            	}
            }
            
            $scope.calcularSeriePagamentosVlrAtual = function ($event) {
            	$event.preventDefault();
            	$scope.vm.submitted = true;
            	
            	var dadosCalcSeriePgVlrAtual = obterObjParaEnvioRest();
            	verificarTxVlrAtualMenorQValorPrestacao(dadosCalcSeriePgVlrAtual);

            	if ($scope.formSeriePagamentosVlrAtual.$invalid) {
                    return;
                }
	
	            SeriePgVlrAtualService.calcularSeriePagamentosVlrAtual(dadosCalcSeriePgVlrAtual)
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
            	inicializarObjDadosCalcSeriePgVlrAtual();
            	inicializarObjResultadoCalcSeriePgVlrAtual();
            	inicializarScopeVM();
            	$scope.formSeriePagamentosVlrAtual.vlrPrestacao.$setValidity('vlrMinimo', true);
            	$('#btnDropTempTaxa, #btnDropTemp').html('M&ecirc;s' + ' <span class="caret"></span>');
            };
            
            $scope.definirLabelResultado = function () {
            	var dadosCalcSeriePgVlrAtual = $scope.dadosCalcSeriePgVlrAtual;
            	
            	switch(dadosCalcSeriePgVlrAtual.vrDescobrir) {
            		case 'VA' : {
            			$scope.vm.labelResultado = 'Valor Atual';
            			dadosCalcSeriePgVlrAtual.vlrAtual = '';
            			break;
            		}
            		case 'QP' : {
            			$scope.vm.labelResultado = 'Quantidade de Prestações';
            			dadosCalcSeriePgVlrAtual.qtdPrestacoes = '';
            			break;
            		}
            		case 'VP' : {
            			$scope.vm.labelResultado = 'Valor da Prestação';
            			dadosCalcSeriePgVlrAtual.prestacaoDTO.vlrPrestacao = '';
            			break;
            		}
            	}
            };
        }])
        .directive('maskMoney', $economia.fn.maskMoney)
        .directive('maskInteiro', $economia.fn.maskInteiro)
        .directive('trocarTextTempoPrestacao', $economia.fn.trocarTextTipoTempo)
		.directive('trocarTextTempoTaxa', $economia.fn.trocarTextTipoTempoTaxa);