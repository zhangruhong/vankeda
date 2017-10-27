(function($) {
	
	window._csrf_headerName = $('meta[name="_csrf_headerName"]').attr('content');
	window._csrf_token = $('meta[name="_csrf_token"]').attr('content');
	
	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		
		$.Deferred(function(defer) {
			// textStatus : "success", "timeout", "error", "parsererror", "null"等
            jqXHR.done(function(data, textStatus, jqXHR) {
            	defer.resolveWith(jqXHR, [data, textStatus, jqXHR]);
            }).fail(function(jqXHR, textStatus, errorThrown) {
            	if (textStatus == 'parsererror' && jqXHR.responseText == '') {
        			defer.resolveWith(jqXHR, ['', 'success', jqXHR]);
            	} else {
            		defer.rejectWith(jqXHR, [jqXHR, textStatus, errorThrown]);
            	}
            });
        }).promise(jqXHR);
		
        // csrf protection
        if (_csrf_headerName && _csrf_token) {
        	jqXHR.setRequestHeader(_csrf_headerName, _csrf_token);
        }
	});
	var $ajax = $.ajax;
	var ajaxProxy = function(originalOptions) {
		var option = {
			type			:	'get',
			dataType		:	'json',
			cache			:	true,
			traditional		:	true,
			timeout			:	1000*60*60
		};
		$.extend(option, originalOptions);
		var form = null;
		// 参数转换为串
		if (option.data) {
			if ($.isPlainObject(option.data)) {option.data = $.param(option.data, true);}
			else if (option.data.tagName && (option.data.tagName == 'FORM')) {form = $(option.data); option.data = $(option.data).serialize();}
			else if ($.isFunction(option.data)) {option.data = $.param(option.data(), true);}
		}
		if ($.isFunction(option.url)) { 
			option.url = option.url();
		}
		// 参数拼接到url
		if (option.data && (option.type.toLowerCase() == 'get' || option.type.toLowerCase() == 'delete')) {
			option.url = option.url + ((option.url.indexOf('?', 0) == -1) ? '?' : '&') + option.data;
			option.data = null;
		}
		// contextPath
		if (typeof(contextPath) != 'undefined' && option.url.indexOf('http') != 0) {
			option.url = contextPath + option.url;
		}
		// 防止重复提交
		if (form) {
			if (form.attr('requesting') == 'yes') {
				return {done: $.noop, fail: $.noop, always: $.noop};
			} else {
				form.validate(null);
				form.attr({requesting: 'yes'});
			}
			// ladda button
			if ($('[type=submit]', form).length > 0) {
				var ladda = $('[type=submit]', form).attr('data-style', 'zoom-out').addClass('ladda-button').ladda();
				form.data('ladda', ladda);
				ladda.ladda('start');
			}
			$('.cancel-btn', form).prop('disabled', true);
		}
		// 提交
		return $ajax(option).fail(function(jqXHR, textStatus, error) {
			if (jqXHR.status == 400) {
				if (jqXHR.responseJSON && jqXHR.responseJSON.validationError) {
					// 校验错误（提示到表单或者弹出）
					var validationError = jqXHR.responseJSON.validationError;
					if (form) {form.validate(validationError);}
					else {var txt = '';for (var key in validationError) {txt=txt+','+validationError[key];}if (txt.length>0) {txt=txt.substring(1, txt.length);} swal({title: '错误！', text: txt, type: 'error'});}
				} else if (jqXHR.responseJSON && jqXHR.responseJSON.applicationError) {
					var applicationError = jqXHR.responseJSON.applicationError;
					if (option.appError) {
						option.appError(applicationError);
					} else {
						swal({title: '错误！', text: applicationError, type: 'error'});
					}
				} else {
					swal({title: '错误！', text: error, type: 'error'});
				}
				return;
        	} else if (jqXHR.status == 403) {
        		swal({title: '错误！', text: '没有访问权限', type: 'error'});
				return;
        	} else if (jqXHR.status == 404) {
        		swal({title: '错误！', text: '资源不存在', type: 'error'});
				return;
        	} else if (jqXHR.status == 405) {
        		swal({title: '错误！', text: '不允许使用的方法', type: 'error'});
				return;
        	} else if (jqXHR.status == 500) {
        		swal({title: '错误！', text: '操作失败', type: 'error'});
				return;
        	} else if (jqXHR.status == 401 || (jqXHR.status == 200 && textStatus == 'parsererror') || jqXHR.status == 0) {
        		if (window.top.jumping) {
        			return;
        		}
        		window.top.jumping = true;
        		if (typeof(contextPath) != 'undefined') {
        			window.top.location.href = contextPath + "/";
        		} else {
        			window.top.location.href = "/";
        		}
        		return;
        	} else {
        		swal({title: '错误！', text: error, type: 'error'});
        	}
		}).always(function() {
			if (form) {
				form.removeAttr('requesting');
				// 
				var ladda = form.data('ladda');
				if (typeof(ladda) != 'undefined') {
					ladda.ladda('stop');
				}
				$('.cancel-btn', form).prop('disabled', false);
			}
		});
	};
	$.extend({
		ajax : ajaxProxy
	});
	
})(jQuery);