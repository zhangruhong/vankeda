$(function(){
	
	var currentPage = 1;
	var pageSize = 24;
	var orderBy = '';
	var orderType = '';
	var categoryPids = [];
	var mainLoading = false;
	var $goodsList = $('#goods-list');
	
	/**
	 * 初始化
	 */
	var load = function(){
		
		$.ajax({
			url: '/search',
			data: {page : currentPage, pageSize: pageSize, name : $('[name=name]').val(), orderBy : orderBy, orderType : orderType, categoryPids: categoryPids},
		}).done(function(objs){
			var $row = null;
			$.each(objs.rows, function(i, g){
				if(i % 4 == 0){
					$row = $('<div class="row"></div>').appendTo($goodsList);
				}
				if(g.ticketLeft > 0){
					var $col = $('<div class="col-md-3 col-sm-6 col-xs-12"></div>').appendTo($row);
					var $ibox = $('<div class="ibox"></div>').appendTo($col);
					var $goodsContainer = $('<div class="ibox-content product-box"></div>').appendTo($ibox);
					var $imageContainer = $('<div class="product-imitation"></div>').appendTo($goodsContainer);
					var $goodsImage = $('<a class="goods-image" style="background-image: url(' + g.mainImageUrl + ')" href="' + (deviceType != 'normal' ? 'javascript:;' : g.ticketUrl) + '" target="_blank"></a>').appendTo($imageContainer);
					var $goodsDesc = $('<div class="product-desc"></div>').appendTo($goodsContainer);
					var $goodsPrice = $('<span class="product-price">￥' + g.originalPrice.toFixed(2) + '</span>').appendTo($goodsDesc);
					var $goodsVolumn = $('<small class="text-muted">月销量:' + g.soldCountPerMonth + '</small>').appendTo($goodsDesc);
					var $goodsName = $('<div class="product-name"><a href="' + (deviceType != 'normal' ? 'javascript:;' : g.ticketUrl) + '" target="_blank">' + g.name +'</a></div>').appendTo($goodsDesc);
					var $goodsTicket = $('<div class="product-buy"></div>').append('<a class="goods-ticket" href="' + (deviceType != 'normal' ? 'javascript:;' : g.ticketUrl) + '" target="_blank">' + g.ticketValue + '</a>').appendTo($goodsDesc);
					if(deviceType != 'normal'){
						$goodsContainer.on('click', function(){
							$.ajax({url: '/index/getToken', data: {logo: null, title : g.name, itemUrl : g.ticketUrl}}).done(function(result){
								var $tokenModal = $('#tokenModal');
								$('#goods-image', $tokenModal).css({backgroundImage: 'url('+ g.mainImageUrl + ')'});
								$('#token-name', $tokenModal).text(g.name);
								$('#price', $tokenModal).text('￥' + g.originalPrice.toFixed(2));
								$('#ticket', $tokenModal).text(g.ticketValue);
								$('#copyContent', $tokenModal).val('复制这条消息，' + result.model + '，打开【手机淘宝】即可购买');
								$('#copyBtn', $tokenModal).remove();
								var $copyBtn = $('<button class="btn btn-default" type="button" id="copyBtn" data-clipboard-target="#copyContent">复制</button>').appendTo($('.modal-footer', $tokenModal));
								$tokenModal.modal('show');
								var clipboard = new Clipboard(document.getElementById('copyBtn'));
								clipboard.on('success', function(e) {
								    window.open('taobao://');
								    e.clearSelection();
								});
								
								clipboard.on('error', function(e) {
								    alert('请选择“拷贝”进行复制!')
								});
							});
						});
					}
				}
			});
			mainLoading = false;
			hideLoading();
		}).fail(function(){
			if(mainLoading){
				currentPage--;
				mainLoading = !mainLoading
			}
		});
	}
	
	var currentScrollTop = $(document).scrollTop();
	$(window).scroll(function() {
		if($(document).scrollTop() > currentScrollTop){
	        if ($(document).scrollTop() + 100 >= $(document).height() - $(window).height() && !mainLoading) {
	        	currentPage++;
	        	mainLoading = true;
	        	showLoading();
	        	load();
	        }
		}
		currentScrollTop = $(document).scrollTop();
	});
	
	$('#search').on('click', function(){
		$goodsList.empty();
		mainLoading = true;
		currentPage = 1;
		load();
	});
	
	$('[name=name]').on('keypress', function(e){
		if(e.keyCode == 13){
			$goodsList.empty();
			mainLoading = true;
			currentPage = 1;
			load();
		}
	});
	
	orderSelect = function(orderByTemp, orderTypeTemp){
		orderBy = orderByTemp;
		orderType = orderTypeTemp;
		$goodsList.empty();
		mainLoading = true;
		currentPage = 1;
		load();
	}
	
	categorySelect = function(isAllOrNot){
		var ids = [];
		if(isAllOrNot){
			$('[name=categoryPid]:checked').each(function(){
				$(this).prop('checked', false);
			});
		}else{
			$('[name=categoryPid]:checked').each(function(){
				if($(this).val() != '-1'){
					ids.push($(this).val());
				}
			});
		}
		categoryPids = ids;
		$goodsList.empty();
		mainLoading = true;
		currentPage = 1;
		load();
	}
	
	load();
});