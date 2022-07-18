<g:each in="['Q1','Q2','Q3','Q4']">
    <td class="monthly_data"><input type="text" data-dataSetId="${datasetId}" value="${quarterlyAnswers?.get(it.toString())}" name="${fieldName}.${it}" class="numeric input-monthly"${disabled ? ' readonly' : ''} /></td>
</g:each>