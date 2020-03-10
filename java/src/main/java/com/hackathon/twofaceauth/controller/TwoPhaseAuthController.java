package com.hackathon.twofaceauth.controller;

import com.cpaassdk.Client;
import com.cpaassdk.resources.Twofactor;
import com.hackathon.twofaceauth.resource.TwoPhaseAuthResource;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/api/v1")
@RestController
public class TwoPhaseAuthController {

    private final Client client;

    public TwoPhaseAuthController(Client client) {
        this.client = client;
    }

    @PostMapping("/2fa/sendCode")
    public ResponseEntity<Twofactor> sendCode(
            @RequestBody @Valid TwoPhaseAuthResource twoPhaseAuthResource) throws IOException {
        JSONObject object = new JSONObject();
        object.put("destinationAddress",twoPhaseAuthResource.getDestinationAddress());
        object.put("method", "sms");
        object.put("message", "Your code is {code}");
        return ResponseEntity.ok(client.twofactor.sendCode(object));
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<Twofactor> verify(
            @RequestBody @Valid TwoPhaseAuthResource twoPhaseAuthResource) throws IOException {
        JSONObject object = new JSONObject();
        object.put("verificationCode",twoPhaseAuthResource.getVerificationCode());
        object.put("codeId", twoPhaseAuthResource.getCodeId());
        return ResponseEntity.ok(client.twofactor.verifyCode(object));
    }

    @PostMapping("/2fa/resendCode")
    public ResponseEntity<Twofactor> resendCode(
            @RequestBody @Valid TwoPhaseAuthResource twoPhaseAuthResource) throws IOException {
        JSONObject object = new JSONObject();
        object.put("destinationAddress",twoPhaseAuthResource.getDestinationAddress());
        object.put("codeId", twoPhaseAuthResource.getCodeId());
        object.put("message", "Your code is {code}");
        return ResponseEntity.ok(client.twofactor.resendCode(object));
    }


    @DeleteMapping("/2fa/deleteCode")
    public ResponseEntity<Twofactor> deleteCode(
            @RequestBody @Valid TwoPhaseAuthResource twoPhaseAuthResource) throws IOException {
        JSONObject object = new JSONObject();
        object.put("codeId", twoPhaseAuthResource.getCodeId());
        return ResponseEntity.ok(client.twofactor.deleteCode(object));
    }
}
