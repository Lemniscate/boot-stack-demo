angular.module( window.globals.module + '.example', [
          window.globals.module
        , 'ui.calendar'
        , 'ui.map'
    ])
    .config(function($stateProvider){
        $stateProvider
            .state('example',{
                url: '/example',
                templateUrl: window.globals.jsBaseUrl + '/example/example.tpl.html',
                controller: 'ExampleController'
            })
        ;
    })
    .controller('ExampleController', function($scope, $timeout, $compile, $templateCache, BASE_URL){
        console.log('We made it!', BASE_URL);
    });