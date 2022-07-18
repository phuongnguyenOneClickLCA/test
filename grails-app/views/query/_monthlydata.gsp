<g:each in="[1,2,3,4,5,6,7,8,9,10,11,12]">
    <td class="monthly_data"><input type="text" data-dataSetId="${datasetId}" value="${monthlyAnswers?.get(it.toString())}" name="${fieldName}.${it}" class="numeric input-monthly"${disabled ? ' readonly' : ''} /></td>
</g:each>
