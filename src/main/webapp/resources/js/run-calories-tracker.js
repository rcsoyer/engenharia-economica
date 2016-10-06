///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////

require.config({
    paths: {
        angular: '../bower_components/angular/angular',
        angularMessages: '../bower_components/angular-messages/angular-messages',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: '../bower_components/lodash/dist/lodash',
        jQuery: '../bower_components/jquery/jquery-1.10.2.min', 
        maskMoney : '../bower_components/jquery-maskmoney-master/dist/jquery.maskMoney.min',
        datetimepicker: './datetimepicker/jquery.datetimepicker',
        editableTableWidgets: '../public/js/editable-table-widgets',
        frontendServices: 'frontend-services',
        caloriesCounterApp: 'calories-counter-app'
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
        caloriesCounterApp: {
            deps: [ 'lodash', 'angular', 'angularMessages', 'editableTableWidgets', 'frontendServices', 'jQuery', 'maskMoney' ]
        }
    }
});

require(['caloriesCounterApp'], function () {

    angular.bootstrap(document.getElementById('caloriesCounterApp'), ['caloriesCounterApp']);

});