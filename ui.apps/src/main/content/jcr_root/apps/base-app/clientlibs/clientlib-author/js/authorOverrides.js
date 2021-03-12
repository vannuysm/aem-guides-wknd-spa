console.log('hello from authorOverrides.js!');

"use strict";
use(function () {
    var info = {};    
    info.title = resource.properties["title"];
    info.description = resource.properties["description"];  
    console.log('yo');
    console.log(info);
    return info;
});