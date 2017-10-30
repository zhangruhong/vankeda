$(function(){
	
	var currentIndexPage = 1;
	var pageSize = 24;
	mainLoading = false;
	/* 品牌/活动展示banner*/
	$('.banner-container').initBanner();
	
	/**
	 * 初始化商品
	 */
	var buildGoodsList = function(list, $goodsList){
		var $row = null;
		$.each(list, function(i, g){
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
	}
	
	var initTopSale = function(){
		$.ajax({url: '/index/getGoods',data: {pageSize: pageSize, page : currentIndexPage}}).done(function(pagination){
			var $goodsList = $('#goods-list');
			buildGoodsList(pagination.rows, $goodsList);
			mainLoading = false;
		}).fail(function(){
			if(mainLoading){
				currentIndexPage--;
				mainLoading = !mainLoading;
			}
		});
	}
	
	var currentScrollTop = $(document).scrollTop();
	$(window).scroll(function(){
		if($(document).scrollTop() > currentScrollTop){
	        if ($(document).scrollTop() + $(window).height() + 100 >= $(document).height() && !mainLoading) {
	        	currentIndexPage++;
	        	mainLoading = true;
	        	initTopSale();
	        }
		}
		currentScrollTop = $(document).scrollTop();
    });
	
	$('#search').on('click', function(){
		window.open('/v/search?name=' + $('[name=name]').val(), '_self');
	});
	
	$('[name=name]').on('keypress', function(e){
		if(e.keyCode == 13){
			window.open('/v/search?name=' + $(this).val(), '_self');
		}
	});
	
	initTopSale();
	
});