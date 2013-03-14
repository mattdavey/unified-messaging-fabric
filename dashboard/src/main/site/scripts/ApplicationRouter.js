/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:42 PM
 */
define(['features/services/View', 'features/services/Collection', './features/subscriptions/View', './features/subscriptions/Collection'],
    function (ServicesView, ServicesCollection, SubscriptionsView, SubscriptionsCollection) {

        return Backbone.Router.extend({
            routes: {
                "": "services",
                "services": "services",
                "subscriptions": "subscriptions"
            },

            _services: new ServicesCollection(),
            _subscriptions: new SubscriptionsCollection(),

            services: function () {
                new ServicesView({
                    el: '#page',
                    collection: this._services
                }).render();
            },

            subscriptions: function () {
                new SubscriptionsView({
                    el: '#page',
                    collection: this._subscriptions
                }).render();
            }
        });
    });