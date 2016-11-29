angular.module('engenhariaEconomicaApp', ['userService', 'spring-security-csrf-token-interceptor'])
	.controller('EngenhariaEconomicaCtrl', ['$scope' , 'UserService', '$timeout',
        function ($scope, UserService, $timeout) {
            $scope.vm = {};
            updateUserInfo();
            
            function showErrorMessage(errorMessage) {
                clearMessages();
                $scope.vm.errorMessages.push({description: errorMessage});
            }

            function updateUserInfo() {
                UserService.getUserInfo()
                    .then(function (userInfo) {
                        $scope.vm.userName = userInfo.userName;
                        markAppAsInitialized();
                    },
                    function (errorMessage) {
                        showErrorMessage(errorMessage);
                    });
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
            
            $scope.loadJurosApp = function () {
            	window.location.replace('/resources/pages/juros-online.html');
            }
            
            $scope.loadSeriePagamentosVlrFuturoApp = function () {
            	window.location.replace('/resources/pages/serie-pagamentos-valor-futuro.html');
            }
            
            $scope.loadDescontosApp = function () {
            	window.location.replace('/resources/pages/descontos.html');
            }
            
            $scope.loadSeriePagamentosVlrAtualApp = function () {
            	window.location.replace('/resources/pages/serie-pagamentos-valor-atual.html');
            }
            
            $scope.logout = function () {
                UserService.logout();
            }
        }]);