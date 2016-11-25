angular.module('seriePagamentosVlrFuturoApp', ['userService', 'seriePagamentosVlrFuturoService', 'spring-security-csrf-token-interceptor'])
    .controller('SeriePagamentosCtrl', ['$scope' , 'UserService', 'SeriePagamentosVlrFuturoService', '$timeout',
        function ($scope, UserService, SeriePagamentosService, $timeout) {
    	
    		$scope.vm = {
                errorMessages: [],
                infoMessages: [],
                submitted: false,
                errorMessages: []
            };
    		
      		$('#tempo').find('li').find('a').click(function(evt) {
     			  evt.preventDefault();
      		  $('#btnDropTemp').html($(this).text() + ' <span class="caret"></span>');
  			});
    	
    		inicializarObjDadosSeriePagamentos();
    		inicializarObjResultadoCalcSeriePg();
    	
            function inicializarObjDadosSeriePagamentos() {
            	$scope.dadosSeriePg = {
            		vlrResgatado : '',
            		qtdDepositos : '',
            		vlrDeposito : '',
        			tempoEmprestDTO : {
        				vlrPeriodo : '',
        				tipoTempoPeriodo : 'M'
        			}
            	};
            }
            
            function inicializarObjResultadoCalcSeriePg() {
            	$scope.resultadoCalcSeriePg = {};
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
            
            $scope.calcularSeriePagamentosVlrFuturo = function ($event) {
            	$event.preventDefault();
            	$scope.vm.submitted = true;

            	if ($scope.formSeriePagamentos.$invalid) {
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
	
	            SeriePagamentosVlrFuturoService.calcularSeriePagamentosVlrFuturo(dadosCalcJuros)
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
            
            $scope.limparDadosInformados = function () {
            	inicializarObjDadosSeriePagamentos();
            	inicializarObjResultadoCalcSeriePg();
            	$scope.vm.submitted = false;
            };
        }])
        .directive('maskMoney', $economia.fn.maskMoney)
        .directive('maskInteiro', $economia.fn.maskInteiro);