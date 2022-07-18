<g:set var="header" value="${"<span class='subText fontBody' style='top: -30px; position: relative'>" + parent.name +", "+ reportDate +"<br></span>"}"/>
<g:set var="footer" value="${"<span class='subText fontBody' style='float:left; top: 920px; position: absolute;'>Created with One Click LCA Planetary: <span class='fontHeader greenP'>oneclicklca.com/planetary</span></span>"}"/>
<g:set var="footerLast" value="${"<span class='subText fontBody' style='color:white;float:left; top: 930px; position: absolute;'>Created with One Click LCA Planetary: <span class='fontHeader'>oneclicklca.com/planetary</span></span>"}"/>
<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<div class="container section" style="page-break-after: always;">
    <div class="sectionbody" id="printing">
        <div class="pageBreak" style="position: relative;">
            <img src="/app/assets/planetaryReport/template1.png" alt="" class="backgroundImage" style="position: absolute; z-index: -1; left: -3px;" />
            <div class ="content-container">
                ${header}
                <span class="fontHeader greenP h2">Embodied Carbon Report<br></span>
                <p>
                    This report is created with One Click LCA Planetary - a free embodied carbon tool. It calculates cradle
                    to gate (A1 - A3) embodied carbon impacts and materials efficiency for the 10 most carbon-intensive
                    material categories. It is built on the world’s #1 life-cycle assessment platform, One Click LCA.
                   <br> Get free access at <span class="fontHeader greenP">oneclicklca.com/planetary</span>.
                </p>
                <div class="divider"></div>
                <div>
                    <div class="col3-container fontHeader">
                        <label>Project name <br></label>
                        <span class="greenP"> ${parent.name ?: "-"}</span>
                    </div>
                    <div class="col3-container fontHeader">
                    <label>Report date <br></label>
                    <span class="greenP"> ${reportDate ?: "-"}</span>
                    </div>
                    <div class="col3-container fontHeader">
                        <label>Building type <br></label>
                        <span class="greenP"> ${parent.type ?: "-"}</span>
                    </div>
                    <div class="col3-container fontHeader">
                        <label>Design name <br></label>
                        <span class="greenP"> ${design.name ?: "-"}</span>
                    </div>
                    <div class="col3-container fontHeader">
                        <label>Assessor<br></label>
                        <span class="greenP"> ${user.name ?: "-"}</span>
                    </div>
                    <div class="col3-container fontHeader">
                        <label>Gross floor area<br></label>
                        <span class="greenP"> ${grossArea ?: "-"}</span>
                    </div>
                    <div class="col3-container fontHeader">
                        <label>Location <br></label>
                        <span class="greenP"> ${parent.country}</span>
                    </div>
                    <div class="col3-container fontHeader">
                        <label>Contact details <br></label>
                        <span class="greenP"> ${user?.username ?: "-"}</span>
                    </div>
                    <div class="col3-container fontHeader">
                        <label>Organisation <br></label>
                        <span class="greenP"> ${userService.getAccount(user)?.companyName ?: "-"}</span>
                    </div>

                </div>
                <div class="divider"></div>
                <span class="fontHeader greenP h3">Embodied carbon result summary</span>
                <div>
                    <p>Embodied carbon is defined as the carbon emissions from the manufacture, transportation, use and
                    end-of-life of construction materials. </p>
                </div>
                <div style="top:10px;text-align: left; width: 100%; height: 100px; position:relative">
                    <div style="width: 200px; display: inline-block; vertical-align: top; background: white; padding-right: 20px; padding-bottom: 10px">
                        <span class="fontBody greenP"><span style="font-size: 30px" ><span id="gwpAnnual"></span> kg<br> </span> CO<sub>2</sub>e/m<sup>2</sup></span>
                        <br><span class="subText fontBody darkGrayP">Kilograms of carbon dioxide equivalent per metre squared</span>
                    </div>
                    <div style="width: 200px; display: inline-block; vertical-align: top; background: white; padding-right: 20px; padding-bottom: 10px">
                        <span class="fontBody greenP"><span style="font-size: 30px"><span id="socialCostOfImpact"></span> ${account?.unitCarbonCost ? account?.unitCarbonCost : '€'}<br> </span> social cost of carbon</span>
                        <br><span class="subText fontBody darkGrayP">Social cost set at a rate of ${account?.carbonCost ?: "50"}${account?.unitCarbonCost ?: "€"}/ton</span>
                    </div>
                    <div style="width: 160px; display: inline-block;vertical-align: top; background: white; padding-bottom: 10px">
                        <span class="fontBody greenP"><span style="font-size: 30px" ><span id="gwpTonnes"></span> t<br> </span> CO<sub>2</sub>e</span>
                        <br><span class="subText fontBody darkGrayP">Total carbon dioxide equivalent emissions in tons</span>
                    </div>

                </div>
                <div class="benchmarkContainer">
                    <img class="benchmarkImage" src="${pdfGraphsForSession?.find({it.key.contains("b64benchmarkTable")})?.value}" />
                </div>

                <div class="darkGrayP" style="padding-right:20px ;text-align: right; position:relative;width: 150px; float:right; background: rgba(255,255,255,0.5); border-radius: 10px; right: -10px ">
                    <div style="width: 30px; display: inline-block;vertical-align: bottom; background: white; padding-right: 30px">
                        <span class="icon"><img src="/app/assets/planetaryReport/arrow.png"/> </span>
                    </div>
                    <div>
                        <span class="fontHeader subText greenP">Which is roughly <br/> equal to: </span>
                    </div>
                    <div>
                        <span class="fontBody h3"><span id="flight"> 252 </span> <img class="icon" src="/app/assets/planetaryReport/plane.png"></span>
                        <br><span class="subText fontBody">return flights from the UK to Australia</span>
                    </div>
                    <div>
                        <span class="fontBody h3" ><span id="tree"> 252 </span> <img class="icon" src="/app/assets/planetaryReport/tree.png"></span>
                        <br><span class="subText fontBody">trees planted to offset emissions</span>
                    </div>
                    <div>
                        <span class="fontBody h3" ><span id="laundry"> 252 </span> <img class="icon" src="/app/assets/planetaryReport/laundry.png"></span>
                        <br><span class="subText fontBody">loads of laundry</span>
                    </div>
                    <div >
                        <span class="fontBody h3" ><span id="car"> 252 </span> <img class="icon" src="/app/assets/planetaryReport/car.png"></span>
                        <br><span class="subText fontBody">new cars</span>
                    </div>

                </div>
                ${footer}
            </div>
        </div>
        <div class="pageBreak break-page" style="position: relative;page-break-before: always;">
            <img src="/app/assets/planetaryReport/template2.png" alt="" class="backgroundImage" style="position: absolute; z-index: -1; left: -3px;" />
            <div class ="content-container">
                ${header}
                <span class="fontHeader greenP h3">Embodied carbon by material</span><br>
                <p>Cradle to gate (A1-A3) covers impacts of a material or product that is ready to ship to the construction site, including raw materials extraction, transport and manufacturing emissions. </p>
                <span class="fontHeader h4 greenP">
                    Embodied carbon and materials use by material type
                </span>
                <p>The below table shows information on absolute and relative embodied carbon and materials use efficiency</p>
                <calc:renderIndicatorReportByItem entity="${design}" parentEntity="${parent}" indicator="${indicator}" report="${indicator?.report}"  itemId="item1" />
                <span class="fontHeader h4 greenP">
                    Global warming potential, t CO2e by material type
                </span>
                <div id="byMaterialSixPack">
                <img class="chart-lg" src="${pdfGraphsForSession?.find({it.key.contains("b64byMaterialSixPack")})?.value}" /></div>
                <span class="fontHeader h4 greenP">
                    Most contributing materials
                </span>
                <div id="highImpactResources" class="section collapsed highHimpacts">
                    <div class="div-scope sectionheader collapsibleImpact"
                         onclick="getHighImpactResourcesOnly('${design?.id}', '${indicator?.indicatorId}',
                             '${calculationRuleId}', '${parent.id}', '${userService.getAccount(user)?.id}', true,
                             toggleCollapsibleImpact);">

                        <a class="pull-left sectionexpanderspec" onclick="event.preventDefault()" >
                            <i class="icon icon-chevron-down expander"></i>
                            <i class="icon icon-chevron-right collapser"></i>
                        </a>

                        <h2 style="margin-left: 8px" id="highImpactResourcesHeading">
                            <g:message code="results.contributing_impacts"/>
                        </h2>
                    </div>
                </div>
                ${footer}
            </div>
        </div>
        <div class="pageBreak break-page" style="position: relative;page-break-before: always;">
            <img src="/app/assets/planetaryReport/template3.png" alt="" class="backgroundImage" style="position: absolute; z-index: -1; left: -3px;" />
            <div class ="content-container" >
                ${header}
                <div>
                    <span class="fontHeader greenP h3">Embodied carbon by building part</span>
                    <p>
                        Choosing low carbon materials while also considering the quantity of materials is key to unlocking carbon reductions. The graphs below provide evidence of both carbon performance and materials efficiency for the design by building element. Identifying and optimizing building elements responsible for the largest emissions, and limiting the material mass can result in both carbon and cost savings
                    </p>

                    <div class="col2-container" id="byClassSixPack">
                        <span class="fontHeader h4 greenP"> Global warming potential, t CO2e - building part</span>
                        <img class="chart-lg" src="${pdfGraphsForSession?.find({it.key.contains("b64byClassSixPack")})?.value}" />
                    </div>
                    <div class="col2-container" id="massByClassSixPack">
                        <span class="fontHeader h4 greenP"> Mass, kg  - building part </span>

                        <img class="chart-lg" src="${pdfGraphsForSession?.find({it.key.contains("b64massByClassSixPack")})?.value}" /></div>
                </div>

                <div id="allBreakDowns">
                    <span class="fontHeader h4 greenP">Global warming potential (GWP) by material type and building part </span>
                    <img class="chart-lg" src="${pdfGraphsForSession?.find({it.key.contains("wrapper")})?.value}" />
                </div>
                ${footer}
            </div>
        </div>
        <div class="pageBreak break-page" style="position: relative;page-break-before: always;">
            <img src="/app/assets/planetaryReport/template4.png" alt="" class="backgroundImage" style="position: absolute; z-index: -1; left: -3px;" />
            <div class ="content-container">
                ${header}
                <div style="position: relative; top: 80px">
                    <span class="fontHeader whiteP h2">About One Click LCA Planetary</span>
                    <p class="whiteP" style="width: 50%">
                       <span class="fontHeader">One Click LCA Planetary</span> aims to help decarbonise the construction industry at a planetary scale. It’s a free embodied carbon tool that can be used to power embodied carbon and materials efficiency policies as well as individual design, construction and procurement decisions.
                    </p>
                    <p class="whiteP">
                        Get free access at <span class="fontHeader">oneclicklca.com/planetary</span>.
                    </p>
                    <div style="height: 100px" class="spacer"></div>

                    <span class="fontHeader whiteP h2">About One Click LCA</span>
                    <p class="whiteP">
                        <span class="fontHeader">One Click LCA</span> is the world-leading construction sector life-cycle assessment software that helps you calculate and reduce the environmental impacts of your construction projects, products, and portfolios. As well as decarbonizing building and infrastructure projects, One Click LCA can also help you to generate and manage Environmental Product Declarations (EPD), and real estate portfolio greenhouse gas reports.
                    </p>
                    <p class="whiteP">
                        If your project requires more advanced features, One Click LCA commercial tools support all impact categories and life-cycle stages, as well as compliance for certifications such as LEED and BREEAM, BIM integrations, all materials categories, and advanced functionality, reporting, support and training.
                    </p>
                    <p class="whiteP">
                        Learn more at <span class="fontHeader"> oneclicklca.com</span>
                    </p>
                </div>
                ${footerLast}
            </div>
        </div>
    </div>
</div>