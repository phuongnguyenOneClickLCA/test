package com.bionova.optimi.core.taglib

class AutocompleteTagLib {

    def autocompleteTextfield = {  attrs ->
        def items = attrs.items
        def fieldName = attrs.fieldName
        def fieldValue = attrs.fieldValue
        def cssClass = attrs.cssClass
        def minChars = attrs.minChars
        out << renderAutocomplete(items, fieldName, fieldValue, cssClass, minChars)
    }

    def renderAutocomplete(items, fieldName, fieldValue, cssClass, minChars) {
        StringBuilder sb = new StringBuilder()

        if(!fieldValue) {
            fieldValue = ""
        }

        sb << """
        <script type="text/javascript">
          \$(document).ready(function() {
            \$("#${fieldName}").autocomplete({
              source: function(request, response) {
                var matches = \$.map(${items}, function(acItem) {
                  if (acItem.toUpperCase().indexOf(request.term.toUpperCase()) === 0 ) {
                    return acItem
                  }
                })
                response(matches)
              },
              minChars: ${minChars}
            })
          });
        </script>
        <input type="text" name="${fieldName}" value="${fieldValue}" id="${fieldName}" class="${cssClass}" />
        """
    }

    def autocompleteCombobox = { attrs ->
        def fieldId = attrs.selectId
        out << renderAutocompleteCombobox(fieldId)
    }

    def renderAutocompleteCombobox(fieldId) {
        StringBuilder sb = new StringBuilder()

        sb << """
		<script type="text/javascript">
		(function( \$ ) {
		  \$.widget("ui.combobox", {
			_create: function() {
			  this.wrapper = \$( "<span>" )
				.addClass( "comboboxTextfield" )
				.insertBefore( this.element );
			  this._createAutocomplete();
			  this._createShowAllButton();
			},

			_createAutocomplete: function() {
			  var selected = this.element.children( ":selected" ),
				value = selected.val() ? selected.text() : "";

			  this.input = \$( "<input>" )
				.appendTo( this.wrapper )
				.val( value )
				.attr( "title", "" )
				.addClass( "input-xlarge" )
				.autocomplete({
				  delay: 0,
				  minLength: 0,
				  source: \$.proxy( this, "_source" )
				})
				.tooltip({
				  tooltipClass: "ui-state-highlight"
				});

			  this._on( this.input, {
				autocompleteselect: function( event, ui ) {
				  ui.item.option.selected = true;
				  this._trigger( "select", event, {
					item: ui.item.option
				  });
				},

				autocompletechange: "_removeIfInvalid"
			  });
			},

			_createShowAllButton: function() {
			  var wasOpen = false;

			  \$( "<a>" )
				.attr( "tabIndex", -1 )
				.attr( "title", "Show All Items" )
				.tooltip()
				.appendTo( this.wrapper )
				.button({
				  icons: {
					primary: "ui-icon-triangle-1-s"
				  },
				  text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "ui-corner-right ui-combobox-toggle" )
				.mousedown(function() {
				  wasOpen = input.autocomplete( "widget" ).is( ":visible" );
				})
				.click(function() {
				  input.focus();

				  if ( wasOpen ) {
					return;
				  }

				  input.autocomplete( "search", "" );
				});
			},

			_source: function( request, response ) {
			  var matcher = new RegExp( \$.ui.autocomplete.escapeRegex(request.term), "i" );
			  response( this.element.children( "option" ).map(function() {
				var text = \$( this ).text();
				if ( this.value && ( !request.term || matcher.test(text) ) )
				  return {
					label: text,
					value: text,
					option: this
				  };
			  }) );
			},

			_removeIfInvalid: function( event, ui ) {

			  if ( ui.item ) {
				return;
			  }

			  var value = this.input.val(),
				valueLowerCase = value.toLowerCase(), valid = false;
			  this.element.children( "option" ).each(function() {
				if ( \$( this ).text().toLowerCase() === valueLowerCase ) {
				  this.selected = valid = true;
				  return false;
				}
			  });

			  if ( valid ) {
				return;
			  }

			  this.input
				.val( "" )
				.attr( "title", value + " didn't match any item" )
				.tooltip( "open" );
			  this.element.val( "" );
			  this._delay(function() {
				this.input.tooltip( "close" ).attr( "title", "" );
			  }, 2500 );
			  this.input.data( "ui-autocomplete" ).term = "";
			},

			_destroy: function() {
			  this.wrapper.remove();
			  this.element.show();
			}
		  });
		})( jQuery );

		\$(function() {
		  \$( "#${fieldId}" ).combobox();
		 });
		</script>
		"""
    }
}
