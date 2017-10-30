$(function(){
	
	var currentPage = 1;
	var pageSize = 24;
	var total = 0;
	var mainLoading = false;//是否正在加载中
	/* 品牌/活动展示banner*/
	var $goodsList = $('#goods-list');
	var isOver = false;//查询是否已结束
	
	/**
	 * 初始化
	 */
	var $row = null;
	var init = function(){
		$.ajax({
			url: '/ticket/getCoupons',
			data: {page : currentPage, keyword : $('[name=keyword]').val()},
		}).done(function(objs){
			if(objs.length < pageSize){
				isOver = true;
			}
			$.each(objs, function(i, g){
				if(total % 4 == 0){
					$row = $('<div class="row"></div>').appendTo($goodsList);
				}
				if(g.couponRemainCount > 0 && g.volume > 0){
					total++;
					var $col = $('<div class="col-md-3 col-sm-6 col-xs-12"></div>').appendTo($row);
					var $ibox = $('<div class="ibox"></div>').appendTo($col);
					var $goodsContainer = $('<div class="ibox-content product-box"></div>').appendTo($ibox);
					var $imageContainer = $('<div class="product-imitation"></div>').appendTo($goodsContainer);
					var $goodsImage = $('<a class="goods-image" style="background-image: url(' + g.pictUrl + ')" href="' + (deviceType != 'normal' ? 'javascript:;' : g.couponClickUrl) + '" target="_blank"></a>').appendTo($imageContainer);
					var $goodsDesc = $('<div class="product-desc"></div>').appendTo($goodsContainer);
					var $goodsPrice = $('<span class="product-price">￥' + g.zkFinalPrice + '</span>').appendTo($goodsDesc);
					var $goodsVolumn = $('<small class="text-muted">月销量:' + g.volume + '</small>').appendTo($goodsDesc);
					var $goodsName = $('<div class="product-name"><a href="' + (deviceType != 'normal' ? 'javascript:;' : g.couponClickUrl) + '" target="_blank">' + g.title +'</a></div>').appendTo($goodsDesc);
					var $goodsTicket = $('<div class="product-buy"></div>').append('<a class="goods-ticket" href="' + (deviceType != 'normal' ? 'javascript:;' : g.couponClickUrl) + '" target="_blank">' + g.couponInfo + '</a>').appendTo($goodsDesc);
					
					if(deviceType != 'normal'){
						$goodsContainer.on('click', function(){
							$.ajax({url: '/index/getToken', data: {logo: null, title : g.title, itemUrl : g.couponClickUrl}}).done(function(result){
								var $tokenModal = $('#tokenModal');
								$('#goods-image', $tokenModal).css({backgroundImage: 'url('+ g.pictUrl + ')'});
								$('#token-name', $tokenModal).text(g.title);
								$('#price', $tokenModal).text('￥' + g.zkFinalPrice);
								$('#ticket', $tokenModal).text(g.couponInfo);
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
			if ($(document).scrollTop() + 100 >= $(document).height() - $(window).height() && !mainLoading && !isOver) {
	        	currentPage++;
	        	mainLoading = true;
	        	isOver = false;
	        	init();
	        }
		}
		currentScrollTop = $(document).scrollTop();
	});
	
	init();
	
	$('#search').on('click', function(){
		reset();
		init();
	});
	
	$('[name=keyword]').on('keypress', function(e){
		if(e.keyCode == 13){
			reset();
			init();
		}
	});
	
	var reset = function(){
		$goodsList.empty();
		mainLoading = true;
		currentPage = 1;
		isOver = false;
	}
});