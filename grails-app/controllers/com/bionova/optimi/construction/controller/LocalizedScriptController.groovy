package com.bionova.optimi.construction.controller

class LocalizedScriptController extends ExceptionHandlerController {

    def index() {
        def script = checkValue() + addLanguageInput()
        render(text: script, contentType: "text/html", encoding: "UTF-8")
    }

    private String checkValue() {
        def content = "function checkValue(input, inputLabel, minAllowed, maxAllowed, inputWidth) {\n" +
                "    if (input.value) {\n" +
                "        var value = Number(input.value.replace(\",\", \".\"));\n" +
                "        var alertMessage;\n" +
                "\n" +
                "        if (isNaN(value)) {\n" +
                "            alertMessage = \"${message(code: 'js.question.validation.not_numeric', args: [inputLabel])}\";\n" +
                "        } else {\n" +
                "            if (minAllowed && maxAllowed && (value < minAllowed || value > maxAllowed)) {\n" +
                "                alertMessage = \"${message(code: 'js.question.validation.not_in_limits', args: [inputLabel, minAllowed, maxAllowed])}\";\n" +
                "            } else if (minAllowed && value < minAllowed) {\n" +
                "                alertMessage = \"${message(code: 'js.question.validation.min_value', args: [inputLabel, minAllowed])}\";\n" +
                "            } else if (maxAllowed && value > maxAllowed) {\n" +
                "                alertMessage = \"${message(code: 'js.question.validation.max_value', args: [inputLabel, maxAllowed])}\";\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        if (inputWidth) {\n" +
                "            inputWidth = inputWidth + 'px';\n" +
                "            \$(input).css('width', inputWidth);\n" +
                "        }\n" +
                "\n" +
                "        if (alertMessage) {\n" +
                "            \$(input).css('border','1px solid red');\n" +
                "            alert(alertMessage);\n" +
                "        } else {\n" +
                "            \$(input).css('border', '');\n" +
                "        }\n" +
                "    }\n" +
                "}"
        return content
    }

    private String addLanguageInput() {
        def content = "function addLanguageInput(languageSelect, inputName, inputType, divToAdd) {\n" +
                "    var language = document.getElementById(languageSelect).value;\n" +
                "    divToAdd = \"#\" + divToAdd;\n" +
                "    var inputnameAndLanguage = '#' + inputName + '\\\\[' + language + '\\\\]';\n" +
                "\n" +
                "    if (!(\$( inputnameAndLanguage ).length)) {\n" +
                "        if (\"textField\" == inputType) {\n" +
                "            \$.ajax({type:'POST',data:'language='+language+'&name='+inputName, url:'/360optimi/languagetextfield',\n" +
                "                success:function(data,textStatus){\n" +
                "                    \$(divToAdd).append(data.output);\n" +
                "                },\n" +
                "                error:function(XMLHttpRequest,textStatus,errorThrown){\n" +
                "                }\n" +
                "            });\n" +
                "        } else if (\"textArea\" == inputType) {\n" +
                "            \$.ajax({type:'POST',data:'language='+language+'&name='+inputName, url:'/360optimi/languagetextarea',\n" +
                "                success:function(data,textStatus){\n" +
                "                    \$(divToAdd).append(data.output);\n" +
                "                },\n" +
                "                error:function(XMLHttpRequest,textStatus,errorThrown){\n" +
                "\n" +
                "                }\n" +
                "            });\n" +
                "        }\n" +
                "    } else {\n" +
                "        var alertMessage = \"${message(code: 'js.duplicate_language')}\";\n" +
                "        alert(alertMessage);\n" +
                "    }\n" +
                "}"
        return content
    }
}
