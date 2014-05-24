var module = angular.module(window.globals.module + '.ApiResource', []);
    module.config(function($httpProvider){
        $httpProvider.defaults.transformRequest.push(function(data, h) {
            var headers = h();

            // set the CSRF header
            if( window.globals && window.globals.csrf ){
                headers[window.globals.csrf.header] = window.globals.csrf.token;
            }

            // set the data type to json
            headers['Accept'] = 'application/json';
            headers['Content-Type'] ='application/json';

            return data;
        });
    })

    /**
     *  Provides a uniform way to access HATEOAS resources. When making a request, objects are immediately returned even
     *  though the data isn't populated yet. In many UI cases, these objects can be bound to and will simply update
     *  when the data returns from the server. If you need to be sure the data has arrived before operating,
     *  utilize the $promise property.
     *
     *  TODO more documentation & examples
     *
     */
    .factory('ApiResource', function($http){

        // Simple helper function to extract the data from $http's wrapper payload
        function unwrap(result){
            return result.data;
        }

        // Represents an actual resource returned from a webservice
        function ApiResource(url, data){
            if( data ){
                this.$resolve(data);
            }else{
                var that = this;
                this.$promise = $http.get(url)
                    .then(unwrap)
                    .then(function(response){
                        that.$resolve(response);
                        return that;
                    });
            }
        }

        // Called when the data actually comes back
        ApiResource.prototype.$resolve = function(data){
            angular.copy(data, this);
            this.$resolved = true;
        }

        // Traverses this Resource's links and pulls in entities
        ApiResource.prototype.$populate = function(){
            var that = this;
            var links = _.reject(this.links, function(){return this.rel == 'self'; });
            // TODO add links filtering if params provided
            for(var i in links){
                that[links[i].rel] = new ApiResource( links[i].href );
            };
        }

        // Represents collections of resources. Extends array
        function ApiResources(url){
            var that = this;
            this.$promise = $http.get(url)
                .then(unwrap)
                .then(function(response){
                    for(var i in response.content){

                        var el = response.content[i];
                        var links = el.links ? _.where(el.links, {rel: 'self'}) : [];
                        if(!links || links.length != 1){
                            console.log('WARNING: Unexpected result on "self" link for ', el);
                            continue;
                        }
                        var r = new ApiResource(links[0].href, el);
                        that.push(r);
                    }
                    delete response.content;
                    that.$page = response;
                    that.$resolved = true;
                });
        }
        ApiResources.prototype = Array.prototype;


        // Service object; represents an endpoint
        function ApiResource(url){
            this.url = url;
        }
        var p = ApiResource.prototype;
        p.findOne = function(id){
            return new ApiResource( this.url + '/' + id );
        };
        p.findAll = function(){
            return new ApiResources( this.url );
        };
        // TODO add query, etc
        return ApiResource;
    })

    .factory('User', function(ApiResource) {
        return new ApiResource('/api/users');
    })
;