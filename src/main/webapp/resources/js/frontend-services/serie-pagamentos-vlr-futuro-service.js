angular.module('seriePgVlrFuturoService', [])
    .service('SeriePgVlrFuturoService', ['$http', '$q', function($http, $q) {
        return {
        	calcularSeriePgVlrFuturo : function(dadosCalcSeriePg) {
	        	var deferred = $q.defer();
	
	            $http.post('/seriePgVlrFuturo', dadosCalcSeriePg)
	            	.then(function (response) {
	                    if (response.status == 200) {
	                        return deferred.resolve(response.data);
	                    }
	                    else {
	                        deferred.reject('Erro calculando Série de Pagamentos Valor Futuro');
	                    }
	                }
	           );
	
	            return deferred.promise;
	        }
      }
    }]);