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

        var sock = Socket.create('http://localhost:8080/realtime/data');
        sock.onopen = function () {
            add = _addAndSend;
            _.forEach(topics, _send, this);
        };
        sock.onclose = function () {
            add = _add;
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

        this.dispose = function () {
            sock.close();
        };


        function _add(serviceTopic) {
            topics.push(serviceTopic);
        }

        function _send(serviceTopic) {
            var command = _.omit(_.extend({sub: true}, serviceTopic), "id");
            sock.send(JSON.stringify(command));
        }

        function _addAndSend(serviceTopic) {
            _add(serviceTopic);
            _send(serviceTopic);
        }

    };
});