<table class="result_indicator">
    <tr>
        <th><g:message code="results.table.points"/></th><th><g:message
            code="results.table.good_level"/></th><th><g:message code="results.table.aptitude"/></th>
    </tr>
    <tr>
        <td>95 - 100</td>
        <td><asset:image src="results/A.png" alt=""/></td>
        <td>
            <g:if test="${score && score >= 95}">
                <asset:image src="results/indicator_A.png" alt=""/>
            </g:if>
        </td>
    </tr>
    <tr>
        <td>85 - 94</td>
        <td><asset:image src="results/B.png" alt=""/></td>
        <td>
            <g:if test="${score && score >= 85 && score < 95}">
                <asset:image src="results/indicator_B.png" alt=""/>
            </g:if>
        </td>
    </tr>
    <tr>
        <td>75 - 84</td>
        <td><asset:image src="results/C.png" alt=""/></td>
        <td>
            <g:if test="${score && score >= 75 && score < 85}">
                <asset:image src="results/indicator_C.png" alt=""/>
            </g:if>
        </td>
    </tr>
    <tr>
        <td>65 - 74</td>
        <td><asset:image src="results/D.png" alt=""/></td>
        <td>
            <g:if test="${score && score >= 65 && score < 75}">
                <asset:image src="results/indicator_D.png" alt=""/>
            </g:if>
        </td>
    </tr>
    <tr>
        <td>55 - 64</td>
        <td><asset:image src="results/E.png" alt=""/></td>
        <td>
            <g:if test="${score && score >= 55 && score < 65}">
                <asset:image src="results/indicator_E.png" alt=""/>
            </g:if>
        </td>
    </tr>
    <tr>
        <td>45 - 54</td>
        <td><asset:image src="results/F.png" alt=""/></td>
        <td>
            <g:if test="${score && score >= 45 && score < 55}">
                <asset:image src="results/indicator_F.png" alt=""/>
            </g:if>
        </td>
    </tr>
    <tr>
        <td><g:message code="results.table.low_result" args="[45]"/></td>
        <td><asset:image src="results/G.png" alt=""/></td>
        <td>
            <g:if test="${score && score < 45}">
                <asset:image src="results/indicator_G.png" alt=""/>\
            </g:if>
        </td>
    </tr>
    <tr>
        <th>&nbsp;</th><th><g:message code="results.table.bad_level"/></th><th>&nbsp;</th>
    </tr>
</table>
