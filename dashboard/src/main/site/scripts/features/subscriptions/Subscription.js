/**
 * Created with IntelliJ IDEA.
 * User: eprystupa
 * Date: 3/15/13
 * Time: 9:20 PM
 */

define(['reconnecting-websocket'], function (Socket) {
    return function (dataView, keyFactory) {

        var topics = [];
        var add = _add;
        var clear = _clear;

        var sock = Socket.create('http://localhost:8080/realtime/data');
        sock.onopen = function () {
            add = _addAndSubscribe;
            clear = _clearAndUnsubscribe;
            _.each(topics, _subscribe, this);
        };
        sock.onclose = function () {
            add = _add;
            clear = _clear;
        };

        sock.onmessage = function (e) {
            var update = JSON.parse(e.data);
            var item = _.extend({id: keyFactory(update)}, update);
            if (dataView.getItemById(item.id))
                dataView.updateItem(item.id, item);
            else
                dataView.addItem(item);
        };


        this.add = function (serviceTopic) {
            add(serviceTopic);
        };

        this.clear = function () {
            clear();
        };

        this.dispose = function () {
            sock.close();
        };


        function _add(serviceTopic) {
            topics.push(serviceTopic);
        }

        function _subscribe(serviceTopic) {
            _send(serviceTopic, true);
        }

        function _unsubscribe(serviceTopic) {
            _send(serviceTopic, false);
        }

        function _send(serviceTopic, sub) {
            var command = _.omit(_.extend({sub: sub}, serviceTopic), "id");
            sock.send(JSON.stringify(command));
        }

        function _addAndSubscribe(serviceTopic) {
            _add(serviceTopic);
            _subscribe(serviceTopic);
        }

        function _clear() {
            topics.length = 0;
        }

        function _clearAndUnsubscribe() {
            _.forEach(topics, _unsubscribe, this);
            topics.length = 0;
        }

    };
});