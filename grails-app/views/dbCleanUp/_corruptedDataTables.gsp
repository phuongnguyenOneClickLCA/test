<div id="datasetTable" style="display: none;">
    <table class='table-striped table table-condensed'>
        <thead>
        <tr>
            <th style="width: 200px;">Parent</th>
            <th style="width: 200px;">Child</th>
            <th>Datasets in incompatible units</th>
            <th>Licenses</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${projectsEntitiesWithIncompatibleUnits}" var="project">
            <g:each in="${project.value}" var="entity">
                <g:if test="${entity.value?.get("dataset") && !entity.value?.get("dataset")?.isEmpty()}">
                    <tr>
                        <td style="width: 200px;"><g:link target="_blank" controller="entity" action="show" params="[entityId: entity.key.parentEntityId.toString(), name: entity.key.parentName.toString()]">${entity.key.parentName.toString()}</g:link></td>
                        <td style="width: 200px;">${entity.key.name.toString()}</td>
                        <td><g:each in="${entity.value?.get("dataset")}" var="dataset">${dataset}<br/></g:each></td>
                        <td>
                            <g:set var="licenseList" value="${entity.value?.get("licenseList")}" />
                            <g:if test="licenseList">
                                <g:each var="license" in="${licenseList}">
                                    <opt:link controller="license" action="form" params="[id: license._id]">${license.name}</opt:link> - ${license?.valid ? "Active " : "Not_Active"}
                                    <br>
                                </g:each>
                            </g:if>
                        </td>
                    </tr>
                </g:if>
            </g:each>
        </g:each>
        </tbody>
    </table>
</div>

<div id="thicknessTable" style="display: none;">
    <table class='table-striped table table-condensed'>
        <thead>
        <tr>
            <th style="width: 200px;">Parent</th>
            <th style="width: 200px;">Child</th>
            <th>Datasets in incompatible thickness</th>
            <th>Licenses</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${projectsEntitiesWithIncompatibleUnits}" var="project">
            <g:each in="${project.value}" var="entity">
                <g:if test="${entity.value?.get("thickness") && !entity.value?.get("thickness")?.isEmpty()}">
                    <tr>
                        <td style="width: 200px;"><g:link target="_blank" controller="entity" action="show" params="[entityId: entity.key.parentEntityId.toString(), name: entity.key.parentName.toString()]">${entity.key.parentName.toString()}</g:link></td>
                        <td style="width: 200px;">${entity.key.name.toString()}</td>
                        <td><g:each in="${entity.value?.get("thickness")}" var="dataset">${dataset}<br/></g:each></td>
                        <td>
                            <g:set var="licenseList" value="${entity.value?.get("licenseList")}" />
                            <g:if test="licenseList">
                                <g:each var="license" in="${licenseList}">
                                    <opt:link controller="license" action="form" params="[id: license._id]">${license.name}</opt:link> - ${license?.valid ? "Active " : "Not_Active"}
                                    <br>
                                </g:each>
                            </g:if>
                        </td>
                    </tr>
                </g:if>
            </g:each>
        </g:each>
        </tbody>
    </table>
</div>