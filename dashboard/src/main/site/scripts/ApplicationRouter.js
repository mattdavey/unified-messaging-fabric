/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:42 PM
 */
define(['./features/nav/View',
    './features/services/View', './features/services/Collection',
    './features/prices/View', './collections/Subscriptions',
    './features/service/View', './features/service/Model'],
    function (NavView, ServicesView, ServicesCollection, SubscriptionsView, SubscriptionsCollection, ServiceView, ServiceModel) {

        return Backbone.Router.extend({

            _page: null,

            routes: {
                "": "services",
                "services(/)": "services",
                "prices(/)": "prices",
                "services/:id(/)": "service"
            },

            _services: new ServicesCollection(),

            services: function () {
                this._show("services", ServicesView, {collection: this._services});
            },

            prices: function () {

                var collection = new SubscriptionsCollection();
                collection.fetch();
                this._show("subscriptions", SubscriptionsView, {collection: collection});
            },

            service: function (id) {
                var model = new ServiceModel({id: id});
                model.fetch();
                this._show("services", ServiceView, {model: model});
            },

            _show: function (page, View, options) {
                if (this._page) this._page.remove();

                var nav = new NavView();
                $('#nav').empty().append(nav.el);
                nav.render();
                nav.$("." + page).addClass("selected");

                this._page = new View(_.extend({}, options));
                $("#page").append(this._page.el);
                this._page.render();
            }
        });
    });