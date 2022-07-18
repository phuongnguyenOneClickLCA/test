<g:set var="companyName" value="${applicationContext.userService.getAccount(user)?.companyName}"/>
<g:set var="localizationService" bean="localizationService"/>

<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2><g:message code="send_me_data"/></h2>
</div>

    <div class="modal-body">
        <g:form action="saveAndSend" controller="epdRequest" name="sendDataForm">
            <div id="stepModal1" class="modalStep">
            <div class="inline-block"><h4><g:message code="send_me_data.step1_description"/> - <g:message code="step"/> 1/ 4</h4></div>
            <p><g:message code="send_me_data.general_description1"/> </p>
                <input class="hidden" name="senderEmail" value="${user?.username}"/>
                <input class="hidden" name="senderName" value="${user?.name}"/>
                <input class="hidden" name="sendingOrganisation" value="${companyName}"/>
                <input class="hidden" name="entityId" value="${entity?.id}"/>
                <input class="hidden" name="entityName" value="${entity?.name}"/>
                <h4><g:message code="send_me_data.form_header1"/></h4>

                <table class="table-condensed table-condensed">
                    <tr>
                        <td><lable><strong><g:message code="country_manufacture"/></strong></lable></td>
                        <td>
                            <select class="select2SendData dataFilterInit width-fixed-90 sendingParameter mandatoryForEpd" name="countryForSendData">
                                <option></option>
                                <g:each in="${countries}" var="country">
                                    <option value="${country?.resourceId}"> ${localizationService.getLocalizedProperty(country, "name", "name", "Localized name missing")}</option>
                                </g:each>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="product_type"/></strong></lable></td>
                        <td>
                            <select class="select2SendData dataFilterInit width-fixed-90 sendingParameter mandatoryForEpd" name="subtypeForSendData">
                                <option></option>
                                <g:each in="${subTypes}" var="subType">
                                    <option value="${subType?.subType}"> ${localizationService.getLocalizedProperty(subType, "name", "name", "Localized name missing")}</option>
                                </g:each>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="manufacturer_name"/></strong></lable></td>
                        <td>
                            <input type="text" class="width-fixed-90 sendingParameter mandatoryForEpd redBorder" name="manufacturer"/>
                            %{--<select class="select2SendData dataFilterInit width-fixed-90 sendingParameter mandatory" name="manufacturer">
                                <option></option>
                                <g:each in="${manufacturers}" var="manufacturer">
                                    <option value="${manufacturer}"> ${manufacturer}</option>
                                </g:each>
                            </select>--}%
                        </td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="entity.show.task.description"/></strong></lable></td>
                        <td>
                            <g:textArea rows="2" cols="15" class="width-fixed-90 sendingParameter"  name="productDescription"/>
                        </td>
                    </tr>
                </table>
            <div>
                <h4><g:message code="send_me_data.existing_data_header"/> : <span id="dataFoundSize"></span><i id="sendDataSpinner" style="padding-right: 4px;" class="fas fa-circle-notch fa-spin oneClickColorScheme hidden"></i></h4>
                <p><g:message code="send_me_data.existing_data_description"/></p>
                <div style="width: 100%; max-height: 150px; overflow: auto">
                    <table class="table table-striped table-condensed" id="resourcesFound">
                        <tr><td>${message(code: 'resource.not_found')}</td></tr>
                    </table>
                </div>

            </div>
        </div>
            <div id="stepModal2" class="hidden modalStep">
                <div class="inline-block"><h4><g:message code="send_me_data.step2_description"/>  - <g:message code="step"/> 2/ 4 </h4></div>

                <div>
                    <h4><g:message code="send_me_data.form_header2"/></h4>
                    <table class="left-align table-condensed table-condensed">
                        <tr>
                            <td><lable><strong><g:message code="project_description"/></strong></lable></td>
                            <td>${basicQueryTable}</td>
                        </tr>
                        <tr>
                            <td><lable><strong><g:message code="send_me_data.estimated_demand"/></strong></lable></td>
                            <td>
                                <input type="text" class="sendingParameter" name="estimatedDemand"/>
                            </td>
                        </tr>
                        <tr>
                            <td><lable><strong><g:message code="send_me_data.deadline"/></strong></lable></td>
                            <td>
                                <opt:textField name="deadline" value="" class="input-xlarge datepicker sendingParameter" autocomplete="off"/>
                            </td>
                        </tr>
                        <tr>
                            <td><lable><strong><g:message code="send_me_data.compliance"/></strong></lable></td>
                            <td>
                                <select class="sendingParameter" name="complianceStandard">
                                    <option value="1"><g:message code="send_me_data.compliance_opt1"/></option>
                                    <option value="2"><g:message code="send_me_data.compliance_opt2"/></option>
                                    <option value="3"><g:message code="send_me_data.compliance_opt3"/></option>
                                    <option value="4"><g:message code="send_me_data.compliance_opt4"/></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><lable><strong><g:message code="send_me_data.compliance_other"/></strong></lable></td>
                            <td>
                                <input type="text" class="sendingParameter" name="complianceStandardOther"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div>
                    <h4><g:message code="send_me_data.tech_required_heading"/></h4>
                    <p><g:message code="send_me_data.tech_required_requirement"/></p>
                    <label><input type="checkbox" name="thirdPartyVerified" class="sendingParameter mandatoryForEpd redBorderCheckbox"> <g:message code="send_me_data.thirdPartyVerified"/></label>
                    <label><input type="checkbox" name="privateEPDVerified" class="sendingParameter mandatoryForEpd redBorderCheckbox"> <g:message code="send_me_data.privateEPDVerified"/></label>
                    <label><input type="checkbox" name="productCarbonVerified" class="sendingParameter mandatoryForEpd redBorderCheckbox"> <g:message code="send_me_data.productCarbonVerified"/></label>
                    <label><input type="checkbox" name="productSpecific" class="sendingParameter mandatoryForEpd redBorderCheckbox"> <g:message code="send_me_data.productSpecific"/></label>
                    <label><input type="checkbox" name="projectSpecific" class="sendingParameter mandatoryForEpd redBorderCheckbox"> <g:message code="send_me_data.projectSpecific"/></label>
                </div>
            </div>
            <div id="stepModal3" class="hidden modalStep">
                <div class="inline-block"><h4><g:message code="send_me_data.step3_description"/> - <g:message code="step"/> 3 / 4 </h4></div>
                <p><g:message code="send_me_data.general_description3"/> </p>
                <h4><g:message code="send_me_data.form_header3"/></h4>
                <table class="left_align table-condensed table-condensed">
                    <tr>
                        <th><g:message code="send_me_data.sender"/></th>
                        <td>
                            <strong><g:message code="entity.name"/></strong>: ${user.name} <br/>
                            <strong><g:message code="account.email"/></strong>: ${user.username} <br/>
                            <strong><g:message code="user.organization"/></strong>: ${companyName} <br/>
                            <strong><g:message code="user.phone"/></strong>: ${user.phone} <br/>
                        </td>
                    </tr>
                    <tr>
                        <th><g:message code="send_me_data.recipient"/> </th>
                        <td><input type="email" class="sendingParameter mandatoryForEpd form-control is-invalid redBorder" name="recipientEmail" data-parsley-required="true" data-parsley-type="email"
                                   data-parsley-error-message="${message(code: 'user.username.email.invalid')}"/> </td>
                    </tr>
                </table>
            </div>
            <div id="stepModal4" class="hidden modalStep">
                <div class="inline-block"><h4><g:message code="send_me_data.step4_description"/> - <g:message code="step"/> 4 / 4 </h4></div>
                <p><g:message code="send_me_data.general_description4"/> </p>
                <table class="table-condensed table-condensed table-striped" style="width: 90% !important">
                    <tr>
                        <td><lable><strong><g:message code="country_manufacture"/></strong></lable></td>
                        <td>
                            <span data-id="countryForSendData"></span>
                        </td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="product_type"/></strong></lable></td>
                        <td><span data-id="subtypeForSendData"></span></td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="manufacturer_name"/></strong></lable></td>
                        <td><span data-id="manufacturer"></span></td>
                    </tr>
                    <tr>
                        <td><label><strong><g:message code="send_me_data.recipient"/></strong></label> </td>
                        <td><span data-id="recipientEmail"></span> </td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="entity.show.task.description"/></strong></lable></td>
                        <td><span data-id="productDescription"></span></td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="send_me_data.estimated_demand"/></strong></lable></td>
                        <td><span data-id="estimatedDemand"></span></td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="send_me_data.deadline"/></strong></lable></td>
                        <td>
                            <span data-id="deadline"></span>
                        </td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="send_me_data.compliance"/></strong></lable></td>
                        <td><span data-id="complianceStandard"></span></td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="send_me_data.compliance_other"/></strong></lable></td>
                        <td><span data-id="complianceStandardOther"></span></td>
                    </tr>
                    <tr>
                        <td><lable><strong>${message(code:"send_me_data.tech_required_heading").toLowerCase().capitalize()}</strong></lable></td>
                        <td>
                            <ul>
                                <li class="hidden" data-id="thirdPartyVerified"><g:message code="send_me_data.thirdPartyVerified"/></li>
                                <li class="hidden" data-id="privateEPDVerified"><g:message code="send_me_data.privateEPDVerified"/></li>
                                <li class="hidden" data-id="productCarbonVerified"><g:message code="send_me_data.productCarbonVerified"/></li>
                                <li class="hidden" data-id="productSpecific"><g:message code="send_me_data.productSpecific"/></li>
                                <li class="hidden" data-id="projectSpecific"><g:message code="send_me_data.projectSpecific"/></li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td><lable><strong><g:message code="project_description"/></strong></lable></td>
                        <td>${basicQueryTable}</td>
                    </tr>
                    <tr>
                        <td><label><strong><g:message code="send_me_data.sender"/></strong></label></td>
                        <td>
                            <strong><g:message code="entity.name"/></strong>: ${user.name} <br/>
                            <strong><g:message code="account.email"/></strong>: ${user.username} <br/>
                            <strong><g:message code="user.organization"/></strong>: ${companyName} <br/>
                            <strong><g:message code="user.phone"/></strong>: ${user.phone} <br/>
                        </td>
                    </tr>

                </table>
            </div>
        </g:form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-default pull-left" id="cancelBtn" type="button" onclick="prevStep()"><g:message code="cancel"/> </button>
        <button class="btn btn-primary pull-right" disabled="disabled" id="confirmBtn" onclick="nextStep(event);"><g:message code="continue"/> </button>

    </div>

