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


//# sourceURL=economiaCommons.js