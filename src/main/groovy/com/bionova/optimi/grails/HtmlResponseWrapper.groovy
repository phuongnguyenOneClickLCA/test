package com.bionova.optimi.grails

import groovy.transform.CompileStatic

import javax.servlet.ServletOutputStream
import javax.servlet.WriteListener
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper

class HtmlResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream capture
    private ServletOutputStream output
    private PrintWriter writer

    HtmlResponseWrapper(HttpServletResponse response) {
        super(response)
        capture = new ByteArrayOutputStream(response.getBufferSize())
    }

    @Override
    @CompileStatic
    ServletOutputStream getOutputStream() {
        if (writer != null) {
            throw new IllegalStateException("getWriter() was called for the response before.")
        }

        if (output == null) {
            output = new ServletOutputStream() {
                @Override
                void write(int b) throws IOException {
                    capture.write(b)
                }

                @Override
                void flush() throws IOException {
                    capture.flush()
                }

                @Override
                void close() throws IOException {
                    capture.close()
                }

                @Override
                boolean isReady() {
                    return false
                }

                @Override
                void setWriteListener(WriteListener writeListener) {}
            }
        }

        return output
    }

    @Override
    @CompileStatic
    PrintWriter getWriter() throws IOException {
        if (output != null) {
            throw new IllegalStateException("getOutputStream() was called before.")}

        if (writer == null) {
            writer = new PrintWriter(new OutputStreamWriter(capture, getCharacterEncoding()))
        }

        return writer
    }

    @Override
    @CompileStatic
    void flushBuffer() throws IOException {
        super.flushBuffer()

        if (writer != null) {
            writer.flush()
        } else if (output != null) {
            output.flush()
        }
    }

    byte[] getCaptureAsBytes() throws IOException {
        if (writer != null) {
            writer.close()
        } else if (output != null) {
            output.close()
        }

        return capture.toByteArray()
    }

    String getCaptureAsString() throws IOException {
        return new String(getCaptureAsBytes(), getCharacterEncoding())
    }

}