<script type="text/javascript">
    var currentStep = 1
    $(function () {
        var resourceSelect = $(".select2SendData")
        $(resourceSelect).select2({tag:true, containerCssClass: "redBorder"})

        $(".mandatoryForEpd").on("change keyup",function () {
            validateAndEnable(".mandatoryForEpd", "#confirmBtn")
        })
        $(".form-control").on("change keypress",function () {
            $(this).parsley().validate()

        })
        $("#deadline").datepicker({
            dateFormat: 'dd.mm.yy',
            minDate:0
        });
        window.Parsley.on('field:validated', function(e) {
            if (e.validationResult.constructor!==Array) {
                this.$element.closest('.form-control').removeClass('is-invalid redBorder').addClass('is-valid');
            } else {
                this.$element.closest('.form-control').removeClass('is-valid').addClass('is-invalid redBorder');
            }
        });
        $(".dataFilterInit").on("change",function () {
            $(this).next().find(".select2").removeClass("redBorder")
            $(this).siblings(".select2").children().children(".redBorder").removeClass("redBorder")
            var countryId = $("[name='countryForSendData'] option:selected").val();
            var subtypeId = $("[name='subtypeForSendData'] option:selected").val();
            if(countryId && subtypeId){
                setTimeout(findResource(countryId,subtypeId), 1000)
            }
        })
        $("").on("keydown", function () {

        })
    })
    function nextStep(e) {
        currentStep++
        e.preventDefault()
        if(currentStep > 4){
            Swal.fire({
                title: "${message(code: 'send_me_data.confirm_message')}",
                confirmButtonText: "${message(code: 'send_me_data.btn_send')}",
                confirmButtonColor: '#8DC73F',
                cancelButtonText: "${message(code: "cancel")}",
                cancelButtonColor: '#CDCDCD',
                showCancelButton: true,
                allowOutsideClick: false,
                reverseButtons: true,
                customClass: 'swal-medium',
                showLoaderOnConfirm: true,
                preConfirm: function () {
                    return new Promise(function (resolve) {
                        $.ajax({
                            url: '/app/sec/epdRequest/saveAndSend',
                            type: 'POST',
                            data: new FormData($("#sendDataForm").get(0)),
                            processData: false,
                            contentType: false,
                            /*async: false,*/
                            success: function () {
                                resolve()
                            },
                            error: function (error, textStatus) {
                                console.log(error+ ": "+ textStatus)
                            }
                        });
                    })
                }
            }).then(result => {
                if (result.value) {
                    $("#sendMeDataModal").modal("hide")
                    Swal.fire({
                        icon: 'success',
                        title: "Request sent!",
                        text:"${message(code:'send_me_data.request_sent')}",
                        confirmButtonText: "OK",
                        confirmButtonColor: '#8DC73F'
                    })
                } else {
                    $("#sendMeDataModal").modal("hide")
                }
            }).catch(function (error) {
                console.log(error)
            })
        } else {
            changeBtnText(currentStep)
            $(".modalStep").addClass("hidden")
            $("#stepModal"+currentStep.toString()).removeClass("hidden");
            validateAndEnable(".mandatoryForEpd", "#confirmBtn")
            appendDataToSummary()
        }
    }
    function prevStep() {
        currentStep--
        if(currentStep == 0){
            $("#sendMeDataModal").modal("hide")
        } else {
            changeBtnText(currentStep)
            $(".modalStep").addClass("hidden");
            $("#stepModal"+currentStep.toString()).removeClass("hidden");
            validateAndEnable(".mandatoryForEpd", "#confirmBtn")
        }
    }
    function findResource(countryId,subtypeId) {
        var jSon = {
            countryId: countryId,
            subtypeId:subtypeId,
            entityId:"${entity.id}",
            indicatorId:"${indicatorId}",
            isPlanetaryOnly:'${isPlanetaryOnly}'
        }
        var url ="/app/sec/util/simpleResourceSearch"
        $.ajax({
            url:url,
            data: JSON.stringify(jSon),
            contentType: "application/json; charset=utf-8",
            type:"POST",
            async: false,
            dataType: 'json',
            beforeSend:function () {
                $("#sendDataSpinner").removeClass("hidden")
            },
            success: function(data,textStatus){
                    if(data){
                        $("#resourcesFound").empty().append(data.output.table)
                        $("#dataFoundSize").empty().text(data.output.size + " ${message(code: 'import.import_resource')}")
                    }
            },
            error: function(error, textStatus){
                console.log(error+ ": "+ textStatus)
            },
        }).done(function(){
            $("#sendDataSpinner").addClass("hidden")
        }
    )
    }
    function changeBtnText(step){
        if(step == 1){
            $("#cancelBtn").text("${message(code: 'cancel')}")
            $("#confirmBtn").text("${message(code: 'continue')}")
        } else if(step == 4){
            $("#confirmBtn").text("${message(code: 'send_me_data.btn_send')}")
            $("#cancelBtn").text("${message(code: 'back')}")
        } else {
            $("#cancelBtn").text("${message(code: 'back')}")
            $("#confirmBtn").text("${message(code: 'continue')}")
        }
    }
    function copyResourceName(element){
        var targetBox = $(element).attr("name")
        var resourceName = $("#"+targetBox).text()
        var $temp = $("<input>");
        $("body").append($temp);
        $temp.val(resourceName).select();
        document.execCommand("copy");
        $temp.remove();
        Swal.fire({
            icon: 'info',
            title: "${message(code: 'send_me_data.data_find_info')}",
        }).then(result => {
            if (result.dismiss) {
                $("#sendMeDataModal").modal("hide")
            }
        })
    }
    function appendDataToSummary(){
        var dataValue = $(".sendingParameter")
        $(dataValue).each(function (i) {
            var item = $(dataValue[i])
            var value = $(item).val()
            var id = $(item).attr("name")
            if($(item).attr("type") == "checkbox"){
                if($(item).is(":checked")){
                    $("[data-id='"+id+"'").removeClass("hidden")
                } else {
                    $("[data-id='"+id+"'").addClass("hidden")
                }
            }else {
                if($(item).is("select")){
                    value = $(item).children("option:selected").text()
                }
                $("[data-id='"+id+"'").text(value)
            }
        })
    }
    function validateAndEnable(classToCheck, buttonId) {
        var dataValue = $(classToCheck)
        var disable = false
        $(dataValue).each(function (i) {
            var item = $(dataValue[i])
            if($(item).is(":visible")){
                if($(item).hasClass("is-invalid")){
                    disable = true
                }
                if($(item).attr('type') == "checkbox"){
                    if($('.mandatoryForEpd[type=checkbox]:checked').length < 1){
                        disable = true
                        $('.mandatoryForEpd[type=checkbox]').addClass("redBorderCheckbox")
                    } else {
                        $('.mandatoryForEpd[type=checkbox]').removeClass("redBorderCheckbox")
                    }
                } else {
                    if(!$(item).val()){
                        disable = true
                        $(item).addClass("redBorder")
                    } else {
                        $(item).removeClass("redBorder")
                    }
                }
            }
        })
        $(buttonId).prop("disabled",disable)
    }
</script>