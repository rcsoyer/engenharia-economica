require.config({
    paths: {
        angular: '/resources/bower_components/angular/angular.min',
        angularMessages: '/resources/bower_components/angular-messages/angular-messages.min',
        csrfInterceptor: '/resources/bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: '/resources/bower_components/lodash/dist/lodash.min',
        jQuery: '/resources/bower_components/jquery/jquery-3.1.1.min', 
        maskMoney : '/resources/bower_components/jquery-maskmoney-master/dist/jquery.maskMoney.min',
        userService: '/resources/js/frontend-services/user-service',
        descontosApp: '/resources/js/apps/descontos-app',
        descontosService: '/resources/js/frontend-services/descontos-service',
        bootstrap: '/resources/bower_components/bootstrap-3.3.7-dist/js/bootstrap.min',
        economiaCommons : '/resources/js/utils/economiaCommons'
    },
    shim: {
        jQuery: {
            exports: 'jQuery'
        },
        angular: {
            exports: 'angular'
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            deps: ['angular']
        },
        userService: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        descontosService: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        maskMoney : {
        	deps: ['jQuery'],
            exports: 'jQuery.fn.maskMoney'
        },
        bootstrap : {
        	deps : ['jQuery']
        },
        economiaCommons : {
        	deps : ['jQuery']
        },
        descontosApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'userService', 'descontosService', 'maskMoney', 'bootstrap', 'economiaCommons' ]
        }
    }
});

require(['descontosApp'], function () {
    angular.bootstrap(document.getElementById('descontosApp'), ['descontosApp']);
});