angular.module('engenhariaEconomicaApp', ['frontendServices', 'spring-security-csrf-token-interceptor'])
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
            
            $scope.loadJurosOnline = function () {
            	window.location.replace('/resources/pages/juros-online.html');
            }

            $scope.logout = function () {
                UserService.logout();
            }
        }]);