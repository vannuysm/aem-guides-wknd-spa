(function(document, $, Coral) {
    "use strict";

    function findTargetItem(target) {
        var foundTarget = target;
        if (target.closest('.coral-Form-fieldwrapper').length) {
            foundTarget = target.closest('.coral-Form-fieldwrapper');
        }
        if (target.parent('coral-panel-content').length) {
            foundTarget = $('#' + target.closest('coral-panel').attr('aria-labelledby'));
        }
        return foundTarget;
    } 

    $(document).on("foundation-contentloaded", function() {
        setTimeout(function() {
            $(".showHideCheckbox").each(function(i, element) {
                var allTargets = $($(element).data("showHideTarget"));
                var target;
                if (allTargets.length > 1) {
                    target = [];
                    $(allTargets).each(function() {
                        target.push(findTargetItem($(this)));
                    });
                } else {
                    target = findTargetItem(allTargets);
                }
                Coral.commons.ready(element, function(component) {
                    showHide(component, target);
                    component.on("change", function() {
                        showHide(component, target);
                    });
                });
            });
        }, 10);


    });

    function showHide(component, target) {
        if (Array.isArray(target)) {
            $.each(target, function(i, elem) {
                if ($(component).prop('checked')) {
                    elem.removeClass('hide');
                } else {
                    elem.addClass('hide');
                };
            });
        } else {
            if ($(component).prop('checked')) {
                target.removeClass('hide');
            } else {
                target.addClass('hide');
            };
        }

    }
})(document, Granite.$, Coral);