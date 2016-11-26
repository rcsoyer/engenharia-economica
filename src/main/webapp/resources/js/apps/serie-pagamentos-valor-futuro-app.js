angular.module('seriePagamentosVlrFuturoApp', ['userService', 'seriePgVlrFuturoService', 'spring-security-csrf-token-interceptor'])
    .controller('SeriePagamentosVlrFuturoCtrl', ['$scope' , 'UserService', 'SeriePgVlrFuturoService', '$timeout',
        function ($scope, UserService, SeriePgVlrFuturoService, $timeout) {

    		inicializarObjDadosCalcSeriePgVlrFuturo();
    		inicializarObjResultadoCalcSeriePgVlrFuturo();
    		inicializarScopeVM();
    		
    		function inicializarScopeVM() {
    			$scope.vm = {
	                errorMessages: [],
	                infoMessages: [],
	                submitted: false,
	                errorMessages: [],
	                labelResultado : 'Valor a ser Resgatado'
	            };
            }
    	
            function inicializarObjDadosCalcSeriePgVlrFuturo() {
            	$scope.dadosCalcSeriePgVlrFuturo = {
            		vlrResgatado : '',
            		qtdDepositos : '',
            		vrDescobrir : 'VR',
            		depositoDTO : {
            			vlrDeposito : '',
            			tipoTempoDepositos : 'M'
            		},
            		taxaDTO : {
            			vlrTaxa : '',
            			tipoTempoTaxa : 'M'
        			}
            	};
            }
            
            function inicializarObjResultadoCalcSeriePgVlrFuturo() {
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
            
            $scope.calcularSeriePagamentosVlrFuturo = function ($event) {
            	$event.preventDefault();
            	$scope.vm.submitted = true;

            	if ($scope.formSeriePagamentosVlrFuturo.$invalid) {
                    return;
                }
                
            	var scopeDadosCalcSeriePgVlrFuturo = $scope.dadosCalcSeriePgVlrFuturo;
                var taxaDTO = scopeDadosCalcSeriePgVlrFuturo.taxaDTO;
                var depositoDTO = scopeDadosCalcSeriePgVlrFuturo.depositoDTO;
            	
	            var dadosCalcSeriePgVlrFuturo = {
            		vlrResgatado : scopeDadosCalcSeriePgVlrFuturo.vlrResgatado.replace(/\.+/g, '').replace(/\,/, '.'),
            		qtdDepositos : scopeDadosCalcSeriePgVlrFuturo.qtdDepositos,
            		vrDescobrir : scopeDadosCalcSeriePgVlrFuturo.vrDescobrir,
            		depositoDTO : {
            			vlrDeposito : depositoDTO.vlrDeposito.replace(/\.+/g, '').replace(/\,/, '.'),
            			tipoTempoDepositos : depositoDTO.tipoTempoDepositos
            		},
            		taxaDTO : {
            			vlrTaxa : taxaDTO.vlrTaxa.replace(/\.+/g, '').replace(/\,/, '.'),
            			tipoTempoTaxa : taxaDTO.tipoTempoTaxa
        			}
	            };
	
	            SeriePgVlrFuturoService.calcularSeriePgVlrFuturo(dadosCalcSeriePgVlrFuturo)
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
            	inicializarObjDadosCalcSeriePgVlrFuturo();
            	inicializarObjResultadoCalcSeriePgVlrFuturo();
            	inicializarScopeVM();
            	$scope.vm.submitted = false;
            };
            
            $scope.definirLabelResultado = function () {
            	switch($scope.dadosCalcSeriePgVlrFuturo.vrDescobrir) {
            		case 'VR' : {
            			$scope.vm.labelResultado = 'Valor a ser Resgatado';
            			$scope.dadosCalcSeriePgVlrFuturo.vlrResgatado = '';
            			break;
            		}
            		case 'QD' : {
            			$scope.vm.labelResultado = 'Quantidade de Depósitos';
            			$scope.dadosCalcSeriePgVlrFuturo.qtdDepositos = '';
            			break;
            		}
            		case 'VD' : {
            			$scope.vm.labelResultado = 'Valor do Depósito';
            			$scope.dadosCalcSeriePgVlrFuturo.depositoDTO.vlrDeposito = '';
            			break;
            		}
            	}
            };
        }])
        .directive('maskMoney', $economia.fn.maskMoney)
        .directive('maskInteiro', $economia.fn.maskInteiro)
        .directive('trocarTextTempoTaxa', $economia.fn.trocarTextTipoTempoTaxa)
		.directive('trocarTextTempoDeposit', $economia.fn.trocarTextTipoTempo);