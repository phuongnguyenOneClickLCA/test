<div class="row-fluid">
  <div class="span12">
    <div class="content-area">	
	  <button class="btn btn-primary pull-right hide-on-print" onclick="window.print();"><i class="icon-print icon-white"></i></button>
        <asset:image class="hide show-on-print pull-right" src="360optimi-rgb.png" alt="360 Optimi"/>
	  <h3>${entity.name}</h3>	
	</div>
  </div>
</div>

<div class="row-fluid">
  <div class="span12">	
    <div class="content-area">		
      <div class="row-fluid">
      <div class="span4">	
      <g:if test="${entity?.hasImage}">
		<opt:showImage entity="${entity}" />
      </g:if>
      <g:else>
        <g:set var="iconPrefix">${entity?.buildingIconPrefix}</g:set>
	    <g:if test="${iconPrefix}">
	      <i class="buildingtype ${iconPrefix}"></i>
	    </g:if>
      </g:else>
      </div>
     		
      <div class="span8">					
	  <table class="table table-condensed" style="width: 60%;">
	    <opt:entitySummaryData entity="${entity}" />
	    <tr><td colspan="2">&nbsp;</td></tr>
	  </table>
	  </div>
	  </div>		
	</div>
  </div>
  
  <p>&nbsp;</p>
  			
  <div class="row-fluid">
  <div class="span12">	
    <div class="content-area">
      <g:if test="${designs}">
		  <table class="table table-condensed">
	        <thead>
	          <tr>
	            <th style="width: 20%;"><g:message code="entity.details.tab.designs" /></th>
		        <g:each in="${designs}" var="design">
		          <th>${design.name}</th>
		        </g:each>
		      </tr>
		    </thead>
		    <tbody>
		      <g:each in="${selectedDesignIndicators}" var="indicator">
		        <tr>
		          <td style="width: 20%;">${indicator.localizedName}<br /></td>
		          <g:each in="${designs}" var="design">
		            <td> - </td>
		          </g:each>                
		        </tr>        
		      </g:each>
		      <tr>
		        <td colspan="${designs.size + 1}">&nbsp;</td>
		      </tr>
		    </tbody>	    
	      </table> 
      </g:if>  
     
       <g:if test="${operatingPeriods}">
		  <table class="table table-condensed">
	        <thead>
	          <tr>
	            <th style="width: 20%;"><g:message code="entity.details.tab.operatingPeriod" /></th>
		        <g:each in="${operatingPeriods}" var="operatingPeriod">
		          <th>${operatingPeriod.operatingPeriodAndName}</th>
		        </g:each>
		      </tr>
		    </thead>
		    <tbody>
		      <g:each in="${selectedOperatingPeriodIndicators}" var="indicator">
		        <tr class="">
		          <td style="width: 20%;">${indicator.localizedName}</td>
		          <g:each in="${operatingPeriods}" var="operatingPeriod">
		            <td>
		                -
		            </td>
		          </g:each>                
		        </tr>        
		      </g:each>
		      <tr>
		        <td colspan="${operatingPeriods.size + 1}">&nbsp;</td>
		      </tr>
		    </tbody>	    
	      </table> 
      </g:if>
          
    </div>
  </div>
  </div>
</div>
