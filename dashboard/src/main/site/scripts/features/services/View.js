/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 2:57 PM
 */

define(['text!./View.html', '../../formatters/ServiceLinkFormatter'], function (template, ServiceLinkFormatter) {

    return Backbone.View.extend({

        _template: _.template(template),
        _rows: new Slick.Data.DataView(),

        initialize: function () {
            this.collection.on('reset', this._renderGrid, this);
        },

        render: function () {

            this.$el.html(this._template());

            var columns = [
                {id: "id", name: "ID", field: "id", formatter: ServiceLinkFormatter.create()},
                {id: "address", name: "Address", field: "address", width: 90},
                {id: "payload", name: "Topics", field: "payload", formatter: ServiceLinkFormatter.create({id: "id", text: "Configure"})},
                {id: "registrationTime", name: "Registered", field: "registrationTime", width: 225},
                {id: "serviceType", name: "Service Type", field: "serviceType"}
            ];

            var options = {enableColumnReorder: false};
            this._grid = new Slick.Grid("#myGrid", this._rows, columns, options);
            this._renderGrid();

            return this;
        },

        _renderGrid: function () {
            var rows = this.collection.toJSON();
            this._rows.setItems(rows);
            this._grid.invalidate();
        }
    });
});