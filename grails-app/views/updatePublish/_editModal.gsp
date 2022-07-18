<g:if test="${update}">
        <div class="modal-header">
            <button data-dismiss="modal" class="close" type="button">×</button>
            <h2>Edit</h2>
        </div>
        <div class="modal-body">
            <g:form action="update" >
                <input type="hidden" value="${update.id}" name="id">
                <table>
                    <tbody>
                    <tr>
                        <th>Title</th>
                        <td>
                            <input type="text" class="input-large" name="title" value="${update?.title ?: ''}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>Type</th>
                        <td>
                            <g:select class="select2Object input-large" name="type" from="${typeForUpdate}" value="${update?.type ?: ''}" optionKey="${{it.key}}"  optionValue="${{it.value}}"></g:select>
                        </td>
                    </tr>

                    <tr>
                        <th>Link</th>
                        <td>
                        <input type="text" class="input-large" name="link" value="${update?.link ?: ''}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>Additional Info</th>
                        <td>
                            <g:textArea name="additionalInfo" rows="5" cols="10" value="${update?.additionalInfo ?: ''}"></g:textArea>
                        </td>
                    </tr>
                    <tr>
                        <th>License type</th>
                        <td>
                            <select class="input-large redBorder select2Object" multiple="multiple" name="licensesType">
                                <g:each in="${licenseTypes}" var="licensesType">
                                    <option value="${licensesType}" ${update?.licensesType?.contains(licensesType) ? "selected" : ""}>${licensesType}</option>
                                </g:each>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>License level</th>
                        <td>
                            <select class="input-large redBorder select2Object" multiple="multiple" name="licensesLevel">
                                <g:each in="${licenceFeatureClass}" var="licensesLevel">
                                    <option value="${licensesLevel}" ${update?.licensesLevel?.contains(licensesLevel) ? "selected" : ""}>${licensesLevel}</option>
                                </g:each>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>IndicatorIds</th>
                        <td>
                            <select class="input-large redBorder select2Object" multiple="multiple" name="indicatorId">
                                <g:each in="${indicatorIds}" var="indicator">
                                    <option value="${indicator}" ${update?.indicatorId?.contains(indicator) ? "selected" : ""}>${indicator}</option>
                                </g:each>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Channel features (choose at least one): </th>
                        <td>
                            <select class="input-large redBorder select2Object" multiple="multiple" name="channelFeatureIds">
                                <g:each in="${channelFeatures}" var="channel">
                                    <option value="${channel?.id}" ${update?.channelFeatureIds?.contains(channel?.id.toString()) ? "selected" : ""}>${channel?.name}</option>
                                </g:each>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Countries</th>
                        <td>
                            <select class="input-large redBorder select2Object" multiple="multiple" name="countries">
                                <g:each in="${countries}" var="country">
                                    <option value="${country.resourceId}" ${update?.countries?.contains(country.resourceId) ? "selected" : ""}>${country?.nameEN}</option>
                                </g:each>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Date expired (Optional - format: DD.mm.yyyy)</th>
                        <td>
                            <g:textField name="dateExpired" value="${formatDate(date: update?.dateExpired, format: 'dd.MM.yyyy')}" class="input-xlarge datepicker" />
                        </td>
                    </tr>
                    <tr>
                        <th>Show as popup</th>
                        <td><input type="checkbox" ${update.showAsPopUp ? 'checked' : ''} name="showAsPopUp"/></td>
                    </tr>
                    <tr>
                        <th>Sticky note</th>
                        <td><input type="checkbox" ${update.stickyNote ? 'checked' : ''} name="stickyNote"/></td>
                    </tr>
                    <tr>
                        <th>Disable </th>
                        <td><input type="checkbox" ${update.disabling ? 'checked' : ''} name="disabling"/></td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <sec:ifAnyGranted roles="ROLE_SALES_VIEW">
                                <opt:submit name="update" class="btn btn-primary ${update?.id}">Save</opt:submit>
                            </sec:ifAnyGranted>
                        </td>
                    </tr>
                    </tbody>

                </table>

            </g:form>
        </div>
    <script>
        $(function () {
            $(".select2Object").select2();
        })
    </script>
</g:if>
