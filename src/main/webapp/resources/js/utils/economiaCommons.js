$economia = {};
$economia.fn = {};


$economia.fn.mascararMoeda = function (valor) {
	if($.isNumeric(valor)) {
		var valorStr = new String(valor);
		return valorStr.replace(/\D/g, '').replace(/(\d{1})(\d{17})$/, '$1.$2').replace(/(\d{1})(\d{14})$/, '$1.$2').replace(/(\d{1})(\d{11})$/, '$1.$2')
				.replace(/(\d{1})(\d{8})$/, '$1.$2').replace(/(\d{1})(\d{5})$/, '$1.$2').replace(/(\d{1})(\d{1,2})$/, '$1,$2');
	}
	
	return '';
};

$economia.fn.voltarParaTelaInicial = function () {
	window.location.replace('/resources/pages/engenharia-economica-index.html');
};

$economia.fn.maskMoney = function () {
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
};

$economia.fn.maskInteiro = function () {
    return {
    	restrict: 'A',
    	scope: {
    	   ngModel: '='
    	},
    	link: function (scope, element, attrs) {
   		  $(element).maskMoney({allowNegative: false, thousands: '', decimal: '', affixesStay: false});
   		  
   		  element.on('keyup', function() {
   			var vlr = element.val();
   			
   			if(!_.isEqual(vlr, '000')) {
   				scope.ngModel = vlr;
   			} else {
   				scope.ngModel = '';
   			}
   		  });
        }
    }
};

$economia.fn.trocarTextTipoTempoTaxa = function () {
    return {
    	restrict: 'AE',
    	link: function (scope, element, attrs) {
    	  element.on('click', function(evt) {
   			evt.preventDefault();
   			$('#btnDropTempTaxa').html($(this).text() + ' <span class="caret"></span>');
		  });
	    }
    }
};

$economia.fn.trocarTextTipoTempo = function () {
    return {
    	restrict: 'AE',
    	link: function (scope, element, attrs) {
    	  element.on('click', function(evt) {
   			evt.preventDefault();
   			$('#btnDropTemp').html($(this).text() + ' <span class="caret"></span>');
		  });
	    }
    }
};


//# sourceURL=economiaCommons.js