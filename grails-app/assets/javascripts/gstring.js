function GString( value, bindings ){
    // Pass the arguments off to the parent constructor
    // in case there is any magic that the String
    // constructor is going.
    String.apply( this, arguments );
    // Store the value of our string.
    this.value = value;
    // Set up the bindings container (this is where we
    // will map variables names to value for our GString).
    this.bindings = {};
    // Add any initial bindings.
    this.addBindings( bindings );
}
// When creating our GString, we want to extend the
// built-in String object. NOTE: When doing this, we
// have to override the toString() and valueOf()
// methods.
GString.prototype = jQuery.extend(
    // Extend a STRING object instance.
    new String(),
    // Define class functions.
    {
        // I add the given binding to the colleciton.
        addBinding: function( name, value ){
            this.bindings[ name ] = value;
            // Return this object for method chaining.
            return( this );
        },
        // I add the given collection of bindings to the
        // internal collection.
        addBindings: function( collection ){
            // Loop over the given collection.
            for (var name in collection){
                // Add each key to the current bindings.
                this.addBinding(
                    name,
                    collection[ name ]
                );
            }
            // Return this object for method chaining.
            return( this );
        },
        // I apply the bindings to the current string
        // and its placehoders.
        applyBindings: function(){
            var self = this;
            // Replace the inline bindings with the ones
            // we have stored in the bindings collection.
            var result = this.value.replace(
                new RegExp( "\\$\\{([\\w_.-]+)\\}", "g" ),
                function( $0, $1 ){
                    // Check to see if the binding name
                    // exists in our binding collection.
                    if ($1 in self.bindings){
                        // Return the binding value.
                        return( self.bindings[ $1 ] );
                    } else {
                        // The binding was not found, so
                        // just return the original match.
                        return( $0 );
                    }
                }
            );
            // Return the evaluted string.
            return( result );
        },
        // I reset the binding collection.
        clearBindings: function(){
            this.bindings = {};
            // Return this object for method chaining.
            return( this );
        },
        // I return the value of the given binding.
        getBinding: function( name ){
            return( this.bindings[ name ] );
        },
        // I return the raw value (before evaluation).
        getValue: function(){
            return( this.value );
        },
        // I check to see if the given binding exists.
        hasBinding: function( name ){
            return( name in this.bindings );
        },
        // I update the stored value.
        setValue: function( value ){
            this.value = value ;
            // Return this object for method chaining.
            return( this );
        },
        // I convert the object to a string representation.
        // This needs to evaluate the string because it
        // gets used in things like the replace() method.
        toString: function(){
            return( this.valueOf() );
        },
        // I get the value of the string (in its fully
        // evaluated format with integrated bindings).
        valueOf: function(){
            return( this.applyBindings() );
        }
    }
);