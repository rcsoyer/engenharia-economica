angular.module('descontosService', [])
    .service('DescontosService', ['$http', '$q', function($http, $q) {
        return {
        	calcularDescontos : function(dadosCalcDescontos) {
	        	var deferred = $q.defer();
	
	            $http.post('/descontos', dadosCalcDescontos)
	            	.then(function (response) {
	                    if (response.status == 200) {
	                        return deferred.resolve(response.data);
	                    }
	                    else {
	                        deferred.reject('Erro calculando Descontos');
	                    }
	                }
	           );
	
	            return deferred.promise;
	        }
      }
    }]);