    <div class="modal-header" id="importFromEntitiesContent">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h2><g:message code="importDatasets.headers"/></h2>
    </div>

    <div class="modal-body">
        <p>
            <g:if test="${!mainPageCheck}"><g:message code="importDatasets.notice"/></g:if><g:else><g:message code="importDatasets.new_notice"/></g:else>
        </p>
        <form class="form" action="/app/sec/query/importFromDesignDatasets" method="POST" id="copyAnwersForm">
            <g:set var="language" value="${org.springframework.context.i18n.LocaleContextHolder.getLocale().getLanguage().toUpperCase()}"/>
            <div class="clearfix"></div>

            <div class="tab-content">
                <div class="tab-pane active" id="tab-details">
                    <input type="hidden" name="targetEntityId" value="${targetEntityId}"/>
                    <input type="hidden" name="entityId" value="${entityId}"/>
                    <input type="hidden" name="queryId" value="${queryId}"/>
                    <input type="hidden" name="indicatorId" value="${indicatorId}"/>
                    <input type="hidden" name="mainPageCheck" value="${mainPageCheck}"/>
                    <select name="fromEntityId" class="selectDesignToImport" data-placeholder="${message(code: 'select.design')}" style="width: 75%" onchange="disableButtonCopyDesign()">
                        <g:render template="/query/copiableDesignOptions" model="[copyEntitiesMapped: copyEntitiesMapped, basicQuery: basicQuery, indicatorCompatible:indicatorCompatible]"/>
                    </select>
                </div>
            </div>

            <div class="clearfix"><br/><br/></div>
            <label><input id="mergeDataset" class="radio" value="mergeDataset" name="mergeDataset" type="checkbox"/> <g:message
                    code="importMapper.index.incremental_import"/><a href="#" class="infopopover" style="padding-left: 5px" data-toggle="popover" data-placement="right" rel="popover" data-trigger="hover" data-content="${message(code: "importDatasets.notice")}"><i class="icon-question-sign"></i></a>
            </label>
            <label><input id="overwriteExistingOnly" class="radio" value="overwriteExistingOnly" name="overwriteExistingOnly" type="checkbox"/> <g:message code="importMapper.overwrite_exisiting_data"/>
                <a href="#" class="infopopover" style="padding-left: 5px" data-toggle="popover" data-placement="right" rel="popover" data-trigger="hover" data-content="${message(code: "importMapper.overwrite_exisiting_warning")}"><i class="icon-question-sign"></i></a>
            </label><br/>
            <a href="javascript:" data-dismiss="modal" class="btn btn-default"><g:message code="cancel"/></a>
            <input type="hidden" name="typeOfImport"/>
            <button id="copyAll" value="all" class="btn btn-primary buttonImportDatasets" type="submit" onclick="submitAndSwal()"><g:message code="copy_all"/></button>
            <g:if test="${!mainPageCheck && queryName}">
                <button id="copyOne" value="one" class="btn btn-primary buttonImportDatasets" type="submit" onclick="submitAndSwal()"><g:message code="copy_one" args="${queryName}"/></button>
            </g:if>
        </form>

    </div>

<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="popover"]').popover();

        //Set warning for overwriting
        $('.buttonImportDatasets').on('click', function () {
            $('input[name=typeOfImport]').val($(this).val())
        });


        $(".radio").on('click', function() {
            if (!$(this).is(":checked")) {
                $(".radio").prop("checked", false);
            } else {
                $(".radio").prop("checked", false);
                $(this).prop("checked", true);
            }
        });
    });

    function submitAndSwal(){
        var form = $("#copyAnwersForm");
        event.preventDefault();
        var warningTitle = "${g.message(code: "warning")}";
        var warningContent = "${g.message(code: "importDatasets.notice")}";
        var confirmButtonText = "${message(code: "overwrite")}";

        if ($("input[id=mergeDataset]").is(":checked")) {
            warningContent = "${g.message(code: "importDatasets.notice_merge")}";
            confirmButtonText = "${message(code: "merge")}";
        }

        if ($("input[id=overwriteExistingOnly]").is(":checked")) {
            warningContent = "${g.message(code: "importMapper.overwrite_exisiting_warning")}";
            confirmButtonText = "${message(code: "overwrite")}";
        }

        let calculationResultSize = ${originalDesign.calculationResults?.size()};

        //if target design has existing data only then, the warning window will pop up
        //if a design calculationResult size is, then this design does not has any data
        if (calculationResultSize > 0) {
            Swal.fire({
                title: warningTitle,
                text: warningContent,
                icon: 'warning',
                showCancelButton: true,
                showCloseButton: true,
                reverseButtons: true,
                confirmButtonColor: '#d62317',
                cancelButtonColor: '#bbbdb7',
                confirmButtonText: confirmButtonText,
                cancelButtonText: "${message(code: "cancel")}",
            }).then(result => {
                if (result.value) {
                    importData(form)
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '${message(code: 'importDatasets.error')}',
                        html: '${message(code: 'importDatasets.error.info')}'
                    })
                }
            });
        } else {
            importData(form)
        }
    }

    function importData(form){
        window.onbeforeunload = null
        Swal.fire({
            title: '${g.message(code: "loading.title")}',
            text: '${g.message(code: "loading.working")}',
            allowOutsideClick: false,
            allowEscapeKey: false,
            allowEnterKey: false,
            onOpen: () => {
                swal.showLoading();
                $(form).submit()
            }
        })
    }
</script>