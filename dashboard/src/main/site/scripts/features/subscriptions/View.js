/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:57 PM
 */

define(['text!./View.html', './Subscription', '../../formatters/ServiceLinkFormatter'], function (template, Subscription, ServiceLinkFormatter) {

    return Backbone.View.extend({

        _template: _.template(template),
        _rows: new Slick.Data.DataView(),

        initialize: function () {
            var self = this;

            this._onRowCountChanged = function () {
                self._grid.updateRowCount();
                self._grid.render();
            };

            this._onRowsChanged = function (e, args) {
                self._grid.invalidateRows(args.rows);
                self._grid.render();
            };

            this._subscription = new Subscription(this._rows, this._key);
            this._subscribe();
            this.collection.on('reset', this._subscribe, this);
        },

        render: function () {

            this.$el.html(this._template());

            var columns = [
                {id: "service", name: "Service", field: "service", formatter: ServiceLinkFormatter.create()},
                {id: "topic", name: "Topic", field: "topic"},
                {id: "value", name: "Data", field: "value"}
            ];

            var options = {enableColumnReorder: false};
            this._grid = new Slick.Grid("#myGrid", this._rows, columns, options);
            this._grid.invalidate();

            this._rows.onRowCountChanged.subscribe(this._onRowCountChanged);
            this._rows.onRowsChanged.subscribe(this._onRowsChanged);

            return this;
        },

        remove: function () {
            this._subscription.dispose();

            this._rows.onRowCountChanged.unsubscribe(this._onRowCountChanged);
            this._rows.onRowsChanged.unsubscribe(this._onRowsChanged);

            Backbone.View.prototype.remove.apply(this, arguments);
        },

        _key: function (item) {
            return item.service + "." + item.topic;
        },

        _subscribe: function () {
            this._subscription.clear();

            var items = this.collection.toJSON().map(function (item) {
                return _.extend({}, item, {id: this._key(item)});
            }, this);

            this._rows.setItems(items);

            _.forEach(items, this._subscription.add, this);
        }
    });
});