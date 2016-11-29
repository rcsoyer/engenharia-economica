angular.module('seriePgVlrAtualService', [])
    .service('SeriePgVlrAtualService', ['$http', '$q', function($http, $q) {
        return {
        	calcularSeriePagamentosVlrAtual : function(dadosCalcSeriePgVlrAtual) {
	        	var deferred = $q.defer();
	
	            $http.post('/seriePgVlrAtual', dadosCalcSeriePgVlrAtual)
	            	.then(function (response) {
	                    if (response.status == 200) {
	                        return deferred.resolve(response.data);
	                    }
	                    else {
	                        deferred.reject('Erro calculando Série de Pagamentos Valor Atual');
	                    }
	                }
	           );
	
	            return deferred.promise;
	        }
      }
    }]);