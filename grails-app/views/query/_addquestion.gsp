<g:set var="resourceTableId"><g:replace value="${divId}Resources" source="." target="" /></g:set><%-- 
--%><g:set var="resourceTableId" value="${resourceTableId.toString().trim()}" /><%-- 
--%><g:set var="inputPrefix" value="${mainSectionId}.${question?.questionId}" /><%-- 
--%><g:set var="addButtonId"><g:replace value="${divId}ButtonAdd" source="." target="" /></g:set><%--
--%><g:set var="addButtonId" value="${addButtonId.toString().trim()}" /><%-- 
--%><g:set var="additionalQuestions" value="${question?.defineAdditionalQuestions(indicator, section, null, entity?.parentEntityId ? entity?.getParentById() : entity)}" /><%--
--%><g:set var="quantity" value="${question?.defineQuantity(section)}" /><%-- 
--%>
<script type="text/javascript">
	var index = 1;
	var trId;
	
	function addResource(buttonId, resourceTableId, inputPrefix, additionalQuestionIds, sectionId, questionId) {
		var addButtonId = '#' + buttonId;
		
	    $(addButtonId).on('click', function (event) {
	  	    trId = resourceTableId + 'New' + index;
	  	    resourceTableId = '#' + resourceTableId;
	        var selectId = resourceTableId + 'SelectNew' + index;
	        var textId = resourceTableId + 'TextNew' + index;
	        var additionalQuestionNames = [];
	        var originalSelectValue = $(selectId).val().trim();
	        var originalSelectText = $(selectId option:selected").text().trim();
	        $(selectId).val("");
	        event.preventDefault();

	        if (originalSelectValue) {
	      	  var unit;
	    		
	      	  if (originalSelectValue.split(" ")[1]) {
	      	    unit = originalSelectValue.split(" ")[1]
	      	    originalSelectValue = originalSelectValue.split(" ")[0]
	      	    originalSelectText = originalSelectText.split("(")[0]
	      	  }
	      	  var resourceId = originalSelectValue;
	      	  var resourceTableHeader = resourceTableId + 'header';
	          $(resourceTableHeader).css("visibility", "visible");
	          var theNames = additionalQuestionIds.split(",");
	  		
			  for (var i = 0; i < theNames.length; i++) {
				  additionalQuestionNames[i] = sectionId + '.' + questionId + '_additional_' + theNames[i];
			  }
	  	      var resourceInput = "<tr id=\"" + trId + "\"><td class=\"first\"><input type=\"hidden\" name=\"${fieldName}\" value=\"" + resourceId + "\" />" + originalSelectText + "</td>";<%--
	        --%><g:if test="${quantity}">
	  			var quantityName = '${inputPrefix}' + '.' + resourceId;
	  	        resourceInput = resourceInput + "<td><input type=\"text\" id=\"" + textId + "\" value=\"\" name=\"" + quantityName + "\" class=\"input-mini\" />";

	  	        if(unit) {
	  	        	resourceInput = resourceInput + " <span class=\"add-on\">" + unit + "</span></td>";
	  		    }
	  	      </g:if><%--
	  	   --%><g:each in="${additionalQuestions}" var="additionalQuestion"><%--
	  	    --%>resourceInput = resourceInput + " ${render(template:"/query/additionalquestion", model:[entity:entity, query:query, section:section, mainSectionId:mainSectionId, additionalQuestion:additionalQuestion, mainQuestion:question, isJavascript: true])}";
	  	      <%--
	  	  --%></g:each><%--
	  	  --%>resourceInput = resourceInput +  "<td><a href=\"javascript:;\" onclick=\"removeResource('" + trId + "');\"><strong>\${message(code:'delete')}</strong></a></td>";
	  	      resourceInput = resourceInput + "</tr>";
	  	      $('#${resourceTableId}').append(resourceInput);
	  	      var additionalQuestionNamesAsString = "";
	  	
	  	  	  if (additionalQuestionNames) {
	  	      	for(var i in additionalQuestionNames) {
	  		      	if (i > 0) {
	  	    			additionalQuestionNamesAsString = additionalQuestionNamesAsString + "," + additionalQuestionNames[i];
	  		      	} else {
	  		      		additionalQuestionNamesAsString = additionalQuestionNames[i];
	  				}
	  	      	}
	  			// Renaming additonalQuestion inputs
	  			if (additionalQuestionNamesAsString) {
	  				var tr = '#' + trId;
	  				var theNames = additionalQuestionNamesAsString.split(",");

	  				for (var i = 0; i < theNames.length; i++) {
	  					var currentName = theNames[i];
	  					
	  					if (currentName) {
	  						var nameParts = currentName.split(".");
	  						
	  						if (nameParts.length == 3) {
	  							var ind = currentName.lastIndexOf(".");
	  							currentName = currentName.substring(0, ind);
	  						}
	  						var additionalFieldId = '#' + currentName.split('.').join('\\.');
	  						var newName = currentName + '.' + resourceId;
	  	    				$(tr).find(additionalFieldId).attr("name", newName);
	  						<g:if test="${question?.resources}"><%--	    	
	      	    		    --%><g:set var="questionResources" value="${question.resources}" />
	  						if (additionalFieldId.indexOf("thickness_mm") != -1) {
	  							var resources = {};
	  							var defaultThickness = {};
	  						    <g:each in="${questionResources}" var="resource"><%-- 
	  						    --%><g:if test="${resource.allowVariableThickness}"><%--
	  						 		--%>resources['${resource.resourceId}'] = true;
	  						 	 	</g:if><%--
	  						 	--%><g:if test="${resource.defaultThickness_mm}"><%--
	  						        --%>defaultThickness['${resource.resourceId}'] = "${formatNumber(number:resource.defaultThickness_mm, format:"###.##")}";
	  						        </g:if><%--
	  						--%></g:each>
	  						    if (!resources[resourceId]) {	
	  						    	$(tr).find(additionalFieldId).attr("disabled", "disabled");
	  						    	$(tr).find(additionalFieldId).val("");
	  						    } else {
	  						    	$(tr).find(additionalFieldId).prop('disabled', false);
	  						    	$(tr).find(additionalFieldId).val(defaultThickness[resourceId]);
	  						    }
	  						} else if(additionalFieldId.indexOf("profileId") != -1) {
	  							var newOptions = {}; 
	  							var defaultProfiles = {};
	  							var lengths = {};<%--
	  						--%><g:each in="${questionResources}" var="resource"><%-- 
	  						    --%><g:set var="resourceProfiles" value="${question?.getResourceProfiles(resource.resourceId, null, indicator, entity)}" /><%--
	  						    --%><g:set var="length" value="${resourceProfiles ? resourceProfiles.size : 0}" /><%--
	  						 --%>lengths['${resource.resourceId}'] = ${length};
	  						     newOptions['${resource.resourceId}'] = {<%--
	  						    --%><g:each in="${resourceProfiles}" var="profile" status="index"><%--
	  	    					  --%>'${profile.profileId}' : '${profile.localizedName}'${length > index + 1 ? ',' : ''}<%--
	  	    					--%></g:each>};
	  	    					</g:each><%--
	  						--%><g:each in="${questionResources}" var="resource"><%-- 
	  						    --%><g:set var="resourceProfiles" value="${question?.getResourceProfiles(resource.resourceId, null, indicator, entity)}" /><%--
	  						    --%><g:each in="${resourceProfiles}" var="profile"><%--
	  						      --%><g:if test="${profile.defaultProfile}"><%--
	  	    					  --%>defaultProfiles['${resource.resourceId}'] = '${profile.profileId}';<%--
	  	    					  --%></g:if><%--
	  	    					--%></g:each><%--
	  	    				--%></g:each><%--
	  	    				--%>$(additionalFieldId).children().remove()
	  	    					$(additionalFieldId).append("<option value=\"\"></option>");	
	  	    					var selected = false;
	  	    					
	  	    					if (newOptions[resourceId]) {	
	  		    					$.each(newOptions[resourceId], function(val, text) {
	  			    					if (val == defaultProfiles[resourceId]) {
	  				    					selected = true;
	  				    					$(additionalFieldId).append("<option selected=\"selected\" value=\"" + val + "\">" + text + "</option>");
	  					    			} else if (lengths[resourceId] == 1) {
	  						    			selected = true;
	  				    					$(additionalFieldId).append("<option selected=\"selected\" value=\"" + val + "\">" + text + "</option>");
	  							    	} else if (!selected) {
	  								    	selected = true;
	  				    					$(additionalFieldId).append("<option selected=\"selected\" value=\"" + val + "\">" + text + "</option>");
	  								    } else {
	  								    	$(additionalFieldId).append("<option value=\"" + val + "\">" + text + "</option>");
	  									}
	  			    				});
	  	    					}
	  	    				}
	  	    				</g:if>
	  					}
	  				}
	  			}
	              var additionalQuestionValue;
	  	        var additionalQuestionId;
	  	        var originalAdditionalId;
	  	        
	  	        for(var i in additionalQuestionNames) {
	  	            originalAdditionalId = '#' + additionalQuestionNames[i].replace("." , "\\.");
	  	            additionalQuestionId = originalAdditionalId + index;
	  	        	additionalQuestionValue = $(originalAdditionalId).val();
	  	        	$(additionalQuestionId).val(additionalQuestionValue);
	  	       	}
	  	  	  }
	  	      index = index + 1;
	        }
	      });
	}
</script>
<script type="text/javascript">
  var index = 1;
  var trId;
		  
  $(document).ready(function() {
    $('#${addButtonId}').on('click', function (event) {
	  trId = '${resourceTableId}New' + index;
      var selectId = '${resourceTableId}SelectNew' + index;
      var textId = '${resourceTableId}TextNew' + index;
      var additionalQuestionNames = [];
      var originalSelectValue = $("#${selectId}").val().trim();
      var originalSelectText = $("#${selectId} option:selected").text().trim();
      $("#${selectId}").val("");
      event.preventDefault();

      if (originalSelectValue) {
    	  var unit;
  		
    	  if (originalSelectValue.split(" ")[1]) {
    	    unit = originalSelectValue.split(" ")[1]
    	    originalSelectValue = originalSelectValue.split(" ")[0]
    	    originalSelectText = originalSelectText.split("(")[0]
    	  }
    	  var resourceId = originalSelectValue;
          $('#${resourceTableId}header').css("visibility", "visible");<%--
      --%><g:each in="${additionalQuestions?.collect({it.questionId})}" var="additionalQuestionId" status="status"><%--
	    --%>additionalQuestionNames[${status}] = "${mainSectionId}.${question?.questionId}_additional_${additionalQuestionId}";
	     </g:each><%--
	      --%><%--
	  --%>var resourceInput = "<tr id=\"" + trId + "\"><td class=\"first\"><input type=\"hidden\" name=\"${fieldName}\" value=\"" + resourceId + "\" />" + originalSelectText + "</td>";<%--
      --%><g:if test="${quantity}">
			var quantityName = '${inputPrefix}' + '.' + resourceId;
	        resourceInput = resourceInput + "<td><input type=\"text\" id=\"" + textId + "\" value=\"\" name=\"" + quantityName + "\" class=\"input-mini\" />";

	        if(unit) {
	        	resourceInput = resourceInput + " <span class=\"add-on\">" + unit + "</span></td>";
		    }
	      </g:if><%--
	   --%><g:each in="${additionalQuestions}" var="additionalQuestion"><%--
	    --%>resourceInput = resourceInput + " ${render(template:"/query/additionalquestion", model:[entity:entity, query:query, section:section, mainSectionId:mainSectionId, additionalQuestion:additionalQuestion, mainQuestion:question, isJavascript: true])}";
	      <%--
	  --%></g:each><%--
	  --%>resourceInput = resourceInput +  "<td><a href=\"javascript:;\" onclick=\"removeResource('" + trId + "');\"><strong>\${message(code:'delete')}</strong></a></td>";
	      resourceInput = resourceInput + "</tr>";
	      $('#${resourceTableId}').append(resourceInput);
	      var additionalQuestionNamesAsString = "";
	
	  	  if (additionalQuestionNames) {
	      	for(var i in additionalQuestionNames) {
		      	if (i > 0) {
	    			additionalQuestionNamesAsString = additionalQuestionNamesAsString + "," + additionalQuestionNames[i];
		      	} else {
		      		additionalQuestionNamesAsString = additionalQuestionNames[i];
				}
	      	}
			// Renaming additonalQuestion inputs
			if (additionalQuestionNamesAsString) {
				var tr = '#' + trId;
				var theNames = additionalQuestionNamesAsString.split(",");

				for (var i = 0; i < theNames.length; i++) {
					var currentName = theNames[i];
					
					if (currentName) {
						var nameParts = currentName.split(".");
						
						if (nameParts.length == 3) {
							var ind = currentName.lastIndexOf(".");
							currentName = currentName.substring(0, ind);
						}
						var additionalFieldId = '#' + currentName.split('.').join('\\.');
						var newName = currentName + '.' + resourceId;
	    				$(tr).find(additionalFieldId).attr("name", newName);
						<g:if test="${question?.resources}"><%--	    	
    	    		    --%><g:set var="questionResources" value="${question.resources}" />
						if (additionalFieldId.indexOf("thickness_mm") != -1) {
							var resources = {};
							var defaultThickness = {};
						    <g:each in="${questionResources}" var="resource"><%-- 
						    --%><g:if test="${resource.allowVariableThickness}"><%--
						 		--%>resources['${resource.resourceId}'] = true;
						 	 	</g:if><%--
						 	--%><g:if test="${resource.defaultThickness_mm}"><%--
						        --%>defaultThickness['${resource.resourceId}'] = "${formatNumber(number:resource.defaultThickness_mm, format:"###.##")}";
						        </g:if><%--
						--%></g:each>
						    if (!resources[resourceId]) {	
						    	$(tr).find(additionalFieldId).attr("disabled", "disabled");
						    	$(tr).find(additionalFieldId).val("");
						    } else {
						    	$(tr).find(additionalFieldId).prop('disabled', false);
						    	$(tr).find(additionalFieldId).val(defaultThickness[resourceId]);
						    }
						} else if(additionalFieldId.indexOf("profileId") != -1) {
							var newOptions = {}; 
							var defaultProfiles = {};
							var lengths = {};<%--
						--%><g:each in="${questionResources}" var="resource"><%-- 
						    --%><g:set var="resourceProfiles" value="${question?.getResourceProfiles(resource.resourceId, null, indicator, entity)}" /><%--
						    --%><g:set var="length" value="${resourceProfiles ? resourceProfiles.size : 0}" /><%--
						 --%>lengths['${resource.resourceId}'] = ${length};
						     newOptions['${resource.resourceId}'] = {<%--
						    --%><g:each in="${resourceProfiles}" var="profile" status="index"><%--
	    					  --%>'${profile.profileId}' : '${profile.localizedName}'${length > index + 1 ? ',' : ''}<%--
	    					--%></g:each>};
	    					</g:each><%--
						--%><g:each in="${questionResources}" var="resource"><%-- 
						    --%><g:set var="resourceProfiles" value="${question?.getResourceProfiles(resource.resourceId, null, indicator, entity)}" /><%--
						    --%><g:each in="${resourceProfiles}" var="profile"><%--
						      --%><g:if test="${profile.defaultProfile}"><%--
	    					  --%>defaultProfiles['${resource.resourceId}'] = '${profile.profileId}';<%--
	    					  --%></g:if><%--
	    					--%></g:each><%--
	    				--%></g:each><%--
	    				--%>$(additionalFieldId).children().remove()
	    					$(additionalFieldId).append("<option value=\"\"></option>");	
	    					var selected = false;
	    					
	    					if (newOptions[resourceId]) {	
		    					$.each(newOptions[resourceId], function(val, text) {
			    					if (val == defaultProfiles[resourceId]) {
				    					selected = true;
				    					$(additionalFieldId).append("<option selected=\"selected\" value=\"" + val + "\">" + text + "</option>");
					    			} else if (lengths[resourceId] == 1) {
						    			selected = true;
				    					$(additionalFieldId).append("<option selected=\"selected\" value=\"" + val + "\">" + text + "</option>");
							    	} else if (!selected) {
								    	selected = true;
				    					$(additionalFieldId).append("<option selected=\"selected\" value=\"" + val + "\">" + text + "</option>");
								    } else {
								    	$(additionalFieldId).append("<option value=\"" + val + "\">" + text + "</option>");
									}
			    				});
	    					}
	    				}
	    				</g:if>
					}
				}
			}
            var additionalQuestionValue;
	        var additionalQuestionId;
	        var originalAdditionalId;
	        
	        for(var i in additionalQuestionNames) {
	            originalAdditionalId = '#' + additionalQuestionNames[i].replace("." , "\\.");
	            additionalQuestionId = originalAdditionalId + index;
	        	additionalQuestionValue = $(originalAdditionalId).val();
	        	$(additionalQuestionId).val(additionalQuestionValue);
	       	}
	  	  }
	      index = index + 1;
      }
    });
  });

</script>  	  
<g:formRemote name="addResource" url="[controller: 'UtilController', action: 'addResource']" update="[success: 'message', failure: 'error']">
    <input type="submit" id="${addButtonId}" class="btn btn-primary" value="${message(code:'add')}" />
    
    <input type="hidden" name="id" value="1" />
 <input type="submit" value="Delete Book!" />
</g:formRemote >
