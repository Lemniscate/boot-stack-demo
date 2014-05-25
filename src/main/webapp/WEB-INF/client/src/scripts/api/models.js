var module = angular.module(window.globals.module + '.models', ['apiResource'])
    .factory('Users', function(ApiResource) {
        return new ApiResource('/api/users');
    })
    .factory('Organizations', function(ApiResource) {
        return new ApiResource('/api/organizations');
    });