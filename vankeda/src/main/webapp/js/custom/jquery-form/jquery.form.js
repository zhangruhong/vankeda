(function($) {
	
	
	$.fn.dictionary = function(op) {
		var option = {};
		$.extend(option, op);
		return this.each(function() {
			var type = $(this).attr('data-dict');
			if (typeof(type) == 'undefined') { return false; }
			var tagName = $(this).get(0).tagName;
			if (tagName == 'SELECT' || tagName == 'select') {
				$(this).attr('lastone', 'true');
				$.ajax({url: ('/public/dictionaries/' + type + '/all')}).done($.proxy(function(items) {
					$(this).empty().append('<option value="">-</option>');
					$.each(items, $.proxy(function(i, item) { $(this).append('<option value="'+ item.code +'">'+ item.name +'</option>'); }, this));
					if (typeof($(this).attr('data-value')) != 'undefined') { $(this).val($(this).attr('data-value')).removeAttr('data-value'); }
				}, this));
			} else {
				// 有下拉框，则初始化
				if ($(this).children('select').length > 0) {
					$(this).children('select').attr('lastone', 'false');
					$(this).children('select:last').attr('lastone', 'true');
					var first = $(this).children('select:first');
					$(this).children('select').each(function(i, n) {
						if (i > 0) {
							$(n).prev().on('change', function() {
								$(n).empty().append('<option value="">-</option>');
								if ($(this).val() == '') {
									$(n).trigger('change');
								} else {
									$.ajax({url: ('/public/dictionaries/' + type + '/children?parentCode=' + $(this).val())}).done(function(children) {
										$.each(children, function(j, child) { $(n).append('<option value="'+ child.code +'">'+ child.name +'</option>'); });
										if (typeof($(n).attr('data-value')) != 'undefined') { $(n).val($(n).attr('data-value')).removeAttr('data-value'); }
										$(n).trigger('change');
									});
								}
							});
						}
					});
					$.ajax({url: ('/public/dictionaries/' + type + '/roots')}).done(function(roots) {
						$(first).empty().append('<option value="">-</option>');
						$.each(roots, function(i, root) { $(first).append('<option value="'+ root.code +'">'+ root.name +'</option>'); });
						if (typeof($(first).attr('data-value')) != 'undefined') { $(first).val($(first).attr('data-value')).removeAttr('data-value'); }
						$(first).trigger('change');
					});
				} else {
					// 同时具有data-trans、data-value，需要翻译
					var code = $(this).attr('data-value');
					var trans = $(this).attr('data-trans');
					if (typeof(trans) == 'undefined') { trans = 'names'; }
					if (typeof(code) != 'undefined' && code.length > 0) {
						$.ajax({url: ('/public/dictionaries/' + type + '/'+ code +'/text?trans=' + trans)}).done($.proxy(function(obj) {
							$(this).text(obj.text);
						}, this));
					}
				}
			}
		});
	};
	
	
	$.fn.selection = function(op) {
		var option = {
			items			:	[],
			value			:	'id',
			text			:	'name',
			complete		:	$.noop
		};
		$.extend(option, op);
		return this.each(function() {
			$('option[value!=""]', this).remove();
			//
			var sb = new StringBuffer();
			if (option.items && $.isArray(option.items)) {
				$.each(option.items, function(i, obj) {
					if ($.isFunction(option.text)) {
						sb.append('<option value=' + obj[option.value] + '>' + option.text(obj) + '</option>');
					} else {
						sb.append('<option value=' + obj[option.value] + '>' + obj[option.text] + '</option>');
					}
				});
			}
			$(this).append(sb.toString());
			// 
			if (option.complete) {
				option.complete();
			}
		});
	};
	
	
	$.fn.validate = function(error) {
		return this.each(function() {
			$('.validation-error', this).removeClass('validation-error');
			if (error) {
				for (var key in error) {
					var $target = $('[name="'+ key +'"]', this);
					if ($target.length) {
						$target.addClass('validation-error');
						$('label[for="'+ key +'"]', this).addClass('validation-error')
						
						$target.attr('title', error[key]).data('toggle', 'tooltip').data('placement', 'top');
						if($('.tooltip[id=' + $target.attr('aria-describedby') + ']')){$('.tooltip[id=' + $target.attr('aria-describedby') + ']').remove()};
						$target.tooltip().tooltip('fixTitle').tooltip('show');
						
					} else {
						swal({title: '错误！', text: error[key], type: 'error'});
					}
				}
			}
		});
	};
	
	
	$.fn.searchForm = function(op) {
		var options = {
			url				:	'/',
			pagination		:	false,
			pageSize		:	10,
			success			:	$.noop,
			triggerEvent	: 	true
		};
		$.extend(options, op);
		return this.each(function() {
			// 分页参数及事件
			var paging = {};
			if (options.pagination) {
				paging = {
					total					:	0,
					pageSize				:	(options.pagination ? options.pageSize : 10),
					// 以下参数为分页插件所用
					currentPage				:	1,
			        totalPages				:	1,
			        numberOfPages			:	5,
			        bootstrapMajorVersion	:	3,
			        size					:	'small',
			        onPageClicked			:	$.proxy(function(e, originalEvent, type, page) { e.stopImmediatePropagation(); paging.currentPage = page; $(this).trigger('submit'); }, this),
			        tooltipTitles			:	function (type, page, current) {
			            switch (type) {
			            case 'first': return '首页';
			            case 'prev': return '上一页';
			            case 'next': return '下一页';
			            case 'last': return '尾页';
			            case 'page': return '第' + page + '页';
			            }
			        }
				};
			}
			// 表单提交
			$(this).on('submit', function(e) {
				e.preventDefault();
				// 
				var pageUrl = options.url;
				if ($.isFunction(pageUrl)) {pageUrl = pageUrl();}
				if (options.pagination) { 
					if (pageUrl.indexOf('?') > 0) {
						pageUrl = pageUrl + '&' + 'page=' + paging.currentPage + '&pageSize=' + paging.pageSize;
					} else {
						pageUrl = pageUrl + '?' + 'page=' + paging.currentPage + '&pageSize=' + paging.pageSize;
					}
				}
				$.ajax({
					dataType	:	'json',
					url			:	pageUrl,
					data		:	this
				}).done(function(result) {
					// 数据集
					var objects = result;
					// 分页计算
					if (options.pagination) {
						paging.total = result.total;
						paging.totalPages = Math.ceil(paging.total / paging.pageSize) || 1;
						if (paging.currentPage > paging.totalPages) { paging.currentPage = paging.totalPages; }
						objects = result.rows;
						// 生成分页条
						$(options.pagination).bootstrapPaginator(paging);
					}
					// 回调
					options.success(objects, (options.pagination ? paging : null));
				});
			});
			// 下拉框change触发提交
			if(options.triggerEvent){
				$('select', this).on('change', $.proxy(function() {
					$(this).trigger('submit');
				}, this));
			}
			
		});
	};
	
	$.fn.fillForm = function(obj){
		var $this = $(this);
		if($(this)[0].tagName != 'FORM'){
			return;
		}
		if(!$.isPlainObject(obj)){
			return;
		}
		$('[name]', $this).each(function(){
			var inputTagName = $(this)[0].tagName;
			var inputType = $(this)[0].attr('type');
			var name = $(this).attr('name');
			var value = obj[name] ? obj[name] : '';
			if(inputTagName == 'INPUT'){
				if(inputType == 'radio'){
					$('[name=' + name + '][value=' + value +']').prop('checked', 'checked');
				}else if(inputType == 'checkbox'){
					if($.isArray(value)){
						$.each(value, function(i, v){
							$('[name=' + name + '][value=' + v +']').prop('checked', 'checked');
						})
					}else{
						$('[name=' + name + '][value=' + v +']').prop('checked', '');
					}
				}else{
					$(this).val(value);
				}
			}else if(inputTagName == 'TEXTAREA'){
				$(this).text(value);
			}else if(inputTagName == 'SELECT'){
				$('option[value=' + value + ']').prop('selected', 'selected');
			}
		});
	}
	
	$.fn.resetForm = function(){
		var $this = $(this);
		if($(this)[0].tagName != 'FORM'){
			return;
		}
		$('[name]', $this).each(function(){
			var inputTagName = $(this)[0].tagName;
			var inputType = $(this)[0].attr('type');
			if(inputTagName == 'INPUT'){
				if(inputType == 'radio'){
					$(this).prop('checked', false);
				}else if(inputType == 'checkbox'){
					$(this).prop('checked', false);
				}else{
					$(this).val('');
				}
			}else if(inputTagName == 'TEXTAREA'){
				$(this).text('');
			}else if(inputTagName == 'SELECT'){
				$('option[value=' + value + ']').prop('selected', '');
			}
		});
	}
	
	$.fn.fileUpload = function(op, param){
		var $this = $(this);
		if(!$this.hasClass('file-upload')){return;}
		var $input = $('<input type="file" name="upload-file-input" />').appendTo($this);
		var option = {
			url: '/admin/fileupload',//上传url
			removeFileUrl: '/admin/removefile',//删除url
			downloadUrl: '/admin/filedownload',//获取url
			dataType: 'json',//数据类型
			inputName: 'file',//默认生成的input的name属性
			acceptFileTypes: /(\.|\/)(gif|jpe?g|png)$/i,//接收的文件类型
		}
		if(!$.isPlainObject(op)){
			if(op != ''){
				switch(op){
				case 'empty':
					$this.empty();
				case 'fill':
					if($.isArray(param['files'])){
						$.each(param['files'], function(i, n){
							buildFileList(n, n.split(/(\/|\\)/)[n.split(/(\/|\\)/).length-1], option.downloadUrl, option.removeUrl, option.inputName)
						});
					}else if(param['files']){
						buildFileList(param['files'], param['files'].split(/(\/|\\)/)[param['files'].split(/(\/|\\)/).length-1], option.downloadUrl, option.removeUrl, option.inputName)
					}
				}
				return;
			}
		}
		
		
		function buildFileList(filePath, fileName, downloadUrl, removeUrl, inputName){
			var $completeDiv = $('<div class="input-group" style="margin-top: 8px;position: relative;"></div>').appendTo($this);
			var $delBtn;
			if(/(\.|\/)(gif|jpe?g|png)$/i.test(filePath)){
				$completeDiv.addClass('pull-left');
				var $imageDiv = $('<img width="100" height="100" src="' + downloadUrl + '?filePath=' + encodeURI(filePath) +'"/>').appendTo($completeDiv);
				var $filePathInput = $('<input type="hidden" name="' + inputName + '" value="' + filePath + '"/>').appendTo($completeDiv);
				$delBtn = $('<div class="delBtn" style="position: absolute; right: 0px; top: 0px; width: 20px; height: 20px;text-align: center;cursor: pointer; border: 1px solid #ccc;display: none;background: rgba(0,0,0,0.25)"><i class="fa fa-trash-o"></i></div>').appendTo($completeDiv);
				$completeDiv.on('mouseenter', function(){
					$delBtn.slideDown(100);
				}).on('mouseleave', function(){
					$delBtn.slideUp(100);
				});
			}else{
				var $fileNameInput = $('<input class="form-control" value="' + fileName + '" readonly/>').appendTo($completeDiv);
				var $filePathInput = $('<input type="hidden" name="' + inputName + '" value="' + filePath + '"/>').appendTo($completeDiv);
				$delBtn = $('<span class="input-group-addon delBtn"><i class="fa fa-trash-o"></i></span>').appendTo($completeDiv);
			}
			
			$delBtn.on('click', function(){
				$.ajax({
					url: removeUrl,
					data: {filePath: filePath},
					type : 'post'
				}).done(function(){
					$completeDiv.remove();
				});
			});
		}
		
		if($.isPlainObject(op)){
			$.extend(option, op);
		}
		option.done = function(e, data){
			buildFileList(data.result['filePath'], data.result['fileName'], option.downloadUrl, option.removeUrl, option.inputName)
		}
		$input.fileupload(option);
	}
	
	
})(jQuery);


