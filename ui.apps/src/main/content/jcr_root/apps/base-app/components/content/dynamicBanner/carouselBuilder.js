"use strict";
use(function () {

    var carousel = {};
    let numOfSlides = this.num;
    let autoplay = granite.resource.properties["autoplay"];
    let millisec = granite.resource.properties["ms"];
    carousel.pre = "<div class=\"orbit\" role=\"region\" data-swipe=\"true\" data-orbit data-auto-play=\"" + autoplay + "\" data-timer-delay=\"" + millisec + "\">" +
        "<div class=\"orbit-wrapper\">" +
        "<div class=\"orbit-controls\">" +
        "<button class=\"orbit-previous\">" +
        "<span class=\"show-for-sr\">Previous Slide</span>&#9664;&#xFE0E;" +
        "</button>" +
        "<button class=\"orbit-next\">" +
        "<span class=\"show-for-sr\">Next Slide</span>&#9654;&#xFE0E;" +
        "</button>" +
        "</div>" +
        "<ul class=\"orbit-container\">";
    carousel.elementPre = "<li class=\"orbit-slide\">";
    carousel.elementPost = "</li>";
    carousel.post = "</ul></div>" +
        '<nav class="orbit-bullets">';

    for (var i = 0; i < numOfSlides; i++) {
        if (i === 0) {
            carousel.post += '<button class="is-active" data-slide="' + i + '">' +
                '  <span class="show-for-sr">First slide details.</span>' +
                '  <span class="show-for-sr" data-slide-active-label>Current Slide</span>' +
                '</button>';
        } else {
            carousel.post += '<button data-slide="' + i + '"><span class="show-for-sr">slide</span></button>';
        }

    }

    carousel.post += "</nav></div><div class='title-1'>";
    return carousel;

});