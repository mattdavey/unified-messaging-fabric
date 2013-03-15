/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:42 PM
 */
define(['features/services/View', 'features/services/Collection', './features/subscriptions/View', './features/subscriptions/Collection'],
    function (ServicesView, ServicesCollection, SubscriptionsView, SubscriptionsCollection) {

        return Backbone.Router.extend({

            _page: null,

            routes: {
                "": "services",
                "services": "services",
                "subscriptions": "subscriptions"
            },

            _services: new ServicesCollection(),
            _subscriptions: new SubscriptionsCollection(),

            services: function () {
                this._show(ServicesView, {collection: this._services});
            },

            subscriptions: function () {

                this._subscriptions.add({topic: 'USD/CAD'});
                this._show(SubscriptionsView, {collection: this._subscriptions});
            },

            _show: function (View, options) {
                if (this._page) this._page.remove();
                this._page = new View(_.extend({}, options));
                $("#page").append(this._page.el);
                this._page.render();
            }
        });
    });