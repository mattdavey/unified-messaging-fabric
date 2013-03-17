/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/15/13
 * Time: 5:03 PM
 */

define(['../../collections/Services', '../../collections/Subscriptions'], function (Services, Subscriptions) {

    return Backbone.Model.extend({

        defaults: {
            topics: new Backbone.Collection()
        },

        initialize: function () {
            this._service = new Services().buildModel({id: this.id});
            this._subscriptions = new Subscriptions();

            this._service.on('change', this._update, this);
            this._subscriptions.on('reset add remove', this._update, this);
        },

        fetch: function () {
            this._service.fetch();
            this._subscriptions.fetch();
        },

        changeSubscription: function (topic) {

            if (topic.attributes.sub)
                this._subscriptions.create(_.omit(topic.toJSON(), 'sub'), {wait: true});
            else
                this._subscriptions.get(topic.id).destroy();
        },

        _update: function () {
            var topics = this._service.attributes.topics.map(this._makeViewModel, this);
            this.attributes.topics.reset(topics);
        },

        _makeViewModel: function (topicInfo) {
            var viewModel = {service: this.id, topic: topicInfo.topic};
            var sub = _.findWhere(this._subscriptions.toJSON(), viewModel);

            return new Backbone.Model(_.extend(sub || viewModel, {sub: !_.isEmpty(sub)}));
        }

    });
});