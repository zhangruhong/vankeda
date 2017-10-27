

WebsocketConnection = function(url, op){
	//最大尝试连接次数
	this.tryTimesLimit = 10000;
	//已尝试连接次数
	this.tryTimes = 0;
	//option
	this.op = {
		reconnectable : false,//关闭或错误后是否重连
		onopen : null,
		onmessage : null,
		onerror : null,
		onclose : null
	};
	//连接url
	this.url = url;
	//websocket实例
	this.ws = null;
	this.reconnectTimer = null;
	if(typeof op.onopen === 'function'){
		this.op.onopen = op.onopen;
	}
	if(typeof op.onmessage === 'function'){
		this.op.onmessage = op.onmessage;
	}
	if(typeof op.onerror === 'function'){
		this.op.onerror = op.onerror;
	}
	if(typeof op.onclose === 'function'){
		this.op.onclose = op.onclose;
	}
	if(op.reconnectable){
		this.op.reconnectable = op.reconnectable;
	}
	return this;
}

WebsocketConnection.prototype.connect = function(){
	this.ws = typeof WebSocket !== 'undefined' ? new WebSocket('ws://' + this.url) : new SockJS('http://' + this.url);
	console.log(this.ws);
	this.bindEvent();
}

WebsocketConnection.prototype.reconnect = function(){
	if(!this.ws && this.op.reconnectable && this.tryTimes < this.tryTimesLimit){
		this.tryTimes++;
		this.connect();
	}
}

WebsocketConnection.prototype.bindEvent = function(){
	var _this = this;
	this.ws.onopen = function(){
		this.tryTimes = 0;
		if(this.reconnectTimer){
			clearTimeout(this.reconnect);
		}
		_this.op.onopen();
	};
	this.ws.onmessage = function(message){_this.op.onmessage(message);};
	this.ws.onerror = function(){
		_this.op.onerror();
	};
	this.ws.onclose = function(event){
		if(_this.op.reconnectable){
			_this.ws = null;
			if(!this.reconnectTimer){
				this.reconnectTimer = setTimeout(this.connect, 2000);
			}
		}
		_this.op.onclose(event);
		
	};
}