angular.module('jurosOnlineApp', ['frontendServices', 'spring-security-csrf-token-interceptor'])
    .controller('JurosOnlineCtrl', ['$scope' , 'UserService', 'JurosService', '$timeout',
        function ($scope, MealService, UserService, JurosService, $timeout) {
    	
    		$('.money').maskMoney({allowNegative: false, thousands:'.', decimal:',', affixesStay: false});
    		
    		$('.dropdown-menu li a').click(function(evt) {
   			  evt.preventDefault();
    		  $(this).parents().find('.dropdown-toggle').html($(this).text() + ' <span class="caret"></span>');
			});
    		
    	
            $scope.vm = {
                errorMessages: [],
                infoMessages: [],
                submitted: false,
                errorMessages: []
            };
            
            $scope.dadosCalcJuros = {
            	tipoJuros : '',
            	capitalInicial : '',
            	txJuros : '',
            	tempo : ''
            };


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

            $scope.calcularJuros = function () {
                
                if ($scope.formJuros.$invalid) {
                	$scope.vm.submitted = true;
                    return;
                }

                JurosService.calcularJuros($scope.dadosCalcJuros)
	                .then(function (data) {
	                		$scope.resultadoCalcJuros = data;
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
            	$scope.dadosCalcJuros = {};
            	$scope.vm.submitted = false;
            };
        }]);