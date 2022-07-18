$(function() {

	$('a[href=#]').click(function(e) {
		e.preventDefault();
	});
	$('.slides', '.banner').cycle({
		fx      : 'scrollHorz',
		timeout : '30000',
		speed   : '600',
		pager   : '.banner-pager', 
		pagerAnchorBuilder: function(idx, slide) { 
			return '<a href="#"></a>'; 
		} 
	});
	$('[data-cycle]', '.slide-controls').click(function(e) {
		$($(this).attr('data-target')).cycle($(this).attr('data-cycle'));
	});
	
	$('a[data-toggle]').each(function() {
		var target = $($(this).attr('data-toggle'));
		if (target.length > 0) {
			target.click(function(e) {
				e.stopPropagation();
			});
		}
	}).click(function(e) {
		e.stopPropagation();
		var target = $($(this).attr('data-toggle'));
		if (target.length > 0) {
			$(this)[target.is(':visible') ? 'removeClass' : 'addClass']('visible');
			target.fadeToggle(200);
		}
	});
	
	$(document).click(function() {
		$('a[data-toggle].visible').click();
	});

	var sliderContainerWidth = 965;
	var sliderItemSpacing    = 35;
	var baseSpeed            = 5000;
	var controlsFadeTime     = 500;

	var slider = $('.slider-sledge');
	var sliderPositioner = $('.logo-slider');
	
	var partnersCount = $('img', slider).length;
	var sliderWidth = 0;
	var loaded = 0;
	var sliderStarted = false;
	var halfMargin = 0;
	var toLeft = false;
	var controlsHidden = false;
	
	$('.logos').mouseenter(function() {
		slider.stop();
	}).mouseleave(function() {
		if (sliderStarted) {
			continuePartnersSlider();
		}
	});

	$('img', slider).each(function() {
		if ( ! $(this).parent().is("a")) {
			$(this).wrap('<a onclick="return false;" style="cursor: default;"></a>');
		}
		$(this).attr('src', $(this).attr('src')+'?l='+Math.floor((Math.random()*1000000)+1));
		$(this).on('load', function() {
			loaded++;
			sliderWidth += (sliderWidth) ? sliderItemSpacing + 4 + parseInt($(this).width()) : parseInt($(this).width());
			if (loaded == partnersCount) {
				initPartnersSlider();
			}
		});
	});

	initPartnersSlider = function() {
		slider.css('visibility', 'visible');
			sliderPositioner.css('width', sliderWidth+'px');
		var margin = (sliderContainerWidth - sliderWidth) / 2;
			sliderPositioner.css('left', margin+'px');
			halfMargin = margin/2;

			slider.css('width', sliderWidth+'px');
			slider.css('margin', 0);
		if (margin < 0) {
			$('.partners-controls-slider a').css('display', 'block');
			continuePartnersSlider();
		}
	}

	continuePartnersSlider = function(stopRunning, runningTimes) {

		var first = slider.find('a').first();
		var last  = slider.find('a').last();
		var from = parseInt(slider.css('marginLeft'));
		var firstW = parseInt(slider.find('a').first().find('img').width());
		var lastW = parseInt(slider.find('a').last().find('img').width());
		if (sliderStarted) {
			var partnersOffsetLeft = $('.logos').offset().left;
			if (toLeft === true) {
				var lastOffsetLeftPlusWidth = last.offset().left;
				var partnersOffsetLeftPlusWidth = partnersOffsetLeft + sliderContainerWidth;
				if (lastOffsetLeftPlusWidth >= partnersOffsetLeftPlusWidth) {
					first.before(last);
					slider.css('marginLeft', parseInt(slider.css('marginLeft')) - lastW - sliderItemSpacing);
					from = parseInt(slider.css('marginLeft'));
					firstW = parseInt(slider.find('a').last().find('img').width());
				}
			} else {
				var firstOffsetLeftPlusWidth = first.offset().left + first.width();
				if (firstOffsetLeftPlusWidth < partnersOffsetLeft + 50) {
					last.after(first);
					slider.css('marginLeft', parseInt(slider.css('marginLeft')) + firstW + sliderItemSpacing/* + 3*/);
					from = parseInt(slider.css('marginLeft'));
					firstW = parseInt(slider.find('a').first().find('img').width());
				}
			}
		}
		var to = from - firstW - sliderItemSpacing;
		if (toLeft === true) {
			to = from + firstW;
		}
		to = parseInt(to);

		var speed = baseSpeed / 100 * firstW;
		slider.animate({
			'marginLeft': to+'px'
		},{
			easing: "linear",
			duration: speed,
			complete: function(){
				continuePartnersSlider(false, runningTimes+1);
			}
		});
		sliderStarted = true;

	}

	$('a', slider).click(function() {
		var href = $(this).attr('href');
		if (href) {
			window.open( href );
		}
		return false;
	});

	$.localScroll({offset:-78, duration:500});
});