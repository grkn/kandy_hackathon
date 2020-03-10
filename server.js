const express = require('express')
var bodyParser = require("body-parser");
const app = express()
const { createClient } = require('@kandy-io/cpaas-nodejs-sdk')

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
 
const client = createClient({
	clientId: '2350c723-ce0f-4b16-975b-e9c52b93abaf',
	clientSecret: '32ac1a6c-7bb9-4c6f-9a2e-dc0a02fc229b',
	baseUrl: 'https://nvs-cpaas-oauth.kandy.io'
})
 
app.get('/2fa/sendCode', function (req, res) {
	client.twofactor.sendCode({
	  destinationAddress: '+12103992596',
	  method: 'sms',
	  message: 'Your code is {code}',
	}).then(response => {
		console.log("Response : " + response);
		console.log("Response : " + response.code);
		console.log("Response : " + response.codeId);
		res.send(response);
	}).catch(error => {
		console.log("ERROR");
		console.log(error);
		res.send({respone: 'sendCode error'});
	})
});

app.post('/2fa/verify', function (req, res) {
	const codeId = req.body.codeId;
	const verificationCode = req.body.verificationCode;
	const response = client.twofactor.verifyCode({
        codeId: codeId,
        verificationCode: verificationCode
    }).then(response => {
		console.log("Response : " + response);
		res.send(response);
	}).catch(error => {
		console.log("ERROR");
		console.log(error);
		res.send({respone: 'sendCode error'});
	});
});

app.post('/2fa/resendCode', function (req, res) {
	const destinationAddress = req.body.destinationAddress;
	const codeId = req.body.codeId;
	client.twofactor.sendCode({
	  destinationAddress: destinationAddress,
	  codeId: codeId,
	  message: 'Your code is {code}'
	}).then(response => {
		console.log("Response : " + response);
		res.send(response);
	}).catch(error => {
		console.log("ERROR");
		console.log(error);
		res.send({respone: 'sendCode error'});
	})
});

app.delete('/2fa/deleteCode', function (req, res) {
	const codeId = req.body.codeId;
	client.twofactor.sendCode({
	  codeId: codeId,
	  destinationAddress: '+12103992596',
	  message: 'Your code is {code}'
	}).then(response => {
		console.log("Response : " + response);
		res.send(response);
	}).catch(error => {
		console.log("ERROR");
		console.log(error);
		res.send({respone: 'sendCode error'});
	})
});
 
app.listen(3000)