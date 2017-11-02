$(function(){
	
	var currentPage = 1;
	var pageSize = 24;
	var mainLoading = false;
	var total = 0;
	var isOver = false;
	/* 品牌/活动展示banner*/
	var $goodsList = $('#goods-list');
	
	/**
	 * 初始化
	 */
	var $row = null;
	var load = function(){
		
		$.ajax({
			url: '/tqg/search',
			data: {page : currentPage, name : $('[name=name]').val()},
		}).done(function(objs){
			if(objs.list.length < pageSize){isOver = true;}
			currentPage = objs.page;
			$.each(objs.list, function(i, g){
				if(total % 4 == 0){
					$row = $('<div class="row"></div>').appendTo($goodsList);
				}
				if(g.soldNum > 0){
					total++;
					var $col = $('<div class="col-md-3 col-sm-6 col-xs-12"></div>').appendTo($row);
					var $ibox = $('<div class="ibox"></div>').appendTo($col);
					var $goodsContainer = $('<div class="ibox-content product-box"></div>').appendTo($ibox);
					var $imageContainer = $('<div class="product-imitation"></div>').appendTo($goodsContainer);
					var $goodsImage = $('<a class="goods-image" style="background-image: url(' + g.picUrl + ')" href="' +  g.clickUrl + '" target="_blank"></a>').appendTo($imageContainer);
					var $goodsDesc = $('<div class="product-desc"></div>').appendTo($goodsContainer);
					var $goodsPrice = $('<span class="product-price">￥' + parseFloat(g.zkFinalPrice).toFixed(2) + '</span>').appendTo($goodsDesc);
					var $goodsVolumn = $('<small class="text-muted">已抢购:' + g.soldNum + '</small>').appendTo($goodsDesc);
					var $goodsName = $('<div class="product-name"><a href="' + (deviceType != 'normal' ? 'javascript:;' : g.clickUrl) + '" target="_blank">' + g.title +'</a></div>').appendTo($goodsDesc);
					var $goodsTicket = $('<div class="product-buy"></div>').append('<a class="goods-ticket" href="' + g.clickUrl + '" target="_blank">抢购</a>').appendTo($goodsDesc);
					
					$goodsContainer.on('click', function(){
						window.open(g.clickUrl, '_blank');
					});
				}
				
			});
			mainLoading = false;
			hideLoading();
		}).fail(function(){
			if(mainLoading){
				currentPage--;
				mainLoading = !mainLoading
			}
		});;
	}
	
	var currentScrollTop = $(document).scrollTop();
	$(window).scroll(function() {
		if($(document).scrollTop() > currentScrollTop){
	        if ($(document).scrollTop() + 100 >= $(document).height() - $(window).height() && !mainLoading && !isOver) {
	        	currentPage++;
	        	mainLoading = true;
	        	showLoading();
	        	load();
	        }
		}
		currentScrollTop = $(document).scrollTop();
	});
	
	load();
	
});