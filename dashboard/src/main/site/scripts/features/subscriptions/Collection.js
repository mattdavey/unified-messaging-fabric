/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/14/13
 * Time: 3:00 PM
 */

define([], function () {

    return Backbone.Collection.extend({

        url: "/subscriptions"

//
//        initialize: function () {
//
//            var self = this;
//
//            this._data.setItems([], "topic");
//
//            var sock = Socket.create('http://localhost:8080/realtime/data');
//
//            sock.onopen = function () {
//                console.log('open');
//                sock.send(JSON.stringify({topic: 'USD/CAD', sub: true}));
//                sock.send(JSON.stringify({topic: 'USD/JPY', sub: true}));
////                sock.send(JSON.stringify({topic: 'USD/CAD', sub: false}));
//            };
//
//            sock.onmessage = function (e) {
////                console.log('message', e.data);
//                var update = JSON.parse(e.data);
//
//                var id = update.topic;
//                if (self._data.getItemById(id))
//                    self._data.updateItem(id, update);
//                else
//                    self._data.addItem(update);
//            };
//
//            sock.onclose = function () {
//                console.log('close');
//            };
//
//        },
//
//        data: function () {
//            return this._data;
//        }
    });
});