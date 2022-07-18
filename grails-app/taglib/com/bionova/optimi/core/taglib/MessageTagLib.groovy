package com.bionova.optimi.core.taglib

import groovy.transform.CompileStatic
import org.grails.plugins.web.taglib.FormatTagLib
import org.grails.encoder.CodecLookup
import org.grails.taglib.GroovyPageAttributes
import org.springframework.context.MessageSourceResolvable
import org.springframework.context.NoSuchMessageException
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.grails.encoder.Encoder
import com.bionova.optimi.grails.OptimiMessageSource
import org.grails.plugins.web.taglib.ValidationTagLib

class MessageTagLib extends ValidationTagLib {

    OptimiMessageSource messageSource
    CodecLookup codecLookup

    /**
     * Resolves a message code for a given error or code from the resource bundle.
     *
     * @emptyTag
     *
     * @attr error The error to resolve the message for. Used for built-in Grails messages.
     * @attr message The object to resolve the message for. Objects must implement org.springframework.context.MessageSourceResolvable.
     * @attr code The code to resolve the message for. Used for custom application messages.
     * @attr args A list of argument values to apply to the message, when code is used.
     * @attr default The default message to output if the error or code cannot be found in messages.properties.
     * @attr encodeAs The name of a codec to apply, i.e. HTML, JavaScript, URL etc
     * @attr locale override locale to use instead of the one detected
     */
    Closure message = { attrs ->
        messageImpl(attrs)
    }

    @CompileStatic
    def messageImpl(Map attrs) {
        Locale locale = FormatTagLib.resolveLocale(attrs.locale)
        def tagSyntaxCall = (attrs instanceof GroovyPageAttributes) ? attrs.isGspTagSyntaxCall() : false

        def text
        Object error = attrs.error ?: attrs.message
        if (error) {
            if (!attrs.encodeAs && error instanceof MessageSourceResolvable) {
                MessageSourceResolvable errorResolvable = (MessageSourceResolvable) error
                if (errorResolvable.arguments) {
                    error = new DefaultMessageSourceResolvable(errorResolvable.codes,
                            encodeArgsIfRequired(errorResolvable.arguments) as Object[], errorResolvable.defaultMessage)
                }
            }
            try {
                if (error instanceof MessageSourceResolvable) {
                    text = messageSource.getMessageSilently(error, locale)
                } else {
                    text = messageSource.getMessageSilently(error.toString(), null, locale)
                }
            }
            catch (NoSuchMessageException e) {
                if (error instanceof MessageSourceResolvable) {
                    text = ((MessageSourceResolvable) error).codes[0]
                } else {
                    text = error?.toString()
                }
            }
        } else if (attrs.code) {
            String code = attrs.code?.toString()
            List args = []
            if (attrs.args) {
                args = attrs.encodeAs ? attrs.args as List : encodeArgsIfRequired(attrs.args)
            }
            String defaultMessage
            if (attrs.containsKey('default')) {
                defaultMessage = attrs['default']?.toString()
            } else {
                defaultMessage = code
            }

            def message
            if (attrs.dynamic) {
                message = messageSource.getMessageSilently(code, args == null ? null : args.toArray(),
                        defaultMessage, locale)
            } else {
                message = messageSource.getMessage(code, args == null ? null : args.toArray(),
                        defaultMessage, locale)
            }

            if (message != null) {
                text = message
            } else {
                text = defaultMessage
            }
        }
        if (text) {
            Encoder encoder = codecLookup.lookupEncoder(attrs.encodeAs?.toString() ?: 'raw')
            return encoder ? encoder.encode(text) : text
        }
        ''
    }

    @CompileStatic
    private List encodeArgsIfRequired(arguments) {
        arguments.collect { value ->
            if (value == null || value instanceof Number || value instanceof Date) {
                value
            } else {
                Encoder encoder = codecLookup.lookupEncoder('HTML')
                encoder ? encoder.encode(value) : value
            }
        }
    }
}
