<tr id="resourceListHeading">
    <th style="display:none">dataproperty</th><th>nameEN</th><th>ResourceId</th><th>ProfileId</th><th>unitForData</th><th>upstreamDB</th><th>impactGWP100_kgCO2e</th><th>impactODP_kgCFC11e</th><th>impactAP_kgSO2e</th><th>impactEP_kgPO4e</th><th>impactPOCP_kgEthenee</th>
    <th>impactADPElements_kgSbe</th><th>impactADPFossilFuels_MJ</th><th>traciGWP_kgCO2e</th><th>traciODP_kgCFC11e</th><th>traciAP_kgSO2e</th><th>traciEP_kgNe</th><th>traciPOCP_kgO3e</th>
    <th>traciNRPE_MJ</th><th>renewablesUsedAsEnergy_MJ</th><th>renewablesUsedAsMaterial_MJ</th><th>nonRenewablesUsedAsEnergy_MJ</th><th>nonRenewablesUsedAsMaterial_MJ</th><th>recyclingMaterialUse_kg</th><th>renewableRecylingFuelUse_MJ</th>
    <th>nonRenewableRecylingFuelUse_MJ</th><th>cleanWaterNetUse_m3</th><th>wasteHazardous_kg</th><th>wasteNonHazardous_kg</th><th>wasteRadioactive_kg</th><th>Import file</th>
</tr>
<g:each in="${resources}" var="resource">
    <tr>
        <td style="display:none">${resource.dataProperties}</td><td>${resource.nameEN}</td><td>${resource.resourceId}</td><td>${resource.profileId}</td><td>${resource.unitForData}</td><td>${resource.upstreamDB}</td><td>${resource.impactGWP100_kgCO2e}</td>
        <td>${resource.impactODP_kgCFC11e}</td><td>${resource.impactAP_kgSO2e}</td><td>${resource.impactEP_kgPO4e}</td><td>${resource.impactPOCP_kgEthenee}</td>
        <td>${resource.impactADPElements_kgSbe}</td><td>${resource.impactADPFossilFuels_MJ}</td><td>${resource.traciGWP_kgCO2e}</td><td>${resource.traciODP_kgCFC11e}</td>
        <td>${resource.traciAP_kgSO2e}</td><td>${resource.traciEP_kgNe}</td><td>${resource.traciPOCP_kgO3e}</td><td>${resource.traciNRPE_MJ}</td>
        <td>${resource.renewablesUsedAsEnergy_MJ}</td><td>${resource.renewablesUsedAsMaterial_MJ}</td><td>${resource.nonRenewablesUsedAsEnergy_MJ}</td><td>${resource.nonRenewablesUsedAsMaterial_MJ}</td>
        <td>${resource.recyclingMaterialUse_kg}</td><td>${resource.renewableRecylingFuelUse_MJ}</td><td>${resource.nonRenewableRecylingFuelUse_MJ}</td><td>${resource.cleanWaterNetUse_m3}</td>
        <td>${resource.wasteHazardous_kg}</td><td>${resource.wasteNonHazardous_kg}</td><td>${resource.wasteRadioactive_kg}</td><td>${resource.importFile}</td>
    </tr>
</g:each>