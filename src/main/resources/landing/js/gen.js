/*
Scrips Name: Gen.js
Author: Sharaeva Christina
Version: 1.0
*/
var numberOfImages = 0;


let canvas = document.createElement("canvas");
canvas.setAttribute("width", "640");
canvas.setAttribute("height", "480");
//canvas.setAttribute("style", "margin-left:auto;" + "margin-right:auto;");
canvas.style.marginLeft = '265px';
canvas.style.marginRight = '265px';

let body = document.getElementById("forCanvas");
body.appendChild(canvas);

let ctx = canvas.getContext("2d");
ctx.fillRect(0, 0, canvas.width, canvas.height);

let saveText = document.createElement("a");
saveText.setAttribute("id", "download");
saveText.setAttribute("class", "btn btn-primary btn-action");
saveText.setAttribute("download", "collage.jpg");
saveText.setAttribute("href", "");
saveText.innerText = "Download Now";

let saveButton = document.createElement("button");
saveButton.setAttribute("class", "center-block");
saveButton.appendChild(saveText);

body.appendChild(document.createElement("br"));
body.appendChild(saveButton);

function loadImages() {
    let urlImage1 = "https://source.unsplash.com/random/400x300";
    let urlImage2 = "https://source.unsplash.com/random/240x300";
    let urlImage3 = "https://source.unsplash.com/random/400x180";
    let urlImage4 = "https://source.unsplash.com/random/240x180";

    let image1 = new Image();
    let image2 = new Image();
    let image3 = new Image();
    let image4 = new Image();

    image1.src = urlImage1;
    image2.src = urlImage2;
    image3.src = urlImage3;
    image4.src = urlImage4;

    ctx.globalAlpha = 0.5;

    image1.onload = function () {
        ctx.drawImage(image1, 0, 0);
        numberOfImages++;
        if (numberOfImages === 4)
            getQuote();
    };

    image2.onload = function () {
        ctx.drawImage(image2, 400, 0);
        numberOfImages++;
        if (numberOfImages === 4)
            getQuote();
    };

    image3.onload = function () {
        ctx.drawImage(image3, 0, 300);
        numberOfImages++;
        if (numberOfImages === 4)
            getQuote();
    };

    image4.onload = function () {
        ctx.drawImage(image4, 400, 300);
        numberOfImages++;
        if (numberOfImages === 4)
            getQuote();
    };
};

function insertText(quote){
    ctx.font = '28px bold';
    ctx.globalAlpha = 1;
    ctx.fillStyle = 'white';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';

    const words = quote.split(' ');
    let fulltext = [];
    let countWords = words.length;
    let line = '';
    let countlines = 0;

    for (let n = 0; n < countWords; ++n) {
        let testLine = line + words[n] + ' ';
        let testWidth = ctx.measureText(testLine).width;
        if (testWidth > (canvas.width * 0.8)) {
            line = words[n] + ' ';
            countlines++;
        }
        else {
            line = testLine;
        }
        fulltext[countlines] = line;
    }
    let marginTop = (canvas.height - 28 * countlines) / 2 ;
    for (let n = 0; n < fulltext.length; n++){
        ctx.fillText(fulltext[n], canvas.width/2, (marginTop + n * 28));
    }
}

function getQuote() {
    $.ajax({
        url: 'https://api.forismatic.com/api/1.0/?method=getQuote&format=jsonp&lang=en&jsonp=?',
        dataType: 'jsonp',
        timeout: 1000
    })
        .done(function (result) {
            insertText(result.quoteText);
        })
        .fail(function () {
            alert('quote failed to load');
        });
}

function generatePost(){
    numberOfImages = 0;
    loadImages();
}

generatePost();