"use strict";
use(function(){

    var cards ={};
        var numOfCards = this.num;
        
        if(numOfCards <=4){
            cards.num = 12/numOfCards;
        }else{
            cards.num = 3;
        }

        // console.log("Card JS Start");
        // console.log("---------------------------------------------");
        // console.log(JSON.stringify(cards.num));
        // console.log("---------------------------------------------");
     return cards;
   
});