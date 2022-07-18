<%
    def userService = grailsApplication.mainContext.getBean("userService")
%>
<div id="modal-${infoId}" class="modal cascading-modal fade sourceListing-modal opened" data-backdrop="false" >
    <div class="modal-dialog">
        <div id="modal-header-${infoId}" class="modal-header modal-draggable">
            <button id="closeBtn-${infoId}" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <div id="small-title-header-${infoId}" class="text-center" style="display: none; padding: 0 20px; max-height: 72px; overflow-y: hidden">
                <strong style="font-size: 12px">
                    ${resourceFullNameHTML}
                </strong>
            </div>
            <div id="modal-header-divider-${infoId}" class="modal-header-divider"></div>
        </div>

        <div class="modal-content" style="position:relative">
            <div id="sourceListing-body-${infoId}" class="sourceListing-body">
                %{-- BRAND LOGO --}%
                <g:if test="${b64BrandImage}">
                    <div id="sourceListing-brand-${infoId}" class="img-brand-container">
                        <img src="data:image/png;base64,${b64BrandImage}" onerror="this.style.display = 'none'" alt="${resource.manufacturer}"/>
                    </div>
                </g:if>
                %{-- PICTURE, FULL NAME AND BUTTONS --}%
                <div style="display: flex">
                    %{-- PICTURE --}%
                    <g:if test="${epdDownloadLink && b64ImageEPD}">
                        <div class="text-center" style="margin: 10px 10px 0 0">
                            <a href="${epdDownloadLink}" target='_blank'>
                                <div id="preview-img-${infoId}" style="width : 100px; display: inline-block">
                                    <img style="border-radius: 4px" width="100%" src="data:image/jpeg;base64, ${b64ImageEPD}" onerror="this.style.display = 'none'" alt="${resource.imgDescription}"/>
                                </div>
                            </a>
                            <div id="large-img-${infoId}" class="img-container-sourceListing-hover">
                                <img class="img-sourceListing" src="data:image/jpeg;base64, ${b64ImageEPD}" onerror="this.style.display = 'none'" alt="${resource.imgDescription}"  />
                                <div id="caption-${infoId}">
                                    <p class="img-caption-sourceListing"><i>${resource.imgDescription ?: ""}</i></p>
                                </div>
                            </div>
                        </div>
                    </g:if>
                    <g:elseif test="${imgLink}">
                        <div class="text-center" style="margin: 10px 10px 0 0">
                            <div id="preview-img-${infoId}" style="width : 100px; display: inline-block">
                                <img style="border-radius: 4px" width="100%" src="${imgLink}" alt="${resource.imgDescription}"  />
                            </div>
                            <div id="large-img-${infoId}" class="img-container-sourceListing-hover">
                                <img class="img-sourceListing" src="${imgLink}" alt="${resource.imgDescription}"  />
                                <div id="caption-${infoId}">
                                    <p class="img-caption-sourceListing"><i>${resource.imgDescription ?: ""}</i></p>
                                    <p><i>${imgCopyRightText}</i></p>
                                </div>
                            </div>
                        </div>
                    </g:elseif>

                    <div style="margin-top: 10px">
                        <div style="display: flex">
                            <span id="card-title-${infoId}">
                                %{-- FULL NAME --}%
                                <strong style="font-size: ${isLongName? 'small' : 'medium'}; line-height: 1.5">
                                    ${resourceFullNameHTML}
                                </strong>
                                <g:if test="${!resource.isDummy && resource.privateDataset && resource.privateDatasetAccountId}">
                                    <i class='far fa-eye-slash triggerPopover' aria-hidden='true' data-content="${message(code: 'privateDataset.icon.helpText')}"></i>
                                </g:if>
                                %{-- FAVORITE BUTTON --}%
                                <g:if test="${showFavoriteBtn}">
                                    <g:if test="${account}">
                                        <a href="javascript:" style="width: 10px; align-self: center; cursor: pointer" class="triggerPopover" data-placement="right" data-content="${message(code: 'data_favorite_help', args: [account.companyName?.encodeAsHTML()])}" onclick="toggleFavoriteMaterial('${resourceId + '.' + profileId}', this, '${user.id}')">
                                            <i class="fa-star-size ${isFavorite ? 'fas fa-star yellowScheme' : 'far fa-star'}"></i>
                                        </a>
                                    </g:if>
                                    <g:else>
                                        <a style="width: 10px; align-self: center; opacity: 0.3" class="triggerPopover" data-content="${message(code: 'data_favorite_no_account')}">
                                            <i class="fa-star-size far fa-star"></i>
                                        </a>
                                    </g:else>
                                </g:if>
                                <i style="margin-left: 1px !important;" class="far fa-clipboard copyToClipBoard triggerPopover" data-content="${message(code: 'resource.sourcelisting.copy')}" onclick="copyFullNameToClipBoard('copyTarget${resource?.resourceId}')"></i>
                                <input type="hidden" id="copyTarget${resource?.resourceId}" value="${resourceFullName}"/>
                            </span>
                        </div>

                        <div style="display: flex; flex-wrap: wrap">
                            <g:if test="${showAddToInputBtn}">
                                <g:if test="${(resource?.resourceQualityWarningText && resource?.resourceQualityWarning) || showResourceFilterWarning}">
                                    <a style="margin: 5px 5px 5px 0" class='btn btn-primary'
                                       onclick='closeSourceListing("${infoId}");
                                       openResourceQualityWarningSwal("${resourceId}", "${profileId}", "${queryId}", "${sectionId}", "${questionId}", "${entityId}",
                                                                      "${sectionId + questionId + 'ResourcesSelect'}", "${indicatorId}", "${sectionId + questionId + 'Resources'}",
                                                                      "${sectionId + '.' + questionId}", null, "${resource.constructionId}", "${uniqueConstructionIdentifier}");
                                       stopBubblePropagation(event);'>
                                        ${message(code: 'query_form.add_to_input')}
                                    </a>
                                </g:if>
                                <g:elseif test="${resource.construction}">
                                    <a style="margin: 5px 5px 5px 0" class='btn btn-primary'
                                       onclick='closeSourceListing("${infoId}");
                                       addResourceFromSelect("${entityId}", null, null, "${sectionId + questionId + 'ResourcesSelect'}", "${indicatorId}", "${queryId}", "${sectionId}", "${questionId}", "${sectionId + questionId + 'Resources'}", "${sectionId + '.'+questionId}", "${ques?.preventDoubleEntries}", "${showGWP}", "${message(code: 'query.question.prevent_double_entries')}", ${uniqueConstructionIdentifier ? "\"${uniqueConstructionIdentifier}\"" : null}, null, null, null, null, "${resourceId + '.'+ profileId}",null,null,null,"${resConstructionId}");
                                       addConstructionResources("${resource.constructionId}", "${entityId}", null, null, null, "${indicatorId}", "${queryId}", "${sectionId}", "${questionId}", "${sectionId + questionId + 'Resources'}", "${sectionId + '.'+questionId}", null, null, null, "${uniqueConstructionIdentifier}", null, null, null, null, "${resConstructionId}");
                                       stopBubblePropagation(event);'>
                                        ${message(code: 'query_form.add_to_input')}
                                    </a>
                                </g:elseif>
                                <g:elseif test="${maxRowsReached}">
                                    <div class="triggerPopover" data-content="${message(code: 'query_row_limit_reached')}">
                                        <button style="margin: 5px" class="btn btn-primary" disabled>
                                            ${message(code: "query_form.add_to_input")}
                                        </button>
                                    </div>
                                </g:elseif>
                                <g:else>
                                    <a style="margin: 5px 5px 5px 0" class='btn btn-primary'
                                       onclick='closeSourceListing("${infoId}");
                                       addResourceFromSelect("${entityId}", null, null, "${sectionId + questionId + 'ResourcesSelect'}", "${indicatorId}", "${queryId}", "${sectionId}", "${questionId}", "${sectionId + questionId + 'Resources'}", "${sectionId + '.'+questionId}", "${ques?.preventDoubleEntries}", "${showGWP}", "${message(code: 'query.question.prevent_double_entries')}", null, null, null, null, null, "${resourceId + '.'+ profileId}");
                                       stopBubblePropagation(event);'>
                                        ${message(code: 'query_form.add_to_input')}
                                    </a>
                                </g:else>
                            </g:if>
                            <g:if test="${showAddToCompareBtn}">
                                <g:if test="${compareFeatureLicensed && !resourceAlreadyAdded}">
                                    <a style="margin: 5px 10px 5px 0" class="btn btn-primary triggerPopover" data-content="${message(code: 'compare_data_help')}"  onclick="addSingleResourceToCompare('${resource.resourceId}', '${parent?.id}', '${indicator?.indicatorId}', '${resource.profileId}')">
                                        ${message(code: "add_to_compare")}
                                    </a>
                                </g:if>
                                <g:else>
                                    <g:if test="${!compareFeatureLicensed}">
                                        <g:set var="warning" value="${message(code: 'enterprise_feature_warning', args: ["${message(code: 'expert')} ${message(code: 'compare_resources')}"])}"/>
                                    </g:if>
                                    <g:elseif test="${resourceAlreadyAdded}">
                                        <g:set var="warning" value="${message(code: 'resource_already_added')}"/>
                                    </g:elseif>
                                    <g:else>
                                        <g:set var="warning" value="${message(code: 'compare_resources_unavailble')}"/>
                                    </g:else>
                                    <div class="triggerPopover" data-content="${warning}">
                                        <button style="margin: 5px 10px 5px 0" class="btn btn-primary" disabled>
                                            ${message(code: "add_to_compare")}
                                        </button>
                                    </div>
                                </g:else>
                            </g:if>
                            <g:if test="${epdDownloadLicensed && displayDownloadText}">
                                <a href="${epdDownloadLink}" target="_blank" style="margin: 5px 10px 5px 0" class="btn btn-primary">
                                    ${displayDownloadText}
                                </a>
                            </g:if>
                            <g:elseif test="${!epdDownloadLicensed && displayDownloadText}">
                                <g:set var="hasImageAndInputBtn" value="${((epdDownloadLink && b64ImageEPD) || imgLink) && showAddToInputBtn}"/>
                                <div class="triggerPopover" data-placement="${hasImageAndInputBtn ? 'top' : 'right'}" data-content="${message(code: 'enterprise_feature_warning', args: ["${message(code: 'business')} Download EPD"])}">
                                    <button style="margin: 5px 10px 5px 0" class="btn btn-secondary dark-gray-background" disabled>
                                        ${message(code: 'resource.pdfFile_download')}
                                    </button>
                                </div>
                            </g:elseif>
                        </div>

                    </div>
                </div>
                %{-- DATA CARD SECTIONS --}%
                <g:if test="${sections}">
                    <div style="display: flex; justify-content: flex-end; width: 98%">
                        <g:if test="${userService.isSystemAdminOrSuperUserOrDataManagerOrDeveloper(user)}">
                            <a href="javascript:" class="fiveMarginLeft" onclick="window.open('${createLink(controller: "import", action: "showData", params: [resourceUUID: resource.id])}', '_blank', 'width=1024, height=768, scrollbars=1');stopBubblePropagation(event);">
                                <i class="fas fa-search-plus"></i>
                            </a>
                        </g:if>
                        <g:if test="${userService.isSystemAdmin(user) && entityId && queryId && sectionId && questionId}">
                            <a href="javascript:" class="fiveMarginLeft" onclick="window.open('${createLink(controller: "util", action: "renderDatasetInformation", params: [datasetManualId: datasetId, childEntityId: entityId, queryId: queryId, sectionId: sectionId, questionId: questionId])}', '_blank', 'width=1024, height=768, scrollbars=1');">
                                <i class="fa fa-archive"></i>
                            </a>
                        </g:if>
                        <a  href="javascript:" class="fiveMarginLeft" onclick="toggleElement('.noValue${infoId}'); toggleContentOfElement(this, '${message('code': 'results.show_empty')}', '${message('code': 'hide_empty_rows')}')">${message('code': 'results.show_empty')}</a>
                    </div>
                    <g:each in="${sections}" var="section">
                        <g:if test="${section && section.authorizedToDisplay}">
                            <div class="section ${section.sectionCollapsed ? "collapsed" : ""} ${section.isEmpty ? " noValue${infoId}" : ''}" style="width: 98%; ${section.isEmpty && !('description' == section.sectionId && constructionGroupDescription) ? 'display: none' : ''}">
                                <div class="sectionheader_level2" style="margin: 0 0 2px 0 !important; padding: 5px 0 !important; cursor: pointer" onclick="toggleExpandSection(this)">
                                    <div style="display: flex">
                                        <a href="javascript:" class="pull-left sectionexpanderspec" style="margin-top: 0">
                                            <i class="icon icon-chevron-down expander"></i>
                                            <i class="icon icon-chevron-right collapser"></i>
                                        </a>
                                        <span style="display: flex; align-items: center">
                                            <div class="h2expanderspec" style="font-size: 14px"><b>${section.heading}</b></div>
                                            <g:if test="${section.help}">
                                                <span class="triggerPopover" style="padding-left: 5px" data-content="${section.help}"><i class="icon-question-sign"></i></span>
                                            </g:if>
                                        </span>
                                    </div>
                                </div>
                                <div class="sectionbody" style="padding-bottom: 0; ${section.sectionCollapsed ? 'display: none' : ''}">
                                    %{-- CUSTOM RENDERING SECTION --}%
                                    <g:if test="${section.customRendering && section.customContent}">
                                        ${section.customContent}
                                    </g:if>
                                    %{-- TABLE SECTION (DEFAULT) --}%
                                    <g:else>
                                        <table class="table table-striped" style="table-layout: fixed; margin-bottom: 0 !important;">
                                            <g:if test="${section.resourceAttributes}">
                                                <g:each in="${section.resourceAttributes}" var="row">
                                                    <g:if test="${row && row.authorizedToDisplay}">
                                                        <tr ${row.content? '' : "class='noValue${infoId}' style='display:none'"}>
                                                            <td style="padding: 4px 0 4px 10px !important; white-space: normal !important; width: 140px">
                                                                <strong>${row.heading}</strong>
                                                            </td>
                                                            <td style="width: 10px">
                                                                <g:if test="${row.help}">
                                                                    <span class="triggerPopover" data-content="${row.help}">
                                                                        <i class="icon-question-sign"></i>
                                                                    </span>
                                                                </g:if>
                                                            </td>
                                                            <td style="padding: 4px 10px 4px 4px !important; white-space: normal !important;">
                                                                ${row.content}
                                                            </td>
                                                        </tr>
                                                    </g:if>
                                                </g:each>
                                            </g:if>
                                        </table>
                                    </g:else>
                                    <g:if test="${'description' == section.sectionId && constructionGroupDescription}">
                                        <div style="padding: 0 10px 5px 10px">${constructionGroupDescription}</div>
                                    </g:if>
                                </div>
                            </div>
                        </g:if>
                    </g:each>
                </g:if>
            </div>
        </div>
        %{--  A small area to drag the data card at the bottom  --}%
        <div id="modal-footer-${infoId}" class="modal-draggable"></div>
    </div>
