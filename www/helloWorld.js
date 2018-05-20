var exec = require('cordova/exec');

module.exports.showMessage = function(arg0,succes,error){
    exec(succes, error , 'helloWorld' , 'showMessage',[arg0]);
}
