<asset:javascript src="rsee.js"/>
<table class="table-striped table table-condensed" style="table-layout: fixed;">
    <thead>
    <tr>
        <th>
            <b>${message(code: "design")}</b>
        </th>
        <th>
            <b>Batiment</b>
            <a href="javascript:" class="fiveMarginLeft"
               title="${message(code: 'query.rset_mapping_help')}">
                <i class="icon-question-sign"></i>
            </a>
        </th>
        <th>
            <b>Zones</b>
            <a href="javascript:" class="fiveMarginLeft"
               title="${message(code: 'query.rset_mapping_help')}">
                <i class="icon-question-sign"></i>
            </a>
        </th>
        <g:if test="${renderParcelleCheckbox}">
            <th>
                <b>Parcelle</b>
                <a href="javascript:" class="fiveMarginLeft"
                   title="${message(code: 'query.parcelle_rset_mapping_help')}">
                    <i class="icon-question-sign"></i>
                </a>
            </th>
        </g:if>
        <g:if test="${isRseeFormat}">
            <th>
                <b>${message(code: 'query.rsee_excel_import_header')}</b>
                <a href="javascript:" class="fiveMarginLeft"
                   title="${message(code: 'query.rsee_excel_import_help')}">
                    <i class="icon-question-sign"></i>
                </a>
            </th>
        </g:if>
    </tr>
    </thead>

    <tbody>
    <g:if test="${designRows}">
        <g:each in="${designRows}" var="designRow">
            <tr>
                <td>${designRow.designName}</td>
                <td>
                    <g:if test="${designRow.rsetBatiments}">
                        <select id="selectDd${designRow.designId}"
                                class="batimentSelect${questionId}" style="max-width: 150px;"
                                name="batimentMapping.${questionId}.${designRow.designId}"
                                onchange="resolveBatiments(this, '${parentId}', '${questionId}', '${designRow.designId}');
                                enableInjectFromRsetButton('${questionId}', ${renderParcelleCheckbox});
                                enableImportFromRseeButton('${designRow.designId}')">
                            <option></option>
                            <g:each in="${designRow.rsetBatiments}" var="rsetBatiment">
                                <option value="${rsetBatiment.index}"
                                    ${rsetBatiment.isSelected ? "selected=\"selected\"" : ''}
                                    ${rsetBatiment.isDisabled ? 'disabled' : ''}>
                                    ${rsetBatiment.name}
                                </option>
                            </g:each>
                        </select>
                    </g:if>
                </td>
                <td class="zoneMappingContainer">
                    <g:if test="${designRow.zonesForRender}">
                        <g:render template="/query/inputs/rsetZoneMapping"
                                  model="[zonesForRender: designRow.zonesForRender, questionId: questionId, renderParcelleCheckbox: renderParcelleCheckbox, designId: designRow.designId]"/>
                    </g:if>
                </td>
                <g:if test="${renderParcelleCheckbox}">
                    <td class="parcelleContainer">
                        <opt:greenSwitch name="parcelleMapping.${questionId}" value="${designRow.designId}"
                                         checked="${designRow.isSelectedForParcelle}"
                                         class="parcelle-switch"
                                         onclick="uncheckOtherParcelleCheckbox(this); enableInjectFromRsetButton('${questionId}', ${renderParcelleCheckbox})"/>
                    </td>
                </g:if>
                <g:if test="${isRseeFormat}">
                    <td class="rseeImportContainer">

                        <a id="importFromRseeButton${designRow.designId}" class="btn btn-primary removeClicks"
                           style="font-weight: bold;"
                           href="javascript:"
                           onclick="downloadImportMapperFileFromRsee('${parentId}', '${designRow.designId}', '${importMapper.applicationId}', '${importMapper.importMapperId}', '${indicatorId}');">${message(code: 'query.rsee_excel_import')}</a>

                    </td>
                    <script>enableImportFromRseeButton("${designRow.designId}")</script>
                </g:if>
            </tr>
        </g:each>
    </g:if>
    </tbody>
</table>