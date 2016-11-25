angular.module('seriePagamentosVlrFuturoService', [])
    .service('SeriePagamentosVlrFuturoService', ['$http', '$q', function($http, $q) {
        return {
	        calcularSeriePagamentosVlrFuturo : function(dadosSeriePg) {
	        	var deferred = $q.defer();
	
	            $http.post('/seriePagamentosVlrFuturo', dadosSeriePg)
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