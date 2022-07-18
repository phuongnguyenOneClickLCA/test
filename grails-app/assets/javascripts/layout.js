// JavaScript Document
jQuery(document).ready(function() {

	$('#querynav li a').on('click', function (event) {
		event.preventDefault();
		$($(this).attr('href'))[0].scrollIntoView();
		scrollBy(0, -40);
	});

	$('span.help-inline').on('click', function (event) {
		if ( ! $(this).hasClass('help-inline-open')) {
			$(this).animate({
				height: 'auto !important'
			}, 500);
		}
		$(this)[($(this).hasClass('help-inline-open') ? 'removeClass' : 'addClass')]('help-inline-open');
	});

	$('[rel="popover"]').popover({
        placement : 'top',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });

    $('[rel="popover_top"]').popover({
        placement : 'top',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });
	
	$('[rel="popover_bottom"]').popover({
		placement : 'bottom',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });

    $('[rel="popover_left"]').popover({
        placement : 'left',
        template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
    });

	$('[rel="popover_right"]').popover({
		placement : 'right',
		template: '<div class="popover"><div class="arrow"></div><div class="popover-content"></div></div>'
	});

	function fadetoggle(){
		$('.fadetoggle').fadeIn(function (){
			var delaytime = $(this).attr('data-fadetoggle-delaytime');
			if(!delaytime) {
				delaytime = 3000;
			}
			$(this).delay(delaytime).fadeOut();
		});		
	}
	fadetoggle();

	$('.tablerowlinks > tbody > tr').on('click', function (event) {
		var href = $(this).attr('rel');
		if(href)
			window.location = href;
	});

	$('ul.nav-tabs a').on('click', function (e) {
		e.preventDefault();
		$(this).tab('show');
	});

	$('*[rel="tooltip"]').tooltip({animation:true, placement:'top'});

	/**
	 * >> DATATABLES FUNCTION >>
	 */
	if ($('table.table-data').length > 0) {
		var dataTables = new Object();
		var dataTablesIdArray = [];

		(function($) {
		$.fn.dataTableExt.oApi.fnGetColumnData = function(oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty) {
			if (typeof iColumn      == "undefined") return new Array();
			if (typeof bUnique      == "undefined") bUnique = true;
			if (typeof bFiltered    == "undefined") bFiltered = true;
			if (typeof bIgnoreEmpty == "undefined") bIgnoreEmpty = true;
			var aiRows;
			if (bFiltered == true) aiRows = oSettings.aiDisplay;
			else aiRows = oSettings.aiDisplayMaster;
			var asResultData = new Array();
			for (var i = 0, c = aiRows.length; i < c; i++) {
				iRow = aiRows[i];
				var aData = this.fnGetData(iRow);
				var sValue = aData[iColumn];

				if (bIgnoreEmpty == true && sValue.length == 0) continue;
				else if (bUnique == true && jQuery.inArray(sValue, asResultData) > -1) continue;
				else asResultData.push(sValue);
			}
			return asResultData;
		}}(jQuery));

		fnInitDataTableObject = function(id) {
			if (typeof id != 'undefined') {
				dataTablesIdArray.push(id);
				dataTables[id] = $('#'+id).dataTable( {
					"sPaginationType": "bootstrap",
					"oLanguage": {
						"sLengthMenu": "<span class='spacer'>Show:</span> _MENU_ entries",
						"sSearch": "<span class='spacer'>Keywords:</span>",
						"oPaginate": {
							"sFirst":    "Ensimmäinen",
							"sPrevious": "<span class='list-browser'>&laquo;</span>",
							"sNext":     "<span class='list-browser'>&raquo;</span>",
							"sLast":     "Viimeinen"
						}
					},
					"iDisplayLength": 10,
					"aLengthMenu": [[1, 10, 25, 50, 100, -1], [1, 10, 25, 50, 100, "Kaikki"]],
					"aaSorting": [[ 0, "asc" ]]
				} );
			}
		}

		fnInitSearchOptionsLimitResultsSelect = function(id) {
			if (typeof id != 'undefined') {
				var optionsContainer = $('div#'+id+'_info').parent().find('.dataTables_paginate');//.parent().closest('.dataTables_paginate');
					optionsContainer.append($('div#'+id+'_length'));
				$('div#'+id+'_length').addClass("pull-right");
				$('div#'+id+'_length').show();
			}
		}

		fnToggleSearchOptions = function(skipAnimation, id) {
			if (typeof skipAnimation == 'undefined') {
				skipAnimation = false;
			}
			if (typeof id != 'undefined') {
				var btn = $('a#'+id + '_search_toggle'), optionsContainer = $('div#'+id+'_filter');
				if (btn) {
					btn.toggleClass('more');
					btn.html((btn.html() == btn.data('more')) ? btn.data('less') : btn.data('more'));
					if (skipAnimation == false) {
						optionsContainer.animate({
							height: (optionsContainer.hasClass('open') ? parseInt(btn.data('closed')) : parseInt(btn.data('open'))) + 'px'
						}, parseInt(btn.data('delay')), function() {
							optionsContainer.toggleClass('open');
						});
					} else {
						optionsContainer.height(optionsContainer.hasClass('open') ? btn.data('closed') : btn.data('open'));
						optionsContainer.removeClass('open');
					}
				}
			}
		}

		fnInitSearchOptionsToggleMoreOptions = function(id) {
			if (typeof id != 'undefined') {
				var btn = $('<a/>'), optionsContainer = $('div#'+id+'_filter'), table = $('table#'+id);
				if(table.attr('data-str-more')) {
					btn.addClass('toggle-search-options');
					btn.addClass('btn');
					btn.addClass('btn-primary');
					btn.attr('id', id + '_search_toggle');
					btn.addClass('more');
					btn.attr('href', '#');
					var more = (table.attr('data-str-more')) ? table.attr('data-str-more') : 'Advanced';
					var less = (table.attr('data-str-less')) ? table.attr('data-str-less') : 'Advanced';
					btn.data({
						'more'   : more,
						'less'   : less,
						'closed' : parseInt(optionsContainer.height()),
						'delay'  : 200
					});
					optionsContainer.css('height', 'auto');
					btn.data('open', parseInt(optionsContainer.height()));
					optionsContainer.css('height', btn.data('closed'));
					btn.html(more);
					btn.click(function(e) { fnToggleSearchOptions(false, id); e.preventDefault(); });
					optionsContainer.append(btn);
				}
			}
		}
		
		fnInitExternalButtons = function(id) {
			if (typeof id != 'undefined') {
				var btn = $('<a/>');
				var optionsContainer = $('div#'+id+'_filter');
				var table = $('table#'+id);
				if(table.attr('data-external-button-caption')) {
					table.attr('data-external-button-href');
					table.attr('data-external-button-caption');
					
					btn.addClass('btn');
					btn.addClass('btn-primary');
					btn.addClass('toggle-search-options');
					btn.attr('href', table.attr('data-external-button-href'));
					btn.html(table.attr('data-external-button-caption'));
					optionsContainer.append(btn);
				}
			}		
		}


		function fnCreateStatusSelect( aData, columnNro ) {
			var r = $('<select/>'), o = $('<option/>'), i, iLen = aData.length;
			r.attr('data-column', columnNro);
			r.append(o);
			for (i = 0; i < iLen; i++) {
				o = $('<option/>');
				o.val(aData[i]).html(aData[i]);
				r.append(o);
			}
			return r;
		}

		fnInitSearchOptionsStatusSelect = function(id) {
			if (typeof id != 'undefined') {
				var table = $('table#'+id);
				var columnData = table.attr('data-select-search-column');
				if (typeof columnData != 'undefined') {
					columns = columnData.split(',');
					var i = 0;
					for (i = 0; i < columns.length; i++) {
						columnNro = parseInt(columns[i]);
						if (typeof columnNro != 'undefined') {
							if (table.find('thead').find('th:nth-child('+(columnNro+1)+')').length > 0) {
								var optionsContainer = $('div#'+id+'_filter');
								var dataTable = dataTables[id];
								var selectStatus = fnCreateStatusSelect(dataTable.fnGetColumnData(columnNro), columnNro);
									selectStatus.on('change', function () { dataTable.fnFilter( $(this).val(), parseInt($(this).attr('data-column')) ); } );
								var div = $('<div/>');
									div.addClass('data-status-select');
								var span = $('<span/>');
									span.addClass('spacer');
								var text = table.find('thead').find('th:nth-child('+(columnNro+1)+')').html();
									span.html(text+':');
								var label = $('<label/>');
									label.append(selectStatus);
								div.append(span);
								div.append(label);
								optionsContainer.append(div);
							}
						}
					}
				}
			}
		}

		fnResetSearchOptionsToggleAtResize = function(dataTablesIdArray) {
			if (typeof dataTablesIdArray != 'undefined' &&
				dataTablesIdArray.length > 0)
			{
				var i = 0;
				var id = 'undefined';
				for (i = 0; i < dataTablesIdArray.length; i++) {
					id = dataTablesIdArray[i];
					if (typeof id != 'undefined') {
						if ($('table#'+id).attr('data-hide-search') != 'true') {
							//console.log('resize: '+id);
							var btn = $('a#' + id + '_search_toggle'), optionsContainer = $('div#' + id + '_filter');
							if (btn.html() != btn.data('more')) {
								fnToggleSearchOptions(true, id);
							}
							optionsContainer.removeAttr('style');
							optionsContainer.removeClass('open');
							btn.data('closed', parseInt(optionsContainer.height()));
							optionsContainer.css('height', 'auto');
							btn.data('open', parseInt(optionsContainer.height()));
							optionsContainer.css('height', btn.data('closed'));
						}
					}
				}
			}
		}

		var w = $(window).width();
		$(function(){
			$(window).on('resize', function(){
				var _w = $(window).width();
				if (w != _w) {
					fnResetSearchOptionsToggleAtResize(dataTablesIdArray);
					w = _w;
				}
			});
		});

		fnHideSearchBar = function(id) {
			if (typeof id != 'undefined') {
				$('div#'+id+'_filter').hide();
			}
		}

		fnInitDataTable = function(id) {
			if (typeof id != 'undefined') {
				fnInitDataTableObject(id);
				fnInitSearchOptionsLimitResultsSelect(id);
				var table = $('table#'+id);
				if (table.attr('data-hide-search') == 'true') {
					fnHideSearchBar(id);
				} else {
					fnInitSearchOptionsStatusSelect(id);
					if (typeof table.attr('data-select-search-column') != 'undefined') {
						fnInitSearchOptionsToggleMoreOptions(id);
					}
				}
				fnInitExternalButtons(id);
			}
		}

		$('table.table-data').each(function(e) {
			var id = $(this).attr('id');
			if (typeof id != 'undefined') {
				fnInitDataTable(id);
			}
		});

		/*
		$('a[data-toggle]').click(function() {
			setTimeout(function() { fnResetSearchOptionsToggleAtResize(dataTablesIdArray) }, 200);
		});
		*/
	}
	/**
	 * << DATATABLES FUNCTION <<
	 */

});

function modalConfirm(sender) {
	if($('#modalconfirm').length < 1) {
		var modalContStr = '<div class="modal hide fade" id="modalconfirm"></div>';
		$("body").append(modalContStr);
	}

	var href = $(sender).attr('href');
	var title_str = $(sender).attr('data-titlestr');
	var true_str = $(sender).attr('data-truestr');
	var false_str = $(sender).attr('data-falsestr');
	var question_str = $(sender).attr('data-questionstr');

	var $modalWin = $('#modalconfirm');

	var modalInnerStr = '<div class="modal-header"><button type="button" class="close" data-dismiss="modal">&times;</button>';
	modalInnerStr += '<h2>'+title_str+'</h2>';
	modalInnerStr += '</div>';
	modalInnerStr += '<div class="modal-body"><p>'+ question_str +'</p></div>';
	modalInnerStr += '<div class="modal-footer"><a href="'+ href +'" class="btn btn-primary">' + true_str + '</a> <a href="javascript: return;" class="btn" data-dismiss="modal">'+false_str+'</a></div>';

	$modalWin.html(modalInnerStr);
	$modalWin.modal();

	return false;
}
