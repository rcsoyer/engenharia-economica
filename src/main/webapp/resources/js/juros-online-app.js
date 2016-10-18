angular.module('jurosOnlineApp', ['frontendServices', 'spring-security-csrf-token-interceptor'])
    .controller('JurosOnlineCtrl', ['$scope' , 'UserService', 'JurosService', '$timeout',
        function ($scope, UserService, JurosService, $timeout) {
    	
    		$('#tempoTxJuros').find('li').find('a').click(function(evt) {
    		  evt.preventDefault();
      		  $('#btnDropTempTxJur').html($(this).text() + ' <span class="caret"></span>');
  			});
    		
    		$('#tempoEmprestimo').find('li').find('a').click(function(evt) {
   			  evt.preventDefault();
    		  $('#btnDropTempEmp').html($(this).text() + ' <span class="caret"></span>');
			});
    		
    		inicializarObjDadosCalcJuros();
    		inicializarObjResultadoCalcJuros();
    	
            $scope.vm = {
                errorMessages: [],
                infoMessages: [],
                submitted: false,
                errorMessages: []
            };
            
            function inicializarObjDadosCalcJuros() {
            	$scope.dadosCalcJuros = {
        			tipoJuros : '',
        			capitalInicial : '',
        			taxaJurosDTO : {
        				vlrTxJuros : '',
        				tipoTempoTxJuros : 'M'
        			},
        			tempoEmprestDTO : {
        				tempoEmprest : '',
        				tipoTempoJuros : 'M'
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
	    				vlrTxJuros : scopeDadosCalcJuros.taxaJurosDTO.vlrTxJuros.replace(/\.+/g, '').replace(/\,/, '.'),
	    				tipoTempoTxJuros : scopeDadosCalcJuros.taxaJurosDTO.tipoTempoTxJuros
	    			},
	    			tempoEmprestDTO : {
	    				tempoEmprest : scopeDadosCalcJuros.tempoEmprestDTO.tempoEmprest.replace(/\.+/g, ''),
	    				tipoTempoJuros : scopeDadosCalcJuros.tempoEmprestDTO.tipoTempoJuros
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
            
            $scope.limparDadosInformados = function () {
            	inicializarObjDadosCalcJuros();
            	inicializarObjResultadoCalcJuros();
            	$('#btnDropTempTxJur, #btnDropTempEmp').html('M&ecirc;s' + ' <span class="caret"></span>');
            	$scope.vm.submitted = false;
            };
        }])
        .directive('maskMoney', function () {
            return {
            	restrict: 'A',
            	scope: {
            	   ngModel: '='
            	},
            	link: function (scope, element, attrs) {
           		  $(element).maskMoney({allowNegative: false, thousands: '.', decimal: ',', affixesStay: false});
           		  
           		  element.on('keyup', function() {
           			var vlr = element.val();
           			
           			if(!_.isEqual(vlr, '0,00')) {
           				scope.ngModel = vlr;
           			} else {
           				scope.ngModel = '';
           			}
           		  });
                }
            };
        })
        .directive('maskInteiro', function () {
            return {
            	restrict: 'A',
            	scope: {
            	   ngModel: '='
            	},
            	link: function (scope, element, attrs) {
           		  $(element).maskMoney({allowNegative: false, thousands: '.', affixesStay: false});
           		  
           		  element.on('keyup', function() {
           			var vlr = element.val();
           			
           			if(!_.isEqual(vlr, '0.00')) {
           				scope.ngModel = vlr;
           			} else {
           				scope.ngModel = '';
           			}
           		  });
                }
            };
        });