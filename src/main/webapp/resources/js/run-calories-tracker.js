///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////

require.config({
    paths: {
        angular: '../bower_components/angular/angular.min',
        angularMessages: '../bower_components/angular-messages/angular-messages.min',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: '../bower_components/lodash/dist/lodash.min',
        jQuery: '../bower_components/jquery/jquery-1.10.2.min', 
        maskMoney : '../bower_components/jquery-maskmoney-master/dist/jquery.maskMoney.min',
        datetimepicker: './datetimepicker/jquery.datetimepicker',
        editableTableWidgets: '../public/js/editable-table-widgets',
        frontendServices: 'frontend-services',
        caloriesCounterApp: 'calories-counter-app',
        bootstrap: '../bower_components/bootstrap-3.3.7-dist/js/bootstrap.min'
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
        datetimepicker: {
            deps: ['jQuery']
        },
        angularMessages: {
            deps: ['angular']
        },
        editableTableWidgets: {
            deps: ['angular', 'lodash', 'datetimepicker', 'jQuery']
        },
        frontendServices: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        maskMoney : {
        	deps: ['jQuery'],
            exports: 'jQuery.fn.maskMoney'
        },
        bootstrap : {
        	deps : ['jQuery']
        },
        caloriesCounterApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'editableTableWidgets', 'frontendServices', 'maskMoney']
        }
    }
});

require(['caloriesCounterApp'], function () {

    angular.bootstrap(document.getElementById('caloriesCounterApp'), ['caloriesCounterApp']);

});