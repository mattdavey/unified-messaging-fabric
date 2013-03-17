/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:57 PM
 */

define(['text!./View.html', 'text!./TopicView.html'], function (template, topicTemplate) {

    return Backbone.View.extend({

        _template: _.template(template),
        _topicTemplate: _.template(topicTemplate),

        initialize: function () {
            this._modelBinder = new Backbone.ModelBinder();
            var elManagerFactory = new Backbone.CollectionBinder.ElManagerFactory(this._topicTemplate(), "data-name");
            this._collectionBinder = new Backbone.CollectionBinder(elManagerFactory);

            this.model.get('topics').on('change:sub', this._onChangeSub, this);
        },

        render: function () {

            this.$el.html(this._template());

            this._modelBinder.bind(this.model, this.el, Backbone.ModelBinder.createDefaultBindings(this.el, "data-name"));
            this._collectionBinder.bind(this.model.attributes.topics, this.$('ul'));

            return this;
        },

        remove: function () {

            this.model.get('topics').off('change:sub', this._onChangeSub, this);
            this._modelBinder.unbind();
            this._collectionBinder.unbind();

            Backbone.View.prototype.remove.apply(this, arguments);
        },

        _onChangeSub: function (topic) {
            this.model.changeSubscription(topic);
        }

    });
});