</div>
<script>
    $(function () {
        init()
        cascadeModals()
        <g:if test="${b64ImageEPD || imgLink || b64BrandImage}">
            imgInit()
            determinePreviewImgOrientation()
        </g:if>
    })

    function init() {
        // default values of data card
        // DO NOT CHANGE the default values. If any default values need to be changed, use renderSourceListingToAnyElement and set the values from there
        const minWidth = 450
        const minHeight = 0.3 * screen.height
        let cardWidth = 500
        let cardHeight = 0.5 * screen.height
        let leftPosition = 0.6 * window.outerWidth
        let topPosition = 0.4 * window.outerHeight
        let zIndexx = 1052
        let cardPosition = 'fixed' // can be fixed or absolute, should not use relative.

        // Re-set position and size of data card is called with defined values from renderSourceListingToAnyElement (These are optional)
        <g:if test="${initWidth}">
            const setWidth = convertProportionOfScreenToPixel(${initWidth}, 'width')
            cardWidth = setWidth >= minWidth ? setWidth : minWidth
        </g:if>

        <g:if test="${initHeight}">
            const setHeight = convertProportionOfScreenToPixel(${initHeight}, 'height')
            cardHeight = setHeight >= minHeight ? setHeight : minHeight
        </g:if>

        <g:if test="${leftCoordinate}">
            leftPosition = convertProportionOfScreenToPixel(${leftCoordinate}, 'width')
        </g:if>

        <g:if test="${topCoordinate}">
            topPosition = convertProportionOfScreenToPixel(${topCoordinate}, 'height')
        </g:if>

        <g:if test="${zIndex}">
            zIndexx = ${zIndex}
        </g:if>

        <g:if test="${cardPosition}">
            cardPosition = "${cardPosition}"
        </g:if>

        const headerInitialHeight = 11

        const $modalContainer = $(`#modal-${infoId}`)
        const $modalBody = $(`#sourceListing-body-${infoId}`)
        const $draggableHeader = $(`#modal-header-${infoId}`)
        const $smallTitleOnHeader = $(`#small-title-header-${infoId}`)
        const $headerDivider = $(`#modal-header-divider-${infoId}`)

        // set the height of the header, so it look pretty
        $draggableHeader.css('height', headerInitialHeight)
        $headerDivider.css('padding-top', headerInitialHeight + 8)

        $modalBody
            .css('height', cardHeight - $draggableHeader.height())
            .on('scroll', function () {
                // display the name on header on scroll, only when the full name cannot be seen anymore
                let positionY = $(this).scrollTop()
                let brandImgContainerHeight = $(`#sourceListing-brand-${infoId}`).height() ? $(`#sourceListing-brand-${infoId}`).height() : 0
                let showTitleOnHeaderThresHold = $(`#card-title-${infoId}`).height() + brandImgContainerHeight
                if (positionY >= showTitleOnHeaderThresHold && $smallTitleOnHeader.is(':hidden')) {
                    $smallTitleOnHeader.fadeIn(150)
                    $draggableHeader.css('height', $smallTitleOnHeader.height())
                    $headerDivider.css({paddingTop: 3, width: $smallTitleOnHeader.width()})
                    $(this).css('height', $modalContainer.height() - $draggableHeader.height())
                } else if (positionY < showTitleOnHeaderThresHold && $smallTitleOnHeader.is(':visible')) {
                    $draggableHeader.css('height', headerInitialHeight)
                    $headerDivider.css({paddingTop: headerInitialHeight + 8, width: '35%'})
                    $(this).css('height', $modalContainer.height() - headerInitialHeight)
                    $smallTitleOnHeader.fadeOut(100)
                }
            })

        $modalContainer
            .draggable({
                handle: `#modal-header-${infoId}, #modal-footer-${infoId}`
            })
            .resizable({
                handles: 'n,s,e,w, ne,se,nw,sw',
                minHeight: minHeight,
                minWidth: minWidth,
                start: function (event, ui) {
                    $(this).css("position", cardPosition);
                },
                stop: function (event, ui) {
                    $(this).css("position", cardPosition);
                }
            })
            .css({
                height: cardHeight,
                width: cardWidth,
                position: cardPosition,
                top: topPosition,
                left: leftPosition
            })
            .on('resize', function () {
                // adjust bunches of sizes when the data card is resized
                if ($smallTitleOnHeader.is(':hidden')) {
                    $draggableHeader.css('height', headerInitialHeight)
                    $headerDivider.css({paddingTop: headerInitialHeight + 8, width: '35%'})
                } else {
                    $draggableHeader.css('height', $smallTitleOnHeader.height())
                    $headerDivider.css({paddingTop: 3, width: $smallTitleOnHeader.width()})
                }
                $modalBody.css('height', $(this).height() - $draggableHeader.height())
                <g:if test="${b64ImageEPD || imgLink}">
                adjustHoverImgSize()
                </g:if>
            })
            .on('click', function (event) {
                stopBubblePropagation(event)
                // bring the data card to top when it's clicked on
                $(".sourceListing-modal").css('z-index', zIndexx);
                $(this).css('z-index', zIndexx + 1)
                $(this).data('trackingIndex', zIndexx + 1)
            })
            .modal("show").click()

        // for QA testing, something to target on
        $('#closeBtn-${infoId}').on('click', function () {
            $modalContainer.removeClass('opened')
        })

        // trigger all the qMark popovers manually.
        keepPopoverOnHover('.triggerPopover', 150, 450)
    }

    <g:if test="${b64ImageEPD || imgLink || b64BrandImage}">
        function imgInit() {
            // display bigger image when the preview is hovered
            $(`#preview-img-${infoId}`).hover(
                function () {
                    if ($(`#large-img-${infoId}`).css(':visible')) {
                        return
                    }
                    $(`#large-img-${infoId}`).fadeIn(150)
                    $(this).fadeTo(150, '0.6')
                },
                function () {
                    // fix the bizarre bug if the big picture overlaps the small one
                    if ($(`#large-img-${infoId}`).is(':hover')) {
                        return
                    }
                    $(`#large-img-${infoId}`).fadeOut(100)
                    $(this).fadeTo(100, '1')
                }
            )
            // fix the bizarre bug if the big picture overlaps the small one
            $(`#large-img-${infoId}`).on('mouseleave', function () {
                $(this).fadeOut(100)
                $(`#preview-img-${infoId}`).fadeTo(100, '1')
            })
        }

        // this hack is to find out if the image should be portrait or landscape
        function determinePreviewImgOrientation() {
            const $thisModal = $(`#modal-${infoId}`)
            const epdImg = '${b64ImageEPD}'
            const resourceImg = '${imgLink}'
            const brandImg = '${b64BrandImage}'

            // only run if we have an image
            if (epdImg || resourceImg) {
                const imgSource = epdImg ? 'data:image/jpeg;base64, ' + epdImg : resourceImg
                // Add a hidden image, display at its full size to see if originally it is portrait or landscape, and then remove
                $('body').append('<img id="hiddenImageToCheckSize" style="visibility: hidden; width: 100%; height: 100%" src="' + imgSource + '"/>')

                $('#hiddenImageToCheckSize').on('load', function () {
                    // Get the width and height of the hidden full size image to know if it's landscape or portrait
                    const orientation = $(this).width() > $(this).height() ? 'landscape' : 'portrait'
                    if (orientation === 'landscape') {
                        $("#preview-img-${infoId}").css('width', '130px')
                    }
                    // Remove this hidden image now, bye!
                    $(this).remove()

                    // save the orientation to data card container, so we can use it on adjustHoverImgSize()
                    $thisModal.data('imgOrientation', orientation)
                    // set the hover image's size and position
                    adjustHoverImgSize()
                })
            }
            // Do the same thing for brand img
            if (brandImg) {
                const brandImgSource = 'data:image/jpeg;base64, ' + brandImg
                $('body').append('<img id="hiddenBrandImageToCheckSize" style="visibility: hidden; width: 100%; height: 100%" src="' + brandImgSource + '"/>')
                $('#hiddenBrandImageToCheckSize').on('load', function () {
                    const orientation = $(this).width() >= $(this).height() ? 'landscape' : 'portrait'
                    if (orientation === 'portrait') {
                        $("#sourceListing-brand-${infoId}")
                            .css({
                                width: 'auto',
                                height: '20%'
                            })
                            .children().css('height', '100%')
                    }
                    $(this).remove()
                    $thisModal.data('brandImgOrientation', orientation)
                })
            }
        }

        function adjustHoverImgSize() {
            const $modalContainer = $(`#modal-${infoId}`)
            // grab data saved from determinePreviewImgOrientation()
            const imgOrientation = $modalContainer.data('imgOrientation')

            let dynamicWidth
            if (imgOrientation === 'landscape') {
                // have a widthLimit so that on resize, so that the height doesn't overflow the data card's height (since it is auto in landscape mode)
                const widthLimit = $modalContainer.height() - 50
                dynamicWidth = 0.75 * $modalContainer.width()
                dynamicWidth = dynamicWidth <= widthLimit ? dynamicWidth : widthLimit
                // let height : auto in landscape mode
            } else {
                let captionHeight = $('#caption-${infoId}').height()
                let dynamicHeight = 0.7 * $modalContainer.height() - captionHeight
                dynamicWidth = dynamicHeight / 1.2
                // set fixed height of the big picture (shown on hover) in portrait mode
                $(`#large-img-${infoId} > img`).css('height', dynamicHeight)
            }

            // set width and position of the big picture shown on hover
            $(`#large-img-${infoId}`)
                .css({
                    width: dynamicWidth,
                    top: ($modalContainer.height() - $(`#large-img-${infoId}`).height()) / 2,
                    left: ($modalContainer.width() - dynamicWidth) / 2
                })
        }
    </g:if>

    function cascadeModals() {
        const $thisModal = $(`#modal-${infoId}`)
        // find the position of the last opened modal to cascade the new one on top of it
        let $lastOpenedModal
        let highestIndex = 1

        // find all the opened data cards
        const $openedModals = $('.sourceListing-modal')
        if ($openedModals.length) {
            // find the highest trackingIndex among them, which is the last opened data card
            $openedModals.each(function () {
                const trackingIndex = $(this).data('trackingIndex')
                highestIndex = trackingIndex > highestIndex ? trackingIndex : highestIndex
            })
            // After having the highest index, target that data card to get its position later
            $lastOpenedModal = $openedModals.filter(function () {
                return $(this).data('trackingIndex') === highestIndex
            })
        }

        // set trackingIndex for our current data card to be the highest, since this is the latest opened so it can be found later.
        $thisModal.data('trackingIndex', highestIndex + 1)

        if ($lastOpenedModal) {
            const lastModalPosition = $lastOpenedModal.position()
            // only cascade if the last opened data card is on the right half of the screen, and below the browser bar.
            if (lastModalPosition && lastModalPosition.left >= $(window).width() / 2 && lastModalPosition.top >= $(window).height() / 5) {
                $thisModal.css({
                    top: lastModalPosition.top + 30,
                    left: lastModalPosition.left + 20
                })
            }
        }
    }
</script>
