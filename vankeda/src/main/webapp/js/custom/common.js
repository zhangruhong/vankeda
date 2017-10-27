$(function(){
	
	/* 通用扩展方法 */
	$.fn.extend({
		initBanner : function(){
			if($(this)[0].tagName === 'DIV'){
				var $bannerContainer = $(this);
				var $bannerController = $('.banner-controller', $bannerContainer);
				var bannerLength = $('li', $bannerController).length;
				
				tickTimer = setInterval(function(){
					var index = $('.active', $bannerController).index();
					if(index != bannerLength -1){
						$('.active', $bannerController).removeClass('active').next('li').addClass('active');
						$('.banner.active', $bannerContainer).removeClass('active').next('.banner').addClass('active');
					}else{
						$('.active', $bannerController).removeClass('active').parent().find('li:first-child').addClass('active');
						$('.banner.active', $bannerContainer).removeClass('active').parent().find('.banner:first-child').addClass('active');
					}
				}, 5000);
				
				$bannerController.on('mouseenter', function(e){
					var _this = this;
					$('li', $(_this)).on('click', function(){
						$('li.active', $(_this)).removeClass('active');
						$(this).addClass('active');
						var index = $(this).index();
						$('.banner', $bannerContainer).removeClass('active').eq(index).addClass('active');
					});
				});
			}
		},
		initGoodsContainer : function(objects){
			var $goodsList = $(this);
			$('.row', $goodsList).remove();
			var $row = null;
			$.each(objects, function(i, g){
				if(i % 4 == 0){
					$row = $('<div class="row"></div>').appendTo($goodsList);
				}
				if(g.ticketTotal > 0){
					var $col = $('<div class="col-md-3"></div>').appendTo($row);
					var $ibox = $('<div class="ibox"></div>').appendTo($goodsContainer);
					var $goodsContainer = $('<div class="ibox-content product-box"></div>').appendTo($ibox);
					var $imageContainer = $('<div class="product-imitation"></div>').appendTo($goodsContainer);
					var $goodsImage = $('<a class="goods-image" style="background-image: url(' + g.mainImageUrl + ')" href="' + g.ticketUrl + '" target="_blank"></a>').appendTo($imageContainer);
					var $goodsDesc = $('<div class="product-desc"></div>').appendTo($goodsContainer);
					var $goodsPrice = $('<span class="product-price">￥' + g.originalPrice.toFixed(2) + '</span>').appendTo($goodsDesc);
					var $goodsVolumn = $('<small class="text-muted">' + g.soldCountPerMonth + '</small>').appendTo($goodsDesc);
					var $goodsName = $('<div class="product-name">' + g.name +'</div>').appendTo($goodsDesc);
					var $goodsTicket = $('<div class="product-buy"></div>').append('<a class="goods-ticket" href="' + g.ticketUrl + '" target="_blank">' + g.ticketValue + '</a>').appendTo($goodsContainer);
				}
			});
			$(window).trigger('resize');
		},
		initType: function(){
			var cookieType = $.cookie('type');
			if(cookieType){
				$('[name=categoryPid]', this).val(cookieType);
				$('#type-tabs > li').removeClass('active');
				$('#type-tabs > li[data-type=' + cookieType +']').addClass('active');
			}else{
				$('[name=categoryPid]', this).val($('#type-tabs > li:first-child').data('type'));
			}
		},
		//position支持元素before,after,over
		warning : function(message, position){
			if(!position){
				position = 'before';
			}
			var $this = $(this);
			var $alertDiv = $('<div class="alert alert-danger alert-dismissable fade in"></div>');
			var $dismissBtn = $('<button class="close" type="button" data-dismiss="alert">&times;</button>').appendTo($alertDiv);
			$alertDiv.append($('<i class="fa fa-warning"></i>'));
			$alertDiv.append(message);
			if(position == 'before'){
				$this.prepend($alertDiv);
			}else if(position == 'after'){
				$this.append('$alertDiv')
			}else if(position == 'above'){
				$this.append('$alertDiv');
				$this.css({position: 'relative'});
				$alertDiv.css({position: 'absolute', left: '0px', top: '20px'});
			}
			$alertDiv.alert();
			setTimeout(function(){
				$alertDiv.alert('close');
			}, 5000);
		}
	});
	
	//顶部类目表
	var init = function(){
		//初始化顶部tabs
		$('.category-menu li').mouseleave(function(){
			$('.category-menu li.category-default').addClass('active');
		}).mouseenter(function(){
			$('.category-menu li.category-default').removeClass('active'); 
		}).trigger('mouseleave');
		
		/**
		 * 初始化分类信息
		 */
		$('.main-category-bar li').mouseleave(function(){
			$('.main-category-bar li.category-default').addClass('active');
		}).mouseenter(function(){
			$('.main-category-bar li.category-default').removeClass('active');
		}).on('click', function(){
			$(this).not('.active').addClass('category-default active').siblings('li').removeClass('category-default').removeClass('active');
			var id = $(this).data('id');
			if(id && $(this).hasClass('active')){
				$('[name=categoryPid]').val(id);
				$('[name=categoryId]').val('');
				var $subC = $('.sub-category-bar').empty();
				$('<li class="active">全部</li>').appendTo($subC);
				$.ajax({url: '/search/getChildrenCategory', data: {id : id}}).done(function(children){
					$.each(children, function(i, c){
						$('<li data-id="' + c.id + '">' + c.name + '</li>').appendTo($subC);
					});
					$('.sub-category-bar li').on('click', function(){
						$(this).addClass('active').siblings('li.active').removeClass('active');
						$('[name=categoryId]').val($(this).data('id'));
						$('#search-form').trigger('submit');
					}).trigger('mouseleave');
				});
			} else{
				$('.sub-category-bar').empty();
				$('[name=categoryPid]').val('');
				$('[name=categoryId]').val('');
			}
			$('#search-form').trigger('submit');
		}).trigger('mouseleave');
		
		/**
		 * 初始化商品展示区居中
		 */
		$(window).on('resize', function(){
			$('.section-content').each(function(index, value){
				var singleWidth = 270;
				var parentWidth = $(this).width();
				var colNumPerRow = Math.floor(parentWidth / singleWidth);
				var realMargin = (parentWidth - (singleWidth *  colNumPerRow ))/2;
				$('.goods-container', $(this)).each(function(i,v){
					$(this).css({marginLeft: '10px'});
					if(i % colNumPerRow == 0){
						$(this).css({marginLeft: realMargin + 10 + 'px'});
					}
				});
				$('.pagination', $(this)).css({marginRight: realMargin + 10 + 'px'});
			});
			
		});
		
		/**
		 * 阻止enter键刷新页面
		 */
		$('input').on('keypress', function(e){
			if(e.keyCode == 13){
				return false;
			}
		});
		
		 $('#firstLoginForm').on('submit', function(e){
			 e.preventDefault();
			 var $this = $(this); 
			 $.ajax({url: '/doFirstLogin', data: this, type: 'post', dataType: 'text'}).done(function(result){
				if(result == 'USER NOT FOUND'){
					$this.warning('用户不存在');
					$('[name=username]', $this).trigger('focus');
				}else if(result.indexOf('redirect:') >= 0){
					window.open(result.split('redirect:')[1], '_self');
				}else{
					$this.warning('登录失败');
				}
			});
		 });
		 
		 $('#loginForm').on('submit', function(e){
			 e.preventDefault();
			 var $this = $(this);
			 $.ajax({url: '/doLogin', data: this, type: 'post', dataType: 'text'}).done(function(result){
				if(result == 'USER NOT FOUND'){
					$this.warning('用户不存在');
					$('[name=username]', $this).trigger('focus');
				}else if(result == 'INCORRECT PASSWORD'){
					$this.warning('密码错误');
					$('[name=password]', $this).trigger('focus');
				}else if(result.indexOf('redirect:') >= 0){
					window.open(result.split('redirect:')[1], '_self');
				}else{
					$this.warning('登录失败');
				}
			});
		 });
	}
	
	firstLogin = function(){
		var $loginForm = $('#firstLoginForm');
		if(!$('[name=nickname]', $loginForm).val()){
			$loginForm.warning('用户昵称不能为空');
			$('[name=nickname]', $loginForm).trigger('focus');
			return;
		}
		var emailReg = new RegExp('^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$');
		if(!$('[name=email]', $loginForm).val()){
			$loginForm.warning('电子邮箱不能为空');
			$('[name=email]', $loginForm).trigger('focus');
			return;
		}
		if(!emailReg.test($('[name=email]', $loginForm).val())){
			$loginForm.warning('电子邮箱格式不正确');
			$('[name=email]', $loginForm).trigger('focus');
			return;
		}
		if(!$('[name=password]', $loginForm).val()){
			$loginForm.warning('密码不能为空');
			$('[name=password]', $loginForm).trigger('focus');
			return;
		}
		if(!$('[name=confirmPassword]', $loginForm).val()){
			$loginForm.warning('确认密码不能为空');
			$('[name=confirmPassword]', $loginForm).trigger('focus');
			return;
		}
		var passwordReg = new RegExp('^(?=.*[0-9].*)(?=.*[a-zA-Z].*)[0-9A-Za-z_]{6,20}$');
		if(!passwordReg.test($('[name=password]', $loginForm).val())){
			$loginForm.warning('密码只能为大小写字母或数字下划线，必须有字母和数字，且长度为6到20位');
			$('[name=password]', $loginForm).trigger('focus');
			return;
		}
		if($('[name=confirmPassword]', $loginForm).val() != $('[name=password]', $loginForm).val()){
			$loginForm.warning('两次输入的密码不一致');
			$('[name=confirmPassword]', $loginForm).trigger('focus');
			return;
		}
		$loginForm.trigger('submit');
		
	}
	doLogin = function(){
		var $loginForm = $('#loginForm');
		if(!$('[name=username]', $loginForm).val()){
			$loginForm.warning('用户名不能为空');
			$('[name=username]', $loginForm).trigger('focus');
			return;
		}
		if(!$('[name=password]', $loginForm).val()){
			$loginForm.warning('密码不能为空');
			$('[name=password]', $loginForm).trigger('focus');
			return;
		}
		$loginForm.trigger('submit');
	}
	init();
});